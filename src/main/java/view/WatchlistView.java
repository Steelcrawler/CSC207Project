package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.ViewManagerModel;
import interface_adapter.movie_info.MovieInfoController;
import interface_adapter.open_watchlist.OpenWatchlistController;
import interface_adapter.select.SelectViewModel;
import interface_adapter.watchlist.WatchlistState;
import interface_adapter.watchlist.WatchlistViewModel;

/**
 * The class for a Watchlist View.
 */
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
    private MovieInfoController movieInfoController;
    private SelectViewModel selectViewModel;

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

        moviePanel = new JPanel(new GridLayout(WatchlistViewModel.MOVIE_PANEL_ROW,
                WatchlistViewModel.MOVIE_PANEL_COLUMN, WatchlistViewModel.UNIT_INCREMENT,
                WatchlistViewModel.UNIT_INCREMENT));
        watchlistScrollPane = new JScrollPane(moviePanel);
        watchlistScrollPane.getVerticalScrollBar().setUnitIncrement(WatchlistViewModel.UNIT_INCREMENT);
        this.add(watchlistScrollPane, BorderLayout.CENTER);
        this.add(menuPanel, BorderLayout.NORTH);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(selectButton)) {
                    selectViewModel.getState().setWatchlist(watchlistViewModel.getState().getWatchlist());
                    selectViewModel.getState().setMovieTitles(watchlistViewModel.getState().getMovieTitles());
                    selectViewModel.getState().setPosterPaths(watchlistViewModel.getState().getPosterPaths());
                    selectViewModel.firePropertyChanged();
                    viewManagerModel.setState(selectViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
            }
        }
        );

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(backButton)) {
                    openWatchlistController.switchToMovieSearchView();
                }
            }
        }
        );
        this.revalidate();
        this.repaint();
    }

    /**
     * A property change method.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        final WatchlistState state = (WatchlistState) evt.getNewValue();
        if (state.isEmptyWatchlist()) {
            JOptionPane.showMessageDialog(this, "Your watchlist is empty.");
        }
        else if (state.getNoSelectedMoviesToDelete() != null) {
            // no movie was selected to delete
            JOptionPane.showMessageDialog(this, state.getNoSelectedMoviesToDelete());
            state.setNoSelectedMoviesToDelete(null);
            watchlistViewModel.firePropertyChanged();
        }
        else {
            moviePanel.removeAll();
            for (int i = 0; i < state.getWatchlist().size(); i++) {
                JButton movieButton = new JButton(state.getMovieTitles().get(i));
                JPanel individualMoviePanel = new JPanel();

                int movieID = state.getWatchlist().get(i);
                movieButton.addActionListener(movie_evt -> movieInfoController.execute(movieID));

                movieButton.setPreferredSize(new Dimension(WatchlistViewModel.MOVIE_BUTTON_WIDTH,
                        WatchlistViewModel.MOVIE_BUTTON_HEIGHT));
                individualMoviePanel.add(movieButton);
                moviePanel.add(individualMoviePanel);
            }
            moviePanel.revalidate();
            moviePanel.repaint();
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setOpenWatchlistController(OpenWatchlistController openWatchlistController) {
        this.openWatchlistController = openWatchlistController;
    }

    public void setSelectViewModel(SelectViewModel selectViewModel) {
        this.selectViewModel = selectViewModel;
    }

    public void setMovieInfoController(MovieInfoController movieInfoController) {
        this.movieInfoController = movieInfoController;
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

