package use_cases.StatusManagement;

import interface_adapter.Drawing.DrawingState;
import interface_adapter.Drawing.DrawingController;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;


public class AutoSave implements AutoSaveInteractor {
    private final File tempDir = new File(System.getProperty("java.io.tmpdir"),"InkFlow");
    private final DrawingController drawingController;

    public AutoSave(DrawingController drawingController) {
        this.drawingController = drawingController;

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
    }

    // Save state very 5 seconds
    @Override
    public void saveCanvasState(Image state) {
        File tempFile = new File(tempDir, "autosave_" + System.currentTimeMillis() + ".tmp");
        RenderedImage image = (RenderedImage) DrawingState.getDrawing();
        drawingController.executeSave(image, tempFile);
    }
}
