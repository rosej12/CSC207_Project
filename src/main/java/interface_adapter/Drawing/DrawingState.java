package interface_adapter.Drawing;

import java.awt.image.RenderedImage;

public class DrawingState {
    private RenderedImage drawing;
    private String error;

    /**
     * Retrieves the current drawing.
     *
     * @return The current drawing as a {@link RenderedImage}.
     */
    public RenderedImage getDrawing() {
        return drawing;
    }

    /**
     * Sets the current drawing.
     *
     * @param drawing The {@link RenderedImage} to set as the current drawing.
     */
    public void setDrawing(RenderedImage drawing) {
        this.drawing = drawing;
    }

    /**
     * Retrieves the current error message.
     *
     * @return The error message as a {@link String}, or null if no error is present.
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the current error message.
     *
     * @param error The {@link String} containing the error message to set.
     */
    public void setError(String error) {
        this.error = error;
    }
}
