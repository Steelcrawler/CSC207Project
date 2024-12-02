package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.*;

import interface_adapter.movie_justif.MovieJustifController;
import interface_adapter.movie_justif.MovieJustifState;
import interface_adapter.movie_justif.MovieJustifViewModel;

/**
 * Movie Justification use case view.
 */
public class MovieJustifView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "movie justification";
    private final Integer fourHundred = 400;
    private final Integer twoHundred = 200;
    private final Integer sixHundred = 600;
    private final Integer oneFifty = 150;
    private final Integer twoFifty = 250;

    private final MovieJustifViewModel movieJustifViewModel;

    private JLabel posterLabel;

    private JButton backButton;
    private JLabel titleLabel;
    private JLabel ratingLabel;
    private JPanel moviePoster;
    private JLabel trailerLabel;
    private JTextArea reviewText;
    private JPanel reviewPanel;

    private JTextArea textArea;

    private MovieJustifController movieJustifController;

    public MovieJustifView(MovieJustifViewModel movieJustifViewModel) {

        this.movieJustifViewModel = movieJustifViewModel;
        this.movieJustifViewModel.addPropertyChangeListener(this);

        this.setLayout(new FlowLayout());

        this.backButton = new JButton(movieJustifViewModel.BACK_BUTTON_LABEL);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(backButton)) {

                    final MovieJustifState currentState = movieJustifViewModel.getState();
                    movieJustifController.switchView();
                }
            }
        });
        this.titleLabel = new JLabel(movieJustifViewModel.MOVIE_TITLE_INFO);
        this.ratingLabel = new JLabel(movieJustifViewModel.RATING_INFO);

        this.textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(fourHundred, twoHundred));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(true);

        textArea.setText("");
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(fourHundred, twoHundred));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JLabel reviewLabel = new JLabel();
        reviewLabel.setText("One user said: ");
        reviewPanel = new JPanel();
        reviewPanel.add(reviewLabel);

        this.reviewText = new JTextArea();
        reviewText.setPreferredSize(new Dimension(sixHundred, twoHundred));
        reviewText.setLineWrap(true);
        reviewText.setWrapStyleWord(true);
        reviewText.setEditable(true);

        JScrollPane reviewScrollPane = new JScrollPane(reviewText);
        reviewScrollPane.setPreferredSize(new Dimension(fourHundred, twoHundred));
        reviewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reviewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        reviewPanel.add(reviewScrollPane);

        this.moviePoster = new JPanel();
        this.posterLabel = new JLabel();

        this.trailerLabel = new JLabel(movieJustifViewModel.TRAILER_INFO);

        JPanel titleAndRating = new JPanel();
        titleAndRating.setLayout(new BoxLayout(titleAndRating, BoxLayout.PAGE_AXIS));
        titleAndRating.add(titleLabel);
        titleAndRating.add(ratingLabel);

        this.add(backButton);
        this.add(moviePoster);
        this.add(titleAndRating);
        this.add(scrollPane);
        this.add(trailerLabel);
        this.add(reviewPanel);
    }

    /**
     * Go to set controller.
     * @param movieJustifController controller for the view.
     */
    public void setMovieJustifController(MovieJustifController movieJustifController) {
        this.movieJustifController = movieJustifController;

    }

    /**
     * Get the name of the view.
     * @return name of the view.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Change the property of the view.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        final MovieJustifState state = (MovieJustifState) evt.getNewValue();

        String movieTitle = state.getMovieTitle();

        System.out.println("Movie title: " + movieTitle);

        double ratingInfo = state.getRatingInfo();

        String posterPath = state.getPosterPath();

        System.out.println(posterPath);

        titleLabel.setText(movieJustifViewModel.MOVIE_TITLE_INFO + movieTitle);
        ratingLabel.setText(movieJustifViewModel.RATING_INFO + ratingInfo);

        String justifInfo = state.getJustifInfo();

        textArea.setText(movieJustifViewModel.JUSTIF_INFO + justifInfo);

        List<String> userReviews = state.getUserReviews();

        if (!userReviews.isEmpty()) {
            reviewText.setText(userReviews.get(0));
        }

        try {
            URL posterURL = new URL(posterPath);
            ImageIcon posterIcon = new ImageIcon(posterURL);

            int originalWidth = posterIcon.getIconWidth();
            int originalHeight = posterIcon.getIconHeight();

            int maxWidth = oneFifty;
            int maxHeight = twoFifty;

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
            }
            else {
                System.out.println("Image loaded successfully");
            }
        }
        catch (MalformedURLException exmalE) {
            exmalE.printStackTrace();
            System.out.println("Invalid URL format: " + exmalE.getMessage());
        }
        String trailerLink = state.getTrailerLink();

        trailerLabel.setText(movieJustifViewModel.TRAILER_INFO + trailerLink);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}
