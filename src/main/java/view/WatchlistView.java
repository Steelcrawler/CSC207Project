package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.Select.SelectViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.moviesearch.MovieSearchState;
import interface_adapter.open_select.OpenSelectController;
import interface_adapter.open_watchlist.OpenWatchlistController;
import interface_adapter.signup.SignupState;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;

public class WatchlistView extends JPanel implements ActionListener, ItemListener, PropertyChangeListener {
    private final String viewName = "Watchlist";

    private final WatchlistViewModel watchlistViewModel;
    private final JPanel menuPanel;
    private final JButton backButton;
    private final JButton selectButton;
    private JScrollPane watchlistScrollPane;
    private JPanel moviePanel;

    private ViewManagerModel viewManagerModel;
    private OpenWatchlistController openWatchlistController;
    private SelectViewModel selectViewModel;
    private OpenSelectController openSelectController;
    public WatchlistView(WatchlistViewModel watchlistViewModel, ViewManagerModel viewManagerModel) {
        this.watchlistViewModel = watchlistViewModel;
        this.watchlistViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        this.setLayout(new BorderLayout());

        menuPanel = new JPanel(new BorderLayout());

        backButton = new JButton(WatchlistViewModel.BACK_BUTTON_LABEL);
        menuPanel.add(backButton, BorderLayout.WEST);

        selectButton = new JButton(WatchlistViewModel.SELECT_BUTTON_LABEL);
        menuPanel.add(selectButton, BorderLayout.EAST);

        JLabel titleLabel = new JLabel(WatchlistViewModel.TITLE_LABEL, SwingConstants.CENTER);
        menuPanel.add(titleLabel, BorderLayout.CENTER);

        this.add(menuPanel, BorderLayout.NORTH);

        selectButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(selectButton)) {
                            selectViewModel.getState().setWatchlist(watchlistViewModel.getState().getWatchlist());
                            selectViewModel.getState().setMovieTitles(watchlistViewModel.getState().getMovieTitles());
                            selectViewModel.getState().setPosterPaths(watchlistViewModel.getState().getPosterPaths());
                            viewManagerModel.setState(selectViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backButton)) {
                            openWatchlistController.switchToMovieSearchView();
                        }
                        }
                    }
        );
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final WatchlistState state = (WatchlistState) evt.getNewValue();
        if (state.isEmptyWatchlist()) {
            // display a message that says your watchlist is empty
        } else {
            moviePanel = new JPanel(new GridLayout(10, 5, 10, 10));

            for (int i = 0; i < state.getWatchlist().size(); i++) {
                JButton movieButton = new JButton(state.getMovieTitles().get(i));
                JPanel individualMoviePanel = new JPanel();
//            the actual movie stuff will go in this JPanel, the button is a placeholder
                movieButton.setPreferredSize(new Dimension(110, 140));
                individualMoviePanel.add(movieButton);
                moviePanel.add(individualMoviePanel);
            }

            watchlistScrollPane = new JScrollPane(moviePanel);
            watchlistScrollPane.getVerticalScrollBar().setUnitIncrement(15);
            this.add(watchlistScrollPane, BorderLayout.CENTER);
        }
    }

    public String getViewName() {
        return viewName;
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
}




