package interface_adapter.StatusManagement;

import interface_adapter.Drawing.DrawingState;

import java.awt.image.RenderedImage;

public class StatusPanel {
    private static RenderedImage image;
    private String error;

    public static RenderedImage getDrawing() {
        return image;
    }

    public void setDrawing(RenderedImage drawing) {
        StatusPanel.image = image;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
