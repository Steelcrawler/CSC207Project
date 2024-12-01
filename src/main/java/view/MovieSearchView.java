package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import interface_adapter.add_to_watchlist.AddToWatchlistController;
import interface_adapter.logout.LogoutController;
import interface_adapter.movie_search.MovieSearchController;
import interface_adapter.movie_search.MovieSearchState;
import interface_adapter.movie_search.MovieSearchViewModel;
import interface_adapter.open_watchlist.OpenWatchlistController;

/**
 * The Movie Search View.
 */
public class MovieSearchView extends JPanel implements ActionListener, ItemListener, PropertyChangeListener {
    private final String viewName = "movie search";
    private final int width = 800;
    private final int height = 800;
    private final int smallerWidth = 700;
    private final int smallerHeight = 400;
    private final int emptyBorderWidth = 5;
    private final int suggestionsMultiplier = 20;
    private final String addToWatchlist = "Add to Watchlist";

    private final MovieSearchViewModel movieSearchViewModel;
    private final JTextField titleTextField = new JTextField(30);
    private String[] genres = {"None", "War", "Music", "Comedy", "Documentary", "History", "Western", "Adventure", 
        "Fantasy", "Science Fiction", "Animation", "Crime", "Mystery", "Drama", "TV Movie", "Thriller", "Horror", 
        "Action", "Romance", "Family"};
    private final JComboBox<String> genreComboBox = new JComboBox<>(genres);
    private String[] ratings = {"None", "> 2", "> 4", "> 6", "> 8"};
    private final JComboBox<String> ratingComboBox = new JComboBox<>(ratings);

    private final JTextField keywordTextField = new JTextField(30);
    private final JList<String> keywordSuggestionsList = new JList<>();
    private final DefaultListModel<String> keywordSuggestionsModel = new DefaultListModel<>();
    private final JPanel tagsPanel = new JPanel();
    private final List<String> keywords = new ArrayList<>();

    private final JButton searchButton;
    private final JButton resetKeywordsButton;
    private final JButton watchlistButton;
    private final JButton logoutButton;
    private final JLabel errorMessageField = new JLabel();
    private JTable resultsTable;  
    private JPanel resultsPanel = new JPanel();

    private MovieSearchController movieSearchController;
    private AddToWatchlistController addToWatchlistController;
    private OpenWatchlistController openWatchlistController;
    private LogoutController logoutController;

    /**
     * Constructor for the Movie Search View.
     * @param movieSearchViewModel the view model for the movie search view
     */
    public MovieSearchView(MovieSearchViewModel movieSearchViewModel) {
        this.movieSearchViewModel = movieSearchViewModel;
        this.movieSearchViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(width, height));

        final JLabel title = new JLabel(movieSearchViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel titleInfo = new LabelTextPanel(
                new JLabel(MovieSearchViewModel.MOVIE_TITLE_LABEL), titleTextField);
        final LabelComboBox genreInfo = new LabelComboBox(
                new JLabel(MovieSearchViewModel.GENRE_LABEL), genreComboBox);
        final LabelComboBox ratingInfo = new LabelComboBox(
                new JLabel(MovieSearchViewModel.RATING_LABEL), ratingComboBox);

        this.add(title);
        this.add(titleInfo);
        this.add(genreInfo);
        this.add(ratingInfo);

        loadKeywords();

        keywordTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateKeywordSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateKeywordSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateKeywordSuggestions();
            }
        });

        keywordSuggestionsList.setModel(keywordSuggestionsModel);
        keywordSuggestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        keywordSuggestionsList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                String selectedKeyword = keywordSuggestionsList.getSelectedValue();
                if (selectedKeyword != null) {
                    addTag(selectedKeyword);
                    keywordTextField.setText("");
                    keywordSuggestionsModel.clear();
                    updateKeywordSuggestionsVisibility();
                }
            }
        });

        this.add(new JLabel("Keywords:"));
        this.add(keywordTextField);
        this.add(new JScrollPane(keywordSuggestionsList));
        this.add(new JLabel("Selected Keywords:"));
        this.add(tagsPanel);

        final JPanel buttons = new JPanel();
        searchButton = new JButton(MovieSearchViewModel.SEARCH_BUTTON_LABEL);
        resetKeywordsButton = new JButton("Reset Keywords");
        watchlistButton = new JButton(MovieSearchViewModel.WATCHLIST_BUTTON_LABEL);
        logoutButton = new JButton("Logout");
        buttons.add(searchButton);
        buttons.add(resetKeywordsButton);
        buttons.add(watchlistButton);
        buttons.add(logoutButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(searchButton)) {
                    if (resultsTable != null) {
                        resultsPanel.remove(resultsTable);
                    }
                    final MovieSearchState currentState = movieSearchViewModel.getState();
                    System.out.println("Title: " + currentState.getTitle());
                    System.out.println("Genre: " + currentState.getGenre());
                    System.out.println("Rating: " + currentState.getRating());
                    System.out.println("Keywords: " + currentState.getKeywords());

                    String title = currentState.getTitle();
                    String genre = currentState.getGenre();
                    String rating = currentState.getRating();
                    List<String> keywords2 = currentState.getKeywords();

                    movieSearchController.execute(title, genre, rating, keywords2);
                }
            }
        });

        resetKeywordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tagsPanel.removeAll();
                tagsPanel.revalidate();
                tagsPanel.repaint();
                updateKeywordState();
            }
        });

        watchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWatchlistController.execute();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutController.switchToSignUpView();
            }
        });

        this.add(buttons);

        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setPreferredSize(new Dimension(smallerWidth, smallerHeight));

        resultsPanel.add(errorMessageField);
        this.add(resultsPanel);

        // Add the note at the bottom
        JLabel noteLabel = new JLabel("Note: Searching by title will not include any other search criteria");
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(noteLabel);

        this.revalidate();
        this.repaint();
        addTitleListener();
        addGenreListener();
        addRatingListener();
        addKeywordListener();
    }

    /**
     * Load the keywords from the persistent data file.
     */
    private void loadKeywords() {
        // Get the base directory (current working directory)
        File baseDir = new File(System.getProperty("user.dir"));
        // Construct the target file path
        File targetFile = new File(baseDir, "persistent_data/keywords_11_22_2024.txt");

        if (targetFile.exists()) {
            System.out.println("File exists: " + targetFile.getAbsolutePath());
            try (BufferedReader reader = new BufferedReader(new FileReader(targetFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    keywords.add(line);
                }
            } 
            catch (IOException exception) {
                exception.printStackTrace();
            }
        } 
        else {
            System.out.println("File not found.");
        }
    }

    /**
     * Update the keyword suggestions based on the current input.
     */
    private void updateKeywordSuggestions() {
        String input = keywordTextField.getText();
        boolean shouldUpdateSuggestions = !input.isEmpty();
    
        if (shouldUpdateSuggestions) {
            List<String> suggestions = keywords.stream()
                    .filter(keyword -> keyword.toLowerCase().startsWith(input.toLowerCase()))
                    .collect(Collectors.toList());
    
            keywordSuggestionsModel.clear();
            for (String suggestion : suggestions) {
                keywordSuggestionsModel.addElement(suggestion);
            }
        } 
        else {
            keywordSuggestionsModel.clear();
        }
    
        updateKeywordSuggestionsVisibility();
    }

    /**
     * Update the visibility of the keyword suggestions list.
     */
    private void updateKeywordSuggestionsVisibility() {
        if (keywordSuggestionsModel.isEmpty()) {
            keywordSuggestionsList.setVisible(false);
        } 
        else {
            keywordSuggestionsList.setVisible(true);
            keywordSuggestionsList.setPreferredSize(new Dimension(keywordTextField.getWidth(), 
                    keywordSuggestionsModel.size() * suggestionsMultiplier));
        }
        keywordSuggestionsList.revalidate();
        keywordSuggestionsList.repaint();
    }

    /**
     * Add a tag to the selected keywords.
     * @param keyword the keyword to add
     */
    private void addTag(String keyword) {
        JLabel tagLabel = new JLabel(keyword);
        tagLabel.setOpaque(true);
        tagLabel.setBackground(Color.LIGHT_GRAY);
        tagLabel.setBorder(BorderFactory.createEmptyBorder(emptyBorderWidth, emptyBorderWidth, emptyBorderWidth, 
            emptyBorderWidth));
        tagsPanel.add(tagLabel);
        tagsPanel.revalidate();
        tagsPanel.repaint();
    }

    /**
     * Add a listener to the title text field.
     */
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

    /**
     * Add a listener to the genre combo box.
     */
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

    /**
     * Add a listener to the rating combo box.
     */
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

    /**
     * Add a listener to the keyword text field.
     */
    private void addKeywordListener() {
        keywordTextField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MovieSearchState currentState = movieSearchViewModel.getState();
                currentState.getKeywords().clear();
                for (int i = 0; i < tagsPanel.getComponentCount(); i++) {
                    JLabel tagLabel = (JLabel) tagsPanel.getComponent(i);
                    currentState.getKeywords().add(tagLabel.getText());
                }
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

    /**
     * Update the keyword state.
     */
    private void updateKeywordState() {
        final MovieSearchState currentState = movieSearchViewModel.getState();
        currentState.getKeywords().clear();
        movieSearchViewModel.setState(currentState);
    }

    /**
     * Update the view based on the new state.
     * @param evt the property change event
     */
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

        if (state.getSearchFound()) {
            errorMessageField.setText("");
            final String[] columnNames = {"Title", "Genre", "Rating", "Plot Synopsis", addToWatchlist};

            // create a DefaultTableModel to store our data
            DefaultTableModel resultsModel = new DefaultTableModel(state.getMoviesInfo(), columnNames);
            // create a JTable with the model
            resultsTable = new JTable(resultsModel);

            // create and display an "add to watchlist" button in each cell in the "Add to Watchlist" column
            resultsTable.getColumn(addToWatchlist).setCellRenderer(new ButtonRenderer());
            // create button editor for each cell in the "Add to Watchlist" column
            resultsTable.getColumn(addToWatchlist).setCellEditor(new ButtonEditor(new JCheckBox(), state, 
                movieSearchViewModel));
            resultsPanel.add(resultsTable);
            resultsPanel.revalidate();
            resultsPanel.repaint();
        }

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
        if (!"".equals(state.getMovieAddedToWatchlist())) {
            JOptionPane.showMessageDialog(this, state.getMovieAddedToWatchlist());
            state.setMovieAddedToWatchlist("");
            movieSearchViewModel.firePropertyChanged();
        }
    }

    /**
     * Get the name of the view.
     * @return the name of the view
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Set the movie search controller.
     * @param movieSearchController the movie search controller
     */
    public void setMovieSearchController(MovieSearchController movieSearchController) {
        this.movieSearchController = movieSearchController;
    }

    /**
     * Set the open watchlist controller.
     * @param openWatchlistController the open watchlist controller
     */
    public void setOpenWatchlistController(OpenWatchlistController openWatchlistController) {
        this.openWatchlistController = openWatchlistController;
    }

    /**
     * Set the add to watchlist controller.
     * @param addToWatchlistController the add to watchlist controller
     */
    public void setAddToWatchlistController(AddToWatchlistController addToWatchlistController) {
        this.addToWatchlistController = addToWatchlistController;
    }

    /**
     * Set the logout controller.
     * @param logoutController the logout controller
     */
    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    /**
     * The ActionListener for the Movie Search View.
     * @param evt the action event
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    /**
     * The ItemListener for the Movie Search View.
     * @param evt the item event
     */
    @Override
    public void itemStateChanged(ItemEvent evt) {
        System.out.println("Click " + evt.getStateChange());
    }
}
