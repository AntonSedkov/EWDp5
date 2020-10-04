package by.epam.xmlvoucher.builder.impl;

import by.epam.xmlvoucher.builder.AbstractVouchersBuilder;
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

public class VouchersSaxBuilder extends AbstractVouchersBuilder {
    private static final Logger logger = LogManager.getLogger(VouchersSaxBuilder.class);
    private final VoucherHandler voucherHandler;
    private XMLReader reader;

    public VouchersSaxBuilder() {
        super();
        voucherHandler = new VoucherHandler();
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

    @Override
    public void buildSetVouchers(String filename) {
        try {
            reader.parse(filename);
        } catch (IOException | SAXException e) {
            logger.error(e);
        }
        vouchers = voucherHandler.getVouchers();
    }

}