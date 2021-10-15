package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static String[] array = new String[100];
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Arrays.fill(array, "");
        menu();
    }

    private static void menu() {
        boolean keepGoing = true;
        while (keepGoing) {
            List<String> input = new ArrayList<>(Arrays.asList(scanner.nextLine().strip().split("\\s+")));
            String command = input.get(0).toLowerCase();
            input.remove(0);

            switch (command) {
                case "set":
                    setCell(input);
                    break;
                case "get":
                    getCell(input);
                    break;
                case "delete":
                    deleteCell(input);
                    break;
                case "exit":
                    keepGoing = false;
                    break;
                default:
                    System.out.println("ERROR");
            }
        }
    }

    private static void deleteCell(List<String> input) {
        int position = -1;
        try {
            position = Integer.parseInt(input.get(0));
        } catch (NumberFormatException e) {
            System.out.println("ERROR");
        }

        if (position > 0 && position < 101) {
            array[position -1] = "";
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
        }
    }

    private static void getCell(List<String> input) {
        int position = -1;
        try {
            position = Integer.parseInt(input.get(0));
        } catch (NumberFormatException e) {
            System.out.println("ERROR");
        }

        if (position > 0 && position < 101) {
            String result = array[position - 1];
            if (!result.equalsIgnoreCase("")) {
                System.out.println(result);
            } else {
                System.out.println("ERROR");
            }
        } else {
            System.out.println("ERROR");
        }
    }

    private static void setCell(List<String> input) {
        int position = -1;
        try {
            position = Integer.parseInt(input.get(0));
        } catch (NumberFormatException e) {
            System.out.println("ERROR");
        }
        input.remove(0);

        String out = String.join(" ", input);
        if (position >= 0 && position <= 100) {
            array[position - 1] = out;
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
        }
    }
}
