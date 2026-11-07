/**
 * Represents a single holding of an investment.
 * Stores the ticker, share count, purchase price, current price
 * and calculates value, profit/loss, and percentage return.
 * 
 * Jonathan Zheng
 * CS 193 Final
 * Investment.java
 * November 7, 2026
 */

public class Investment {
    private final String ticker;
    private final double shares;
    private final double purchasePrice;
    private final double currentPrice;

    public Investment(String ticker, double shares, double purchasePrice, double currentPrice) {
        //exception handling
        if (ticker == null || ticker.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticker can't be empty.");
        }
        if (Double.isNaN(shares) || Double.isInfinite(shares) || shares <= 0) {
            throw new IllegalArgumentException("Shares must be greater than zero.");
        }
        if (Double.isNaN(purchasePrice) || Double.isInfinite(purchasePrice) || purchasePrice <= 0) {
            throw new IllegalArgumentException("Purchase price must be greater than zero.");
        }
        if (Double.isNaN(currentPrice) || Double.isInfinite(currentPrice) || currentPrice < 0) {
            throw new IllegalArgumentException("Current price can't be negative.");
        }

        this.ticker = ticker.trim().toUpperCase();
        this.shares = shares;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
    }

    public String getTicker() {
        return ticker;
    }
}