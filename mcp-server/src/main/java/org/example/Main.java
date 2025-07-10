package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        PromptService service = new PromptService();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if ("list".equalsIgnoreCase(line)) {
                System.out.println(String.join("\n", service.names()));
            } else {
                String result = service.getPrompt(line);
                if (result != null) {
                    System.out.println(result);
                } else {
                    System.out.println("Prompt not found");
                }
            }
        }
    }
}
