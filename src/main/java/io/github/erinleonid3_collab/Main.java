package io.github.erinleonid3_collab;

public class Main {
    public static void main(String[] args) {
        DataAggregator aggregator = new DataAggregator();
        try {
            ProductInfo info = aggregator.aggregateProductInfo("Ноутбук");
            System.out.println(info);
        } finally {
            aggregator.shutdown();
        }
    }
}