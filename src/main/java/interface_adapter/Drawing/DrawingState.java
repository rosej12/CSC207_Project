package interface_adapter.Drawing;

import java.awt.image.RenderedImage;

public class DrawingState {
    private RenderedImage drawing;
    private String error;

    public RenderedImage getDrawing() {
        return drawing;
    }

    public void setDrawing(RenderedImage drawing) {
        this.drawing = drawing;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
