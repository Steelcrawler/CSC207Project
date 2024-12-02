package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

import interface_adapter.add_recommended_to_watchlist.AddRecommendedToWatchlistController;
import interface_adapter.movie_justif.MovieJustifController;
import interface_adapter.recommendation.RecommendationController;
import interface_adapter.recommendation.RecommendationState;
import interface_adapter.recommendation.RecommendationViewModel;

/**
 * The view for the recomendation returned by the recommendation use case.
 */
public class RecommendationView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Recommendation";

    private final RecommendationViewModel recommendationViewModel;

    private final JButton nextButton;
    private JButton backButton;
    private JButton justificationButton;
    private JButton addToWatchlistButton;
    private JLabel titleLabel;
    private JLabel plotLabel;
    private JPanel moviePoster;
    private int recNumber;
    private JLabel posterLabel;

    private JTextArea textArea;

    private RecommendationController recommendationController;
    private MovieJustifController movieJustifController;
    private AddRecommendedToWatchlistController addRecommendedToWatchlistController;

    public RecommendationView(RecommendationViewModel recommendationViewModel) {

        this.setPreferredSize(new Dimension(recommendationViewModel.PANE_SIZE,
                recommendationViewModel.PANE_SIZE));
        this.recommendationViewModel = recommendationViewModel;
        this.recommendationViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.justificationButton = new JButton(recommendationViewModel.JUST_BUTTON);
        this.nextButton = new JButton(recommendationViewModel.NEXT_BUTTON);
        this.addToWatchlistButton = new JButton(RecommendationViewModel.ADD_TO_WATCHLIST_BUTTON_LABEL);
        this.backButton = new JButton(recommendationViewModel.BACK_BUTTON_LABEL);

        JPanel buttonsPanel = new JPanel();

        buttonsPanel.add(this.justificationButton);
        buttonsPanel.add(this.nextButton);
        buttonsPanel.add(this.addToWatchlistButton);

        addToWatchlistButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(addToWatchlistButton)) {
                    final RecommendationState currentState = recommendationViewModel.getState();
                    String movieTitle = currentState.getMovieTitles().get(recNumber);
                    Integer movieID = currentState.getRecIDslist().get(recNumber);
                    String posterPath = currentState.getPosterPaths().get(recNumber);
                    addRecommendedToWatchlistController.execute(movieTitle, movieID, posterPath);
                }
            }
        });

        this.titleLabel = new JLabel(recommendationViewModel.MOVIE_TITLE_INFO);

        this.plotLabel = new JLabel();
        // Create the JTextArea
        this.textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(recommendationViewModel.TAREA_WIDTH,
                recommendationViewModel.TAREA_HEIGHT));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        // Add content to the JTextArea (Optional)
        textArea.setText("");

        // Create the JScrollPane and add the JTextArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(recommendationViewModel.TAREA_WIDTH,
                recommendationViewModel.TAREA_HEIGHT));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.moviePoster = new JPanel();
        this.posterLabel = new JLabel();
        JPanel backPosterTitle = new JPanel();

        backPosterTitle.add(backButton);
        backPosterTitle.add(moviePoster);
        backPosterTitle.add(titleLabel);
        this.add(backPosterTitle);
        this.add(scrollPane);
        this.add(buttonsPanel);

        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backButton)) {
                            recommendationController.toSelectView();
                        }
                    }
                });

        nextButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(nextButton)) {
                            nextHelper();
                        }
                    }
                }
        );

        justificationButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(justificationButton)) {
                            System.out.println(recommendationViewModel.getState().getInputMovieIDs());
                            movieJustifController.execute(recommendationViewModel.getState().getInputMovieIDs(),
                                    recommendationViewModel.getState().getRecIDslist().get(recNumber));
                        }
                    }
                }
        );

    }

    private void nextHelper() {
        if (recNumber + 1 < recommendationViewModel.getState().getRecIDslist().size()) {
            recNumber++;
            System.out.println(recNumber);
            recommendationViewModel.firePropertyChanged();
        }
        else {
            recNumber = 0;
            System.out.println(recNumber);
            recommendationViewModel.firePropertyChanged();
        }

    }

    public void setRecommendationController(RecommendationController recommendationController) {
        this.recommendationController = recommendationController;
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Property change method for retrieving the movie info for the recommendation.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        final RecommendationState state = (RecommendationState) evt.getNewValue();

        String movieTitle = state.getMovieTitles().get(recNumber);
        System.out.println("Movietitle: " + movieTitle);
        String plotInfo = state.getPlots().get(recNumber);
        String posterPath = state.getPosterPaths().get(recNumber);
        System.out.println(posterPath);

        titleLabel.setText(RecommendationViewModel.MOVIE_TITLE_INFO + movieTitle);
        textArea.setText(plotInfo);

        try {
            URL posterURL = new URL(posterPath);
            ImageIcon posterIcon = new ImageIcon(posterURL);

            // Get the original dimensions of the image
            int originalWidth = posterIcon.getIconWidth();
            int originalHeight = posterIcon.getIconHeight();

            // Set max width and height
            int maxWidth = recommendationViewModel.MAX_WIDTH;
            int maxHeight = recommendationViewModel.MAX_HEIGHT;

            // Calculate the scaling factor while maintaining the aspect ratio
            double widthRatio = (double) maxWidth / originalWidth;
            double heightRatio = (double) maxHeight / originalHeight;
            double scaleRatio = Math.min(widthRatio, heightRatio);

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
            }
            else {
                System.out.println("Image loaded successfully");
            }
        }
        catch (MalformedURLException exception) {
            // Handle invalid URL format
            exception.printStackTrace();
            System.out.println("Invalid URL format: " + exception.getMessage());
        }

        if (!state.getMovieAddedToWatchlist().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getMovieAddedToWatchlist());
            state.setMovieAddedToWatchlist("");
            recommendationViewModel.firePropertyChanged();
        }
    }

    public void setMovieJustifController(MovieJustifController movieJustifController) {
        this.movieJustifController = movieJustifController;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void setAddRecommendedToWatchlistController(
            AddRecommendedToWatchlistController addRecommendedToWatchlistController) {
        this.addRecommendedToWatchlistController = addRecommendedToWatchlistController;
    }
}

