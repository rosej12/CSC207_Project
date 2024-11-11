package use_cases.Drawing;

import java.awt.image.RenderedImage;
import java.io.File;

public interface DrawingInputBoundary {
     void executeSave(RenderedImage image, File file);

     void executeClear();
}
