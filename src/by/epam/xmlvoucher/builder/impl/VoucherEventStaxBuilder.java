package by.epam.xmlvoucher.builder.impl;

import by.epam.xmlvoucher.builder.AbstractVouchersBuilder;
import by.epam.xmlvoucher.entity.Excursion;
import by.epam.xmlvoucher.entity.Relaxation;
import by.epam.xmlvoucher.entity.Voucher;
import by.epam.xmlvoucher.handler.VoucherXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;

public class VoucherEventStaxBuilder extends AbstractVouchersBuilder {
    private static final Logger logger = LogManager.getLogger(VoucherEventStaxBuilder.class);
    private XMLInputFactory inputFactory;

    public VoucherEventStaxBuilder() {
        super();
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetVouchers(String filename) {
        Voucher voucher = null;
        try {
            XMLEventReader reader = inputFactory.createXMLEventReader(new FileInputStream(filename));
            logger.info("Event StAX has been configured.");
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
                reader.close();                                                               // TODO: 03.10.2020 DO NEED CLOSE?
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