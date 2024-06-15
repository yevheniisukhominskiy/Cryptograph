package com.project.services;

import com.project.constants.Alphabet;
import com.project.entity.Result;
import com.project.exception.ApplicationException;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import static com.project.repository.ResultCode.ERROR;
import static com.project.repository.ResultCode.OK;

public class Encode implements Function {
    private final List<Character> alphabet = Alphabet.ALPHABET;
    @Override
    public Result execute(String[] parameters) {
        try {
            String path = parameters[1];
            int key = Integer.parseInt(parameters[2]);
            encryptFile(path, key);
        } catch (Exception e) {
            return new Result(ERROR, new ApplicationException("Decode operation finished with exception ", e));
        }
        return new Result(OK);
    }

    private void encryptFile(String inputFilePath, int key) {
        Path outputFilePath = creatingOutputFile(Path.of(inputFilePath));

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath.toFile()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String encryptedLine = encrypt(line, key);
                writer.write(encryptedLine);
                writer.newLine();
            }

        } catch (IOException e) {
            throw new ApplicationException("Error during file encryption", e);
        }
    }

    private Path creatingOutputFile(Path inputFilePath) {
        String fileName = inputFilePath.getFileName().toString();
        String fullName = fileName.replaceFirst("(\\.[^\\.]+)$", "[ENCRYPT]" + "$1");

        return inputFilePath.getParent().resolve(fullName);
    }

    private String encrypt(String line, int key) {
        StringBuilder result = new StringBuilder();

        for (char ch : line.toCharArray()) {
            int index = alphabet.indexOf(Character.toLowerCase(ch));

            if (index != -1) {
                int newIndex = (index + key) % alphabet.size();

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
