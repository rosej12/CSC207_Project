package use_case.Drawing;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;

public interface DrawingInputBoundary {
    /**
      * Executes the operation to save the provided rendered image to a file.
      *
      * @param image the {@link RenderedImage} to be saved
      * @param file  the {@link File} where the image will be stored
      */
    void executeSave(RenderedImage image, File file);

    /**
      * Clears the current drawing or resets the state to an empty canvas.
      */
    void executeClear();

    /**
      * Switches to the render view with the provided sketch image.
      *
      * @param image the {@link Image} representing the sketch to be rendered
      */
    void switchToRenderView(Image image);

}
