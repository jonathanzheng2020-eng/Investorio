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

    // One shared Scanner: a second Scanner on System.in can swallow buffered input.
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();
        boolean inPlay = true;

        while (inPlay) {
            printMenu();
            String choice = SCANNER.nextLine().trim();

            switch (choice) {
                case "1":
                    addInvestment(portfolio);
                    break;
                case "2":
                    removeInvestment(portfolio);
                    break;
                case "3":
                    portfolio.displayAll();
                    break;
                case "4":
                    portfolio.displaySummary();
                    break;
                case "5":
                    savePortfolio(portfolio);
                    break;
                case "6":
                    portfolio = loadPortfolio(portfolio);
                    break;
                case "7":
                    inPlay = false;
                    System.out.println("Goodbye.");
                    break;
                case "8":
                    portfolio.displaySortedByValue();
                    break;
                case "9":
                    searchByTicker(portfolio);
                    break;
                default:
                    System.out.println("Invalid. Please enter a number from 1-9.");
            }
        }
        SCANNER.close();
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
        System.out.println("8. Sort Investments by Value");
        System.out.println("9. Search Investments by Ticker");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    private static void addInvestment(Portfolio portfolio) {
        System.out.print("Ticker symbol (ex. AAPL): ");
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

        double purchasePrice = readDouble("Purchase price per share: ", false);
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

    private static void removeInvestment(Portfolio portfolio) {
        if (portfolio.isEmpty()) {
            System.out.println("Your portfolio's empty. Nothing to remove from here.");
            return;
        }

        System.out.print("Ticker to remove: ");
        String ticker = SCANNER.nextLine().trim();

        if (portfolio.removeInvestment(ticker)) {
            System.out.println("Removed " + ticker.toUpperCase() + ".");
        } else {
            System.out.println("No investment found with ticker \"" + ticker + "\".");
        }
    }

    private static void savePortfolio(Portfolio portfolio) {
        try {
            FileManager.save(portfolio, FILENAME);
            System.out.println("Portfolio saved to " + FILENAME + ".");
        } catch (IOException e) {
            System.out.println("Could not save portfolio: " + e.getMessage());
        }
    }

    private static Portfolio loadPortfolio(Portfolio currentPortfolio) {
        try {
            Portfolio loadedPortfolio = FileManager.load(FILENAME);
            System.out.println("Loaded " + loadedPortfolio.size() + " investments from " + FILENAME + ".");
            return loadedPortfolio;
        } 
        // Return the current portfolio on failure so a bad load never wipes the user's data
        catch (FileNotFoundException e) {
            System.out.println("No saved portfolio found (" + FILENAME + " doesn't exist yet). "
                    + "Save one first.");
            return currentPortfolio;
        }
    }

    // NaN is the "user cancelled" signal because it can never be a valid answer
    // prompts until valid number or "cancel", zero is allowed if true, negatives aren't
    //return NaN if cancelled
    private static double readDouble(String prompt, boolean allowZero) {
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
                else if (value < 0 || (value == 0 && !allowZero)) {
                    if (allowZero) {
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

    private static void searchByTicker(Portfolio portfolio) {
        if (portfolio.isEmpty()) {
            System.out.println("Your portfolio is empty. Nothing to search.");
            return;
        }

        System.out.print("Enter ticker to search: ");
        String ticker = SCANNER.nextLine().trim();

        if (ticker.isEmpty()) {
            System.out.println("Ticker cannot be empty.");
            return;
        }

        portfolio.displayByTicker(ticker);
    }
}
