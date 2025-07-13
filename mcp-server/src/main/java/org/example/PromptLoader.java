package org.example;

import io.quarkiverse.mcp.server.PromptManager;
import io.quarkiverse.mcp.server.PromptMessage;
import io.quarkiverse.mcp.server.PromptResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import io.quarkus.runtime.StartupEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads prompts from the prompts.properties file and registers them with the MCP server.
 */
@ApplicationScoped
public class PromptLoader {

    @Inject
    PromptManager promptManager;

    void onStart(@Observes StartupEvent event) throws IOException {
        Properties props = new Properties();
        try (InputStream in = PromptLoader.class.getResourceAsStream("/prompts.properties")) {
            if (in != null) {
                props.load(in);
            }
        }
        for (String name : props.stringPropertyNames()) {
            String text = props.getProperty(name);
            promptManager.newPrompt(name)
                    .setDescription("Prompt: " + name)
                    .setHandler(args -> PromptResponse.withMessages(
                            PromptMessage.withAssistantRole(text)))
                    .register();
        }
    }
}
