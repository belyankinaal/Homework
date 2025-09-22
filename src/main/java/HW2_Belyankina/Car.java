package HW2_Belyankina;

public abstract class Car {
    int year;
    boolean isAutomatic;
    String color;
    String country;
    int price;
    double engine;
    String series;
    int topRating;
    int miliage;

    public Car(int year,
               boolean isAutomatic,
               String color,
               String country,
               int price,
               double engine,
               String series,
               int topRating,
               int miliage) {
        this.year = year;
        this.isAutomatic = isAutomatic;
        this.color = color;
        this.country = country;
        this.price = price;
        this.engine = engine;
        this.series = series;
        this.topRating = topRating;
        this.miliage = miliage;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
