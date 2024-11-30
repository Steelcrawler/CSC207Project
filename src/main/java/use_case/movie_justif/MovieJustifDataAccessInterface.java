package use_case.movie_justif;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import entity.Movie;

public interface MovieJustifDataAccessInterface {

    /**
     * Returns a String that is supposed to be similar to 'hello, world!'.
     * @return response of call of 'hello, world!'.
     * @throws JsonProcessingException Json related problems.
     */
    String testWork() throws JsonProcessingException;

    /**
     * Returns the list of recommendations for movies generated in searchMoviesByTitle.
     * @param movies a list of movies that
     * @return recommendations and justification generated from gemini.
     */
    List<String> genRecommendations(List<Movie> movies) throws Exception;

    /**
     * Returns the justification made by Gemini/Chatgpt why the movie is recommended to the user.
     * @param movie the recommended movie.
     * @return the justification made by Gemini/Chatgpt.
     * @throws Exception
     */
    String getJustification(Movie movie) throws Exception;
}
