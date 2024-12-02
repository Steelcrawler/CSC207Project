package use_case.movie_justif;

import java.util.List;

/**
 * Input Data for Movie Justification use case.
 */
public class MovieJustifInputData {
    private final List<Integer> wantFrom;
    private final Integer recommended;

    public MovieJustifInputData(List<Integer> wantFrom, Integer recommended) {
        this.wantFrom = wantFrom;
        this.recommended = recommended;
    }

    /**
     * Get the ids of the movies the user wants to be recommended from.
     * @return a list of movie ids.
     */
    public List<Integer> getWantFrom() {
        return wantFrom;
    }

    /**
     * Get the id of the movie recommended to the user.
     * @return a movie id.
     */
    public int getRecommended() {
        return recommended;
    }

}
