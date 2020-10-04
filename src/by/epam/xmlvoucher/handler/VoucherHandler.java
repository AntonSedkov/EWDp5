package by.epam.xmlvoucher.handler;

import by.epam.xmlvoucher.entity.Excursion;
import by.epam.xmlvoucher.entity.Relaxation;
import by.epam.xmlvoucher.entity.Voucher;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class VoucherHandler extends DefaultHandler {
    private Set<Voucher> vouchers;
    private Voucher currentVoucher;
    private VoucherXmlTag currentXmlTag;
    private EnumSet<VoucherXmlTag> elementTagsSet;
    private static final String ELEMENT_EXCURSION = VoucherXmlTag.EXCURSION.getValue();
    private static final String ELEMENT_RELAXATION = VoucherXmlTag.RELAXATION.getValue();

    public VoucherHandler() {
        vouchers = new HashSet<>();
        elementTagsSet = EnumSet.range(VoucherXmlTag.DAYS, VoucherXmlTag.DATE_START);
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (ELEMENT_EXCURSION.equals(qName)) {
            currentVoucher = new Excursion();
            elementTagsSet = EnumSet.range(VoucherXmlTag.DAYS, VoucherXmlTag.MOVING);
            currentVoucher.setIdentifier(attrs.getValue(VoucherXmlTag.IDENTIFIER.getValue()));
            currentVoucher.setRestPlace(attrs.getValue(VoucherXmlTag.REST_PLACE.getValue()));
            currentVoucher.setCost(Integer.parseInt(attrs.getValue(VoucherXmlTag.COST.getValue())));
            if (attrs.getLength() > 3) {
                if (attrs.getIndex(VoucherXmlTag.TRANSPORT.getValue()) > -1) {
                    currentVoucher.setTransport(attrs.getValue(VoucherXmlTag.TRANSPORT.getValue()));
                }
                if (attrs.getIndex(VoucherXmlTag.EXCURSION_TYPE.getValue()) > -1) {
                    ((Excursion) currentVoucher).setExcursionType(attrs.getValue(VoucherXmlTag.EXCURSION_TYPE.getValue()));
                }
            }
        } else if (ELEMENT_RELAXATION.equals(qName)) {
            currentVoucher = new Relaxation();
            elementTagsSet = EnumSet.range(VoucherXmlTag.DAYS, VoucherXmlTag.RELAX_PROCEDURES);
            currentVoucher.setIdentifier(attrs.getValue(VoucherXmlTag.IDENTIFIER.getValue()));
            currentVoucher.setRestPlace(attrs.getValue(VoucherXmlTag.REST_PLACE.getValue()));
            currentVoucher.setCost(Integer.parseInt(attrs.getValue(VoucherXmlTag.COST.getValue())));
            if (attrs.getLength() > 3) {
                currentVoucher.setTransport(attrs.getValue(VoucherXmlTag.TRANSPORT.getValue()));
            }
        } else {
            VoucherXmlTag temp = VoucherXmlTag.valueOf(qName.replace('-', '_').toUpperCase());
            if (elementTagsSet.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (ELEMENT_EXCURSION.equals(qName) || ELEMENT_RELAXATION.equals(qName)) {
            vouchers.add(currentVoucher);
        }
    }

    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case DAYS -> currentVoucher.setDays(Integer.parseInt(data));
                case HOTEL -> currentVoucher.getHotel();
                case STARS -> currentVoucher.getHotel().setStars(Integer.parseInt(data));
                case MEAT -> currentVoucher.getHotel().setMeat(data);
                case ROOM -> currentVoucher.getHotel().setRoomSize(data);
                case AIR_CONDITIONING -> currentVoucher.getHotel().setAirConditioning(Boolean.parseBoolean(data));
                case DATE_START -> currentVoucher.setDate(LocalDate.parse(data));
                case MOVING -> ((Excursion) currentVoucher).setMoving(Integer.parseInt(data));
                case RELAX_PROCEDURES -> ((Relaxation) currentVoucher).addRelaxProcedure(data);
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }

}