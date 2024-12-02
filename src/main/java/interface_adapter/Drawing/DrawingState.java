package interface_adapter.Drawing;

import java.awt.image.RenderedImage;

public class DrawingState {
    private static RenderedImage drawing;
    private String error;

    public static RenderedImage getDrawing() {
        return drawing;
    }

    public void setDrawing(RenderedImage drawing) {
        DrawingState.drawing = drawing;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
