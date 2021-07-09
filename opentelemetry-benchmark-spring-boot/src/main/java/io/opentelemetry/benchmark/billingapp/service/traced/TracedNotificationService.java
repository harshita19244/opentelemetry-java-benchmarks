package io.opentelemetry.benchmark.billingapp.service.traced;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.benchmark.billingapp.model.Invoice;
import io.opentelemetry.benchmark.billingapp.service.untraced.NotificationServiceUntraced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TracedNotificationService extends NotificationServiceUntraced {
    @Autowired
    public Tracer tracer;

    @Override
    public Boolean notifyCustomer(Invoice invoice) {
        
        Span span = tracer.spanBuilder("notifyCustomer").startSpan();
        String recipientAddress = invoice.getCustomer().getEmail();
        //String taxId = span.getSpanContext().
        span.setAttribute("address", recipientAddress);
        span.setAttribute("customer taxId", "2h" );
        return super.notifyCustomer(invoice);
        
    }
}
