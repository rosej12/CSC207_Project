package interface_adapter.Drawing;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;

import use_case.Drawing.DrawingInputBoundary;

public class DrawingController {

    private final DrawingInputBoundary drawingInteractor;

    public DrawingController(DrawingInputBoundary drawingInteractor) {
        this.drawingInteractor = drawingInteractor;
    }

    /**
     * Executes the save operation for a rendered image to a specified file.
     *
     * @param image the {@link RenderedImage} to be saved
     * @param file  the {@link File} where the image will be saved
     */
    public void executeSave(RenderedImage image, File file) {
        drawingInteractor.executeSave(image, file);
    }

    /**
     * Executes the clear operation to reset the drawing or clear any existing content.
     */
    public void executeClear() {
        drawingInteractor.executeClear();
    }

    /**
     * Switches to the render view with the provided sketch.
     *
     * @param sketch the {@link Image} sketch to be used in the render view
     */
    public void switchToRenderView(Image sketch) {
        drawingInteractor.switchToRenderView(sketch);
    }

}
