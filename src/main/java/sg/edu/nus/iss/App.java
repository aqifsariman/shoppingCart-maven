package sg.edu.nus.iss;

import java.io.Console;
import java.io.File;
import java.io.IOException;
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
                    listCart(cartItems);
                    break;
                case "users":
                    listUsers(dirPath);
                    break;
                default:
            }

            if (input.startsWith("add")) {
                addCartItem(cartItems, input);
            }
            if (input.startsWith("delete")) {
                deleteCartItem(cartItems, input);
            }

            if (input.startsWith("login")) {
                login(input, filename, dirPath);
            }
        }
    }

    public static void addCartItem(List<String> cartItems, String input) {
        input = input.replace(",", " ");
        Scanner scan = new Scanner(input.substring(4));
        String strValue = "";

        while (scan.hasNext()) {
            strValue = scan.next();
            if (!cartItems.contains(strValue)) {
                cartItems.add(strValue);
                System.out.printf("%s added to cart.\n", strValue);
            }
        }
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

    public static void login(String input, String filename, String dirPath) throws IOException {
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
    }

    public static void listUsers(String dirPath) {
        File directoryPath = new File(dirPath);
        String[] contents = directoryPath.list();

        for (String content : contents) {
            System.out.println(content.replace(".txt", ""));
        }
    }
}
