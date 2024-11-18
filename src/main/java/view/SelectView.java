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
//import interface_adapter.moviesearch.MovieSearchController;
//import interface_adapter.moviesearch.MovieSearchState;
//import interface_adapter.moviesearch.MovieSearchViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.watchlist.WatchlistController;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;

public class SelectView extends JPanel implements ActionListener, ItemListener, PropertyChangeListener {
    private final String viewName = "Watchlist";

    private final SelectViewModel selectViewModel;
    private final JPanel menuPanel;
    private final JButton backButton;
    private final JButton deleteButton;
    private final JScrollPane selectScrollPane;
    private final JPanel moviePanel;
    private final JButton recommendationButton;

    private WatchlistController watchlistController;

    public SelectView(SelectViewModel selectViewModel) {
        this.selectViewModel = selectViewModel;
        this.selectViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        menuPanel = new JPanel(new BorderLayout());

        backButton = new JButton(WatchlistViewModel.BACK_BUTTON_LABEL);
        menuPanel.add(backButton, BorderLayout.WEST);

        deleteButton = new JButton(WatchlistViewModel.SELECT_BUTTON_LABEL);
        menuPanel.add(deleteButton, BorderLayout.EAST);

        JLabel titleLabel = new JLabel(WatchlistViewModel.TITLE_LABEL, SwingConstants.CENTER);
        menuPanel.add(titleLabel, BorderLayout.CENTER);

        this.add(menuPanel, BorderLayout.NORTH);

        moviePanel = new JPanel(new GridLayout(10, 5, 10, 10));

        for (int i = 1; i <= 50; i++) {
            JButton movieButton = new JButton("Movie " + i);
            JCheckBox movieCheckBox = new JCheckBox("Movie" + i);
            JPanel individualMoviePanel = new JPanel(new BorderLayout());
            JPanel checkboxPanel = new JPanel(new FlowLayout());
//            the actual movie stuff will go in this JPanel, the button is a placeholder
            movieButton.setPreferredSize(new Dimension(110, 140)); // Optional: Set preferred size for each button
            individualMoviePanel.add(movieButton);
            checkboxPanel.add(movieCheckBox);
            individualMoviePanel.add(checkboxPanel, BorderLayout.SOUTH);
            moviePanel.add(individualMoviePanel);
        }

        recommendationButton = new JButton("Get a recommendation");
        recommendationButton.setPreferredSize(new Dimension(110, 140));
        moviePanel.add(recommendationButton);

        selectScrollPane = new JScrollPane(moviePanel);
        selectScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        this.add(selectScrollPane, BorderLayout.CENTER);
    }
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setPreferredSize(new Dimension(800, 800));
//
//        final JLabel title = new JLabel(watchlistViewModel.TITLE_LABEL);
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
//
////        final LabelTextPanel titleInfo = new LabelTextPanel(
////                new JLabel(MovieSearchViewModel.MOVIE_TITLE_LABEL), titleTextField);
////        final LabelComboBox genreInfo = new LabelComboBox(
////                new JLabel(MovieSearchViewModel.GENRE_LABEL), genreComboBox);
////        final LabelComboBox ratingInfo = new LabelComboBox(
////                new JLabel(MovieSearchViewModel.RATING_LABEL), ratingComboBox);
//
//        this.menuPanel = new JPanel();
//        this.backButton = new JButton(watchlistViewModel.BACK_BUTTON_LABEL);
//        this.selectButton = new JButton(watchlistViewModel.SELECT_BUTTON_LABEL);
//        menuPanel.add(backButton);
//        menuPanel.add(title);
//        menuPanel.add(selectButton);

//        backButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                if (evt.getSource().equals(searchButton)) {
//                    if (resultsTable != null) {
//                        resultsPanel.remove(resultsTable);
//                    }
//                    final MovieSearchState currentState = movieSearchViewModel.getState();
//                    System.out.println("Title: " + currentState.getTitle());
//
//                    movieSearchController.execute(currentState.getTitle());
//                }
//            }
//        });
//
//        this.revalidate();
//        this.repaint();

//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


    //    public String setResultsTable(JTable newResultsTable) {
//        this.resultsTable = newResultsTable;
//    }
//    private void addTitleListener() {
//        titleTextField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final MovieSearchState currentState = movieSearchViewModel.getState();
//                currentState.setTitle(titleTextField.getText());
//                watchlistViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//    }

//    private void addGenreListener() {
//        genreComboBox.addItemListener(new ItemListener() {
//
//            private void itemListenerHelper() {
//                final MovieSearchState currentState = movieSearchViewModel.getState();
//                currentState.setGenre(genreComboBox.getItemAt(genreComboBox.getSelectedIndex()));
//                movieSearchViewModel.setState(currentState);
//            }
//
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    itemListenerHelper();
//                }
//            }
//        });
//    }
//
//    private void addRatingListener() {
//        ratingComboBox.addItemListener(new ItemListener() {
//
//            private void itemListenerHelper() {
//                final MovieSearchState currentState = movieSearchViewModel.getState();
//                currentState.setRating(ratingComboBox.getItemAt(ratingComboBox.getSelectedIndex()));
//                movieSearchViewModel.setState(currentState);
//            }
//
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    itemListenerHelper();
//                }
//            }
//        });
//    }

    public void propertyChange(PropertyChangeEvent evt) {
        final WatchlistState state = (WatchlistState) evt.getNewValue();

    }

//
//
////        final String[] columnNames = {"Title", "Genre", "Rating", "Plot Synopsis"};
////        Object[][] results = {{"xyz", "xyz", "xyz", "xyz"}};
////        this.add(new JTable(results, columnNames));
//
//        if (state.getSearchFound()) {
//            errorMessageField.setText("");
//            final String[] columnNames = {"Title", "Genre", "Rating", "Plot Synopsis"};
//            resultsTable = new JTable(state.getMoviesInfo(), columnNames);
//            resultsPanel.add(resultsTable);
//            resultsPanel.revalidate();
//            resultsPanel.repaint();
//        }
//    }

    public String getViewName() {
        return viewName;
    }
    public void setWatchlistController(WatchlistController watchlistController) {
        this.watchlistController = watchlistController;
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
