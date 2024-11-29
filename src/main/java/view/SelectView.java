package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;

import interface_adapter.Select.SelectState;
import interface_adapter.Select.SelectViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
//import interface_adapter.moviesearch.MovieSearchController;
//import interface_adapter.moviesearch.MovieSearchState;
//import interface_adapter.moviesearch.MovieSearchViewModel;
import interface_adapter.recommendation.RecommendationController;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.open_watchlist.OpenWatchlistController;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;
import interface_adapter.delete_from_watchlist.DeleteFromWatchlistController;

public class SelectView extends JPanel implements ActionListener, ItemListener, PropertyChangeListener {
    private final String viewName = "Select";

    private final ViewManagerModel viewManagerModel;
    private final SelectViewModel selectViewModel;
    private RecommendationController recommendationController;
    private final JPanel menuPanel;
    private final JButton backButton;
    private final JButton deleteButton;
    private JScrollPane selectScrollPane;
    private JPanel moviePanel;
    private final JButton recommendationButton;
    private final JPanel eastPanel;

    private DeleteFromWatchlistController deleteFromWatchlistController;

    public SelectView(SelectViewModel selectViewModel, ViewManagerModel viewManagerModel) {
        this.selectViewModel = selectViewModel;
        this.viewManagerModel = viewManagerModel;
        this.selectViewModel.addPropertyChangeListener(this);
        this.setPreferredSize(new Dimension(800, 800));

        this.setLayout(new BorderLayout());

        menuPanel = new JPanel(new BorderLayout());

        backButton = new JButton(SelectViewModel.BACK_BUTTON_LABEL);
        menuPanel.add(backButton, BorderLayout.WEST);

        eastPanel = new JPanel(new BorderLayout());
        deleteButton = new JButton(SelectViewModel.DELETE_BUTTON_LABEL);
        recommendationButton = new JButton(SelectViewModel.REC_BUTTON_LABEL);
        eastPanel.add(deleteButton, BorderLayout.EAST);
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(20, 20));
        eastPanel.add(spacer, BorderLayout.CENTER);
        eastPanel.add(recommendationButton, BorderLayout.WEST);

        recommendationButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(recommendationButton)) {
                            if (selectViewModel.getState().getSelectedMovies().size() == 0) {
                                System.out.println("No movies selected");
                                JOptionPane.showMessageDialog(null, "No movies selected");
                            } else {
                                recommendationController.execute(selectViewModel.getState().getSelectedMovies());
                            }
                        }
                    }
                });

        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backButton)) {
                            viewManagerModel.setState("Watchlist");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        JLabel titleLabel = new JLabel(SelectViewModel.TITLE_LABEL, SwingConstants.CENTER);
        menuPanel.add(titleLabel, BorderLayout.CENTER);
        menuPanel.add(eastPanel, BorderLayout.EAST);

        moviePanel = new JPanel(new GridLayout(10, 5, 10, 10));
        selectScrollPane = new JScrollPane(moviePanel);
        selectScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        this.add(selectScrollPane, BorderLayout.CENTER);
        this.add(menuPanel, BorderLayout.NORTH);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectState currentState = selectViewModel.getState();
                List<Integer> selectedMovies = currentState.getSelectedMovies();
                deleteFromWatchlistController.execute(selectedMovies);
            }
        });
        this.revalidate();
        this.repaint();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final SelectState state = (SelectState) evt.getNewValue();
        if (state.isEmptyWatchlist()) {
            JOptionPane.showMessageDialog(this, "Your watchlist is empty.");
        } else {
            moviePanel.removeAll();
                for (int i = 0; i < state.getWatchlist().size(); i++) {
                    JButton movieButton = new JButton(state.getMovieTitles().get(i));
                    JCheckBox movieCheckBox = new JCheckBox(state.getMovieTitles().get(i));
                    JPanel individualMoviePanel = new JPanel(new BorderLayout());
                    JPanel checkboxPanel = new JPanel(new FlowLayout());

                    int movieID = state.getWatchlist().get(i);
//              the actual movie stuff will go in this JPanel, the button is a placeholder
                    movieButton.setPreferredSize(new Dimension(110, 140));
                    individualMoviePanel.add(movieButton);
                    checkboxPanel.add(movieCheckBox);

                    movieCheckBox.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                state.getSelectedMovies().add(movieID);
                                System.out.println(movieCheckBox.getText() + " selected");
                                System.out.println(state.getSelectedMovies());
                            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                                System.out.println(movieCheckBox.getText() + " deselected");
                                state.getSelectedMovies().remove((Object) movieID);
                                System.out.println(state.getSelectedMovies());
                            }
                        }
                    });
                    individualMoviePanel.add(checkboxPanel, BorderLayout.SOUTH);
                    moviePanel.add(individualMoviePanel);
                }
                moviePanel.revalidate();
                moviePanel.repaint();
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setRecommendationController(RecommendationController recommendationController) {
        this.recommendationController = recommendationController;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        System.out.println("Click " + evt.getStateChange());
    }

    public void setDeleteFromWatchlistController(DeleteFromWatchlistController deleteFromWatchlistController) {
        this.deleteFromWatchlistController = deleteFromWatchlistController;
    }
}

