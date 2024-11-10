package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import entity.Movie;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.moviesearch.MovieSearchController;
import interface_adapter.moviesearch.MovieSearchState;
import interface_adapter.moviesearch.MovieSearchViewModel;
import interface_adapter.signup.SignupViewModel;

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
    private final JLabel errorMessageField = new JLabel();

//    private final String[] columnNames = {"Title", "Genre", "Rating", "Plot Synopsis"};
//    private Object[][] data = {
//            {"Kathy", "Smith",
//                    "Snowboarding", "xyz"},
//            {"John", "Doe",
//                    "Rowing", "xyz"},
//            {"Sue", "Black",
//                    "Knitting", "xyz"},
//            {"Jane", "White",
//                    "Speed reading", "xyz"},
//            {"Joe", "Brown",
//                    "Pool", "xyz"}
//    };
//    private JTable resultsTable = new JTable(data, columnNames);
//JTable resultsTable = new JTable(new DefaultTableModel());
//    private Object[][] data = {
//        {"", "",
//                "", ""},
//        {"", "",
//                "", ""},
//        {"", "",
//                "", ""},
//        {"", "",
//                "", ""},
//        {"", "",
//                "", ""}
//        };
//    JTable resultsTable = new JTable(data, columnNames);

//    private JTable resultsTable = new JTable();
    private MovieSearchController movieSearchController;

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

        final JPanel buttons = new JPanel();
        searchButton = new JButton(MovieSearchViewModel.SEARCH_BUTTON_LABEL);
        buttons.add(searchButton);

//        final JPanel resultsPanel = new JPanel();
//        this.resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

//        errorMessageField.setText(movieSearchViewModel.getState().getErrorMessage());

//        resultsTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));
//        resultsTable.setFillsViewportHeight(true);
//        JScrollPane scrollPane = new JScrollPane(resultsTable);
//
//        // Add errorMessageField and scrollPane to resultsPanel
//        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
//        resultsPanel.add(errorMessageField);
//        resultsPanel.add(scrollPane);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(searchButton)) {
                    final MovieSearchState currentState = movieSearchViewModel.getState();

                    movieSearchController.execute(currentState.getTitle());
                }
            }
        });
        this.add(title);
        this.add(titleInfo);
        this.add(genreInfo);
        this.add(ratingInfo);
        this.add(buttons);

        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setPreferredSize(new Dimension(400, 400));

        resultsPanel.add(errorMessageField);
        this.add(resultsPanel);

        this.revalidate();
        this.repaint();

//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

//    public String setResultsTable(JTable newResultsTable) {
//        this.resultsTable = newResultsTable;
//    }
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
        System.out.println(state.getErrorMessage());
        errorMessageField.setForeground(Color.RED);
        errorMessageField.setText(state.getErrorMessage());
        this.revalidate();
        this.repaint();

        if (state.getSearchFound()) {
            final String[] columnNames = {"Title", "Genre", "Rating", "Plot Synopsis"};
            JTable resultsTable = new JTable(state.getMoviesInfo(), columnNames);
            this.add(resultsTable);
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
}



