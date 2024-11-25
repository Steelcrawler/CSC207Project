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
import interface_adapter.open_watchlist.OpenWatchlistController;
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
    private final JPanel eastPanel;

    private SelectController selectController;

    public SelectView(SelectViewModel selectViewModel) {
        this.selectViewModel = selectViewModel;
        this.selectViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        menuPanel = new JPanel(new BorderLayout());

        backButton = new JButton(WatchlistViewModel.BACK_BUTTON_LABEL);
        menuPanel.add(backButton, BorderLayout.WEST);

        eastPanel = new JPanel(new BorderLayout());
        deleteButton = new JButton(WatchlistViewModel.SELECT_BUTTON_LABEL);
        recommendationButton = new JButton("Get a recommendation"); //SelectViewModel isn't implemented yet
        eastPanel.add(deleteButton, BorderLayout.EAST);
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(20, 20));
        eastPanel.add(spacer, BorderLayout.CENTER);
        eastPanel.add(recommendationButton, BorderLayout.WEST);
        JLabel titleLabel = new JLabel(WatchlistViewModel.TITLE_LABEL, SwingConstants.CENTER);
        menuPanel.add(titleLabel, BorderLayout.CENTER);
        menuPanel.add(eastPanel, BorderLayout.EAST);

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

        selectScrollPane = new JScrollPane(moviePanel);
        selectScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        this.add(selectScrollPane, BorderLayout.CENTER);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final WatchlistState state = (WatchlistState) evt.getNewValue();

    }


    public String getViewName() {
        return viewName;
    }
    public void setSelectController(SelectController selectController) {
        this.selectController = selectController;
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
