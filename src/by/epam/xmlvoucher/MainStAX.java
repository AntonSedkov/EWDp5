package by.epam.xmlvoucher;

import by.epam.xmlvoucher.parser.VouchersStaxBuilder;

public class MainStAX {

    public static void main(String[] args) {
        VouchersStaxBuilder staxBuilder = new VouchersStaxBuilder();
        staxBuilder.buildSetVouchers("data/voucher.xml");
        System.out.println(staxBuilder.getVouchers());
        System.out.println(staxBuilder.getVouchers().size());

        VouchersStaxBuilder staxBuilder2 = new VouchersStaxBuilder();
        staxBuilder2.buildSetVouchersByEvent("data/voucher.xml");
        System.out.println(staxBuilder2.getVouchers());
        System.out.println(staxBuilder2.getVouchers().size());
    }

}