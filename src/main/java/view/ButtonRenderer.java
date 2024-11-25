package view;

import interface_adapter.moviesearch.MovieSearchState;
import interface_adapter.moviesearch.MovieSearchViewModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class for rendering 'Add to watchlist' buttons in the MovieSearchView.
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {


    public ButtonRenderer() {
        setText("Add to watchlist");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
