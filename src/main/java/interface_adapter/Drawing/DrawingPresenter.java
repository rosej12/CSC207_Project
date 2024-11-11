package interface_adapter.Drawing;

import use_cases.Drawing.DrawingOutputBoundary;

public class DrawingPresenter implements DrawingOutputBoundary {

    private final DrawingViewModel drawingViewModel;

    public DrawingPresenter(DrawingViewModel drawingViewModel) {
        this.drawingViewModel = drawingViewModel;
    }

    @Override
    public void prepareSuccessView(String drawing) {
        drawingViewModel.getState().setDrawing(drawing);
        drawingViewModel.getState().setError(null);
        drawingViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        drawingViewModel.getState().setError(errorMessage);
        drawingViewModel.firePropertyChanged();
    }
}
