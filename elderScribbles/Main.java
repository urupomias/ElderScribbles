package elderScribbles;
import java.awt.MouseInfo;
import java.util.concurrent.TimeUnit;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main{
	
	

	public static void main(String [] args) {
		StartUpPane startuppane = new StartUpPane();
        while(startuppane.hasFrame()) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
		System.out.println("nimi : " + startuppane.getChosenNotes());

		MainFrame mainframe = new MainFrame();
		SidePanel sidePanel = new SidePanel(mainframe);
		CenterPanel centerpanel = new CenterPanel();
		NoteContainer noteContainer = new NoteContainer(startuppane.getChosenNotes(), centerpanel, sidePanel);
		sidePanel.setNoteContainer(noteContainer);
		sidePanel.addMouseListener(mainframe);
		centerpanel.addMouseListener(mainframe);
		mainframe.addsPanel(sidePanel.getPanel(), "WEST");
		mainframe.addPanel(centerpanel, "CENTER");
		mainframe.addPanel(new TopPanel(mainframe,sidePanel,noteContainer,startuppane.getChosenNotes()), "NORTH");
		mainframe.setVisible();

		int counter= 0;
		while (true){
			try {

				TimeUnit.MILLISECONDS.sleep(10);
				counter++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        	double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
       	    //System.out.println("X:" + mouseX);
        	//System.out.println("Y:" + mouseY);

			// Checks if the mouse is within the sidepanel, sends update command if yes.
			try{
				if (mouseX > sidePanel.getPanel().getLocationOnScreen().x && mouseX < sidePanel.getPanel().getLocationOnScreen().x + 230){
					sidePanel.updateMouse(mouseX, mouseY);
				}
				else{
					sidePanel.offScreen();
				}
				MouseState.getInstance().setState(false);
				MouseState.getInstance().setState2(false);
			}catch(IOException e){
				e.printStackTrace();
			}
			if(counter == 2000){
				try{
					noteContainer.saveCurrent();
					counter = 0;
				}catch(IOException x){
					x.printStackTrace();
				}
				
			}
			
			//System.out.println(sidePanel.getPanel().getLocationOnScreen());
		}
		
	}
}
