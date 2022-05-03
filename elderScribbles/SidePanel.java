package elderScribbles;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class SidePanel {
    JPanel panel;

    public SidePanel(){
        panel = new JPanel();
        panel.setBackground(Color.gray);
        panel.setPreferredSize(new Dimension(250,250));
    }

    public JPanel getPanel(){
        return panel;
    }
}
