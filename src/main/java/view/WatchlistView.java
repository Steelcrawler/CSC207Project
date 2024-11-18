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

public class WatchlistView extends JPanel implements ActionListener, ItemListener, PropertyChangeListener {
    private final String viewName = "Watchlist";

    private final WatchlistViewModel watchlistViewModel;
    private final JPanel menuPanel;
    private final JButton backButton;
    private final JButton selectButton;
    private JScrollPane watchlistScrollPane = new JScrollPane();
    private final JPanel moviePanel;

    private WatchlistController watchlistController;

    public WatchlistView(WatchlistViewModel watchlistViewModel) {
        this.watchlistViewModel = watchlistViewModel;
        this.watchlistViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        menuPanel = new JPanel(new BorderLayout());

        backButton = new JButton(WatchlistViewModel.BACK_BUTTON_LABEL);
        menuPanel.add(backButton, BorderLayout.WEST);

        selectButton = new JButton(WatchlistViewModel.SELECT_BUTTON_LABEL);
        menuPanel.add(selectButton, BorderLayout.EAST);

        JLabel titleLabel = new JLabel(WatchlistViewModel.TITLE_LABEL, SwingConstants.CENTER);
        menuPanel.add(titleLabel, BorderLayout.CENTER);

        this.add(menuPanel, BorderLayout.NORTH);

        moviePanel = new JPanel(new GridLayout(10, 5, 10, 10));

        for (int i = 1; i <= 50; i++) {
            JButton movieButton = new JButton("Movie " + i);
            JPanel individualMoviePanel = new JPanel();
//            the actual movie stuff will go in this JPanel, the button is a placeholder
            movieButton.setPreferredSize(new Dimension(110, 140));
            individualMoviePanel.add(movieButton);
            moviePanel.add(individualMoviePanel);
        }

        watchlistScrollPane = new JScrollPane(moviePanel);
        watchlistScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        this.add(watchlistScrollPane, BorderLayout.CENTER);

        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backButton)) {
                            final WatchlistState currentState = watchlistViewModel.getState();

                            watchlistController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                }
        )
    }

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




