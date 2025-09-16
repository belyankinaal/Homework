package Autotesting;

public class Kia extends Car {
    public String series; //марка
    public int topRating; // оценка по 10 бальной
    public int miliage;

    public Kia (int year,
                   boolean isAutomatic,
                   String color,
                   String country,
                   int price,
                   double engine,
                   String series,
                   int topRating,
                   int miliage){
        super(year, isAutomatic, color, country, price, engine);
        this.series = series;
        this.topRating = topRating;
        this.miliage = miliage;
    }

    // Методы гетеры
    public String getSeries(){
        return series;
    }

    public int getTopRating() {
        return topRating;
    }
    public  int getMiliage(){
        return miliage;
    }
    //Методы сетеры
    public void setSeries (String series){
        this.series = series;
    }
    public void setTopRating (int topRating) {
        this.topRating=topRating;
    }
    public void setMiliage (int price){
        this.miliage=miliage;
    }
}
