package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 * A class for rendering 'Add to watchlist' buttons in the MovieSearchView.
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setText("Add to watchlist");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        return this;
    }
}
