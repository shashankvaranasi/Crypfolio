package com.shashank.crypfolio;

public class CurrencyModel {
    private final String name;
    private final String symbol;
    private final double price;
    private final String rank;
    private final String id;


    public CurrencyModel(String name, String symbol, double price, String rank,String id) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.rank = rank;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public String getRank() {
        return rank;
    }

    public String getId() {
        return id;
    }


}
