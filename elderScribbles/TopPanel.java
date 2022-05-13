package elderScribbles;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class TopPanel extends JPanel {
    JPanel panel;
    MainFrame f;

    public TopPanel(MainFrame f){ 
        this.f = f;
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(250,30));
        this.setLayout(new FlowLayout(FlowLayout.LEADING,0,5));
        String[] text = {"Elden Ring","Morrowind","Divinity"};
        JComboBox menu = new JComboBox(text);
        JTextField search = new JTextField("Search");
        System.out.println(f.getWidth());
        search.setPreferredSize(new Dimension(f.getWidth()-270, 30));
        menu.setPreferredSize(new Dimension(250,30));
        this.add(menu);
        this.add(search);

    }

    
}
