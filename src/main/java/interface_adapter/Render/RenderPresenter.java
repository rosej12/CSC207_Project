package interface_adapter.Render;

import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.ViewManagerModel;
import use_case.Render.RenderOutputBoundary;
import use_case.Render.RenderOutputData;

/**
 * The presenter for the Render Use Case.
 */
public class RenderPresenter implements RenderOutputBoundary {
    private final RenderViewModel renderViewModel;
    private final DrawingViewModel drawingViewModel;
    private final ViewManagerModel viewManagerModel;

    public RenderPresenter(ViewManagerModel viewManagerModel, RenderViewModel renderViewModel,
                           DrawingViewModel drawingViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.renderViewModel = renderViewModel;
        this.drawingViewModel = drawingViewModel;
    }

    @Override
    public void prepareSuccessView(RenderOutputData outputData) {
        // On success, update the render states that are shown on the render view
        final RenderState renderState = renderViewModel.getState();
        renderState.setRender(outputData.getRenderedImage());
        renderViewModel.setState(renderState);
        renderViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // On failure, update the error message shown on render view
        final RenderState renderState = renderViewModel.getState();
        renderState.setRenderError(errorMessage);
        renderViewModel.firePropertyChanged();
    }

    @Override
    public void switchToDrawingView() {
        viewManagerModel.setState(drawingViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
