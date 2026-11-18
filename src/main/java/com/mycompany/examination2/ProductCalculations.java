
package com.mycompany.examination2;

public class ProductCalculations implements IProductCalculations {

    private final double productPrice;

    public ProductCalculations(double productPrice) {
        if (productPrice < 0) {
            throw new IllegalArgumentException("Product price cannot be negative.");
        }

        this.productPrice = productPrice;
    }

    @Override
    public double GetVat() {
        return productPrice * 0.15;
    }

    @Override
    public double GetCommission() {
        return productPrice * 0.05;
    }

    @Override
    public double GetTotal() {
        return productPrice + GetVat() + GetCommission();
    }
}
