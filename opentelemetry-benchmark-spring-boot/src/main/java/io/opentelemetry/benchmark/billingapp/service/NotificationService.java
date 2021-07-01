package io.opentelemetry.benchmark.billingapp.service;

import io.opentelemetry.benchmark.billingapp.model.Invoice;
import io.opentelemetry.benchmark.billingapp.service.NotificationService;

public interface NotificationService {
    Boolean notifyCustomer(Invoice invoice);
 }

