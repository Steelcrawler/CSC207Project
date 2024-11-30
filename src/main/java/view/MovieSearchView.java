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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import interface_adapter.add_to_watchlist.AddToWatchlistController;
import interface_adapter.movie_search.MovieSearchController;
import interface_adapter.movie_search.MovieSearchState;
import interface_adapter.movie_search.MovieSearchViewModel;
import interface_adapter.open_watchlist.OpenWatchlistController;

import static javax.swing.BoxLayout.Y_AXIS;

public class MovieSearchView extends JPanel implements ActionListener, ItemListener, PropertyChangeListener {
    private final String viewName = "movie search";

    private final MovieSearchViewModel movieSearchViewModel;
    private final JTextField titleTextField = new JTextField(30);
    String[] genres = {"None", "War", "Music", "Comedy", "Documentary", "History", "Western", "Adventure", "Fantasy", "Science Fiction", "Animation", "Crime", "Mystery", "Drama", "TV Movie", "Thriller", "Horror", "Action", "Romance", "Family"};
    private final JComboBox<String> genreComboBox = new JComboBox<>(genres);
    String[] ratings = {"None", "> 2", "> 4", "> 6", "> 8"};
    private final JComboBox<String> ratingComboBox = new JComboBox<>(ratings);

    private final JTextField keywordTextField = new JTextField(30);
    private final JList<String> keywordSuggestionsList = new JList<>();
    private final DefaultListModel<String> keywordSuggestionsModel = new DefaultListModel<>();
    private final JPanel tagsPanel = new JPanel();
    private final List<String> keywords = new ArrayList<>();

    private final JButton searchButton;
    private final JButton resetKeywordsButton;
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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(800, 800));

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
        keywordSuggestionsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
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
        buttons.add(searchButton);
        buttons.add(resetKeywordsButton);
        buttons.add(watchlistButton);

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
                    List<String> keywords = currentState.getKeywords();

                    movieSearchController.execute(title, genre, rating, keywords);
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

        this.add(buttons);

        resultsPanel.setLayout(new BoxLayout(resultsPanel, Y_AXIS));
        resultsPanel.setPreferredSize(new Dimension(700, 400));

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found.");
        }
    }

    private void updateKeywordSuggestions() {
        String input = keywordTextField.getText();
        if (input.isEmpty()) {
            keywordSuggestionsModel.clear();
            updateKeywordSuggestionsVisibility();
            return;
        }

        List<String> suggestions = keywords.stream()
                .filter(keyword -> keyword.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());

        keywordSuggestionsModel.clear();
        for (String suggestion : suggestions) {
            keywordSuggestionsModel.addElement(suggestion);
        }

        updateKeywordSuggestionsVisibility();
    }

    private void updateKeywordSuggestionsVisibility() {
        if (keywordSuggestionsModel.isEmpty()) {
            keywordSuggestionsList.setVisible(false);
        } else {
            keywordSuggestionsList.setVisible(true);
            keywordSuggestionsList.setPreferredSize(new Dimension(keywordTextField.getWidth(), keywordSuggestionsModel.size() * 20));
        }
        keywordSuggestionsList.revalidate();
        keywordSuggestionsList.repaint();
    }

    private void addTag(String keyword) {
        JLabel tagLabel = new JLabel(keyword);
        tagLabel.setOpaque(true);
        tagLabel.setBackground(Color.LIGHT_GRAY);
        tagLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tagsPanel.add(tagLabel);
        tagsPanel.revalidate();
        tagsPanel.repaint();
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

    private void updateKeywordState() {
        final MovieSearchState currentState = movieSearchViewModel.getState();
        currentState.getKeywords().clear();
        movieSearchViewModel.setState(currentState);
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

    public void setOpenWatchlistController(OpenWatchlistController openWatchlistController) {
        this.openWatchlistController = openWatchlistController;
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
}



