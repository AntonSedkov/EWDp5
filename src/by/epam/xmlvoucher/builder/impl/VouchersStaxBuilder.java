package by.epam.xmlvoucher.builder.impl;

import by.epam.xmlvoucher.builder.AbstractVouchersBuilder;
import by.epam.xmlvoucher.entity.Voucher;

import javax.xml.stream.XMLInputFactory;
import java.util.Set;

public class VouchersStaxBuilder extends AbstractVouchersBuilder {
    private XMLInputFactory inputFactory;

    public VouchersStaxBuilder() {
// more code
    }

    @Override
    public void buildSetVouchers(String filename) {
        // more code
    }

    public VouchersStaxBuilder(Set<Voucher> vouchers) {
        super(vouchers);
// more code
    }

// private methods

}