package by.epam.xmlvoucher.entity;

import java.time.LocalDate;

public class Excursion extends Voucher {
    private int moving;
    private String excursionType;
    private static final String DEFAULT_EXCURSION_TYPE = "different";

    public Excursion() {
        excursionType = DEFAULT_EXCURSION_TYPE;
    }

    public Excursion(String identifier, String restPlace, int cost, String transport, int days, Hotel hotel, LocalDate date, int moving, String excursionType) {
        super(identifier, restPlace, cost, transport, days, hotel, date);
        this.moving = moving;
        this.excursionType = excursionType;
    }

    public int getMoving() {
        return moving;
    }

    public void setMoving(int moving) {
        this.moving = moving;
    }

    public String getExcursionType() {
        return excursionType;
    }

    public void setExcursionType(String excursionType) {
        if (excursionType != null && !excursionType.isBlank()) {
            this.excursionType = excursionType;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Excursion excursion = (Excursion) o;
        if (moving != excursion.moving) return false;
        return excursionType != null ? excursionType.equals(excursion.excursionType) : excursion.excursionType == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + moving;
        result = 31 * result + (excursionType != null ? excursionType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Excursion.class.getSimpleName()).append(" ");
        stringBuilder.append(super.toString());
        stringBuilder.append(" moving=").append(moving);
        stringBuilder.append(" excursionType=").append(excursionType);
        return stringBuilder.toString();
    }

}