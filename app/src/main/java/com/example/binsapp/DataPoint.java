package com.example.binsapp;

public class DataPoint {
    private double width;
    private double height;
    private double totalBricksRequired;

    public DataPoint(double width, double height, double totalBricksRequired) {
        this.width = width;
        this.height = height;
        this.totalBricksRequired = totalBricksRequired;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getTotalBricksRequired() {
        return totalBricksRequired;
    }
}