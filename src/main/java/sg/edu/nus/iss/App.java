package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws IOException {
        String strValue = "";
        String filename = "";
        String dirPath = "c:\\Users\\aqifs\\ibf\\day3_workshop\\data";
        // INSTANTIATE FILE DIRECTORY
        File newDirectory = new File(dirPath);

        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        } else {
            System.out.println("Directory already exists.");
        }
        System.out.println("Welcome to my shopping cart.");

        // CREATE LIST FOR CART ITEMS
        List<String> cartItems = new ArrayList<>();
        Console cons = System.console();
        String input = "";

        while (!input.equals("quit")) {
            input = cons.readLine("What do you want to perform? (Type 'quit' if you want to exit the program.)\n");

            switch (input) {
                case "help":
                    System.out.println("'list' to show a list of items in your shopping cart.");
                    System.out.println("'login' <name> to access your shopping cart.");
                    System.out.println("'add' <item> to your shopping cart.");
                    System.out.println("'delete' to show a list of items in your shopping cart.");
                    System.out.println("'quit' to exit this program.");
                    break;
                case "list":
                    cartItems = readCartItemsFromFile(dirPath, filename);
                    listCart(cartItems);
                    break;
                case "users":
                    listUsers(dirPath);
                    break;
                default:
            }

            if (input.startsWith("add")) {
                addCartItem(cartItems, input, dirPath, filename);
            }
            if (input.startsWith("delete")) {
                deleteCartItem(cartItems, input);
            }

            if (input.startsWith("login")) {
                filename = login(input, filename, dirPath);
            }
        }
    }

    public static void addCartItem(List<String> cartItems, String input, String dirPath, String filename)
            throws IOException {
        input = input.replace(",", " ");
        Scanner scan = new Scanner(input.substring(4));
        String strValue = "";

        FileWriter fw = new FileWriter(dirPath + File.separator + filename);
        PrintWriter pw = new PrintWriter(fw);

        while (scan.hasNext()) {
            strValue = scan.next();
            if (!cartItems.contains(strValue)) {
                cartItems.add(strValue);
                pw.printf("%s\n", strValue);
                System.out.printf("%s added to cart.\n", strValue);
            }
        }
        pw.flush();
        fw.flush();
        pw.close();
        fw.close();

    }

    public static void listCart(List<String> cartItems) {

        if (cartItems.size() > 0) {
            for (String item : cartItems) {
                System.out.printf("%d. %s\n", cartItems.indexOf(item) + 1, item);
            }
        } else {
            System.out.println("Cart is empty.");
        }
    }

    public static void deleteCartItem(List<String> cartItems, String input) {
        Scanner scan = new Scanner(input);
        String strValue = input.substring(7);
        int indexValue = Integer.parseInt(strValue) - 1;
        cartItems.remove(indexValue);
    }

    public static String login(String input, String filename, String dirPath) throws IOException {
        input = input.replace(",", " ");
        // SCAN THE NAME AFTER LOGIN IS TYPED
        Scanner scan = new Scanner(input.substring(6));

        // SCAN AND DECLARE FILE NAME
        while (scan.hasNext()) {
            filename = scan.next();
        }

        // CREATE LOGIN FILE
        File loginFile = new File(dirPath + File.separator + filename + ".txt");
        // CREATENEWFILE WILL RETURN BOOLEAN.
        boolean isCreated = loginFile.createNewFile();

        if (isCreated) {
            System.out.printf("New file (%s) successfully created.", loginFile.getCanonicalFile());
        } else {
            System.out.println("File already created.");
        }
        return filename;
    }

    public static void listUsers(String dirPath) {
        File directoryPath = new File(dirPath);
        String[] contents = directoryPath.list();

        for (String content : contents) {
            System.out.println(content.replace(".txt", ""));
        }
    }

    public static List<String> readCartItemsFromFile(String dirPath, String filename) throws IOException {
        List<String> items = new ArrayList<>();
        File file = new File(dirPath + File.separator + filename);
        // CREATE BUFFEREDREADER OBJECT
        BufferedReader br = new BufferedReader(new FileReader(file));
        String sr = "";

        while (null != (sr = br.readLine())) {
            items.add(sr);
        }

        br.close();

        return items;
    }

}
