package use_case.movie_justif;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Movie justification use case data access interface.
 */
public interface MovieJustifDataAccessInterface {

    /**
     * Returns a String that is supposed to be similar to 'hello, world!'.
     * @return response of call of 'hello, world!'.
     * @throws JsonProcessingException Json related problems.
     */
    String testWork() throws JsonProcessingException;

    /**
     * Returns the justification made by Gemini/Chatgpt why the movie is recommended to the user.
     * @param wantFrom the movies that the user wants to be recommended from.
     * @param recommended the movie that is recommended to the user.
     * @return the justification made by Gemini/Chatgpt.
     * @throws IOException exception for nonresponse by LLM.
     */
    String getJustification(List<String> wantFrom, String recommended) throws IOException;
}
