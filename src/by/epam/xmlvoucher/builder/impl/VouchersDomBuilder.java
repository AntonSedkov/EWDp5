package by.epam.xmlvoucher.builder.impl;

import by.epam.xmlvoucher.builder.AbstractVouchersBuilder;
import by.epam.xmlvoucher.entity.Voucher;

import javax.xml.parsers.DocumentBuilder;
import java.util.Set;

public class VouchersDomBuilder extends AbstractVouchersBuilder {
    private DocumentBuilder docBuilder;

    public VouchersDomBuilder() {
// more code
    }

    @Override
    public void buildSetVouchers(String filename) {
        // more code
    }

    public VouchersDomBuilder(Set<Voucher> vouchers) {
        super(vouchers);
// more code
    }

// private methods
}
