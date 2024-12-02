package use_cases.StatusManagement.AutoSave;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public interface AutoSaveDataAccessInterface {
    void saveDrawing(RenderedImage g2,String extention, File file) throws IOException;
}
