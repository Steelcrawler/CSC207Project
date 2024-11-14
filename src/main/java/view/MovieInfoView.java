package view;

import interface_adapter.movieinfo.MovieInfoState;
import interface_adapter.movieinfo.MovieInfoViewModel;
import java.awt.Dimension;

import javax.swing.*;

public class MovieInfoView extends JPanel {
    private final String viewName = "movie info";

    private final MovieInfoViewModel movieInfoViewModel;

    private final JButton backButton;
    private final JLabel titleLabel;
    private final JLabel ratingLabel;
    private final JLabel plotLabel;
    private final JLabel trailerLabel;

    public MovieInfoView(MovieInfoViewModel movieInfoViewModel) {

        this.movieInfoViewModel = movieInfoViewModel;
        MovieInfoState current_state = movieInfoViewModel.getState();

        String movie_title = current_state.getMovieTitle();
        String rating_info = current_state.getRatingInfo();
        String plot_info = current_state.getPlotInfo();
        String trailer_link = current_state.getTrailerLink();

        this.titleLabel = new JLabel(movieInfoViewModel.MOVIE_TITLE_INFO + movie_title);
        this.ratingLabel = new JLabel(movieInfoViewModel.RATING_INFO + rating_info);

        this.plotLabel = new JLabel();
        plotLabel.setPreferredSize(new Dimension(100, 50));
        plotLabel.setText(movieInfoViewModel.getState().getPlotInfo());
        this.plotLabel.setText(movieInfoViewModel.PLOT_INFO + plot_info);

        this.trailerLabel = new JLabel(movieInfoViewModel.TRAILER_INFO + trailer_link);
        this.backButton = new JButton(movieInfoViewModel.BACK_BUTTON_LABEL);




    }
}
