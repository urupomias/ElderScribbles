package elderScribbles;

public class Main {
	
	

	public static void main(String [] args) {
		
		MainFrame mainframe = new MainFrame();
		mainframe.addPanel(new SidePanel().getPanel(), "WEST");
		mainframe.addPanel(new CenterPanel().getPanel(), "CENTER");
		mainframe.addPanel(new TopPanel().getPanel(), "NORTH");
		mainframe.setVisible();

	}
}
