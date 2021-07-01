package io.opentelemetry.benchmark;

import java.math.BigDecimal;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import io.opentelemetry.benchmark.billingapp.BillingApplication;
import io.opentelemetry.benchmark.billingapp.model.*;
import io.opentelemetry.benchmark.billingapp.persistence.Invoicerepo;
import io.opentelemetry.benchmark.billingapp.service.InvoiceService;
import io.opentelemetry.benchmark.billingapp.service.traced.InvoiceServiceTraced;

public class BillingApplicationBase {

    public static final String TRACER = "tracer";

    @State(Scope.Thread)
    public static class StateVariables {
        ConfigurableApplicationContext c;

        //Spring bean used in doTearDown method
        Invoicerepo repository;

        //Spring bean used in benchmark method
        InvoiceService invoiceService;
        InvoiceServiceTraced invoiceTracedService;

        //Data objects
        Invoice invoice;
        LineItem item;

        private int accountNumber = 0;

        @Setup(Level.Invocation)
        public void loadInvoice(){
            Customer customer = new Customer("Jane" , "Doe",
                    "janedoe@gmail.com", "+16044444444",
                    "800123123");
            Invoice i = new Invoice();

            i.setAccountNumber(accountNumber++);
            i.setCustomer(customer);
            i.setCurrency(Currency.USD);
            invoice = i;

            LineItem it = new LineItem();

            it.setItemDescription("New item");
            it.setRate(new BigDecimal(10.5));
            it.setQuantity(3);
            item = it;

        }

        @TearDown(Level.Iteration)
        public void doTearDown() {
            repository.reset();
            c.close();
        }
    }

    // public static class StateVariablesNoInstrumentation extends StateVariables {
    //     @Setup(Level.Iteration)
    //     public void doSetup() {

    //         System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "NO_INSTRUMENTATION");

    //         c = SpringApplication.run(BillingApplication.class);
    //         invoiceService =  c.getBean("invoiceServiceImpl", InvoiceService.class);
    //         repository = c.getBean(Invoicerepo.class);
    //     }
    // }

    public static class StateVariablesJaeger extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "JAEGERTRACER");

            c = SpringApplication.run(BillingApplication.class);
            invoiceTracedService =  c.getBean(InvoiceServiceTraced.class);
            repository = c.getBean(Invoicerepo.class);
        }
    }

    public static class StateVariablesOtlp extends StateVariables {
        @Setup(Level.Iteration)
        public void doSetup() {

            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "OTLPTRACER");

            c = SpringApplication.run(BillingApplication.class);
            invoiceTracedService =  c.getBean(InvoiceServiceTraced.class);
            repository = c.getBean(Invoicerepo.class);
        }
    }
    

    // public Invoice doBenchmarkBillingNoInstrumentation(StateVariables state) {
    //     //Create invoice
    //     return issueInvoice(state, state.invoiceService);
    // }

    public Invoice doBenchmarkBillingJaegerTracer(StateVariablesJaeger state) {
        //Create invoice
        return issueInvoice(state, state.invoiceTracedService);
    }

    public Invoice doBenchmarkBillingOtlpTracer(StateVariablesOtlp state) {
        //Create invoice
        return issueInvoice(state, state.invoiceTracedService);
    }


    private Invoice issueInvoice(StateVariables state, InvoiceService invoiceService) {
        //Create invoice
        Long invoiceNumber = invoiceService.createInvoice(state.invoice);

        //Add item / items
        LineItem item = state.item;
        invoiceService.addLineItem(invoiceNumber, item);

        // Issue invoice
        Invoice invoice = invoiceService.getInvoice(invoiceNumber);
        return invoiceService.issueInvoice(invoiceNumber);
    }
    
}
