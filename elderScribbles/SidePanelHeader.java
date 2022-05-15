package elderScribbles;


public class SidePanelHeader{
    
    private String text;
    private int indentation1 = -5;

    public SidePanelHeader(String text, int indentation1){
        this.text = text;
        this.indentation1 = indentation1;

    }

    public String getText(){
        return text;
    }
    public void setText(String text){
       this.text = text;
    }
    public int getIndentation(){
        return indentation1;
    }
    public void setIndentation(int indentation){
        this.indentation1 = indentation;
    }

}
