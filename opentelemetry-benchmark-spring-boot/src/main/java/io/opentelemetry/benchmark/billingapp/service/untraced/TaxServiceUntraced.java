package io.opentelemetry.benchmark.billingapp.service.untraced;

import io.opentelemetry.benchmark.billingapp.model.*;
import io.opentelemetry.benchmark.billingapp.service.TaxService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaxServiceUntraced implements TaxService {
    private static final BigDecimal TAX_RATE_INR = new BigDecimal(0.3);
    private static final BigDecimal TAX_RATE_USD = new BigDecimal(0.4);

    private static Map<Currency, BigDecimal> TAX_RATES = new HashMap<>();

    static {
        TAX_RATES.put(Currency.INR, TAX_RATE_INR);
        TAX_RATES.put(Currency.USD, TAX_RATE_USD);
    }

    @Override
    public Invoice computeTaxes(Invoice invoice) {
        //total
        List<LineItem> items = invoice.getLineItems();
        BigDecimal total = BigDecimal.ZERO;
        for (LineItem item : items) {
            BigDecimal t = BigDecimal.ZERO;
            t = t.add(item.getTotal());
            total = t;
        }

        BigDecimal rate = TAX_RATES.get(invoice.getCurrency());
        BigDecimal taxes = total.multiply(rate);
        total = total.add(taxes);

        invoice.setAmountDue(total);
        invoice.setState(InvoiceState.TAXED);

        return invoice;
    }
}