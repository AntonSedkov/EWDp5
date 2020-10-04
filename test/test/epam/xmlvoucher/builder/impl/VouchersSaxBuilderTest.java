package test.epam.xmlvoucher.builder.impl;

import by.epam.xmlvoucher.builder.AbstractVouchersBuilder;
import by.epam.xmlvoucher.builder.impl.VouchersSaxBuilder;
import by.epam.xmlvoucher.entity.Voucher;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;

public class VouchersSaxBuilderTest {
    AbstractVouchersBuilder builder;
    Set<Voucher> actualVouchers;
    Voucher expectedVoucher;

    @BeforeMethod
    public void setUp() {
        builder = new VouchersSaxBuilder();
        builder.buildSetVouchers("data/voucher.xml");
        actualVouchers = builder.getVouchers();
        expectedVoucher = null;
    }

    @Test
    public void testBuildSetVouchersSize() {
        int actualSize = actualVouchers.size();
        int expectedSize = 16;
        assertEquals(actualSize, expectedSize);
    }

    @Test
    public void testBuildSetVouchersElementExcursionWithDefaultFields() {
        expectedVoucher = VoucherExample.createExampleExcursion();
        boolean actual = actualVouchers.contains(expectedVoucher);
        assertTrue(actual);
    }

    @Test
    public void testBuildSetVouchersElementExcursionWithDefaultFieldsWrong() {
        expectedVoucher = VoucherExample.createExampleExcursionWrong();
        boolean actual = actualVouchers.contains(expectedVoucher);
        assertFalse(actual);
    }

    @Test
    public void testBuildSetVouchersElementRelaxation() {
        expectedVoucher = VoucherExample.createExampleRelaxation();
        boolean actual = actualVouchers.contains(expectedVoucher);
        assertTrue(actual);
    }

    @Test
    public void testBuildSetVouchersElementRelaxationWrong() {
        expectedVoucher = VoucherExample.createExampleRelaxationWrong();
        boolean actual = actualVouchers.contains(expectedVoucher);
        assertFalse(actual);
    }

}