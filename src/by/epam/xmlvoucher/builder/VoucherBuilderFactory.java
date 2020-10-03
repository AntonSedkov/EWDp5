package by.epam.xmlvoucher.builder;

import by.epam.xmlvoucher.builder.impl.VouchersDomBuilder;
import by.epam.xmlvoucher.builder.impl.VouchersSaxBuilder;
import by.epam.xmlvoucher.builder.impl.VouchersStaxBuilder;

public class VoucherBuilderFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }

    private VoucherBuilderFactory() {
    }

    public static AbstractVouchersBuilder createVoucherBuilder(String typeParser) {
// insert parser name validation
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM -> {
                return new VouchersDomBuilder();
            }
            case STAX -> {
                return new VouchersStaxBuilder();
            }
            case SAX -> {
                return new VouchersSaxBuilder();
            }
            default -> throw new EnumConstantNotPresentException(
                    type.getDeclaringClass(), type.name());
        }
    }

}