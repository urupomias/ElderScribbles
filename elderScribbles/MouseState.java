package elderScribbles;

public class MouseState {
    
    private static MouseState instance = null;
    boolean pressed = false;
    boolean pressed2 = false;

    // I don't know how to communicate between classes without adding all of them a seperate mouse listener so this is the solution
    // Feel free to come up with a better one.

    public static synchronized MouseState getInstance(){
        if ( null == instance){
            instance = new MouseState();
        }
        return instance;
    }

    public boolean getState(){
        return pressed;
    }
    public void setState(boolean state){
        pressed = state;
    }

    public boolean getState2(){
        return pressed2;
    }
    public void setState2(boolean state){
        pressed2 = state;
    }
}
