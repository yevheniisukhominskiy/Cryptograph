package com.project.view;

import com.project.entity.Result;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static com.project.constants.ApplicationCompletionConstants.EXCEPTION;
import static com.project.constants.ApplicationCompletionConstants.SUCCESS;

public class ConsoleView implements View {
    private final Scanner scanner;
    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String[] getParameters() {
        // Get mode
        System.out.println("Select a function, write a letter and press enter.");
        System.out.println("[e]ncrypt | [d]ecrypt | [b]rute-force");
        System.out.print("Your choice: ");
        String mode = scanner.nextLine().toLowerCase();

        // Validate mode input
        while (!mode.equals("e") && !mode.equals("d") && !mode.equals("b")) {
            System.err.println("Invalid mode. Please enter 'e' for encrypt, 'd' for decrypt, or 'b' for brute-force.");
            System.out.println("[e]ncrypt | [d]ecrypt | [b]rute-force");
            System.out.print("Your choice: ");
            mode = scanner.nextLine().toLowerCase();
        }

        // Get file path
        String path;
        while (true) {
            System.out.println("Enter the absolute path of the input file (or leave blank to use the default file): ");
            System.out.print("File path: ");
            path = scanner.nextLine();

            if (path.isEmpty()) {
                path = "resources/example.txt";
                break;
            } else if (Files.exists(Paths.get(path))) {
                break;
            } else {
                System.err.println("Invalid path. Please try again.");
            }
        }

        // Get key if not in brute-force mode
        String key = null;
        if (!"b".equals(mode)) {
            System.out.println("Enter the key");
            System.out.print("Key: ");
            key = scanner.nextLine();

            while (!isValidKey(key)) {
                System.err.println("Invalid key. Please enter a numeric key.");
                System.out.println("Enter the key: ");
                System.out.print("Key: ");
                key = scanner.nextLine();
            }
        }

        return new String[]{mode, path, key};
    }

    private boolean isValidKey(String key) {
        try {
            Integer.parseInt(key);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void printResult(Result result) {
        switch (result.getResultCode()) {
            case OK -> System.out.println(SUCCESS);
            case ERROR -> System.out.println(EXCEPTION + result.getApplicationException().getMessage());
        }
    }
}
