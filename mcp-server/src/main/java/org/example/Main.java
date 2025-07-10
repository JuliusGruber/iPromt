package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Main {
    public static void main(String[] args) throws Exception {
        PromptService service = new PromptService();
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            JsonNode req;
            try {
                req = mapper.readTree(line);
            } catch (Exception e) {
                continue; // ignore malformed input
            }
            String method = req.path("method").asText();
            JsonNode params = req.path("params");
            String id = req.has("id") ? req.get("id").asText() : null;

            Object result = null;
            boolean error = false;
            String errorMsg = null;

            if ("listPrompts".equals(method)) {
                result = service.names();
            } else if ("getPrompt".equals(method)) {
                String name = params.path("name").asText(null);
                if (name == null) {
                    error = true;
                    errorMsg = "Missing parameter 'name'";
                } else {
                    String prompt = service.getPrompt(name);
                    if (prompt != null) {
                        result = prompt;
                    } else {
                        error = true;
                        errorMsg = "Prompt not found";
                    }
                }
            } else {
                error = true;
                errorMsg = "Method not found";
            }

            ObjectNode resp = mapper.createObjectNode();
            resp.put("jsonrpc", "2.0");
            if (id != null) {
                resp.put("id", id);
            }
            if (error) {
                ObjectNode err = mapper.createObjectNode();
                err.put("code", -32602);
                err.put("message", errorMsg);
                resp.set("error", err);
            } else {
                resp.set("result", mapper.valueToTree(result));
            }
            System.out.println(mapper.writeValueAsString(resp));
        }
    }
}
