package use_case.movie_rec;

import data_access.OpenAIDataAccessObject;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OpenAIDataAccessObjectTest {

    private OpenAIDataAccessObject dao;
    private OpenAiChatModel mockModel;

    @BeforeEach
    void setUp() {
        mockModel = mock(OpenAiChatModel.class);
        dao = new OpenAIDataAccessObject() {
            protected OpenAiChatModel createModel() {
                return mockModel;
            }
        };
    }

    @Test
    void testTestWork() {
        // Arrange
        when(mockModel.generate("Say 'Hello, World!'")).thenReturn("Hello, World!");

        // Act
        String result = dao.testWork();

        // Assert
        assertEquals("Hello, World!", result);
        verify(mockModel).generate("Say 'Hello, World!'");
    }
}
