package io.opentelemetry.benchmark.billingapp.service.untraced;

import io.opentelemetry.benchmark.billingapp.model.Invoice;
import io.opentelemetry.benchmark.billingapp.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceUntraced implements NotificationService{
    // @Value()
    //private String msToSleep;

    @Override
    public Boolean notifyCustomer(Invoice invoice) {

        String recipientAddress = invoice.getCustomer().getEmail();

        //mock sending email
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Boolean.TRUE;
    }
}