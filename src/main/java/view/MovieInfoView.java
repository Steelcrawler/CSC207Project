package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import interface_adapter.movie_info.MovieInfoController;
import interface_adapter.movie_info.MovieInfoState;
import interface_adapter.movie_info.MovieInfoViewModel;

/**
 * Movie Info View.
 */
public class MovieInfoView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "movie info";

    private final MovieInfoViewModel movieInfoViewModel;

    private JButton backButton;
    private JLabel titleLabel;
    private JLabel ratingLabel;
    private JPanel moviePoster;
    private JLabel trailerLabel;
    private JTextArea reviewText;
    private JPanel reviewPanel;
    private JLabel posterLabel;
    private JTextArea textArea;

    private final int columnNum = 40;
    private final int width = 400;
    private final int height = 200;
    private final int posterWidth = 150;
    private final int posterLength = 250;

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
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setColumns(columnNum);

        textArea.setText("");
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JLabel reviewLabel = new JLabel();
        reviewLabel.setText("One user said: ");

        this.reviewText = new JTextArea();
        reviewText.setEditable(false);
        reviewText.setLineWrap(true);
        reviewText.setWrapStyleWord(true);
        reviewText.setColumns(columnNum);
      
        JScrollPane reviewScrollPane = new JScrollPane(reviewText);
        reviewScrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        reviewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        reviewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        reviewPanel = new JPanel();
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
        reviewPanel.add(reviewLabel);
        reviewPanel.add(reviewScrollPane);

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
        this.add(reviewPanel);
    }

    /**
     * Set the Movie Info Controller for this view.
     * @param movieInfoController the view's movie info controller.
     */
    public void setMovieInfoController(MovieInfoController movieInfoController) {
        this.movieInfoController = movieInfoController;
    }

    /**
     * Get the view name for this view.
     * @return viewName string.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Update the view.
     * @param evt property change
     */
    public void propertyChange(PropertyChangeEvent evt) {
        final MovieInfoState state = (MovieInfoState) evt.getNewValue();

        String movieTitle = state.getMovieTitle();
        double ratingInfo = state.getRatingInfo();
        String plotInfo = state.getPlotInfo();
        String posterPath = state.getPosterPath();

        titleLabel.setText(movieInfoViewModel.MOVIE_TITLE_INFO + movieTitle);
        ratingLabel.setText(movieInfoViewModel.RATING_INFO + ratingInfo);
        textArea.setText(plotInfo);

        List<String> userReviews = state.getUserReviews();
        if (!userReviews.isEmpty()) {
            reviewText.setText(userReviews.get(0));
        }
        else {
            reviewText.setText("No reviews available for this movie.");
        }
        textArea.revalidate();
        textArea.repaint();
        reviewText.revalidate();
        reviewText.repaint();

        reviewPanel.revalidate();
        reviewPanel.repaint();
        try {
            URL posterUrl = new URL(posterPath);
            ImageIcon posterIcon = new ImageIcon(posterUrl);

            int originalWidth = posterIcon.getIconWidth();
            int originalHeight = posterIcon.getIconHeight();

            int maxWidth = posterWidth;
            int maxHeight = posterLength;

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
        catch (MalformedURLException ex) {
            ex.printStackTrace();
            System.out.println("Invalid URL format: " + ex.getMessage());
        }
        String trailerLink = state.getTrailerLink();
        trailerLabel.setText(movieInfoViewModel.TRAILER_INFO + trailerLink);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}
