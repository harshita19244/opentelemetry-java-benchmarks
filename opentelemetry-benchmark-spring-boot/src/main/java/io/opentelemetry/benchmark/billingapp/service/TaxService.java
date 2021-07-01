package io.opentelemetry.benchmark.billingapp.service;

import io.opentelemetry.benchmark.billingapp.model.Invoice;

public interface TaxService {
    Invoice computeTaxes(Invoice invoice);
}
