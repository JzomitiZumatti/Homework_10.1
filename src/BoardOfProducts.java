import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class BoardOfProducts {
    private final List<Product> productList;
    public BoardOfProducts() {
        this.productList = new ArrayList<>();
    }

    public void clearBoard() {
        productList.clear();
    }

    public void add(String type, double price) {
        Product newProduct = new Product(type, price);
        productList.add(newProduct);
    }

    public void add(String type, double price, boolean discount) {
        Product newProduct = new Product(type, price, discount);
        productList.add(newProduct);
    }

    public void add(String type, double price, boolean discount, LocalDateTime addingTime) {
        Product newProduct = new Product(type, price, discount, addingTime);
        productList.add(newProduct);
    }

    public void add(int id, String type, double price, boolean discount, LocalDateTime addingTime) {
        Product newProduct = new Product(id, type, price, discount, addingTime);
        productList.add(newProduct);
    }

    public List<Product> getProductList() {
        return productList.stream()
                .filter(s -> s.getType().equals("Book"))
                .filter(s -> s.getPrice() > 250)
                .collect(Collectors.toList());
    }

    public List<Product> getDiscountProductList() {
        return productList.stream()
                .filter(Product::getDiscount)
                .peek(s -> s.setPrice(s.getPrice() * 0.9))
                .collect(Collectors.toList());
    }

    public void getCheapestProduct() {
        /*Optional<Product> cheapestProduct = productList.stream()
                .filter(s -> s.getType().equals("Book"))
                .min(Comparator.comparing(Product::getPrice));
        if (cheapestProduct.isEmpty()) {
            System.out.println("Product from [Book] category didn't find.");
        } else {
            System.out.println(cheapestProduct);
        }*/
        productList.stream()
                .filter(s -> s.getType().equals("Book"))
                .min(Comparator.comparing(Product::getPrice))
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Product from [Book] category didn't find.")
                );
    }

    public List<Product> getLastThreeAddedProducts() {
        return productList.stream()
                .sorted(Comparator.comparing(Product::getDateAdded).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public double generalPrice() {
        Year currentYear = Year.now();
        return productList.stream()
                .filter(product -> product.getDateAdded().getYear() == currentYear.getValue())
                .filter(s -> s.getType().equals("Book"))
                .filter(s -> s.getPrice() <= 75)
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public Map<String, List<Product>> groupProductsByType() {
        return productList.stream()
                .collect(Collectors.groupingBy(Product::getType));
    }

    @Override
    public String toString() {
        return productList.toString();
    }
}
