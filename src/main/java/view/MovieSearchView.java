package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.add_to_watchlist.AddToWatchlistController;
import interface_adapter.moviesearch.MovieSearchController;
import interface_adapter.moviesearch.MovieSearchState;
import interface_adapter.moviesearch.MovieSearchViewModel;
import interface_adapter.open_watchlist.OpenWatchlistController;

import static javax.swing.BoxLayout.Y_AXIS;

public class MovieSearchView extends JPanel implements ActionListener, ItemListener, PropertyChangeListener {
    private final String viewName = "movie search";

    private final MovieSearchViewModel movieSearchViewModel;
    private final JTextField titleTextField = new JTextField(30);
    String[] genres = {"Comedy", "Horror"};
    private final JComboBox<String> genreComboBox = new JComboBox<>(genres);
    String[] ratings = {"> 20%", "> 40%", "> 60%", "> 80%"};
    private final JComboBox<String> ratingComboBox = new JComboBox<>(ratings);
    String[] keywords = {"plot twist", "time travel", "conspiracy", "criminal", "monster"};
    private final JComboBox<String> keywordsComboBox = new JComboBox<>(keywords);

    private final JButton searchButton;
    private final JButton watchlistButton;
    private final JLabel errorMessageField = new JLabel();
    private JTable resultsTable;  // Table to display results
    private JPanel resultsPanel = new JPanel();

    private MovieSearchController movieSearchController;
    private AddToWatchlistController addToWatchlistController;
    private OpenWatchlistController openWatchlistController;

    public MovieSearchView(MovieSearchViewModel movieSearchViewModel) {
        this.movieSearchViewModel = movieSearchViewModel;
        this.movieSearchViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, Y_AXIS));
        this.setPreferredSize(new Dimension(1000, 800));

        final JLabel title = new JLabel(movieSearchViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel titleInfo = new LabelTextPanel(
                new JLabel(MovieSearchViewModel.MOVIE_TITLE_LABEL), titleTextField);
        final LabelComboBox genreInfo = new LabelComboBox(
                new JLabel(MovieSearchViewModel.GENRE_LABEL), genreComboBox);
        final LabelComboBox ratingInfo = new LabelComboBox(
                new JLabel(MovieSearchViewModel.RATING_LABEL), ratingComboBox);

        final JPanel buttons = new JPanel();
        searchButton = new JButton(MovieSearchViewModel.SEARCH_BUTTON_LABEL);
        buttons.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(searchButton)) {
                    if (resultsTable != null) {
                        resultsPanel.remove(resultsTable);
                    }
                    final MovieSearchState currentState = movieSearchViewModel.getState();
                    System.out.println("Title: " + currentState.getTitle());

                    movieSearchController.execute(currentState.getTitle());
                }
            }
        });

        watchlistButton = new JButton(MovieSearchViewModel.WATCHLIST_BUTTON_LABEL);
        buttons.add(watchlistButton);

        watchlistButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openWatchlistController.execute();
            }
        });

        this.add(title);
        this.add(titleInfo);
        this.add(genreInfo);
        this.add(ratingInfo);
        this.add(buttons);

        resultsPanel.setLayout(new BoxLayout(resultsPanel, Y_AXIS));
        resultsPanel.setPreferredSize(new Dimension(700, 400));

        resultsPanel.add(errorMessageField);
        this.add(resultsPanel);

        this.revalidate();
        this.repaint();
        addTitleListener();
        addGenreListener();
        addRatingListener();
    }

    private void addTitleListener() {
        titleTextField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MovieSearchState currentState = movieSearchViewModel.getState();
                currentState.setTitle(titleTextField.getText());
                movieSearchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addGenreListener() {
        genreComboBox.addItemListener(new ItemListener() {

            private void itemListenerHelper() {
                final MovieSearchState currentState = movieSearchViewModel.getState();
                currentState.setGenre(genreComboBox.getItemAt(genreComboBox.getSelectedIndex()));
                movieSearchViewModel.setState(currentState);
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemListenerHelper();
                }
            }
        });
    }

    private void addRatingListener() {
        ratingComboBox.addItemListener(new ItemListener() {

            private void itemListenerHelper() {
                final MovieSearchState currentState = movieSearchViewModel.getState();
                currentState.setRating(ratingComboBox.getItemAt(ratingComboBox.getSelectedIndex()));
                movieSearchViewModel.setState(currentState);
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemListenerHelper();
                }
            }
        });
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final MovieSearchState state = (MovieSearchState) evt.getNewValue();
        if (resultsTable != null) {
            resultsPanel.remove(resultsTable);
        }
        if (!state.getSearchFound()) {
            System.out.println(state.getErrorMessage());
            errorMessageField.setForeground(Color.RED);
            errorMessageField.setText(state.getErrorMessage());
            this.revalidate();
            this.repaint();
        }

        // if movie get search found
         if (state.getSearchFound()) {
             errorMessageField.setText("");
             final String[] columnNames = {"Title", "Genre", "Rating", "Plot Synopsis", "Add to Watchlist"};

             // create a DefaultTableModel to store our data
             DefaultTableModel resultsModel = new DefaultTableModel(state.getMoviesInfo(), columnNames);
             // create a JTable with the model
             resultsTable = new JTable(resultsModel);

             // create and display an "add to watchlist" button in each cell in the "Add to Watchlist" column
             resultsTable.getColumn("Add to Watchlist").setCellRenderer(new ButtonRenderer());
             // create button editor for each cell in the "Add to Watchlist" column
             resultsTable.getColumn("Add to Watchlist").setCellEditor(new ButtonEditor(new JCheckBox(), state, movieSearchViewModel));
             resultsPanel.add(resultsTable);
             resultsPanel.revalidate();
             resultsPanel.repaint();
             }

         // if an Add to Watchlist button has been clicked, call the controller on that button's movie
        if (state.getRowOfATWButtonClicked() != -1) {
            // need to know the row of the button that's been clicked to get the movieTitle and movieID
            int rowOfButtonClicked = state.getRowOfATWButtonClicked();
            // set the row of ATW button clicked back to -1 before executing the use case so we're not stuck in a loop
            state.setRowOfATWButtonClicked(-1);
            String movieTitle = (String) state.getMoviesInfo()[rowOfButtonClicked][0];
            Integer movieID = state.getMoviesIDs().get(rowOfButtonClicked);
            addToWatchlistController.execute(movieTitle, movieID);
            movieSearchViewModel.firePropertyChanged();
        }

        // if a movie's been added to the watchlist or it's already been added, create a pop-up
        if (state.getMovieAddedToWatchlist() != "") {
            JOptionPane.showMessageDialog(this, state.getMovieAddedToWatchlist());
            state.setMovieAddedToWatchlist("");
            movieSearchViewModel.firePropertyChanged();
        }
    }

    public String getViewName() {
        return viewName;
    }
    public void setMovieSearchController(MovieSearchController movieSearchController) {
        this.movieSearchController = movieSearchController;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        System.out.println("Click " + evt.getStateChange());
    }
    public void setAddToWatchlistController(AddToWatchlistController addToWatchlistController) {
        this.addToWatchlistController = addToWatchlistController;
    }
    public void setOpenWatchlistController(OpenWatchlistController openWatchlistController) {
        this.openWatchlistController = openWatchlistController;
    }
}



