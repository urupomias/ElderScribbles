package elderScribbles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class SidePanel extends JPanel{
    //private JPanel panel;
    //JScrollBar bar = new JScrollBar(JScrollBar.VERTICAL,30,20,0,500);
    private JScrollPane sPanel;
    private ArrayList<Header> testheaders = new ArrayList<>();
    private ArrayList<JLabel> labels = new ArrayList<>();
    private int currentHighlight = -1;
    private int currentPress = -1;

    

    public SidePanel(){
        //panel = new JPanel(new FlowLayout(FlowLayout.LEADING,0,5));
        this.setLayout(new FlowLayout(FlowLayout.LEADING,0,5));
        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(230,5000));
        createTestHeaders();
        addNoteheaders(testheaders, 0);
        /*
        JLabel label = new JLabel("First thing   ");
        label.setFont(new Font("Calibri", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(label);

        JLabel label1_2 = new JLabel();
        label1_2.setText("          Belowuwu");
        label1_2.setFont(new Font("Calibri", Font.BOLD, 15));
        panel.add(label1_2);
        
        JLabel label2 = new JLabel();
        label2.setText("Second thing");
        label2.setFont(new Font("Calibri", Font.BOLD, 30));
        label2.setVerticalTextPosition(JLabel.TOP);
        label2.setBounds(0, 0, 300, 30);
        panel.add(label2);
        */
        this.setAutoscrolls(true);
        sPanel = new JScrollPane(this);
        sPanel.setPreferredSize(new Dimension(250,250));
        
        

    }

    public JScrollPane getPanel(){
        return sPanel;
    }

    public void updateMouse(double x, double y){
        int spacing;
        int height = (int)y - this.getLocationOnScreen().y; 
        spacing = height / 30;
        System.out.println(spacing);
        System.out.println(height);
        if (labels.size() > spacing && spacing >= 0){
            labels.get(spacing).setBackground(Color.LIGHT_GRAY);
            if (MouseState.getInstance().getState()){
                labels.get(spacing).setForeground(Color.GREEN);
                if (currentPress != -1){
                    labels.get(currentPress).setForeground(Color.black);
                    
                }
                currentPress = spacing;
                MouseState.getInstance().setState(false);
            }
            if (currentHighlight != -1 && currentHighlight != spacing){
                labels.get(currentHighlight).setBackground(Color.GRAY);
            }
            currentHighlight = spacing;
        }
        
        

    }

    private void addNoteheaders(ArrayList<Header> headerList, int subCount){
        JLabel label;
        String spaces = "";
        for (int k = 0; k < subCount; k++){
            spaces = spaces + "    ";
        }
        for (int i = 0; i < headerList.size(); i++){
            label = new JLabel();
            label.setText(spaces + headerList.get(i).getName() + "                                      ");
            label.setFont(new Font("Calibri", Font.BOLD, (20)));
            label.setOpaque(true);
            label.setBackground(Color.GRAY);
            this.add(label);
            labels.add(label);

            if (headerList.get(i).getSubheaders().size() > 0){
                for (int j = 0; j < headerList.get(i).getSubheaders().size(); j++){
                    addNoteheaders(headerList.get(i).getSubheaders(), (subCount + 1));
                }
            }
        }
    }

    public void createTestHeaders(){
        testheaders.add(new Header("First header", "First subheader"));
        testheaders.add(new Header("2nd header", "2nd subheader"));
        testheaders.add(new Header("3rd header no sub"));
        testheaders.add(new Header("fourth header", "First subheader"));
    }

    public class Header{
        // This is for testing purposes, this info will come from the notecontainer in the future. 
        private String name;
        private ArrayList<Header> subheaders = new ArrayList<>();
        
        public Header(String name){
            this.name = name;
        }
        public Header(String name, String subheader){
            this.name = name;
            subheaders.add(new Header(subheader));
        }
        public String getName(){
            return name;
        }
        public ArrayList<Header> getSubheaders(){
            return subheaders;
        }
    }
}
