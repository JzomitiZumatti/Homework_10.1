import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class ProductsBoard {
    private final List<Product> products;
    private final String productType = "Book";

    public ProductsBoard() {
        this.products = new ArrayList<>();
    }

    public void clearBoard() {
        products.clear();
    }

    public void add(String type, double price) {
        products.add(new Product(type, price));
    }

    public void add(String type, double price, boolean discount) {
        products.add(new Product(type, price, discount));
    }

    public void add(String type, double price, boolean discount, LocalDateTime addingTime) {
        products.add(new Product(type, price, discount, addingTime));
    }

    public void add(int id, String type, double price, boolean discount, LocalDateTime addingTime) {
        products.add(new Product(id, type, price, discount, addingTime));
    }

    public List<Product> getProductList() {
        double minPrice = 251;
        return products.stream()
                .filter(p -> p.getType().equals(productType))
                .filter(p -> p.getPrice() >= minPrice)
                .collect(Collectors.toList());
    }

    public List<Product> getDiscountProductList() {
        double discount = 0.9;
        return products.stream()
                .filter(Product::getDiscount)
                .peek(p -> p.setPrice(p.getPrice() * discount))
                .collect(Collectors.toList());
    }

    public void getCheapestProduct() {
        products.stream()
                .filter(p -> p.getType().equals(productType))
                .min(Comparator.comparing(Product::getPrice))
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Product from [Book] category didn't find.")
                );
    }

    public List<Product> getLastThreeAddedProducts() {
        int maxSize = 3;
        return products.stream()
                .sorted(Comparator.comparing(Product::getDateAdded).reversed())
                .limit(maxSize)
                .collect(Collectors.toList());
    }

    public double generalPrice() {
        Year currentYear = Year.now();
        double maxPrice = 75.0;
        return products.stream()
                .filter(product -> product.getDateAdded().getYear() == currentYear.getValue())
                .filter(p -> p.getType().equals(productType))
                .filter(p -> p.getPrice() <= maxPrice)
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public Map<String, List<Product>> groupProductsByType() {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getType));
    }

    @Override
    public String toString() {
        return products.toString();
    }
}
