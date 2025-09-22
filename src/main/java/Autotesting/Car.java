package Autotesting;

public abstract class Car {
    int year; //год выпуска автомобиля
    boolean isAutomatic; //автоматическая (true) или механическая (false) коробка
    String color; //цвет
    String country; //страна выпуска
    int price;//цена
    double engine; //объем двигателя

    //Конструктор
public Car(int year,
           boolean isAutomatic,
           String color,
           String country,
           int price,
           double engine){
    this.year = year;
    this.isAutomatic = isAutomatic;
    this.color = color;
    this.country = country;
    this.price = price;
    this.engine = engine;
}
    //Методы гетеры
public int getYear(){
    return year;
}

public boolean isAutomatic(){
    return isAutomatic;
}

public String getColor(){
    return color;
}

public String getCountry(){
    return country;
}

public int getPrice(){
    return price;
}

public double getEngine(){
    return engine;
}

    //Методы сетеры
public  void setYear(int year){
    this.year = year;
}

public void setAutomatic(boolean automatic) {
    isAutomatic=automatic;
}

public void setColor(String color) {
    this.color=color;
}

public void setCountry(String country){
    this.country=country;
}

public void setPrice(int price){
    this.price=price;
}
public void setEngine(double engine){
    this.engine=engine;
}
}
