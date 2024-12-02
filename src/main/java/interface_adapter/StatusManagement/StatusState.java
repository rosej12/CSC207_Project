package interface_adapter.StatusManagement;

import interface_adapter.Drawing.DrawingState;

import java.awt.image.RenderedImage;

public class StatusState {
    private static RenderedImage drawing;
    private String error;

    public static RenderedImage getDrawing() {
        return drawing;
    }

    public void setDrawing(RenderedImage drawing) {
        StatusState.drawing = drawing;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
