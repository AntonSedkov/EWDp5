package by.epam.xmlvoucher.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Relaxation extends Voucher {
    private List<String> relaxProcedures;

    public Relaxation() {
    }

    public Relaxation(String identifier, String restPlace, int cost, String transport, int days, Hotel hotel, LocalDate date, List<String> relaxProcedures) {
        super(identifier, restPlace, cost, transport, days, hotel, date);
        this.relaxProcedures = relaxProcedures;
    }

    public List<String> getRelaxProcedures() {
        return relaxProcedures;
    }

    public void setRelaxProcedures(List<String> relaxProcedures) {
        this.relaxProcedures = relaxProcedures;
    }

    public boolean addRelaxProcedure(String s) {
        if (relaxProcedures == null) {
            relaxProcedures = new ArrayList<>();
        }
        return relaxProcedures.add(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Relaxation that = (Relaxation) o;
        return relaxProcedures != null ? relaxProcedures.equals(that.relaxProcedures) : that.relaxProcedures == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (relaxProcedures != null ? relaxProcedures.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Relaxation.class.getSimpleName()).append(" ");
        stringBuilder.append(super.toString());
        stringBuilder.append(" relaxProcedures=").append(relaxProcedures);
        return stringBuilder.toString();
    }

}