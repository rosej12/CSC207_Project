package use_cases.Drawing;

public interface DrawingOutputBoundary {

    void prepareSuccessView(String drawing);

    void prepareFailView(String errorMessage);
}
