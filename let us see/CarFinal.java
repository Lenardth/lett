import java.io.*;
import java.util.*;

class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    private String licensePlate;
    private String brand;
    private int yearOfProduction;

    public Car(String licensePlate, String brand, int yearOfProduction) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.yearOfProduction = yearOfProduction;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    @Override
    public String toString() {
        return licensePlate + " " + brand + " " + yearOfProduction;
    }
}

class PlateComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        return car1.getLicensePlate().compareTo(car2.getLicensePlate());
    }
}

class BrandComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        return car1.getBrand().compareTo(car2.getBrand());
    }
}

class YearComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        return Integer.compare(car1.getYearOfProduction(), car2.getYearOfProduction());
    }
}

class CarInventory {
    private static List<Car> carInventory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the command: ");
            String userInput = input.nextLine();
            String[] commandParts = userInput.split(" ");
            String command = commandParts[0];

            switch (command) {
                case "add":
                    if (commandParts.length == 4) {
                        String licensePlate = commandParts[1];
                        String brand = commandParts[2];
                        int year = Integer.parseInt(commandParts[3]);
                        addCar(licensePlate, brand, year);
                    } else {
                        System.out.println("Invalid command format. Use: add <licensePlate> <brand> <year>");
                    }
                    break;
                case "list":
                    if (commandParts.length == 2) {
                        String argument = commandParts[1];
                        listCars(argument);
                    } else {
                        listCars("");
                    }
                    break;
                case "remove":
                    if (commandParts.length == 2) {
                        String licensePlate = commandParts[1];
                        removeCar(licensePlate);
                    } else {
                        System.out.println("Invalid command format. Use: remove <licensePlate>");
                    }
                    break;
                case "save":
                    if (commandParts.length == 2) {
                        String fileName = commandParts[1];
                        saveInventory(fileName);
                    } else {
                        System.out.println("Invalid command format. Use: save <fileName>");
                    }
                    break;
                case "load":
                    if (commandParts.length == 2) {
                        String fileName = commandParts[1];
                        loadInventory(fileName);
                    } else {
                        System.out.println("Invalid command format. Use: load <fileName>");
                    }
                    break;
                case "exit":
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("Command not detected");
                    break;
            }
        }
    }

    public static void addCar(String licensePlate, String brand, int year) {
        Car car = new Car(licensePlate, brand, year);
        carInventory.add(car);
        System.out.println("Car added: " + car);
    }

    public static void listCars(String argument) {
        if (carInventory.isEmpty()) {
            System.out.println("No cars in the inventory.");
            return;
        }

        switch (argument) {
            case "plate":
                Collections.sort(carInventory, new PlateComparator());
                break;
            case "brand":
                Collections.sort(carInventory, new BrandComparator());
                break;
            case "year":
                Collections.sort(carInventory, new YearComparator());
                break;
            default:
                break;
        }

        for (Car car : carInventory) {
            System.out.println(car);
        }
    }

    public static void removeCar(String licensePlate) {
        Car carToRemove = null;
        for (Car car : carInventory) {
            if (car.getLicensePlate().equals(licensePlate)) {
                carToRemove = car;
                break;
            }
        }
        if (carToRemove != null) {
            carInventory.remove(carToRemove);
            System.out.println("Car removed: " + carToRemove);
        } else {
            System.out.println("Car with license plate " + licensePlate + " not found.");
        }
    }

    public static void saveInventory(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(carInventory);
            System.out.println("Inventory saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public static void loadInventory(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            carInventory = (List<Car>) ois.readObject();
            System.out.println("Inventory loaded from " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}