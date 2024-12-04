package interface_adapter.Drawing;

import java.awt.Image;
import java.awt.image.RenderedImage;

import interface_adapter.Render.RenderViewModel;
import interface_adapter.ViewManagerModel;
import use_case.Drawing.DrawingOutputBoundary;

public class DrawingPresenter implements DrawingOutputBoundary {

    private final DrawingViewModel drawingViewModel;
    private final RenderViewModel renderViewModel;
    private final ViewManagerModel viewManagerModel;

    public DrawingPresenter(ViewManagerModel viewManagerModel, DrawingViewModel drawingViewModel,
                            RenderViewModel renderViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.drawingViewModel = drawingViewModel;
        this.renderViewModel = renderViewModel;
    }

    @Override
    public void prepareSuccessView(RenderedImage drawing) {
        drawingViewModel.getState().setDrawing(drawing);
        drawingViewModel.getState().setError(null);
        drawingViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        drawingViewModel.getState().setError(errorMessage);
        drawingViewModel.firePropertyChanged();
    }

    @Override
    public void switchToRenderView(Image sketch) {
        viewManagerModel.setState(renderViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
        renderViewModel.getState().setSketch(sketch);
        renderViewModel.firePropertyChanged();
    }

}
