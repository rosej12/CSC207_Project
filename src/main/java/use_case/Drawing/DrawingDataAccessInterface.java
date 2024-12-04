package use_case.Drawing;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public interface DrawingDataAccessInterface {

    /**
     * Saves the provided rendered image to the specified file.
     *
     * @param image the {@link RenderedImage} to be saved
     * @param file  the {@link File} where the image will be stored
     * @throws IOException if an I/O error occurs during the save operation
     */
    void saveDrawing(RenderedImage image, File file) throws IOException;
}
