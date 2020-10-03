package by.epam.xmlvoucher;

import by.epam.xmlvoucher.parser.VouchersDomBuilder;

public class MainDOM {

    public static void main(String[] args) {

        VouchersDomBuilder domBuilder = new VouchersDomBuilder();
        domBuilder.buildSetVouchers("data/voucher.xml");
        System.out.println(domBuilder.getVouchers());
        System.out.println(domBuilder.getVouchers().size());
    }

}