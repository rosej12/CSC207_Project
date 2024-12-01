package interface_adapter.Drawing;

import use_cases.Drawing.DrawingInputBoundary;

import java.awt.image.RenderedImage;
import java.io.File;

public class DrawingController {

    private final DrawingInputBoundary drawingInteractor;

    public DrawingController(DrawingInputBoundary drawingInteractor) {
        this.drawingInteractor = drawingInteractor;
    }

    public void executeSave(RenderedImage image, File file) {
        drawingInteractor.executeSave(image, file);
    }

    public void executeClear() {
        drawingInteractor.executeClear();
    }

    public void switchToRenderView() {
        drawingInteractor.switchToRenderView();
    }

}
