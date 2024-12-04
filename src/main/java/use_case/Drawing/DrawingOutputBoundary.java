package use_case.Drawing;

import java.awt.Image;
import java.awt.image.RenderedImage;

public interface DrawingOutputBoundary {

    /**
     * Prepares the success view with the provided drawing.
     *
     * @param drawing The rendered image representing the drawing to display in the success view.
     */
    void prepareSuccessView(RenderedImage drawing);

    /**
     * Prepares the failure view with an error message.
     *
     * @param errorMessage A string containing the error message to display in the failure view.
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches the application view to the render view, displaying the provided sketch.
     *
     * @param sketch The image representing the sketch to display in the render view.
     */
    void switchToRenderView(Image sketch);
}
