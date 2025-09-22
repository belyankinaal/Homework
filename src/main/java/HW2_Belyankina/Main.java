package HW2_Belyankina;

import java.util.ArrayList;
import java.util.List;

public class Main {
    //it's Main origin
    public static void main(String[] args) {
        List<Car> cars = new ArrayList<>();

        cars.add(new Toyota(2006, true, "зеленый", "Japan", 1500000, 2.5, "Corolla", 4, 43646));
        cars.add(new Kia(2020, true, "синий", "Japan", 360000, 3.5, "SX4", 5, 56494));
        cars.add(new Mazda(2000, false, "зеленый", "Japan", 250000, 1.8, "Camry", 5, 5474));
        cars.add(new Suzuki(2015, true, "зеленый", "Japan", 1200000, 2.0, "Vitara", 4, 358));
        cars.add(new Volvo(2022, true, "красный", "Japan", 2600000, 1.6, "Prius", 5, 3566));
        cars.add(new Suzuki(2007, false, "черный", "Japan", 200000, 1.4, "Swift", 4, 964));
        cars.add(new Toyota(2005, true, "зеленый", "Japan", 350000, 3.0, "RAV4", 5, 456));
        cars.add(new Suzuki(2021, true, "белый", "Japan", 5000000, 0.0, "EV", 5, 458));
        cars.add(new Toyota(2020, true, "голубой", "Japan", 250000, 2.4, "Alphard", 5, 5488));
        cars.add(new Suzuki(2016, false, "зеленый", "Japan", 120000, 1.6, "S-Cross", 4, 3466));

        checkCars(cars);
        checkCarColor(cars);
        checkCarPrice(cars);
    }

    public static void checkCars(List<Car> cars) {
        System.out.println("\n\n1. Ищем новую машину\n");

        for (Car car : cars) {
            String name = car.getClass().getSimpleName();
            if (car.getYear() > 2006) {
                System.out.println(name + " " + car.getYear() + " выпуска нам подходит");
            } else {
                System.out.println(name + " " + car.getYear() + " устаревшее авто");
            }
        }
    }

    public static void checkCarColor(List<Car> cars) {
        System.out.println("\n\n2. Красим машины\n");

        for (Car car : cars) {
            String name = car.getClass().getSimpleName();
            if (car.getColor().equalsIgnoreCase("зеленый")) {
                System.out.println("Сейчас " + name + " зеленая, значит перекрашиваем в красный!");
                car.setColor("красный");
            } else if (car.getColor().equalsIgnoreCase("красный")) {
                System.out.println(name + " моего любимого красного цвета!");
            } else {
                System.out.println("Цвет " + name + " " + car.getColor() + "!");
            }
        }
    }

    public static void checkCarPrice(List<Car> cars) {
        System.out.println("\n\n3. Смотрим машину до 1 миллиона рублей\n");

        for (Car car : cars) {
            String name = car.getClass().getSimpleName();
            int price = car.getPrice();

            if (price > 1000000) {
                System.out.println("Чтобы купить " + name + " мне не хватает " + (price - 1000000) + " рублей...");
            } else {
                System.out.println(name + " стоит всего " + price + " рублей? Покупаю!");
            }
        }
    }
}
