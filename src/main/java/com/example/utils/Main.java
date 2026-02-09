package com.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author Vasu created on 14-02-2025
 * @project VDR
 */

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\env\\eSign\\sample.pdf";
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);

        System.out.println("Base64 Encoded String:");
        System.out.println(base64Encoded);
    }
}
