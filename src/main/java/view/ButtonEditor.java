package view;

import interface_adapter.movie_search.MovieSearchState;
import interface_adapter.movie_search.MovieSearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class for editing each 'Add to watchlist' button in the MovieSearchView.
 */
public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private MovieSearchState movieSearchState;
    private MovieSearchViewModel movieSearchViewModel;
    private int clickedRow;

    public ButtonEditor(JCheckBox checkBox, MovieSearchState movieSearchState, MovieSearchViewModel movieSearchViewModel) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        this.movieSearchState = movieSearchState;
        this.movieSearchViewModel = movieSearchViewModel;

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                // tell the state the row of the button that's been clicked
                movieSearchState.setRowOfATWButtonClicked(clickedRow);
                movieSearchViewModel.firePropertyChanged();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null)? "" : value.toString();
        button.setText(label);
        // keep track of the row the button is in
        this.clickedRow = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }
}
