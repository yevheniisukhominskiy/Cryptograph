package com.project.services;

import com.project.constants.Alphabet;
import com.project.entity.Result;
import com.project.exception.ApplicationException;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import static com.project.repository.ResultCode.ERROR;
import static com.project.repository.ResultCode.OK;

public class BruteForce implements Function {
    private final List<Character> alphabet = Alphabet.ALPHABET;
    @Override
    public Result execute(String[] parameters) {
        try {
            String path = parameters[1];
            bruteforceFile(path);
        } catch (Exception e) {
            return new Result(ERROR, new ApplicationException("Decode operation finished with exception ", e));
        }
        return new Result(OK);
    }

    private void bruteforceFile(String inputFilePath) {
        Path outputFilePath = creatingOutputFile(Path.of(inputFilePath));

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath.toFile()))){
            String fileContent = getFileContent(reader);
            for (int key = 0; key < alphabet.size(); key++) {
                String decryptedText = decrypt(fileContent, key);

                if (isValidText(decryptedText)) {
                    System.out.println("Successfully decrypted with key: " + key);
                    writer.write(decryptedText);
                    break;
                }
            }
        } catch (IOException e) {
            throw new ApplicationException("Error during file brute-forcing", e);
        }
    }

    private String getFileContent(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        return content.toString();
    }

    private boolean isValidText(String text) {
        return text.contains(". ");
    }

    private Path creatingOutputFile(Path inputFilePath) {
        String fileName = inputFilePath.getFileName().toString();
        String fullName;

        if (fileName.contains("[ENCRYPT]")) {
            fullName = fileName.replace("[ENCRYPT]", "[BRUTE_FORCE]");
        } else {
            fullName = fileName.replaceFirst("(\\.[^\\.]+)$", "[BRUTE_FORCE]$1");
        }

        return inputFilePath.getParent().resolve(fullName);
    }

    private String decrypt(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            int index = alphabet.indexOf(Character.toLowerCase(ch));

            if (index != -1) {
                int newIndex = (index - key) % alphabet.size();

                if (newIndex < 0) {
                    newIndex += alphabet.size();
                }

                char newChar = alphabet.get(newIndex);

                if (Character.isUpperCase(ch)) {
                    newChar = Character.toUpperCase(newChar);
                }
                result.append(newChar);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
