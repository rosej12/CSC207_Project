package interface_adapter.Render;

import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.ViewManagerModel;
import use_cases.Render.RenderOutputBoundary;
import use_cases.Render.RenderOutputData;

/**
 * The presenter for the Render Use Case.
 */
public class RenderPresenter implements RenderOutputBoundary {
    private final RenderViewModel renderViewModel;
    private final DrawingViewModel drawingViewModel;
    private final ViewManagerModel viewManagerModel;

    public RenderPresenter(ViewManagerModel viewManagerModel, RenderViewModel renderViewModel, DrawingViewModel drawingViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.renderViewModel = renderViewModel;
        this.drawingViewModel = drawingViewModel;
    }
    @Override
    public void prepareSuccessView(RenderOutputData outputData) {
        // On success, update the render states that are shown on the render view

    }

    @Override
    public void prepareFailView(String errorMessage) {
        // on failure, update the render states that are shown on the render view

    }

    @Override
    public void switchToDrawingView() {
        viewManagerModel.setState(drawingViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
