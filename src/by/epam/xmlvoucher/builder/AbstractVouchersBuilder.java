package by.epam.xmlvoucher.builder;

import by.epam.xmlvoucher.entity.Voucher;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractVouchersBuilder {
    // protected - it is often accessed from a subclass
    protected Set<Voucher> vouchers;

    public AbstractVouchersBuilder() {
        vouchers = new HashSet<Voucher>();
    }

    public AbstractVouchersBuilder(Set<? extends Voucher> vouchers) {
        this.vouchers = (Set<Voucher>) vouchers;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public abstract void buildSetVouchers(String filename);
}