import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Read adn writes portfolio as CSV file
 * 
 * Jonathan Zheng
 * CS 193 Final
 * FileManager.java
 * November 10, 2026
 */

public class FileManager {
    private static final String HEADER = "ticker,shares,purchasePrice,currentPrice";

    // writes every investment from portfolio to csv file
    public static void save(Portfolio portfolio, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println(HEADER);
            
            for (Investment investment : portfolio.getInvestments()) {
                writer.printf("%s,%s,%s,%s%n",
                        investment.getTicker(),
                        investment.getShares(),
                        investment.getPurchasePrice(),
                        investment.getCurrentPrice());
            }

            if (writer.checkError()) {
                throw new IOException("Error while writing to " + filename);
            }
        }
    }

    //loads a portfolio, malformed lines are skipped
    public static Portfolio load(String filename) throws FileNotFoundException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException(filename + " not found.");
        }

        Portfolio portfolio = new Portfolio();
        int lineNum = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                lineNum++;

                if (line.isEmpty()) {
                    continue;
                }

                if (lineNum == 1 && line.toLowerCase().startsWith("ticker")) {
                    continue;
                }

                try {
                    String[] parts = line.split(",");
                    if (parts.length != 4) {
                        throw new IllegalArgumentException("Expected 4 values.");
                    }

                    Investment investment = new Investment(
                        parts[0].trim(),
                        Double.parseDouble(parts[1].trim()),
                        Double.parseDouble(parts[2].trim()),
                        Double.parseDouble(parts[3].trim())
                    );

                    if (!portfolio.addInvestment(investment)) {
                        throw new IllegalArgumentException("Duplication Ticker: " + investment.getTicker());
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping line " + lineNum + ": " + e.getMessage());
                }
            }
        }
        return portfolio;
    }
}
