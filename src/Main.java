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
    }
}
