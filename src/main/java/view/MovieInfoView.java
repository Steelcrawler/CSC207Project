package view;

import interface_adapter.movieinfo.MovieInfoController;
import interface_adapter.movieinfo.MovieInfoState;
import interface_adapter.movieinfo.MovieInfoViewModel;
import interface_adapter.moviesearch.MovieSearchController;
import interface_adapter.moviesearch.MovieSearchState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class MovieInfoView extends JPanel {
    private final String viewName = "movie info";

    private final MovieInfoViewModel movieInfoViewModel;

    private final JButton backButton;
    private final JLabel titleLabel;
    private final JLabel ratingLabel;
    private final JLabel plotLabel;
    private final JLabel trailerLabel;
    private ImageIcon moviePoster;

    private MovieInfoController movieInfoController;

    public MovieInfoView(MovieInfoViewModel movieInfoViewModel) {

        this.movieInfoViewModel = movieInfoViewModel;
        MovieInfoState current_state = movieInfoViewModel.getState();

        this.setLayout(new BorderLayout());


        String movie_title = current_state.getMovieTitle();
        double rating_info = current_state.getRatingInfo();
        String plot_info = current_state.getPlotInfo();
        String trailer_link = current_state.getTrailerLink();
        String posterPath = current_state.getPosterPath();
        List<String> userReviews = current_state.getUserReviews();

        this.titleLabel = new JLabel(movieInfoViewModel.MOVIE_TITLE_INFO + movie_title);
        this.ratingLabel = new JLabel(movieInfoViewModel.RATING_INFO + rating_info);

        this.plotLabel = new JLabel();
        plotLabel.setPreferredSize(new Dimension(100, 50));
        plotLabel.setText(movieInfoViewModel.getState().getPlotInfo());
        this.plotLabel.setText(movieInfoViewModel.PLOT_INFO + plot_info);

        ImageIcon moviePoster = new ImageIcon(posterPath);
        JLabel moviePosterLabel = new JLabel(moviePoster);

        this.trailerLabel = new JLabel(movieInfoViewModel.TRAILER_INFO + trailer_link);
        this.backButton = new JButton(movieInfoViewModel.BACK_BUTTON_LABEL);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(backButton)) {

                    final MovieInfoState currentState = movieInfoViewModel.getState();
                    movieInfoController.execute(currentState.getMovieID());
                }
            }
        });

        JPanel titleAndRating = new JPanel();
        titleAndRating.add(titleLabel);
        titleAndRating.add(ratingLabel);

        this.add(backButton, BorderLayout.NORTH);
        this.add(moviePosterLabel, BorderLayout.WEST);
        this.add(plotLabel, BorderLayout.EAST);
        this.add(titleAndRating, BorderLayout.CENTER);
        this.add(trailerLabel, BorderLayout.SOUTH);

//        this.add(backButton);
//        this.add(titleLabel);
//        this.add(ratingLabel);
//        this.add(moviePosterLabel);
//        this.add(plotLabel);
//        this.add(trailerLabel);
    }

    public void setMovieInfoController(MovieInfoController movieInfoController) {
        this.movieInfoController = movieInfoController;
    }
    public String getViewName() {
        return viewName;
    }
}
