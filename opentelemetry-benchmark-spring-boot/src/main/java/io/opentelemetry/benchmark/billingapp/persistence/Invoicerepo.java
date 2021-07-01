package io.opentelemetry.benchmark.billingapp.persistence;

import io.opentelemetry.benchmark.billingapp.model.*;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class Invoicerepo {
    
    private static final Random random =  new Random();

    public void persistInvoice(Invoice invoice) {
    }

    public Invoice getInvoice(Long invoiceNumber) {
        Customer customer = new Customer("John" , "Doe",
                "jdoe@gmail.com", "+16044444444",
                "800123123");
        Invoice i = new Invoice();

        i.setAccountNumber(random.nextInt());
        i.setCustomer(customer);
        i.setCurrency(Currency.INR);
        return i;
    }

    public void reset(){
    }
}
