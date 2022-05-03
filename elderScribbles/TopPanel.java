package elderScribbles;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;


public class TopPanel {
    JPanel panel;

    public TopPanel(){
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setPreferredSize(new Dimension(250,40));
    }

    public JPanel getPanel(){
        return panel;
    }
}
