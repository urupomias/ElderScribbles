package elderScribbles;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class CenterPanel {
    JPanel panel;

    public CenterPanel(){
        panel = new JPanel();
        panel.setBackground(Color.white);
        //panel.setPreferredSize(new Dimension(250,250));
    }

    public JPanel getPanel(){
        return panel;
    }
}
