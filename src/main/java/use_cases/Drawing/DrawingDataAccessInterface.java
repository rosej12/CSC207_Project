package use_cases.Drawing;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public interface DrawingDataAccessInterface {
    void saveDrawing(RenderedImage image, File file) throws IOException;
}
