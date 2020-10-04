package test.epam.xmlvoucher.builder;

import by.epam.xmlvoucher.builder.AbstractVouchersBuilder;
import by.epam.xmlvoucher.builder.VoucherBuilderFactory;
import by.epam.xmlvoucher.builder.impl.VoucherEventStaxBuilder;
import by.epam.xmlvoucher.builder.impl.VouchersDomBuilder;
import by.epam.xmlvoucher.builder.impl.VouchersSaxBuilder;
import by.epam.xmlvoucher.builder.impl.VouchersStreamStaxBuilder;
import by.epam.xmlvoucher.exception.XMLVoucherException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class VoucherBuilderFactoryTest {

    @Test(dataProvider = "data")
    public void testCreateVoucherBuilder(String type, AbstractVouchersBuilder expected) {
        AbstractVouchersBuilder actual = null;
        try {
            actual = VoucherBuilderFactory.createVoucherBuilder(type);
        } catch (XMLVoucherException e) {
            fail();
        }
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test(expectedExceptions = XMLVoucherException.class)
    public void testCreateVoucherBuilderException() throws XMLVoucherException {
        AbstractVouchersBuilder actual = VoucherBuilderFactory.createVoucherBuilder("abracadabra");
    }

    @DataProvider(name = "data")
    public Object[][] receiveData() {
        return new Object[][]{
                {"sax", new VouchersSaxBuilder()}, {"Dom", new VouchersDomBuilder()},
                {"strEam_stAx", new VouchersStreamStaxBuilder()}, {"event_STaX", new VoucherEventStaxBuilder()}
        };
    }
    
}