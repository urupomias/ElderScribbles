package elderScribbles;
import java.awt.MouseInfo;
import java.util.concurrent.TimeUnit;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main{
	
	

	public static void main(String [] args) {
		MainFrame mainframe = new MainFrame();
		SidePanel sidePanel = new SidePanel();
		CenterPanel centerpanel = new CenterPanel();
		sidePanel.addMouseListener(mainframe);
		centerpanel.addMouseListener(mainframe);
		mainframe.addsPanel(sidePanel.getPanel(), "WEST");
		sidePanel.addKeyListener(mainframe);
		mainframe.addPanel(new CenterPanel().getPanel(), "CENTER");
		mainframe.addPanel(new TopPanel(mainframe), "NORTH");
		mainframe.setVisible();

		
		while (true){
			try {

				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        	double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
       	    //System.out.println("X:" + mouseX);
        	//System.out.println("Y:" + mouseY);

			// Checks if the mouse is within the sidepanel, sends update command if yes.
			if (mouseX > sidePanel.getPanel().getLocationOnScreen().x && mouseX < sidePanel.getPanel().getLocationOnScreen().x + 230){
				sidePanel.updateMouse(mouseX, mouseY);
			}
			MouseState.getInstance().setState(false);
			MouseState.getInstance().setState2(false);
			//System.out.println(sidePanel.getPanel().getLocationOnScreen());
		}
		
	}
}
