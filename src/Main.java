import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Cheese", 12.5, 6));
        productList.add(new Product("Roquefort Cheese", 30.0, 2));
        productList.get(productList.size() - 1).makeExpired();
        productList.add(new Product("TV", 1500, 10, 15.7));
        productList.add(new Product("Tablet", 980, 7));
        productList.get(productList.size() - 1).makeShippable(0.808);

        Customer customer = new Customer("Moaaz", 2000.0);
        Scanner scanner = new Scanner(System.in);
        List<Product> cart = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        int []quantitiesTaken = new int[productList.size()];
        char c = 'y';
        do {
            System.out.println("\nWelcome to our E-commerce System!");
            System.out.println("Available Products:\n");

            for (int i = 0; i < productList.size(); i++) {
                Product p = productList.get(i);

                System.out.printf("[%d] %s | Price: $%.2f | Quantity: %d | %s | %s%n",
                        i + 1, p.getName(), p.getPrice(), p.getQuantity(),
                        p.isShippable() ? "Shippable" : "Not Shippable",
                        p.isExpired() ? "Expired" : "Valid");
            }

            System.out.print("\nEnter product number to add to cart (0 to checkout): ");
            int choice = scanner.nextInt();

            if (choice == 0) break;

            if (choice < 1 || choice > productList.size()) {
                System.out.println("Invalid product number.");
                continue;
            }

            Product selected = productList.get(choice - 1);
            if (selected.isExpired()) {
                System.out.println("This product is expired and cannot be purchased.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int qty = scanner.nextInt();

            if (qty <= 0 || qty > selected.getQuantity() - quantitiesTaken[choice - 1]) {
                System.out.println("Invalid quantity. Available: " + (selected.getQuantity() - quantitiesTaken[choice - 1]));
                continue;
            }

            cart.add(selected);
            quantities.add(qty);

            quantitiesTaken[choice - 1] += qty;
            System.out.println("Added to cart.");


            System.out.print("Continue shopping? (y/n): ");
            c = scanner.next().charAt(0);
        } while (c != 'n');

        // === Checkout ===
        if (cart.isEmpty()) {
            System.out.println("\nCart is empty. Cannot proceed to checkout.");
            return;
        }

        double subtotal = 0;
        double shippingFee = 0;
        List<Shipping> itemsToShip = new ArrayList<>();

        System.out.println("\n--- Checkout Summary ---");
        for (int i = 0; i < cart.size(); i++) {
            Product p = cart.get(i);
            int q = quantities.get(i);

            double itemCost = p.getPrice() * q;
            subtotal += itemCost;

            System.out.printf("%s x %d = $%.2f%n", p.getName(), q, itemCost);

            if (p.isShippable()) {
                itemsToShip.add(p);
                shippingFee += quantities.get(i) * p.getWeight() * 2;
            }
        }

        double total = subtotal + shippingFee;
        System.out.printf("Subtotal: $%.2f%n", subtotal);
        System.out.printf("Shipping Fee: $%.2f%n", shippingFee);
        System.out.printf("Total: $%.2f%n", total);

        if (customer.getBalance() < total) {
            System.out.println("Error: Insufficient balance.");
            return;
        }

        customer.deduct(total);

        for (int i = 0; i < cart.size(); i++) {
            Product p = cart.get(i);
            int q = quantities.get(i);
            p.removeQuantity(q);
        }

        System.out.printf("Payment successful! Remaining balance: $%.2f%n", customer.getBalance());

        if (!itemsToShip.isEmpty()) {
            System.out.println("Sending items to Shipping Service:");
            for (Shipping s : itemsToShip) {
                System.out.printf("- %s (%.2f kg)%n", s.getName(), s.getWeight());
            }
        }

        scanner.close();
    }
}
