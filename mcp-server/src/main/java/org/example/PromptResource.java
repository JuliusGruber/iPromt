package org.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/iprompt")
public class PromptResource {

    private static final Map<String, String> PROMPTS = new HashMap<>();

    static {
        PROMPTS.put("hello", "Hello from custom prompt library.");
        PROMPTS.put("bye", "Goodbye from custom prompt library.");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String list() {
        return String.join("\n", PROMPTS.keySet());
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String prompt(@PathParam("name") String name) {
        return PROMPTS.getOrDefault(name, "Prompt not found");
    }
}
