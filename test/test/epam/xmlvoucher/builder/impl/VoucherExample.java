package test.epam.xmlvoucher.builder.impl;

import by.epam.xmlvoucher.entity.Excursion;
import by.epam.xmlvoucher.entity.Relaxation;
import by.epam.xmlvoucher.entity.Voucher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VoucherExample {
    private VoucherExample() {
    }

    public static Relaxation createExampleRelaxation() {
        Relaxation relaxation = new Relaxation();
        relaxation.setIdentifier("re895");
        relaxation.setRestPlace("Montenegro");
        relaxation.setCost(1200);
        relaxation.setTransport("airplane");
        relaxation.setDays(10);
        Voucher.Hotel hotel = relaxation.getHotel();
        hotel.setStars(4);
        hotel.setMeat("RO");
        hotel.setRoomSize("triple");
        hotel.setAirConditioning(true);
        relaxation.setDate(LocalDate.parse("2021-07-05"));
        List<String> procedures = new ArrayList<>();
        procedures.add("rafting");
        procedures.add("rock climbing");
        relaxation.setRelaxProcedures(procedures);
        return relaxation;
    }

    public static Relaxation createExampleRelaxationWrong() {
        Relaxation relaxation = new Relaxation();
        relaxation.setIdentifier("re95");
        relaxation.setRestPlace("Montenegro");
        relaxation.setCost(1200);
        relaxation.setTransport("airplane");
        relaxation.setDays(10);
        Voucher.Hotel hotel = relaxation.getHotel();
        hotel.setStars(4);
        hotel.setMeat("RO");
        hotel.setRoomSize("triple");
        hotel.setAirConditioning(true);
        relaxation.setDate(LocalDate.parse("2021-07-05"));
        List<String> procedures = new ArrayList<>();
        procedures.add("rafting");
        procedures.add("rock climbing");
        relaxation.setRelaxProcedures(procedures);
        return relaxation;
    }

    public static Excursion createExampleExcursion() {
        Excursion excursion = new Excursion();
        excursion.setIdentifier("ex256");
        excursion.setRestPlace("Belarus");
        excursion.setCost(150);
        excursion.setDays(4);
        Voucher.Hotel hotel = excursion.getHotel();
        hotel.setStars(3);
        hotel.setMeat("HB");
        hotel.setRoomSize("triple");
        hotel.setAirConditioning(false);
        excursion.setDate(LocalDate.parse("2020-11-07"));
        excursion.setMoving(6);
        return excursion;
    }

    public static Excursion createExampleExcursionWrong() {
        Excursion excursion = new Excursion();
        excursion.setIdentifier("ex256");
        excursion.setRestPlace("Chile");
        excursion.setCost(150);
        excursion.setDays(4);
        Voucher.Hotel hotel = excursion.getHotel();
        hotel.setStars(3);
        hotel.setMeat("HB");
        hotel.setRoomSize("triple");
        hotel.setAirConditioning(false);
        excursion.setDate(LocalDate.parse("2020-11-07"));
        excursion.setMoving(6);
        return excursion;
    }

}