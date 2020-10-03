package by.epam.xmlvoucher.parser;

import by.epam.xmlvoucher.entity.Excursion;
import by.epam.xmlvoucher.entity.Relaxation;
import by.epam.xmlvoucher.entity.Voucher;
import by.epam.xmlvoucher.handler.VoucherXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class VouchersStaxBuilder {
    private static final Logger logger = LogManager.getLogger(VouchersStaxBuilder.class);
    private Set<Voucher> vouchers;
    private XMLInputFactory inputFactory;

    public VouchersStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
        vouchers = new HashSet<>();
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void buildSetVouchers(String filename) {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(filename))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            logger.info("StAX has been configured.");
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
            reader.close();                                                             // TODO: 03.10.2020
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
                    switch (VoucherXmlTag.valueOf(tagValue.replace('-', '_').toUpperCase())) {
                        case DAYS -> excursion.setDays(Integer.parseInt(getXMLStringData(reader)));
                        case HOTEL -> excursion.setHotel(getXMLHotel(reader));
                        case DATE_START -> excursion.setDate(LocalDate.parse(getXMLStringData(reader)));
                        case MOVING -> excursion.setMoving(Integer.parseInt(getXMLStringData(reader)));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    tagValue = reader.getLocalName();
                    if (VoucherXmlTag.valueOf(tagValue.replace('-', '_').toUpperCase()) == VoucherXmlTag.EXCURSION) {
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
                    switch (VoucherXmlTag.valueOf(tagValue.replace('-', '_').toUpperCase())) {
                        case DAYS -> relaxation.setDays(Integer.parseInt(getXMLStringData(reader)));
                        case HOTEL -> relaxation.setHotel(getXMLHotel(reader));
                        case DATE_START -> relaxation.setDate(LocalDate.parse(getXMLStringData(reader)));
                        case RELAX_PROCEDURES -> relaxation.addRelaxProcedure(getXMLStringData(reader));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    tagValue = reader.getLocalName();
                    if (VoucherXmlTag.valueOf(tagValue.replace('-', '_').toUpperCase()) == VoucherXmlTag.RELAXATION) {
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
                    switch (VoucherXmlTag.valueOf(tagValue.replace('-', '_').toUpperCase())) {
                        case STARS -> hotel.setStars(Integer.parseInt(getXMLStringData(reader)));
                        case MEAT -> hotel.setMeat(getXMLStringData(reader));
                        case ROOM -> hotel.setRoomSize(getXMLStringData(reader));
                        case AIR_CONDITIONING -> hotel.setAirConditioning(Boolean.parseBoolean(getXMLStringData(reader)));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    tagValue = reader.getLocalName();
                    if (VoucherXmlTag.valueOf(tagValue.replace('-', '_').toUpperCase()) == VoucherXmlTag.HOTEL) {
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

    //2nd API set: "iterator API" by EventReader (previous was "cursor API" by StreamReader)
    public void buildSetVouchersByEvent(String fileName) {
        Voucher voucher = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = inputFactory.createXMLEventReader(new FileInputStream(fileName));
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(VoucherXmlTag.EXCURSION.getValue())) {
                        voucher = new Excursion();
                        setAttributes(startElement, voucher);
                        Attribute excursionType = startElement.getAttributeByName(new QName(VoucherXmlTag.EXCURSION_TYPE.getValue()));
                        if (excursionType != null) {
                            ((Excursion) voucher).setExcursionType(excursionType.getValue());
                        }
                    } else if (startElement.getName().getLocalPart().equals(VoucherXmlTag.RELAXATION.getValue())) {
                        voucher = new Relaxation();
                        setAttributes(startElement, voucher);
                    } else if (startElement.getName().getLocalPart().equals(VoucherXmlTag.DAYS.getValue())) {
                        event = reader.nextEvent();
                        voucher.setDays(Integer.parseInt(event.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals(VoucherXmlTag.DATE_START.getValue())) {
                        event = reader.nextEvent();
                        voucher.setDate(LocalDate.parse(event.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals(VoucherXmlTag.MOVING.getValue())) {
                        event = reader.nextEvent();
                        ((Excursion) voucher).setMoving(Integer.parseInt(event.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals(VoucherXmlTag.RELAX_PROCEDURES.getValue())) {
                        event = reader.nextEvent();
                        ((Relaxation) voucher).addRelaxProcedure(event.asCharacters().getData());
                    } else if (event.isStartElement()) {
                        StartElement startElementHotel = event.asStartElement();
                        Voucher.Hotel hotel = null;
                        if (voucher != null) {
                            hotel = voucher.getHotel();
                        }
                        if (startElementHotel.getName().getLocalPart().equals(VoucherXmlTag.STARS.getValue())) {
                            event = reader.nextEvent();
                            hotel.setStars(Integer.parseInt(event.asCharacters().getData()));
                        } else if (startElementHotel.getName().getLocalPart().equals(VoucherXmlTag.MEAT.getValue())) {
                            event = reader.nextEvent();
                            hotel.setMeat(event.asCharacters().getData());
                        } else if (startElementHotel.getName().getLocalPart().equals(VoucherXmlTag.ROOM.getValue())) {
                            event = reader.nextEvent();
                            hotel.setRoomSize(event.asCharacters().getData());
                        } else if (startElementHotel.getName().getLocalPart().equals(VoucherXmlTag.AIR_CONDITIONING.getValue())) {
                            event = reader.nextEvent();
                            hotel.setAirConditioning(Boolean.parseBoolean(event.asCharacters().getData()));
                        }
                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(VoucherXmlTag.EXCURSION.getValue())
                            || endElement.getName().getLocalPart().equals(VoucherXmlTag.RELAXATION.getValue())) {
                        vouchers.add(voucher);
                    }
                }
                reader.close();                                                 // TODO: 03.10.2020
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            logger.error(e);
        }
    }

    private void setAttributes(StartElement element, Voucher voucher) {
        Attribute id = element.getAttributeByName(new QName(VoucherXmlTag.IDENTIFIER.getValue()));
        voucher.setIdentifier(id.getValue());
        Attribute place = element.getAttributeByName(new QName(VoucherXmlTag.REST_PLACE.getValue()));
        voucher.setRestPlace(place.getValue());
        Attribute cost = element.getAttributeByName(new QName(VoucherXmlTag.COST.getValue()));
        voucher.setCost(Integer.parseInt(cost.getValue()));
        Attribute transport = element.getAttributeByName(new QName(VoucherXmlTag.TRANSPORT.getValue()));
        if (transport != null) {
            voucher.setTransport(transport.getValue());
        }
    }

}