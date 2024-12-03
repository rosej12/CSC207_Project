package interfaceadapters.Render;

import interfaceadapters.Drawing.DrawingViewModel;
import interfaceadapters.ViewManagerModel;
import usecases.Render.RenderOutputBoundary;
import usecases.Render.RenderOutputData;

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
    public void prepareRenderSuccessView(RenderOutputData outputData) {
        // On success, update the render states that are shown on the render view
        final RenderState renderState = renderViewModel.getState();
        renderState.setRender(outputData.getRenderedImage());
        renderViewModel.setState(renderState);
        renderViewModel.firePropertyChanged();
    }

    @Override
    public void prepareRenderFailView(String errorMessage) {
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

    @Override
    public void saveRender() {

    }
}
