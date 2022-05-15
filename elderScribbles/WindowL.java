package elderScribbles;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;



public class WindowL implements WindowListener {

    NoteContainer nContainer;

    public WindowL(NoteContainer nContainer){
    this.nContainer = nContainer;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowClosed(WindowEvent e) {
        try{
            nContainer.saveCurrent();
        }catch(IOException x){
            x.printStackTrace();
        }
        
        System.exit(0);
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
