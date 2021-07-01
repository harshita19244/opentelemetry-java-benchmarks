package io.opentelemetry.benchmark.billingapp.service;

import io.opentelemetry.benchmark.billingapp.model.*;
import java.util.List;
public interface InvoiceService {
    
    Long createInvoice(Invoice invoice);
    Invoice getInvoice(Long invoiceNumber);
    void addLineItem(Long invoiceNumber, LineItem item);
    void addLineItems(Long invoiceNumber, List<LineItem> items);
    Invoice issueInvoice(Long invoiceNumber);
}
