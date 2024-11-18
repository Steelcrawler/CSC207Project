package use_case.movie_rec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data_access.GeminiDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class GeminiDataAccessObjectTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockHttpResponse;

    private GeminiDataAccessObject geminiDataAccessObject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        geminiDataAccessObject = new GeminiDataAccessObject();
    }

    @Test
    void testWork_shouldReturnExtractedText() throws Exception {
        // Mocking response body
        // Define the JSON payload
        Map<String, Object> textObject = new HashMap<>();
        textObject.put("text", "Say 'hello, world!'");

        Map<String, Object> partsObject = new HashMap<>();
        partsObject.put("parts", List.of(textObject));

        Map<String, Object> contentObject = new HashMap<>();
        contentObject.put("contents", List.of(partsObject));

        // Convert to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String mockResponseBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contentObject);

        // System.out.println(mockResponseBody);

        // Mocking HTTP response
        when(mockHttpResponse.body()).thenReturn(mockResponseBody);
        when(mockHttpResponse.statusCode()).thenReturn(200);

        // Mocking HTTP client behavior
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        // Setting up HttpClient in GeminiDataAccessObject
        GeminiDataAccessObject geminiDataAccessObject = new GeminiDataAccessObject();
        geminiDataAccessObject.setHttpClient(mockHttpClient);

        System.out.println("Calling testWork...");
        // Call the method
        String result = geminiDataAccessObject.testWork();
        System.out.println("Result: " + result);

        // Validate the result
        assertEquals("Hello, world!", result);

        // Verify interactions
        // verify(mockHttpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        // verify(mockHttpResponse, times(1)).body();
    }
}
