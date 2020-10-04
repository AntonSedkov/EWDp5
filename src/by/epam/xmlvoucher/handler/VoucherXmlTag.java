package by.epam.xmlvoucher.handler;

public enum VoucherXmlTag {                                     // TODO: 03.10.2020 Where to be - entity or handler
    VOUCHERS("vouchers"),
    RELAXATION("relaxation"),
    EXCURSION("excursion"),
    IDENTIFIER("identifier"),
    REST_PLACE("rest-place"),
    COST("cost"),
    TRANSPORT("transport"),
    EXCURSION_TYPE("excursion-type"),
    DAYS("days"),
    HOTEL("hotel"),
    STARS("stars"),
    MEAT("meat"),
    ROOM("room"),
    AIR_CONDITIONING("air-conditioning"),
    DATE_START("date-start"),
    RELAX_PROCEDURES("relax-procedures"),
    MOVING("moving");

    private String value;

    VoucherXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}