package interface_adapter.StatusManagement.UndoRedo;

import interface_adapter.StatusManagement.AutoSave.AutoSaveState;

import java.awt.image.RenderedImage;

public class UndoRedoState {
    private static RenderedImage state;
    private String error;
    private static String status;

    public RenderedImage getState() {
        return state;
    }

    public void setState(RenderedImage drawing) { UndoRedoState.state = drawing;}

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        UndoRedoState.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
