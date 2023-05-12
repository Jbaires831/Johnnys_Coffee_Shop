package com.example.johnnyscoffeeshop;

public class MenuItem {
    private int mCoffeeId;

    private String mCoffeeName;
    private int mCalories;
    private double mPrice;

    public MenuItem(String coffeeName, int calories, double price) {
        mCoffeeName = coffeeName;
        mCalories = calories;
        mPrice = price;
    }

    public int getCoffeeId() {
        return mCoffeeId;
    }

    public void setCoffeeId(int coffeeId) {
        mCoffeeId = coffeeId;
    }

    public String getCoffeeName() {
        return mCoffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        mCoffeeName = coffeeName;
    }

    public int getCalories() {
        return mCalories;
    }

    public void setCalories(int calories) {
        mCalories = calories;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "mCoffeeId=" + mCoffeeId +
                ", mCoffeeName='" + mCoffeeName + '\'' +
                ", mCalories=" + mCalories +
                ", mPrice=" + mPrice +
                '}';
    }
}
