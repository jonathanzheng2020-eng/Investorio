import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Entry point, displays menu, handles user input,
 * links Portfolio & FileManager.
 * 
 * Jonathan Zheng
 * CS 193 Final
 * Main.java
 * November 12, 2026
 */

public class Main {
    private static final String FILENAME = "portfolio.csv";
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

    }

    private static void printMenu() {
        System.out.println();
        System.out.println("----- Investorio -----");
        System.out.println();
        System.out.println("1. Add Investment");
        System.out.println("2. Remove Investment");
        System.out.println("3. View Portfolio");
        System.out.println("4. View Portfolio Summary");
        System.out.println("5. Save Portfolio");
        System.out.println("6. Load Portfolio");
        System.out.println("7. Exit");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    private static void addInvestment(Portfolio portfolio) {
        System.out.println("Ticker symbol (ex. AAPL): ");
        String ticker = SCANNER.nextLine().trim();
        
        if (ticker.isEmpty()) {
            System.out.println("Ticker cannot be empty.");
            return;
        }
        if (portfolio.findByTicker(ticker) != null) {
            System.out.println(ticker.toUpperCase() + " is already in your portfolio. " +
                    "Remove it first if you want to re-enter it.");
            return;
        }

        double shares = readDouble("Number of shares: ", false);
        if (Double.isNaN(shares)) {
            return;
        }

        double purchasePrice = readDouble("Purchase price per shares: ", false);
        if (Double.isNaN(purchasePrice)) {
            return;
        }

        double currentPrice = readDouble("Current price per share: ", true);
        if (Double.isNaN(currentPrice)) {
            return;
        }

        try {
            Investment investment = new Investment(ticker, shares, purchasePrice, currentPrice);
            portfolio.addInvestment(investment);
            System.out.println("Added " + investment.getTicker() + ".");
            System.out.println("Value: " + portfolio.formatCurrency(investment.getCurrentValue()));
            System.out.println("Profit/Loss: " + portfolio.formatCurrency(investment.getProfitLoss())
                    + String.format(" (%+.2f%%)", investment.getPercentReturn()));
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add investment: " + e.getMessage());
        }
    }


    // prompts until valid number or "cancel", zero is allowed if true, negatives aren't
    //return NaN if cancelled
    private static double readDouble(String prompt, boolean zero) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();

            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Cancelled.");
                return Double.NaN;
            }

            try {
                double value = Double.parseDouble(input);
                if (Double.isNaN(value) || Double.isInfinite(value)) {
                    System.out.println("Please enter a real number (or type 'cancel').");
                } 
                else if (value < 0 || (value == 0 && !zero)) {
                    if (zero) {
                        System.out.println("Can't be negative. Try again (or type 'cancel').");
                    } 
                    else {
                        System.out.println("Must be greater than zero. Try again (or type 'cancel').");
                    }
                } 
                else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number. Try again (or type 'cancel').");
            }
        }
    }
}
