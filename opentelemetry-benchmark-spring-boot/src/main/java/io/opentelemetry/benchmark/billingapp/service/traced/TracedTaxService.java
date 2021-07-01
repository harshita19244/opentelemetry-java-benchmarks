package io.opentelemetry.benchmark.billingapp.service.traced;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.benchmark.billingapp.model.Invoice;
import io.opentelemetry.benchmark.billingapp.service.untraced.TaxServiceUntraced;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tracedTaxService")
public class TracedTaxService extends TaxServiceUntraced {

    @Autowired
    public Tracer tracer;

    @Override
    public Invoice computeTaxes(Invoice invoice) {
        Span span = tracer.spanBuilder("computeTaxes").startSpan();
        Invoice computedInvoice =  super.computeTaxes(invoice);
        //String taxId = span.getBaggageItem("taxId");
        span.setAttribute("currency", computedInvoice.getCurrency().toString());
        span.addEvent("computeTaxes");
        span.setAttribute("total", computedInvoice.getAmountDue().doubleValue());
        span.setAttribute("customer taxId", "66");
        return computedInvoice;
        
    }
}