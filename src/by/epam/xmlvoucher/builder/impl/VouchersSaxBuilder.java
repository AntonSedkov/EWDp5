package by.epam.xmlvoucher.builder.impl;

import by.epam.xmlvoucher.builder.AbstractVouchersBuilder;
import by.epam.xmlvoucher.entity.Voucher;
import by.epam.xmlvoucher.handler.VoucherHandler;
import org.xml.sax.XMLReader;

import java.util.Set;

public class VouchersSaxBuilder extends AbstractVouchersBuilder {
    private VoucherHandler handler;
    private XMLReader reader;

    public VouchersSaxBuilder() { // more code
    }

    @Override
    public void buildSetVouchers(String filename) {
        // more code
    }

    public VouchersSaxBuilder(Set<Voucher> vouchers) {
        super(vouchers);
// more code
    }
// private methods
}