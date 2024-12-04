package use_cases.StatusManagement.AutoSave;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public interface AutoSaveInputBoundary {
    void saveCanvasState(RenderedImage state); // Save canvas state to a temporary file
    RenderedImage loadCanvasState(String filePath) throws IOException; // Load state from a file
    void clearTemporaryFiles(); // Delete all temporary files
}
