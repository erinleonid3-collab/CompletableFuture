package io.github.erinleonid3_collab;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DataAggregator {

    public static final double DEFAULT_PRICE = 0.0;
    public static final String DEFAULT_DESCRIPTION = "Нет данных";
    public static final double DEFAULT_RATING = 0.0;

    private final ExecutorService executor;

    public DataAggregator() {
        this(Executors.newFixedThreadPool(3));
    }

    public DataAggregator(ExecutorService executor) {
        this.executor = executor;
    }

    public ProductInfo aggregateProductInfo(String productName) {
        CompletableFuture<Double> priceFuture = CompletableFuture
                .supplyAsync(() -> fetchPrice(productName), executor)
                .exceptionally(ex -> {
                    System.err.println("Ошибка получения цены: " + ex.getMessage());
                    return DEFAULT_PRICE;
                });

        CompletableFuture<String> descriptionFuture = CompletableFuture
                .supplyAsync(() -> fetchDescription(productName), executor)
                .exceptionally(ex -> {
                    System.err.println("Ошибка получения описания: " + ex.getMessage());
                    return DEFAULT_DESCRIPTION;
                });

        CompletableFuture<Double> ratingFuture = CompletableFuture
                .supplyAsync(() -> fetchRating(productName), executor)
                .exceptionally(ex -> {
                    System.err.println("Ошибка получения рейтинга: " + ex.getMessage());
                    return DEFAULT_RATING;
                });

        return priceFuture
                .thenCombine(descriptionFuture, (price, desc) -> new Object[]{price, desc})
                .thenCombine(ratingFuture, (arr, rating) ->
                        new ProductInfo(productName, (Double) arr[0], (String) arr[1], rating))
                .join();
    }

    //имитация сервисов

    double fetchPrice(String productName) {
        sleepRandom();
        maybeFail("price");
        return ThreadLocalRandom.current().nextDouble(100, 2000);
    }

    String fetchDescription(String productName) {
        sleepRandom();
        maybeFail("description");
        return "Описание товара: " + productName;
    }

    double fetchRating(String productName) {
        sleepRandom();
        maybeFail("rating");
        return Math.round(ThreadLocalRandom.current().nextDouble(1, 5) * 10.0) / 10.0;
    }

    private void sleepRandom() {
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(1000, 3001));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Прервано", e);
        }
    }

    private void maybeFail(String source) {
        if (ThreadLocalRandom.current().nextDouble() < 0.2) {
            throw new RuntimeException("Сбой сервиса: " + source);
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}