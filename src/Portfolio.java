import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Stores a collection of investments and 
 * calculates portfolio totals & allocations.
 * 
 * Jonathan Zheng
 * CS 193 Final
 * Portfolio.java
 * November 7, 2026
 */

public class Portfolio {
    private final ArrayList<Investment> investments = new ArrayList<>();
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    public boolean addInvestment(Investment investment) {
        if (findByTicker(investment.getTicker()) != null) {
            return false;
        }
        investments.add(investment);
        return true;
    }

    public boolean removeInvestment(String ticker) {
        Investment investment = findByTicker(ticker);
        if (investment == null) {
            return false;
        }
        investments.remove(investment);
        return true;
    }

    public boolean isEmpty() {
        return investments.isEmpty();
    }

    public int size() {
        return investments.size();
    }

    //returns a copy so other classes can't modify the real list
    public List<Investment> getInvestments() {
        return new ArrayList<>(investments);
    }

    //finds investment by their ticker (ex: AAPL)
    public Investment findByTicker(String ticker) {
        if (ticker == null) {
            return null;
        }

        for (Investment investment : investments) {
            if (investment.getTicker().equalsIgnoreCase(ticker.trim())) {
                return investment;
            }
        }
        return null;
    }

    // Calculations -------------------

    public double getTotalValue() {
        double total = 0;
        for (Investment investment : investments) {
            total += investment.getCurrentValue();
        }
        return total;
    }

    public double getTotalInvested() {
        double total = 0;
        for (Investment investment : investments) {
            total += investment.getTotalCost();
        }
        return total;
    }

    public double getTotalProfitLoss() {
        return getTotalValue() - getTotalInvested();
    }

    public double getTotalReturnPercent() {
        double invested = getTotalInvested();
        if (invested == 0) {
            return 0;
        }
        return (getTotalProfitLoss() / invested) * 100;
    }

    // Avoids dividing by zero when the portfolio is empty
    public double getAllocation(Investment investment) {
        double total = getTotalValue();
        if (total == 0) {
            return 0;
        }
        return (investment.getCurrentValue() / total) * 100;
    }

    // Formatting & Display -----------------------

    public String formatCurrency(double amount) {
        return currency.format(amount);
    }

    private String formatPercent(double percent) {
        return String.format("%+.2f%%", percent);
    }
    
    private String formatShares(double shares) {
        if (shares == Math.floor(shares)) {
            return String.valueOf((long) shares);
        }
        return String.valueOf(shares);
    }

    // displays a table of the investments in portfolio
    public void displayAll() {
        if (investments.isEmpty()) {
            System.out.println("Your portfolio is empty.");
            return;
        }

        System.out.println();

        System.out.printf("%-8s %10s %12s %12s %14s %14s %9s %10s%n",
                "Ticker", "Shares", "Buy Price", "Curr Price",
                "Value", "Profit/Loss", "Return", "Allocation");

        System.out.println("-".repeat(97));

        for (Investment investment : investments) {
            System.out.printf("%-8s %10.4f %12s %12s %14s %14s %9s %9.2f%%%n",
                    investment.getTicker(),
                    investment.getShares(),
                    formatCurrency(investment.getPurchasePrice()),
                    formatCurrency(investment.getCurrentPrice()),
                    formatCurrency(investment.getCurrentValue()),
                    formatCurrency(investment.getProfitLoss()),
                    formatPercent(investment.getPercentReturn()),
                    getAllocation(investment));
        }
        
        System.out.println();
    }

    // displays portfolio totals
    public void displaySummary() {
        if (investments.isEmpty()) {
            System.out.println("Your portfolio is empty.");
            return;
        }

        System.out.println();

        System.out.println("===== PORTFOLIO SUMMARY =====");
        System.out.println("Investments:     " + investments.size());
        System.out.println("Total Invested:  " + formatCurrency(getTotalInvested()));
        System.out.println("Total Value:     " + formatCurrency(getTotalValue()));
        System.out.println("Profit/Loss:     " + formatCurrency(getTotalProfitLoss()));
        System.out.println("Total Return:    " + formatPercent(getTotalReturnPercent()));
        
        System.out.println();
    }

    // Sorting ------------------

    // Hand-written selection sort 
    // Sorts a copy so the portfolio's original order and the saved CSV never changes.    
    public List<Investment> getSortedByValue() {
        ArrayList<Investment> sorted = new ArrayList<>(investments);

        for (int i = 0; i < sorted.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < sorted.size(); j++) {
                if (sorted.get(j).getCurrentValue() > sorted.get(maxIndex).getCurrentValue()) {
                    maxIndex = j;
                }
            }
            Investment temporary = sorted.get(i);
            sorted.set(i, sorted.get(maxIndex));
            sorted.set(maxIndex, temporary);
        }

        return sorted;
    }

    public void displaySortedByValue() {
        if (investments.isEmpty()) {
            System.out.println("Your portfolio is empty.");
            return;
        }

        System.out.println();
        System.out.println("Investments sorted by value:");
        System.out.println();

        for (Investment investment : getSortedByValue()) {
            System.out.printf("%-6s - %s%n",
                    investment.getTicker(),
                    formatCurrency(investment.getCurrentValue()));
        }

        System.out.println();
    }

    public void displayByTicker(String ticker) {
        boolean found = false;

        for (Investment investment : investments) {
            if (investment.getTicker().equalsIgnoreCase(ticker.trim())) {
                System.out.println();
                System.out.println(investment.getTicker());
                System.out.println("Shares: " + formatShares(investment.getShares()));
                System.out.println("Current Value: " + formatCurrency(investment.getCurrentValue()));
                System.out.println("Profit/Loss: " + formatCurrency(investment.getProfitLoss()));
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Investment not found.");
        }
    }
}
