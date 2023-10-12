package andreademasi;

import andreademasi.shop.Customer;
import andreademasi.shop.Order;
import andreademasi.shop.Product;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        Supplier<Customer> customerSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random rndm = new Random();
            return new Customer(faker.name().firstName(), rndm.nextInt(0, 5));
        };

        List<Customer> customers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            customers.add(customerSupplier.get());
        }

        //customers.forEach(System.out::println);


        Supplier<Product> productSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            Random rndm = new Random();
            return new Product(faker.book().title(), "Books");
        };

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(productSupplier.get());
        }

        //products.forEach(System.out::println);

        Supplier<Order> orderSupplier = () -> {

            Random rndm = new Random();
            return new Order("Consegnato", LocalDate.of(2021, 02, 1), LocalDate.of(2021, 04, 1), products, customers.get(rndm.nextInt(0, 5)));
        };

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            orders.add(orderSupplier.get());
        }

        //orders.forEach(System.out::println);

        // ESERCIZIO 1
        System.out.println("*********************** ESERCIZIO 1 ***********************");
        Map<Customer, List<Order>> ordiniPerCustomer = orders.stream().collect(Collectors.groupingBy(Order::getCustomer));
        ordiniPerCustomer.forEach((customer, order) -> System.out.println("Cliente: " + customer.getName() + " " + order));

        // ESERCIZIO 2
        System.out.println("*********************** ESERCIZIO 2 ***********************");
        Map<Customer, Double> totaleVendite = orders.stream().collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum())));
        totaleVendite.forEach((customer, total) -> System.out.println("Cliente: " + customer.getName() + " -> totale speso: " + total));

        // ESERCIZIO 3
        System.out.println("*********************** ESERCIZIO 3 ***********************");
        List<Product> dalPiuCostoso = products.stream().sorted(Comparator.comparing(product -> product.getPrice(), Comparator.reverseOrder())).limit(3).toList();
        dalPiuCostoso.forEach(System.out::println);

        // ESERCZIO 4
        System.out.println("*********************** ESERCIZIO 4 ***********************");
        Double mediaOrdini = orders.stream().collect(Collectors.summingDouble(sum -> sum.getProducts().stream().mapToDouble(Product::getPrice).average().orElse(0.0)));
        System.out.println(mediaOrdini);
    }
}
