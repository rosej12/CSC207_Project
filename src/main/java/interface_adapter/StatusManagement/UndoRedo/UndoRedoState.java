package interface_adapter.StatusManagement.UndoRedo;

import java.awt.*;

public class UndoRedoState {
    private Image image;
    public Image getState(){
        return image;}

    public void setState(Image newImage){image = newImage;}
}
