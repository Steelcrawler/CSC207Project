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

    private JButton testButton;
    private JButton backButton;
    private JLabel titleLabel;
    private JLabel ratingLabel;
    private JLabel plotLabel;
    private JPanel moviePoster;
    private JLabel trailerLabel;
    JLabel posterLabel;
//    private ImageIcon moviePoster;

    private JTextArea textArea;

    private MovieInfoController movieInfoController;

    public MovieInfoView(MovieInfoViewModel movieInfoViewModel) {


        this.movieInfoViewModel = movieInfoViewModel;
        movieInfoViewModel.addPropertyChangeListener(this);

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
        this.testButton = new JButton("Test");
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(testButton)) {

                    final MovieInfoState currentState = movieInfoViewModel.getState();
                    System.out.println("Currentstatemovieid: " + currentState.getMovieID());
                    movieInfoController.execute(currentState.getMovieID());
                }
            }
        });
        this.titleLabel = new JLabel(movieInfoViewModel.MOVIE_TITLE_INFO);
        this.ratingLabel = new JLabel(movieInfoViewModel.RATING_INFO);

        this.plotLabel = new JLabel();
        // Create the JTextArea
        this.textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(400, 200));
        textArea.setLineWrap(true);  // Wrap lines to fit the width
        textArea.setWrapStyleWord(true); // Wrap lines at word boundaries
        textArea.setEditable(true);  // Allow editing

        // Add content to the JTextArea (Optional)
        textArea.setText("plottttt");

        // Create the JScrollPane and add the JTextArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        this.plotLabel.add(scrollPane);
//        this.plotLabel.setText(movieInfoViewModel.PLOT_INFO);

        this.moviePoster = new JPanel();
        this.posterLabel = new JLabel();

        this.trailerLabel = new JLabel(movieInfoViewModel.TRAILER_INFO);

        JPanel titleAndRating = new JPanel();
        titleAndRating.add(titleLabel);
        titleAndRating.add(ratingLabel);

        this.add(testButton);
//        this.add(backButton, BorderLayout.NORTH);
        this.add(moviePoster);
        this.add(titleAndRating);
        this.add(scrollPane);
//        this.add(plotLabel, BorderLayout.CENTER);

        this.add(trailerLabel);

//        this.add(testButton, BorderLayout.NORTH);
////        this.add(backButton, BorderLayout.NORTH);
//        this.add(moviePosterLabel, BorderLayout.WEST);
//        this.add(titleAndRating, BorderLayout.CENTER);
//        this.add(scrollPane, BorderLayout.CENTER);
////        this.add(plotLabel, BorderLayout.CENTER);
//
//        this.add(trailerLabel, BorderLayout.SOUTH);

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

        titleLabel.setText(movieInfoViewModel.MOVIE_TITLE_INFO + movie_title);
        ratingLabel.setText(movieInfoViewModel.RATING_INFO + rating_info);
        textArea.setText(plot_info);

        try {
        URL posterURL = new URL(posterPath);
        ImageIcon posterIcon = new ImageIcon(posterURL);


            // Get the original dimensions of the image
            int originalWidth = posterIcon.getIconWidth();
            int originalHeight = posterIcon.getIconHeight();

            // Set max width and height
            int maxWidth = 150;
            int maxHeight = 250;

            // Calculate the scaling factor while maintaining the aspect ratio
            double widthRatio = (double) maxWidth / originalWidth;
            double heightRatio = (double) maxHeight / originalHeight;
            double scaleRatio = Math.min(widthRatio, heightRatio); // Use the smaller ratio to preserve aspect ratio

            // Calculate the new width and height based on the scaling factor
            int newWidth = (int) (originalWidth * scaleRatio);
            int newHeight = (int) (originalHeight * scaleRatio);

            // Resize the image
            Image img = posterIcon.getImage();
            Image resizedImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            // Create a new ImageIcon from the resized image
            posterIcon = new ImageIcon(resizedImg);

            posterLabel.setIcon(posterIcon);
            posterLabel.setPreferredSize(new Dimension(newWidth, newHeight));
        moviePoster.add(posterLabel);
            if (posterIcon.getIconWidth() == -1 || posterIcon.getIconHeight() == -1) {
                System.out.println("Failed to load image");
            } else {
                System.out.println("Image loaded successfully");}
        } catch (MalformedURLException e) {
            // Handle invalid URL format
            e.printStackTrace();
            System.out.println("Invalid URL format: " + e.getMessage());
        }


        trailerLabel.setText(movieInfoViewModel.TRAILER_INFO + trailer_link);





//        titleLabel.setText(state.getMovieTitle());
//        ratingLabel.setText(ratingLabel.getText() + state.getRatingInfo());
//        plotLabel.setText(state.getPlotInfo());
//        trailerLabel.setText(state.getTrailerLink());

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}
