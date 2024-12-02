package data_access;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import use_case.movie_justif.MovieJustifDataAccessInterface;

/**
 * Accessing Gemini.
 */
public class GeminiDataAccessObject implements MovieJustifDataAccessInterface {

    // private static final String AI_API_URL = "https://api.your-ai-service.com/generate-text";
    // private static final String API_KEY = "your-api-key";
    private HttpClient httpClient;
    private final String content = "text";
    private final String block = "parts";
    // private static final Dotenv dotenv = Dotenv.load();
    // private static final GEMINI_API_KEY = dotenv.get("GEMINI_API_KEY");

    // Constructor
    public GeminiDataAccessObject() {
        // Default HttpClient
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Accessing the curl request.
     * @param httpClient client request.
     */
    // Setter for HttpClient (to be used in tests)
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        System.out.println("HttpClient has been set: " + httpClient);
    }

    /**
     * Test on whether 'Say hello, world!' works.
     * @return Gemini response.
     * @throws JsonProcessingException In case LLM does not respond.
     */
    public String testWork() throws JsonProcessingException {
        String answer = "";

        // Define the JSON payload
        // String jsonPayload = """{"contents": [{"parts": [{"text": "Explain how AI works"}]}]}""";
        Map<String, Object> textObject = new HashMap<>();
        textObject.put(content, "Say 'hello, world!'");

        Map<String, Object> partsObject = new HashMap<>();
        partsObject.put(block, List.of(textObject));

        Map<String, Object> contentObject = new HashMap<>();
        contentObject.put("contents", List.of(partsObject));

        // Convert to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentObject);

        // Create the HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Define the API key and endpoint
        String apiKey = "";
        String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                + "gemini-1.5-flash-latest:generateContent?key=" + apiKey;

        // Build the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            // answer = response.body();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            String extractedText = jsonNode
                    .path("candidates")
                    .get(0)
                    // Navigate to "contents"
                    .path("content")
                    // .path(0)
                    // First element in the array
                    // Navigate to "parts"
                    .path(block)
                    // First element in the array
                    .get(0)
                    // Get the "text" field
                    .path(content)
                    .asText();
            answer = extractedText.trim();
        }
        catch (IOException exE) {
            // Handle I/O issues
            System.err.println("I/O error: " + exE.getMessage());
        }
        catch (InterruptedException exE) {
            // Handle interruptions
            System.err.println("Request interrupted: " + exE.getMessage());
            // Restore interrupted status
            Thread.currentThread().interrupt();
        }
        catch (IndexOutOfBoundsException exE) {
            // Handle unexpected JSON structure issues
            System.err.println("Unexpected JSON structure: " + exE.getMessage());
        }
        return answer;
    }

    //    public List<String> genRecommendations(List<Movie> movies) throws Exception {
    //        // TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
    //        // List<Movie> movies = tmdbDataAccessObject.searchMoviesByTitle(title);
    //
    //        // JSONObject json = new JSONObject();
    //        List<String> recs = new ArrayList<>();
    //        for (Movie movie : movies) {
    //            // Define the API key and endpoint
    //            String apiKey = "";
    //            String url =
    //            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key="
    //            + apiKey;
    //
    //            // Define the JSON payload
    //            // String jsonPayload = """{"contents": [{"parts": [{"text": "Explain how AI works"}]}]}""";
    //            Map<String, Object> textObject = new HashMap<>();
    //            textObject.put("text", "Can you recommend me 3 movie related to " + movie.getTitle() + "?");
    //
    //            Map<String, Object> partsObject = new HashMap<>();
    //            partsObject.put("parts", List.of(textObject));
    //
    //            Map<String, Object> contentObject = new HashMap<>();
    //            contentObject.put("contents", List.of(partsObject));
    //
    //            // Convert to JSON string
    //            ObjectMapper objectMapper = new ObjectMapper();
    //            String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentObject);
    //
    //            // Create the HttpClient
    //            HttpClient client = HttpClient.newHttpClient();
    //
    //            // Build the HttpRequest
    //            HttpRequest request = HttpRequest.newBuilder()
    //                    .uri(URI.create(url))
    //                    .header("Content-Type", "application/json")
    //                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
    //                    .build();
    //
    //            try {
    //                // Send the request and get the response
    //                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    //
    //                // Print the response
    //                System.out.println("Response Code: " + response.statusCode());
    //                System.out.println("Response Body: " + response.body());
    //
    //                JsonNode jsonNode = objectMapper.readTree(response.body());
    //                String extractedText = jsonNode
    //                        .path("candidates")
    //                        .get(0)
    //                        .path("content") // Navigate to "contents"
    //                        //.path(0) //     First element in the array
    //                        .path("parts") // Navigate to "parts"
    //                        .get(0) // First element in the array
    //                        .path("text") // Get the "text" field
    //                        .asText();
    //                recs.add(extractedText.trim());
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //        }
    //        return recs;
    //   }

    @Override
    public String getJustification(List<String> wantFrom, String recommended) throws JsonProcessingException {
        String answer = "";

        // Define the JSON payload
        // String jsonPayload = """{"contents": [{"parts": [{"text": "Explain how AI works"}]}]}""";
        String question = "Explain in 100 words: Why is " + recommended
                + " is a good recommendation when I want similar movies from ";
        for (String want : wantFrom) {
            question += want + " ";
        }
        question += "?";
        Map<String, Object> textObject = new HashMap<>();
        textObject.put(content, question);

        Map<String, Object> partsObject = new HashMap<>();
        partsObject.put(block, List.of(textObject));

        Map<String, Object> contentObject = new HashMap<>();
        contentObject.put("contents", List.of(partsObject));

        // Convert to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentObject);

        // Create the HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Define the API key and endpoint
        String apiKey = "";
        String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                + "gemini-1.5-flash-latest:generateContent?key=" + apiKey;

        // Build the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            // answer = response.body();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            String extractedText = jsonNode
                    .path("candidates")
                    .get(0)
                    // Navigate to "contents"
                    .path("content")
                    // .path(0); First element in the array; Navigate to "parts"
                    .path(block)
                    // First element in the array
                    .get(0)
                    // Get the "text" field
                    .path(content)
                    .asText();
            answer = extractedText.trim();
        }
        catch (JsonProcessingException exE) {
            // Handle JSON parsing issues
            System.err.println("Error processing JSON: " + exE.getMessage());
        }
        catch (IOException exE) {
            // Handle I/O issues
            System.err.println("I/O error: " + exE.getMessage());
        }
        catch (InterruptedException exE) {
            // Handle interruptions
            System.err.println("Request interrupted: " + exE.getMessage());
            // Restore interrupted status
            Thread.currentThread().interrupt();
        }
        catch (IndexOutOfBoundsException exE) {
            // Handle unexpected JSON structure issues
            System.err.println("Unexpected JSON structure: " + exE.getMessage());
        }
        return answer;
    }
}

