package by.epam.xmlvoucher.builder;

import by.epam.xmlvoucher.entity.Voucher;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractVouchersBuilder {
    protected Set<Voucher> vouchers;

    public AbstractVouchersBuilder() {
        vouchers = new HashSet<>();
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public abstract void buildSetVouchers(String filename);

}