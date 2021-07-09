package io.opentelemetry.benchmark.billingapp.service.traced;

import io.opentelemetry.benchmark.billingapp.service.untraced.InvoiceServiceUntraced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.stereotype.Component;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.benchmark.billingapp.model.Invoice;
import io.opentelemetry.benchmark.billingapp.service.TaxService;

@Component
public class InvoiceServiceTraced extends InvoiceServiceUntraced{
    @Autowired
    private TracedNotificationService notificationService;

    @Autowired
    private TracedTaxService taxService;

    @Autowired
    public Tracer tracer;

    @Override
    public Long createInvoice(Invoice invoice) {
        
        Span span = tracer.spanBuilder("InvoiceBuilder").startSpan();
        span.addEvent("createInvoice");
        span.setAttribute("customer",invoice.getCustomer().getEmail());
        span.setAttribute("taxId", invoice.getCustomer().getTaxId());
        return super.createInvoice(invoice);
        
    }

    @Override
    public TracedNotificationService getNotificationService() {
        return notificationService;
    }

    @Override
    public TaxService getTaxesService() {
        return taxService;
    }
}
