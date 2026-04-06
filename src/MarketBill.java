// Author: Aland Abubakr
// GitHub: https://github.com/Aland-Abubakr-Kurd

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


// MarketBill represents a simple console-based checkout system.
// It allows users to scan items via product codes, adjust quantities, 
// remove items, and print a final formatted receipt.

class MarketBill {

    // Command line text colors
    final static String Red = "\u001b[31;1m";
    final static String Blue = "\u001b[36;1m";
    final static String Yellow = "\u001b[33;1m";
    final static String Reset = "\u001b[0m";
 
    public static void main(String[] arg) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        // Initialize and configure the beep sound effect for scanning items
        File file = new File("scanning_beeb_sound.wav");
        AudioInputStream audio = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audio);

        Scanner scanner = new Scanner(System.in);

        // Database of available products using
        int[]    productCodes  = {   101     ,   102    ,    103    ,    104     ,    105    ,   106    ,   107   ,    108    ,    109     ,   110   ,       111        ,     112       ,      113       ,     114      ,      115       ,      116      ,   117   ,       118       ,       119        ,      120        ,     121     ,        122        ,       123      ,       124        ,      125        };
        String[] productNames  = {  "Water"  ,  "Cake"  ,  "Chips"  ,  "Cookie"  ,  "Juice"  ,  "Coke"  ,  "Egg"  ,  "Bread"  ,  "Cheese"  ,  "Milk" ,  "Coffee (box)"  ,  "Tea (box)"  ,  "Volleyball"  ,  "Football"  ,  "Basketball"  ,  "Chess Set"  ,  "Uno"  ,  "Sleeping Bag" ,  "Hiking Boots"  ,  "Rain Jacket"  ,  "Compass"  ,  "First Aid Kit"  ,  "Bug Spray"   ,  "Fishing Rod"   ,  "Creamy Cake"  };
        double[] productPrices = {   0.25    ,   3.50   ,    1.25   ,    0.10    ,    0.40   ,   1.00   ,   0.20  ,    1.20   ,    2.75    ,   1.50  ,       5.00       ,     3.60      ,     15.00      ,    10.00     ,     20.00      ,      9.00     ,   5.00  ,      22.00      ,       31.00      ,      46.00      ,    7.00     ,        15.00      ,      5.00      ,      18.00       ,      2.50       };
        int numberOfProducts = productCodes.length;

        // Arrays to hold the data for the user's current shopping cart session
        int[]    scannedProductCode = new int[numberOfProducts];
        String[] scannedProductName = new String[numberOfProducts];
        double[] scannedProductPrice = new double[numberOfProducts];
        double[] scannedTotalPruductPrice = new double[numberOfProducts];
        int[]  quantity = new int[numberOfProducts];
        double totalPrice = 0;
        int scannedProducts = 0; // Tracks how many unique items are in the cart

        // Print welcome banner
        System.out.printf("%n%n%n%n%174s%n%174s%n%n%n%n%117s%n%118s%n%118s%n%118s%n%118s%n%118s%n%n%n%n%174s%n%174s%n%n%s%88s%s%n%n%n%n%n"
                        , "███████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████"
                        , "==================================================================================================================================================================="
                        ,  "██████╗ █████╗ ███████╗██╗  ██╗██╗███████╗██████╗"
                        , "██╔════╝██╔══██╗██╔════╝██║  ██║██║██╔════╝██╔══██╗"
                        , "██║     ███████║███████╗███████║██║█████╗  ██████╔╝"
                        , "██║     ██╔══██║╚════██║██╔══██║██║██╔══╝  ██╔══██╗"
                        , "╚██████╗██║  ██║███████║██║  ██║██║███████╗██║  ██║"
                        ,  "╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝"
                        , "==================================================================================================================================================================="
                        , "███████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████", Yellow, "- Programmed by: Aland Abubakr", Reset
                        );

        // Main application loop (keeps running until the user selects exit)
        while(true) {
            int option;

            // Input validation loop for main menu selection
            while(true) {
                try {
                    System.out.printf("%32s%s%s%n"    , "", Blue, "- Enter 1 to add product to cart:");
                    System.out.printf("%32s%s%n"      , "",       "- Enter 2 to see all product informations:");
                    System.out.printf("%32s%s%s%n%34s", "",       "- Enter 0 to exit:", Reset, "> ");
                    option = scanner.nextInt();
                    break; // Exit the loop if input is a valid integer
                }
                catch(Exception e) {
                    System.out.printf("%31s%s%s%s%n%n", "", Red, "*) Invalid input! Please enter one of the options:", Reset);
                    scanner.next(); // Clear the previuos input
                }
            }

            // Route the user to the correct feature based on their input
            switch(option) {
                case 1: scanningProducts(scanner, numberOfProducts, productCodes, productNames, productPrices, scannedProductCode, scannedProductName, scannedProductPrice, scannedTotalPruductPrice, quantity, scannedProducts, totalPrice, clip); break;
                case 2: printProductInformations(numberOfProducts, productCodes, productNames, productPrices); break;
                case 0: System.out.printf("%32s%s%s%n", "", Blue, "- Have a nice day :D"); System.exit(0); break;
                default: System.out.printf("%31s%s%s%s%n%n", "", Red, "*) Invalid input! Please enter one of the options:", Reset);
            }
        }
    }

    
    // Prints the complete catalog of available items and their prices.
    public static void printProductInformations(int numberOfProducts, int[] productCodes, String[] productNames, double[] productPrices) {
        System.out.printf("%n%135s%n%n", "===================================================================================");
        System.out.printf("%134s%n%n"  , " \\       [ Product code ]       [ Product name ]       [ Product price ]         /");
        System.out.printf("%135s%n%n"  , "===================================================================================");
        
        // A loop to print all item
        for(int i = 0; i < numberOfProducts; i++) {
            System.out.printf("%53s%-11s%-23d%-24s%s%-21.2f%s%n%n", "", "\\", productCodes[i],productNames[i], "$", productPrices[i], "/");
        }
        System.out.printf("%135s%n%n", "===================================================================================");
    }

    
    // Handles the core logic of scanning items, updating quantities, and navigating the checkout process.
    public static void scanningProducts(Scanner scanner, int numberOfProducts, int[] productCodes, String[] productNames, double[] productPrices, int[] scannedProductCode, String[] scannedProductName, double[] scannedProductPrice, double[] scannedTotalPruductPrice, int[] quantity, int scannedProducts, double totalPrice, Clip clip) {
        System.out.printf("%n%32s%s%s%n"    , "", Blue, "- Enter 0 to print your bill:");
        System.out.printf("%32s%s%n"        , "", "- Enter 99 to remove items:");
        System.out.printf("%32s%s%n%32s%s%n", "", "- Enter 88 to step back to the menu: if so,", "", "  your scanned product(s) will be removed...");
        System.out.printf("%32s%s%s%93s%n%n", "", "- Wating for the product codes:", Reset, "===============================================================");
        System.out.printf("%32s%123s%n"     , "", "|           [ Poduct name ]     [ Product Price ]           |");
        System.out.printf("%32s%123s%n"     , "", "            ---------------     -----------------            ");

        while(true) {
            boolean codeExistence = false;
            int code;
            
            // Input validation loop for product codes
            while(true) {
                try {
                    System.out.printf("%34s", "> ");
                    code = scanner.nextInt();
                    break;
                }
                catch(Exception e) {
                    System.out.printf("%31s%s%s%s%n%94s%-60s%s%n", "", Red, "*) Invalid input! The product codes are not letter(s):", Reset, "", "|", "|");
                    scanner.next(); // Clear invalid input
                }
            }

            // Handle special control codes (88 = back, 99 = remove, 0 = print bill)
            if(code == 88) {
                resetScannedArrays(scannedProductCode, scannedProductName, quantity, scannedProductPrice, scannedTotalPruductPrice, totalPrice, scannedProducts);
                break;
            }
            else if(code == 99 && scannedProducts == 0) {
                System.out.printf("%31s%s%s%s%n%94s%-60s%s%n", "", Red, "*) Can't remove item(s), no products have been scanned!", Reset, "", "|", "|");
            }
            else if(code == 99 && scannedProducts != 0) {
                scannedProducts = removeProducts(scanner, numberOfProducts, scannedProducts, scannedProductCode, scannedProductName, scannedProductPrice, quantity);
                System.out.printf("%n", "");
            }
            else if(code == 0 && scannedProducts == 0) {
                System.out.printf("%31s%s%s%s%n%94s%-60s%s%n", "", Red, "*) Can't print the bill, no products have been scanned!", Reset, "", "|", "|");
            }
            else if(code == 0 && scannedProducts != 0) {
                System.out.printf("%156s%n%n%n%n%n%n", "===============================================================");
                printBill(scannedProducts, scannedProductCode, scannedProductName, quantity, scannedProductPrice, scannedTotalPruductPrice, totalPrice);
                break;
            }
            else if(code != 0) {
                // Search the database for the entered product code
                for(int i = 0; i < numberOfProducts; i++) {
                    if(code == productCodes[i]) {
                        
                        // Play the beep sound
                        clip.setMicrosecondPosition(0);
                        clip.start();

                        codeExistence = true;
                        boolean repeatedCode = false;
                        int indexOfRepeatedCode = 0;

                        // Check if the product is already in the cart to increment quantity instead of adding a new line
                        for(int l = 0; l < scannedProducts; l++) {
                            if(code == scannedProductCode[l]) {
                                repeatedCode = true;
                                indexOfRepeatedCode = l;
                                break;
                            }
                        }

                        if(repeatedCode) {
                            quantity[indexOfRepeatedCode]++;
                            System.out.printf("%32s%s%s (%s) %s%s%n%94s%-60s%s%n", "", Blue, "- The quantity of", scannedProductName[indexOfRepeatedCode], "has been increased by one", Reset, "", "|", "|");
                        }
                        else {
                            // If it's a new product, ask for the quantity
                            while(true) {
                                while(true) {
                                    try {
                                        System.out.printf("%32s%s%s%s", "", Blue, "- Enter the quantity: ", Reset);
                                        quantity[scannedProducts] = scanner.nextInt();
                                        break;
                                    }
                                    catch(Exception e) {
                                        System.out.printf("%31s%s%s%s%n%94s%-60s%s%n", "", Red, "*) Wrong input! The quantity can not be letter(s):", Reset, "", "|", "|");
                                        scanner.next();
                                    }
                                }
                                
                                // Ensure quantity is physically possible
                                if(quantity[scannedProducts] == 0) {
                                    System.out.printf("%31s%s%s%s%n%94s%-60s%s%n", "", Red, "*) The quantity can not be zero...", Reset, "", "|", "|");
                                }
                                else if(quantity[scannedProducts] < 0) {
                                    System.out.printf("%31s%s%s%s%n%94s%-60s%s%n", "", Red, "*) The quantity can not be smaller than zero...", Reset, "", "|", "|");
                                }
                                else {
                                    break;
                                }
                            }
        
                            // Add new product details to the cart arrays
                            scannedProductCode[scannedProducts] =  productCodes[i];
                            scannedProductName[scannedProducts] =  productNames[i];
                            scannedProductPrice[scannedProducts] = productPrices[i];

                            scannedProducts++; // Increment cart size counter
        
                            System.out.printf("%94s%-14s%-21s%s%-24.2f%s%n", "", "|", productNames[i], "$", productPrices[i], "|");
                        }
                    }
                }
                if(!codeExistence) {
                    System.out.printf("%31s%s%s%s%n%94s%-60s%s%n", "", Red, "*) This code does not exist...", Reset, "", "|", "|");
                }
            }
        }
    }

    
    // Handles removing an item entirely or reducing its quantity in the cart.
    public static int removeProducts(Scanner scanner, int numberOfProducts, int scannedProducts, int[] scannedProductCode, String[] scannedProductName, double[] scannedProductPrice, int[] quantity) {
        int numberOfScannedProducts = scannedProducts;

        while(true) {
            boolean codeExistence = false;
            System.out.printf("%32s%s%s%n", "", Blue, "- Enter 99 to step back for scanning items:");
            System.out.printf("%32s%s%s%n%n", "", "- Enter the product code, to remove:", Reset);
            int code;
            
            while(true) {
                try{
                    System.out.printf("%34s", "> ");
                    code = scanner.nextInt();
                    break;
                }
                catch(Exception e) {
                    System.out.printf("%n%31s%s%s%s%n%n", "", Red, "*) Invalid input! The product codes are not letter(s):", Reset);
                    scanner.next();
                }
            }

            if(code == 99) {
                // Exit removal mode
                System.out.printf("%n%32s%s%s%n", "", Blue, "- Enter 0 to print your bill:");
                System.out.printf("%32s%s%n", "", "- Enter 99 to remove items:");
                System.out.printf("%32s%s%n%32s%s%n", "", "- Enter 88 to step back to the menu: if so,", "", "  your scanned product(s) will be removed...");
                System.out.printf("%32s%s%s%n", "", "- Wating for the product codes:", Reset);
                break;
            }
            else {
                // Search the cart for the product to remove
                for(int i = 0; i < scannedProducts; i++) {
                    if(code == scannedProductCode[i]) {
                        codeExistence = true;
                        
                        if( quantity[i] == 1 ){
                            // If only 1 exists, remove the product entirely and reshiftArrayElements the array
                            System.out.printf("%32s%s%s %d (%s) %s%s%n%n", "", Blue, "- The quantity of", scannedProductCode[i], scannedProductName[i], "was 1, so, it has been removed from your list.", Reset);
                            scannedProducts--;
                            scannedProductCode[i] = 0;
                            scannedProductName[i] = null;
                            scannedProductPrice[i] = 0.0;
                            quantity[i] = 0;

                            shiftArrayElements(i, numberOfScannedProducts, numberOfProducts, scannedProductCode, scannedProductName, scannedProductPrice, quantity);
                        }
                        else {
                            // If multiple exist, ask how many to remove
                            while(true) {
                                int remover;
                                while(true) { 
                                    try {
                                        System.out.printf("%32s%s%s %d (%s) %s %d%n", "", Blue, "- The quantity of", scannedProductCode[i], scannedProductName[i], "is", quantity[i]);
                                        System.out.printf("%32s%s%s%n", "", "- How many of it do you want to remove?", Reset);
                                        System.out.printf("%34s", "> ");
                                        remover = scanner.nextInt();
                                        break;
                                    }
                                    catch(Exception e) {
                                        System.out.printf("%n%31s%s%s%s%n%n", "", Red, "*) Wrong input!", Reset);
                                        scanner.next();
                                        continue;
                                    }
                                }

                                if(remover == 0) {
                                    System.out.printf("%32s%s%s%s%n%n", "", Blue, "- The quantity remains the same", Reset);
                                    break;
                                }
                                else if(remover < 0) {
                                    System.out.printf("%n%31s%s%s%s%n%n", "", Red, "*) Wrong input!", Reset);
                                }
                                else if(remover > quantity[i]) {
                                    System.out.printf("%n%31s%s%s%s%n%n", "", Red, "*) Can't remove more than the quantity", Reset);
                                }
                                else if(remover == quantity[i]) {
                                    // Removed product entirely
                                    System.out.printf("%32s%s%s%s%n%n", "", Blue, "- The item has been removed from your list", Reset);

                                    scannedProducts--;
                                    scannedProductCode[i] = 0;
                                    scannedProductName[i] = null;
                                    scannedProductPrice[i] = 0.0;
                                    quantity[i] = 0;

                                    shiftArrayElements(i, numberOfScannedProducts, numberOfProducts, scannedProductCode, scannedProductName, scannedProductPrice, quantity);
                                    break;
                                }
                                else {
                                    // Just decrement the quantity
                                    System.out.printf("%32s%s%s %d (%s) %s %d%s%n%n", "", Blue, "- The quantity of", scannedProductCode[i], scannedProductName[i], "has been reduced by", remover,Reset);
                                    quantity[i] -= remover;
                                    break;
                                }
                            }
                        }
                    }
                }
                if(!codeExistence) {
                    System.out.printf("%n%31s%s%s%s%n%n", "", Red, "*) This item does not exist, or it has not been scanned!", Reset);
                }
            }
        }
        return scannedProducts; // Return updated cart size
    }

    
    // Shifts array elements leftward to fill the gap left by a deleted item.
    // This prevents null/zero gaps in the middle of the cart arrays.
    public static void shiftArrayElements(int i, int numberOfScannedProducts, int numberOfProducts, int[] scannedProductCode, String[] scannedProductName, double[] scannedProductPrice, int[] quantity) {
        for(int l = i; l < numberOfScannedProducts; l++) {
            // Prevent OutOfBounds exception when we reach the end of the array
            if(l == (numberOfProducts - 1)) {
                scannedProductCode[l] = 0;
                scannedProductName[l] = null;
                scannedProductPrice[l] = 0.0;
                quantity[l] = 0;
                break;
            }
            // Shift the next element into the current slot
            scannedProductCode[l] = scannedProductCode[l+1];
            scannedProductName[l] = scannedProductName[l+1];
            scannedProductPrice[l] = scannedProductPrice[l+1];
            quantity[l] = quantity[l+1];
        }
    }

    
    // Prints the final receipt, loops through cart for display, and clears data for the next customer.
    public static void printBill(int scannedProducts, int[] scannedProductCode, String[] scannedProductName, int[] quantity, double[] scannedProductPrice, double[] scannedTotalPruductPrice, double totalPrice) {
        totalPrice = calculateSubtotalAndSumOfAllSubtotals(scannedProducts, quantity, scannedProductPrice, scannedTotalPruductPrice, totalPrice);
        
        System.out.printf("%156s%n%n", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.printf("%155s%n%n", "|       [ Product code ]     [ Product name ]     [ quantity ]     [ Product price ]     [ Total product price ]       |"    );
        System.out.printf("%156s%n%n", "============================================================================================================================");
        
        for(int i = 0; i < scannedProducts; i++) {
            System.out.printf("%33s%-10s%-21d%-21s%-19d%s%-21.2f%s%-27.2f%s%n%n", "", "|", scannedProductCode[i], scannedProductName[i], quantity[i], "$", scannedProductPrice[i], "$", scannedTotalPruductPrice[i], "|");
        }
        
        System.out.printf("%156s%n%n"                   , "============================================================================================================================");
        System.out.printf("%33s%-8s%-85s%s%-27.2f%s%n%n", "", "|", "[ Total ]", "$", totalPrice, "|");
        System.out.printf("%156s%n%n%n%n%n"             , "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        resetScannedArrays(scannedProductCode, scannedProductName, quantity, scannedProductPrice, scannedTotalPruductPrice, totalPrice, scannedProducts);
    }


    // Calculates line totals and the grand total of the receipt.
    public static double calculateSubtotalAndSumOfAllSubtotals(int scannedProducts, int[] quantity, double[] scannedProductPrice, double[] scannedTotalPruductPrice, double totalPrice){
        // Calculate subtotal for each item based on quantity
        for(int i = 0; i < scannedProducts; i++) {
            scannedTotalPruductPrice[i] = scannedProductPrice[i] * quantity[i];
        }
        // Sum up all subtotals into the grand total
        for(int i = 0; i < scannedProducts; i++) {
            totalPrice += scannedTotalPruductPrice[i];
        }
        return totalPrice;
    }


    // Resets the cart arrays so the program can be used again without restarting.
    public static void resetScannedArrays(int[] scannedProductCode, String[] scannedProductName, int[] quantity, double[] scannedProductPrice, double[] scannedTotalPruductPrice, double totalPrice, int scannedProducts) {
        // Zero out everything based on what was actively in the cart
        for(int i = 0; i < scannedProducts; i++) {
            scannedProductCode[i] = 0;
            scannedProductName[i] = null;
            quantity[i] = 0;
            scannedProductPrice[i] = 0.0;
            scannedTotalPruductPrice[i] = 0.0;
        }
    }

}
