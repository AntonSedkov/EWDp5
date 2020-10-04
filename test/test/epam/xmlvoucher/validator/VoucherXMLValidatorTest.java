package test.epam.xmlvoucher.validator;

import by.epam.xmlvoucher.validator.VoucherXMLValidator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class VoucherXMLValidatorTest {

    @Test
    public void testIsCorrectXML() {
        boolean actual = VoucherXMLValidator.isCorrectXML("data/voucher.xml", "data/voucher.xsd");
        assertTrue(actual);
    }

    @Test(dataProvider = "wrongData")
    public void testNotCorrectXML(String file, String schema) {
        boolean actual = VoucherXMLValidator.isCorrectXML(file, schema);
        assertFalse(actual);
    }

    @DataProvider(name = "wrongData")
    public Object[][] getWrongData() {
        return new Object[][]{
                {"data/voucher3.xml", "data/voucher.xsd"}, {"data/voucher.xml", "data/voucher3.xsd"},
                {"data/voucherBF.xml", "data/voucher.xsd"}, {"data/voucherBV.xml", "data/voucher.xsd"}
        };
    }

}