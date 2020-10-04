package by.epam.xmlvoucher.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class VoucherXMLValidator {
    private static final Logger logger = LogManager.getLogger(VoucherXMLValidator.class);
    private static final String NAMESPACE_SCHEMA_XML = XMLConstants.W3C_XML_SCHEMA_NS_URI;

    private VoucherXMLValidator() {
    }

    public static boolean isCorrectXML(String filename, String schemaName) {
        SchemaFactory factory = SchemaFactory.newInstance(NAMESPACE_SCHEMA_XML);
        File schemaLocation = new File(schemaName);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(filename);
            validator.validate(source);
            logger.info("XML-file: " + filename + " match XSD: " + schemaName);
            return true;
        } catch (SAXException e) {
            logger.warn("XML-file: " + filename + " is not correct or valid. Exception: " + e);
            return false;
        } catch (IOException e) {
            logger.error("XML-file: " + filename + " is not find. Exception: " + e);
            return false;
        }
    }

}