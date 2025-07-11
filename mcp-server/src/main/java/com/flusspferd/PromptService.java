package com.flusspferd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class PromptService {
    private Map<String, String> prompts = Collections.emptyMap();

    public PromptService() {
        loadPrompts();
    }

    private void loadPrompts() {
        Properties props = new Properties();
        try (InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("prompts.properties")) {
            if (is != null) {
                props.load(is);
                prompts = props.entrySet().stream()
                        .collect(Collectors.toMap(e -> String.valueOf(e.getKey()), e -> String.valueOf(e.getValue())));
            }
        } catch (IOException e) {
            // leave prompts empty if file not found or cannot be read
        }
    }

    public Set<String> names() {
        return prompts.keySet();
    }

    public String getPrompt(String name) {
        return prompts.get(name);
    }
}
