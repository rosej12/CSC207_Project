package use_cases.Drawing;

public class DrawingInteractor implements DrawingInputBoundary {

    private final DrawingOutputBoundary outputBoundary;
    private final DrawingDataAccessInterface dataAccessInterface;

    public DrawingInteractor(DrawingOutputBoundary outputBoundary, DrawingDataAccessInterface dataAccessInterface) {
        this.outputBoundary = outputBoundary;
        this.dataAccessInterface = dataAccessInterface;
    }

    @Override
    public void executeSave(String drawing) {
        if (drawing != null) {
            outputBoundary.prepareSuccessView(drawing);
        }
        else {
            outputBoundary.prepareFailView("Drawing is null");
        }
    }

    @Override
    public void executeClear() {
        outputBoundary.prepareFailView("Drawing is null");
    }
}
