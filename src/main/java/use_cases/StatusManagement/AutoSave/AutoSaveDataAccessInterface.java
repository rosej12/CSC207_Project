package use_cases.StatusManagement.AutoSave;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public interface AutoSaveDataAccessInterface {
    void saveDrawing(Graphics2D g2, File file) throws IOException;
}
