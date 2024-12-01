package use_case.recommendation;

import java.util.List;

import entity.Movie;

/**
 * The interface for the recommendation use case data access.
 */
public interface RecommendationDataAccessInterface {

    /**
     * Return a list of movies similar to one movie.
     * @param movies the list of movie ids.
     * @return a list of movie objects.
     */
    List<Movie> searchRecommendations(List<Integer> movies);

    /**
     * Returns the keyword ids from the given movie.
     * @param ids the movie id.
     * @return a list of keyword ids.
     */
    List<Integer> getKeywordIDs(int ids);

    /**
     * Returns a list of recommended movies.
     * @param keywords the list of keyword ids to search with.
     * @return a list of movies.
     */
    List<Movie> searchMoviesByKeywordsHelper(List<Integer> keywords);
}
