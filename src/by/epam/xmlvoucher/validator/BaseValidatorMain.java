package by.epam.xmlvoucher.validator;

import java.io.*;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import by.epam.xmlvoucher.handler.VoucherErrorHandler;
import org.xml.sax.SAXException;

public class BaseValidatorMain {

    public static void main(String[] args) {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String fileName = "data/voucher.xml";
        String schemaName = "data/voucher.xsd";
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileName);
            validator.setErrorHandler(new VoucherErrorHandler());
            validator.validate(source);
            System.out.println("good");
        } catch (SAXException | IOException e) {
            System.err.println(fileName + " is not correct or valid");
        }
    }

}