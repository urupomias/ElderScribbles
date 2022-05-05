package elderScribbles;

public class MouseState {
    private static MouseState instance = null;
    boolean pressed = false;


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
}
