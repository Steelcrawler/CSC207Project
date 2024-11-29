package view;

import interface_adapter.movieinfo.MovieInfoController;
import interface_adapter.movieinfo.MovieInfoState;
import interface_adapter.movieinfo.MovieInfoViewModel;
import interface_adapter.moviesearch.MovieSearchController;
import interface_adapter.moviesearch.MovieSearchState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.*;

public class MovieInfoView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "movie info";

    private final MovieInfoViewModel movieInfoViewModel;

    private JButton backButton;
    private JLabel titleLabel;
    private JLabel ratingLabel;
    private JPanel moviePoster;
    private JLabel trailerLabel;
    private JPanel reviewPanel;
    JLabel posterLabel;

    private JTextArea textArea;

    private MovieInfoController movieInfoController;

    public MovieInfoView(MovieInfoViewModel movieInfoViewModel) {


        this.movieInfoViewModel = movieInfoViewModel;
        this.movieInfoViewModel.addPropertyChangeListener(this);

        this.setLayout(new FlowLayout());

        this.backButton = new JButton(movieInfoViewModel.BACK_BUTTON_LABEL);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(backButton)) {

                    final MovieInfoState currentState = movieInfoViewModel.getState();
                    movieInfoController.switchView();
                }
            }
        });
        this.titleLabel = new JLabel(movieInfoViewModel.MOVIE_TITLE_INFO);
        this.ratingLabel = new JLabel(movieInfoViewModel.RATING_INFO);

        this.textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(400, 200));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(true);

        textArea.setText("");
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.moviePoster = new JPanel();
        this.posterLabel = new JLabel();

        this.trailerLabel = new JLabel(movieInfoViewModel.TRAILER_INFO);

        JPanel titleAndRating = new JPanel();
        titleAndRating.setLayout(new BoxLayout(titleAndRating, BoxLayout.PAGE_AXIS));
        titleAndRating.add(titleLabel);
        titleAndRating.add(ratingLabel);

        this.add(backButton);
        this.add(moviePoster);
        this.add(titleAndRating);
        this.add(scrollPane);
        this.add(trailerLabel);
    }

    public void setMovieInfoController(MovieInfoController movieInfoController) {
        this.movieInfoController = movieInfoController;

    }
    public String getViewName() {
        return viewName;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final MovieInfoState state = (MovieInfoState) evt.getNewValue();

        String movie_title = state.getMovieTitle();
        System.out.println("Movietitle: " + movie_title);
        double rating_info = state.getRatingInfo();
        String plot_info = state.getPlotInfo();
        String trailer_link = state.getTrailerLink();
        String posterPath = state.getPosterPath();
        System.out.println(posterPath);
        List<String> userReviews = state.getUserReviews();

        JLabel reviewLabel = new JLabel();
        reviewLabel.setText("One user said: ");
        JPanel reviewPanel = new JPanel();
        reviewPanel.add(reviewLabel);

        JTextArea reviewText = new JTextArea();
        reviewText.setPreferredSize(new Dimension(600, 200));
        reviewText.setLineWrap(true);
        reviewText.setWrapStyleWord(true);
        reviewText.setEditable(true);

        reviewText.setText(userReviews.get(0));
        JScrollPane reviewScrollPane = new JScrollPane(reviewText);
        reviewScrollPane.setPreferredSize(new Dimension(400, 200));
        reviewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reviewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        reviewPanel.add(reviewScrollPane);
        this.add(reviewPanel);

        titleLabel.setText(movieInfoViewModel.MOVIE_TITLE_INFO + movie_title);
        ratingLabel.setText(movieInfoViewModel.RATING_INFO + rating_info);
        textArea.setText(plot_info);

        try {
        URL posterURL = new URL(posterPath);
        ImageIcon posterIcon = new ImageIcon(posterURL);

            int originalWidth = posterIcon.getIconWidth();
            int originalHeight = posterIcon.getIconHeight();

            int maxWidth = 150;
            int maxHeight = 250;

            double widthRatio = (double) maxWidth / originalWidth;
            double heightRatio = (double) maxHeight / originalHeight;
            double scaleRatio = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (originalWidth * scaleRatio);
            int newHeight = (int) (originalHeight * scaleRatio);

            Image img = posterIcon.getImage();
            Image resizedImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            posterIcon = new ImageIcon(resizedImg);

            posterLabel.setIcon(posterIcon);
            posterLabel.setPreferredSize(new Dimension(newWidth, newHeight));
        moviePoster.add(posterLabel);
            if (posterIcon.getIconWidth() == -1 || posterIcon.getIconHeight() == -1) {
                System.out.println("Failed to load image");
            } else {
                System.out.println("Image loaded successfully");}
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Invalid URL format: " + e.getMessage());
        }
        trailerLabel.setText(movieInfoViewModel.TRAILER_INFO + trailer_link);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}
