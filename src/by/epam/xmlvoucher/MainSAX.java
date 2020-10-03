package by.epam.xmlvoucher;

import by.epam.xmlvoucher.parser.VouchersSaxBuilder;

public class MainSAX {

    public static void main(String[] args) {
        VouchersSaxBuilder saxBuilder = new VouchersSaxBuilder();
        saxBuilder.buildSetVouchers("data/voucher.xml");
        System.out.println(saxBuilder.getVouchers());
        System.out.println(saxBuilder.getVouchers().size());
    }

}