package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

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
