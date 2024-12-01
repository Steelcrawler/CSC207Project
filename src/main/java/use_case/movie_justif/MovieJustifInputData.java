package use_case.movie_justif;

import entity.Movie;

import java.util.List;

public class MovieJustifInputData {
    private final List<Integer> wantFrom;
    private final Integer recommended;

    public MovieJustifInputData(List<Integer> wantFrom, Integer recommended)
    {
        this.wantFrom = wantFrom;
        this.recommended = recommended;
    }

    public List<Integer> getWantFrom() {
        return wantFrom;
    }

    public int getRecommended() {
        return recommended;
    }

}
