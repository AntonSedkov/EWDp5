package by.epam.xmlvoucher.builder;

import by.epam.xmlvoucher.builder.impl.VoucherEventStaxBuilder;
import by.epam.xmlvoucher.builder.impl.VouchersDomBuilder;
import by.epam.xmlvoucher.builder.impl.VouchersSaxBuilder;
import by.epam.xmlvoucher.builder.impl.VouchersStreamStaxBuilder;
import by.epam.xmlvoucher.exception.XMLVoucherException;

public class VoucherBuilderFactory {

    private enum TypeParser {
        SAX, DOM, EVENT_STAX, STREAM_STAX
    }

    private VoucherBuilderFactory() {
    }

    public static AbstractVouchersBuilder createVoucherBuilder(String typeParser) throws XMLVoucherException {
        boolean flag = false;
        for (TypeParser type : TypeParser.values()) {
            if (type.name().equals(typeParser.toUpperCase())) {
                flag = true;
                break;
            }
        }
        if (flag) {
            TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
            switch (type) {
                case SAX -> {
                    return new VouchersSaxBuilder();
                }
                case DOM -> {
                    return new VouchersDomBuilder();
                }
                case STREAM_STAX -> {
                    return new VouchersStreamStaxBuilder();
                }
                case EVENT_STAX -> {
                    return new VoucherEventStaxBuilder();
                }
                default -> throw new EnumConstantNotPresentException(
                        type.getDeclaringClass(), type.name());
            }
        } else {
            throw new XMLVoucherException("No such XML parsing, wrong type");
        }
    }

}