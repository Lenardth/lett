import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String licensePlate;
    private String brand;
    private int yearOfProduction;

    public Car(String licensePlate, String brand, int yearOfProduction) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.yearOfProduction = yearOfProduction;
    }

    @Override
    public String toString() {
        return licensePlate + " " + brand + " " + yearOfProduction;
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
                    listCars();
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

    public static void listCars() {
        if (carInventory.isEmpty()) {
            System.out.println("No cars in the inventory.");
        } else {
            for (Car car : carInventory) {
                System.out.println(car);
            }
        }
    }
}