package io.github.erinleonid3_collab;

import java.util.Objects;

public class ProductInfo {
    private final String name;
    private final double price;
    private final String description;
    private final double rating;

    public ProductInfo(String name, double price, String description, double rating) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public double getRating() { return rating; }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfo that = (ProductInfo) o;
        return Double.compare(that.price, price) == 0
                && Double.compare(that.rating, rating) == 0
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, description, rating);
    }
}
