package org.example;

import io.quarkiverse.mcp.server.*;
import jakarta.enterprise.context.*;

@ApplicationScoped
public class HelloPrompt {

    @Prompt
    public PromptResponse meanGreeting() {
        return PromptResponse.withMessages(
            PromptMessage.withAssistantRole("Hello, you mean motherfucker!")
        );
    }
}