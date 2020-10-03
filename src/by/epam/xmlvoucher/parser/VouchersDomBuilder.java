package by.epam.xmlvoucher.parser;

import by.epam.xmlvoucher.entity.Excursion;
import by.epam.xmlvoucher.entity.Relaxation;
import by.epam.xmlvoucher.entity.Voucher;
import by.epam.xmlvoucher.handler.VoucherXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class VouchersDomBuilder {
    private static final Logger logger = LogManager.getLogger(VouchersDomBuilder.class);
    private Set<Voucher> vouchers;
    private DocumentBuilder docBuilder;

    public VouchersDomBuilder() {
        vouchers = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
            logger.info("DOM has been configured.");
        } catch (ParserConfigurationException e) {
            logger.error(e);
        }
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void buildSetVouchers(String filename) {
        Document doc;
        try {
            doc = docBuilder.parse(filename);
            Element root = doc.getDocumentElement();
            NodeList excursionList = root.getElementsByTagName(VoucherXmlTag.EXCURSION.getValue());
            for (int i = 0; i < excursionList.getLength(); i++) {
                Element excursionElement = (Element) excursionList.item(i);
                Excursion excursion = buildExcursion(excursionElement);
                vouchers.add(excursion);
            }
            NodeList relaxationList = root.getElementsByTagName(VoucherXmlTag.RELAXATION.getValue());
            for (int i = 0; i < relaxationList.getLength(); i++) {
                Element relaxationElement = (Element) relaxationList.item(i);
                Relaxation relaxation = buildRelaxation(relaxationElement);
                vouchers.add(relaxation);
            }
        } catch (IOException | SAXException e) {
            logger.error(e);
        }
    }

    private Excursion buildExcursion(Element excursionElement) {
        Excursion excursion = new Excursion();
        if (excursionElement != null) {
            excursion.setIdentifier(excursionElement.getAttribute(VoucherXmlTag.IDENTIFIER.getValue()));
            excursion.setRestPlace(excursionElement.getAttribute(VoucherXmlTag.REST_PLACE.getValue()));
            excursion.setCost(Integer.parseInt(excursionElement.getAttribute(VoucherXmlTag.COST.getValue())));
            excursion.setTransport(excursionElement.getAttribute(VoucherXmlTag.TRANSPORT.getValue()));
            excursion.setExcursionType(excursionElement.getAttribute(VoucherXmlTag.EXCURSION_TYPE.getValue()));
            excursion.setDays(Integer.parseInt(getElementTextData(excursionElement, VoucherXmlTag.DAYS.getValue())));
            Voucher.Hotel hotel = excursion.getHotel();
            NodeList hotels = excursionElement.getElementsByTagName(VoucherXmlTag.HOTEL.getValue());
            if (hotels.getLength() == 1) {
                Element hotelElement = (Element) excursionElement.getElementsByTagName(VoucherXmlTag.HOTEL.getValue()).item(0);
                hotel.setStars(Integer.parseInt(getElementTextData(hotelElement, VoucherXmlTag.STARS.getValue())));
                hotel.setMeat(getElementTextData(hotelElement, VoucherXmlTag.MEAT.getValue()));
                hotel.setRoomSize(getElementTextData(hotelElement, VoucherXmlTag.ROOM.getValue()));
                hotel.setAirConditioning(Boolean.parseBoolean(getElementTextData(hotelElement, VoucherXmlTag.AIR_CONDITIONING.getValue())));
                // excursion.setHotel(hotel);
            } else {
                logger.warn("More than 1 <Hotel> tag.");
            }
            excursion.setDate(LocalDate.parse(getElementTextData(excursionElement, VoucherXmlTag.DATE_START.getValue())));
            excursion.setMoving(Integer.parseInt(getElementTextData(excursionElement, VoucherXmlTag.MOVING.getValue())));
        } else {
            logger.error("Element from DOM is null");
        }
        return excursion;
    }

    private Relaxation buildRelaxation(Element relaxationElement) {
        Relaxation excursion = new Relaxation();
        if (relaxationElement != null) {
            excursion.setIdentifier(relaxationElement.getAttribute(VoucherXmlTag.IDENTIFIER.getValue()));
            excursion.setRestPlace(relaxationElement.getAttribute(VoucherXmlTag.REST_PLACE.getValue()));
            excursion.setCost(Integer.parseInt(relaxationElement.getAttribute(VoucherXmlTag.COST.getValue())));
            excursion.setTransport(relaxationElement.getAttribute(VoucherXmlTag.TRANSPORT.getValue()));
            excursion.setDays(Integer.parseInt(getElementTextData(relaxationElement, VoucherXmlTag.DAYS.getValue())));
            Voucher.Hotel hotel = excursion.getHotel();
            NodeList hotels = relaxationElement.getElementsByTagName(VoucherXmlTag.HOTEL.getValue());
            if (hotels.getLength() == 1) {
                Element hotelElement = (Element) relaxationElement.getElementsByTagName(VoucherXmlTag.HOTEL.getValue()).item(0);
                hotel.setStars(Integer.parseInt(getElementTextData(hotelElement, VoucherXmlTag.STARS.getValue())));
                hotel.setMeat(getElementTextData(hotelElement, VoucherXmlTag.MEAT.getValue()));
                hotel.setRoomSize(getElementTextData(hotelElement, VoucherXmlTag.ROOM.getValue()));
                hotel.setAirConditioning(Boolean.parseBoolean(getElementTextData(hotelElement, VoucherXmlTag.AIR_CONDITIONING.getValue())));
                // excursion.setHotel(hotel);
            } else {
                logger.warn("More than 1 <Hotel> tag.");
            }
            excursion.setDate(LocalDate.parse(getElementTextData(relaxationElement, VoucherXmlTag.DATE_START.getValue())));
            String procedures = getElementTextData(relaxationElement, VoucherXmlTag.RELAX_PROCEDURES.getValue());
            excursion.setRelaxProcedures(getListFromTextData(procedures));
        } else {
            logger.error("Element from DOM is null");
        }
        return excursion;
    }

    private static String getElementTextData(Element element, String elementName) {
        NodeList nodeList = element.getElementsByTagName(elementName);
        int size = nodeList.getLength();
        StringBuilder text = new StringBuilder();
        Node node = nodeList.item(--size);
        text.append(node.getTextContent());
        if (size > 0) {
            while (size > 0) {
                text.append(",");
                node = nodeList.item(--size);
                text.append(node.getTextContent());
            }
        }
        return text.toString();
    }

    private static List<String> getListFromTextData(String data) {
        List<String> values = Arrays.asList(data.split(","));
        Collections.reverse(values);
        return values;
    }

}