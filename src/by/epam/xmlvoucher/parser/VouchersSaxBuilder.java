package by.epam.xmlvoucher.parser;

import by.epam.xmlvoucher.entity.Voucher;
import by.epam.xmlvoucher.handler.VoucherErrorHandler;
import by.epam.xmlvoucher.handler.VoucherHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class VouchersSaxBuilder {
    private static final Logger logger = LogManager.getLogger(VouchersSaxBuilder.class);
    private Set<Voucher> vouchers;
    private VoucherHandler voucherHandler = new VoucherHandler();
    private XMLReader reader;

    public VouchersSaxBuilder() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
            logger.info("SAX has been configured.");
        } catch (ParserConfigurationException | SAXException e) {
            logger.error(e);
        }
        reader.setErrorHandler(new VoucherErrorHandler());
        reader.setContentHandler(voucherHandler);
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void buildSetVouchers(String filename) {
        try {
            reader.parse(filename);
        } catch (IOException | SAXException e) {
            logger.error(e);
        }
        vouchers = voucherHandler.getVouchers();
    }

}