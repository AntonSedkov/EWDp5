package by.epam.xmlvoucher.entity;

import java.time.LocalDate;
import java.util.StringJoiner;

public abstract class Voucher {
    private String identifier;
    private String restPlace;
    private int cost;
    private String transport;
    private int days;
    private Hotel hotel;
    private LocalDate date;
    private static final String DEFAULT_TRANSPORT = "bus";

    public Voucher() {
        transport = DEFAULT_TRANSPORT;
    }

    public Voucher(String identifier, String restPlace, int cost, String transport, int days, Hotel hotel, LocalDate date) {
        this.identifier = identifier;
        this.restPlace = restPlace;
        this.cost = cost;
        this.transport = transport;
        this.days = days;
        this.hotel = hotel;
        this.date = date;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRestPlace() {
        return restPlace;
    }

    public void setRestPlace(String restPlace) {
        this.restPlace = restPlace;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        if (transport != null && !transport.isBlank()) {
            this.transport = transport;
        }
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Hotel getHotel() {
        if (hotel == null) {
            hotel = new Hotel();
        }
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        if (cost != voucher.cost) return false;
        if (days != voucher.days) return false;
        if (identifier != null ? !identifier.equals(voucher.identifier) : voucher.identifier != null) return false;
        if (restPlace != null ? !restPlace.equals(voucher.restPlace) : voucher.restPlace != null) return false;
        if (transport != null ? !transport.equals(voucher.transport) : voucher.transport != null) return false;
        if (hotel != null ? !hotel.equals(voucher.hotel) : voucher.hotel != null) return false;
        return date != null ? date.equals(voucher.date) : voucher.date == null;
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + (restPlace != null ? restPlace.hashCode() : 0);
        result = 31 * result + cost;
        result = 31 * result + (transport != null ? transport.hashCode() : 0);
        result = 31 * result + days;
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Voucher.class.getSimpleName() + "[", "]")
                .add("identifier='" + identifier + "'")
                .add("restPlace='" + restPlace + "'")
                .add("cost=" + cost)
                .add("transport='" + transport + "'")
                .add("days=" + days)
                .add("hotel=" + hotel)
                .add("date=" + date)
                .toString();
    }

    public class Hotel {
        private int stars;
        private String meat;
        private String roomSize;
        private boolean airConditioning;

        public Hotel() {
        }

        public Hotel(int stars, String meat, String roomSize, boolean airConditioning) {
            this.stars = stars;
            this.meat = meat;
            this.roomSize = roomSize;
            this.airConditioning = airConditioning;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public String getMeat() {
            return meat;
        }

        public void setMeat(String meat) {
            this.meat = meat;
        }

        public String getRoomSize() {
            return roomSize;
        }

        public void setRoomSize(String roomSize) {
            this.roomSize = roomSize;
        }

        public boolean isAirConditioning() {
            return airConditioning;
        }

        public void setAirConditioning(boolean airConditioning) {
            this.airConditioning = airConditioning;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Hotel hotel = (Hotel) o;
            if (stars != hotel.stars) return false;
            if (airConditioning != hotel.airConditioning) return false;
            if (meat != null ? !meat.equals(hotel.meat) : hotel.meat != null) return false;
            return roomSize != null ? roomSize.equals(hotel.roomSize) : hotel.roomSize == null;
        }

        @Override
        public int hashCode() {
            int result = stars;
            result = 31 * result + (meat != null ? meat.hashCode() : 0);
            result = 31 * result + (roomSize != null ? roomSize.hashCode() : 0);
            result = 31 * result + (airConditioning ? 1 : 0);
            return result;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Hotel.class.getSimpleName() + "[", "]")
                    .add("stars=" + stars)
                    .add("meat='" + meat + "'")
                    .add("roomSize='" + roomSize + "'")
                    .add("airConditioning=" + airConditioning)
                    .toString();
        }

    }

}