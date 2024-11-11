package interface_adapter.Drawing;

import use_cases.Drawing.DrawingInputBoundary;

public class DrawingController {

    private final DrawingInputBoundary drawingInteractor;

    public DrawingController(DrawingInputBoundary drawingInteractor) {
        this.drawingInteractor = drawingInteractor;
    }

    public void execute(String drawing) {
        if (drawing != null) {
            drawingInteractor.executeSave(drawing);
        }
        else {
            drawingInteractor.executeClear();
        }
    }
}
