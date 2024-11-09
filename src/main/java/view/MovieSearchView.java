package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
    private final JTable resultsTable = new JTable();
    private MovieSearchController movieSearchController;

    public MovieSearchView(MovieSearchViewModel movieSearchViewModel) {
        this.movieSearchViewModel = movieSearchViewModel;
        this.movieSearchViewModel.addPropertyChangeListener(this);

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
        this.add(errorMessageField);
        this.add(resultsTable);
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
        errorMessageField.setText(state.getErrorMessage());
        resultsTable.
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



