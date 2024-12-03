package interface_adapter.StatusManagement.AutoSave;

import java.awt.image.RenderedImage;

public class AutoSaveState {
    private static RenderedImage state;
    private String error;
    private static String status;

    public static RenderedImage getState() {
        return state;
    }

    public void setState(RenderedImage newState) {
        AutoSaveState.state = newState;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        AutoSaveState.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
