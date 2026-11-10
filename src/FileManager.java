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
}
