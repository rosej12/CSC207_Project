package use_cases.Drawing;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;

public interface DrawingInputBoundary {
     void executeSave(RenderedImage image, File file);

     void executeClear();

     void switchToRenderView(Image image);
}
