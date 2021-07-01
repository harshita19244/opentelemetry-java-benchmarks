package io.opentelemetry.benchmark.billingapp.service.untraced;

import io.opentelemetry.benchmark.billingapp.model.Invoice;
import io.opentelemetry.benchmark.billingapp.model.InvoiceState;
import io.opentelemetry.benchmark.billingapp.model.LineItem;
import io.opentelemetry.benchmark.billingapp.persistence.Invoicerepo;
import io.opentelemetry.benchmark.billingapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service("invoiceServiceUntraced")
public class InvoiceServiceUntraced implements InvoiceService {
    @Autowired
    private Invoicerepo repository;

    @Autowired
    @Qualifier("notificationServiceUntraced")
    private NotificationService notifications;

    @Autowired
    @Qualifier("taxServiceUntraced")
    private TaxServiceUntraced taxes;

    Long invoiceNumber;

    @Override
    public Long createInvoice(Invoice invoice) {
            invoice.setInvoiceDate(LocalDateTime.now());
            invoice.setState(InvoiceState.DRAFT);

            invoice.setInvoiceNumber(generateInvoiceNumber());
            repository.persistInvoice(invoice);

            return invoice.getInvoiceNumber();
    }

    @Override
    public Invoice getInvoice(Long invoiceNumber) {
        return repository.getInvoice(invoiceNumber);
    }

    @Override
    public void addLineItem(Long invoiceNumber, LineItem item) {
        Invoice invoice = repository.getInvoice(invoiceNumber);

        item.setInvoiceNumber(invoiceNumber);
        //calculate total
        item.setTotal(item.getRate().multiply(new BigDecimal(
                item.getQuantity())));

        invoice.addLineItem(item);

        repository.persistInvoice(invoice);
    }

    @Override
    public void addLineItems(Long invoiceNumber, List<LineItem> items) {
        Invoice invoice = repository.getInvoice(invoiceNumber);

        //calculate total
        items.forEach((item) -> {
            item.setInvoiceNumber(invoiceNumber);
            item.setTotal(item.getRate().multiply(
                    new BigDecimal(item.getQuantity())));
        });

        invoice.addLineItems(items);

        repository.persistInvoice(invoice);
    }

    @Override
    public Invoice issueInvoice(Long invoiceNumber) {
        //Retrieve invoice
        Invoice invoice = repository.getInvoice(invoiceNumber);

        //compute taxes
        Invoice taxedInvoice = getTaxesService().computeTaxes(invoice);

        //notify to customer
        Boolean notified = getNotificationService().notifyCustomer(taxedInvoice);
        if (notified) {
            taxedInvoice.setNotified(Boolean.TRUE);
        }

        taxedInvoice.setState(InvoiceState.ISSUED);
        taxedInvoice.setInvoiceDate(LocalDateTime.now());
        taxedInvoice.setDueDate(LocalDate.now().plusDays(30));

        repository.persistInvoice(taxedInvoice);

        return taxedInvoice;
    }

    private Long generateInvoiceNumber() {
        return invoiceNumber++;
    }

    public NotificationService getNotificationService(){
        return notifications;
    };

    public TaxService getTaxesService() {
        return taxes;
    }
}
