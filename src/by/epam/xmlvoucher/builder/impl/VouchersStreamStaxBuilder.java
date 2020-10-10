package by.epam.xmlvoucher.builder.impl;

import by.epam.xmlvoucher.builder.AbstractVouchersBuilder;
import by.epam.xmlvoucher.entity.Excursion;
import by.epam.xmlvoucher.entity.Relaxation;
import by.epam.xmlvoucher.entity.Voucher;
import by.epam.xmlvoucher.handler.VoucherXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

public class VouchersStreamStaxBuilder extends AbstractVouchersBuilder {
    private static final Logger logger = LogManager.getLogger(VouchersStreamStaxBuilder.class);
    private final XMLInputFactory inputFactory;

    public VouchersStreamStaxBuilder() {
        super();
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetVouchers(String filename) {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(filename))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            logger.info("Stream StAX has been configured.");
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(VoucherXmlTag.EXCURSION.getValue())) {
                        Excursion excursion = buildExcursion(reader);
                        vouchers.add(excursion);
                    }
                    if (name.equals(VoucherXmlTag.RELAXATION.getValue())) {
                        Relaxation relaxation = buildRelaxation(reader);
                        vouchers.add(relaxation);
                    }
                }
            }
            reader.close();
        } catch (XMLStreamException | IOException e) {
            logger.error(e);
        }
    }

    private Excursion buildExcursion(XMLStreamReader reader) throws XMLStreamException {
        Excursion excursion = new Excursion();
        String nullNamespace = null;
        excursion.setIdentifier(reader.getAttributeValue(nullNamespace, VoucherXmlTag.IDENTIFIER.getValue()));
        excursion.setRestPlace(reader.getAttributeValue(nullNamespace, VoucherXmlTag.REST_PLACE.getValue()));
        excursion.setCost(Integer.parseInt(reader.getAttributeValue(nullNamespace, VoucherXmlTag.COST.getValue())));
        String value = reader.getAttributeValue(nullNamespace, VoucherXmlTag.TRANSPORT.getValue());
        if (value != null) {
            excursion.setTransport(value);
        }
        value = reader.getAttributeValue(nullNamespace, VoucherXmlTag.EXCURSION_TYPE.getValue());
        if (value != null) {
            excursion.setExcursionType(value);
        }
        String tagValue;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    tagValue = reader.getLocalName();
                    switch (returnCurrentTag(tagValue)) {
                        case DAYS -> excursion.setDays(Integer.parseInt(getXMLStringData(reader)));
                        case HOTEL -> excursion.setHotel(getXMLHotel(reader));
                        case DATE_START -> excursion.setDate(LocalDate.parse(getXMLStringData(reader)));
                        case MOVING -> excursion.setMoving(Integer.parseInt(getXMLStringData(reader)));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    tagValue = reader.getLocalName();
                    if (returnCurrentTag(tagValue) == VoucherXmlTag.EXCURSION) {
                        return excursion;
                    }
                }
            }
        }
        throw new XMLStreamException("Wrong element in tag <excursion>");
    }

    private Relaxation buildRelaxation(XMLStreamReader reader) throws XMLStreamException {
        Relaxation relaxation = new Relaxation();
        String nullNamespace = null;
        relaxation.setIdentifier(reader.getAttributeValue(nullNamespace, VoucherXmlTag.IDENTIFIER.getValue()));
        relaxation.setRestPlace(reader.getAttributeValue(nullNamespace, VoucherXmlTag.REST_PLACE.getValue()));
        relaxation.setCost(Integer.parseInt(reader.getAttributeValue(nullNamespace, VoucherXmlTag.COST.getValue())));
        if (reader.getAttributeValue(nullNamespace, VoucherXmlTag.TRANSPORT.getValue()) != null) {
            relaxation.setTransport(reader.getAttributeValue(nullNamespace, VoucherXmlTag.TRANSPORT.getValue()));
        }
        String tagValue;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    tagValue = reader.getLocalName();
                    switch (returnCurrentTag(tagValue)) {
                        case DAYS -> relaxation.setDays(Integer.parseInt(getXMLStringData(reader)));
                        case HOTEL -> relaxation.setHotel(getXMLHotel(reader));
                        case DATE_START -> relaxation.setDate(LocalDate.parse(getXMLStringData(reader)));
                        case RELAX_PROCEDURES -> relaxation.addRelaxProcedure(getXMLStringData(reader));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    tagValue = reader.getLocalName();
                    if (returnCurrentTag(tagValue) == VoucherXmlTag.RELAXATION) {
                        return relaxation;
                    }
                }
            }
        }
        throw new XMLStreamException("Wrong element in tag <relaxation>");
    }

    private Voucher.Hotel getXMLHotel(XMLStreamReader reader) throws XMLStreamException {
        Voucher.Hotel hotel = new Excursion().getHotel();
        int type;
        String tagValue;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    tagValue = reader.getLocalName();
                    switch (returnCurrentTag(tagValue)) {
                        case STARS -> hotel.setStars(Integer.parseInt(getXMLStringData(reader)));
                        case MEAT -> hotel.setMeat(getXMLStringData(reader));
                        case ROOM -> hotel.setRoomSize(getXMLStringData(reader));
                        case AIR_CONDITIONING -> hotel.setAirConditioning(Boolean.parseBoolean(getXMLStringData(reader)));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    tagValue = reader.getLocalName();
                    if (returnCurrentTag(tagValue) == VoucherXmlTag.HOTEL) {
                        return hotel;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag <hotel>");
    }

    private String getXMLStringData(XMLStreamReader reader) throws XMLStreamException {
        String value = null;
        if (reader.hasNext()) {
            reader.next();
            value = reader.getText();
        }
        return value;
    }

    private VoucherXmlTag returnCurrentTag(String tagValue) {
        return VoucherXmlTag.valueOf(tagValue.replace('-', '_').toUpperCase());
    }

}