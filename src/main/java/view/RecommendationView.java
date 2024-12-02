package view;


import interface_adapter.add_recommended_to_watchlist.AddRecommendedToWatchlistController;
import interface_adapter.movie_justif.MovieJustifController;

import interface_adapter.recommendation.RecommendationController;
import interface_adapter.recommendation.RecommendationState;
import interface_adapter.recommendation.RecommendationViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;

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
    JLabel posterLabel;

    private JTextArea textArea;

    private RecommendationController recommendationController;
    private MovieJustifController movieJustifController;
    private AddRecommendedToWatchlistController addRecommendedToWatchlistController;

    public RecommendationView(RecommendationViewModel recommendationViewModel) {

        this.setPreferredSize(new Dimension(800, 800));
        this.recommendationViewModel = recommendationViewModel;
        this.recommendationViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel buttonsPanel = new JPanel();

        this.justificationButton = new JButton(recommendationViewModel.JUST_BUTTON);
        this.nextButton = new JButton(recommendationViewModel.NEXT_BUTTON);
        this.addToWatchlistButton = new JButton(RecommendationViewModel.ADD_TO_WATCHLIST_BUTTON_LABEL);
        this.backButton = new JButton(recommendationViewModel.BACK_BUTTON_LABEL);

        buttonsPanel.add(this.justificationButton);
        buttonsPanel.add(this.nextButton);
        buttonsPanel.add(this.addToWatchlistButton);

        addToWatchlistButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(addToWatchlistButton)) {
                    final RecommendationState currentState = recommendationViewModel.getState();
                    String movieTitle = currentState.getMovieTitles().get(recNumber);
                    Integer movieID = currentState.getRecIDslist().get(recNumber);
                    addRecommendedToWatchlistController.execute(movieTitle, movieID);
                }
            }
        });

        this.titleLabel = new JLabel(recommendationViewModel.MOVIE_TITLE_INFO);

        this.plotLabel = new JLabel();
        // Create the JTextArea
        this.textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(400, 200));
        textArea.setLineWrap(true);  // Wrap lines to fit the width
        textArea.setWrapStyleWord(true); // Wrap lines at word boundaries
        textArea.setEditable(false);

        // Add content to the JTextArea (Optional)
        textArea.setText("");

        // Create the JScrollPane and add the JTextArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
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
                            movieJustifController.execute(recommendationViewModel.getState().getInputMovieIDs(), recommendationViewModel.getState().getRecIDslist().get(recNumber));
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
        } else {
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

    public void propertyChange(PropertyChangeEvent evt) {
        final RecommendationState state = (RecommendationState) evt.getNewValue();

        String movie_title = state.getMovieTitles().get(recNumber);
        System.out.println("Movietitle: " + movie_title);
        String plot_info = state.getPlots().get(recNumber);
        String posterPath = state.getPosterPaths().get(recNumber);
        System.out.println(posterPath);

        titleLabel.setText(RecommendationViewModel.MOVIE_TITLE_INFO + movie_title);
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

