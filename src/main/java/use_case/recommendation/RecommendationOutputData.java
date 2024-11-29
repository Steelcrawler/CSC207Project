package use_case.recommendation;

import java.util.List;

public class RecommendationOutputData {
    private List<Integer> movieIDsList;
    private List<String> movieTitlesList;
    private List<String> posterPathsList;
    private final boolean useCaseFailed;

    public RecommendationOutputData(List<Integer> movieIDsList, List<String> movieTitlesList, List<String> posterPathsList, boolean useCaseFailed) {
        this.movieIDsList = movieIDsList;
        this.movieTitlesList = movieTitlesList;
        this.posterPathsList = posterPathsList;
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public List<Integer> getMovieIDsList() {
        return movieIDsList;
    }

    public List<String> getMovieTitlesList() {
        return movieTitlesList;
    }

    public List<String> getPosterPathsList() {
        return posterPathsList;
    }
}
