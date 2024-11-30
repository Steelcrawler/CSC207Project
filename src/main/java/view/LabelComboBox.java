package view;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel containing a label and a combo box.
 */
class LabelComboBox extends JPanel {
    LabelComboBox(JLabel label, JComboBox comboBox) {
        this.add(label);
        this.add(comboBox);
    }
}
