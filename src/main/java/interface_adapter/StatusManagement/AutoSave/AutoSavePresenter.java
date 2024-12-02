package interface_adapter.StatusManagement.AutoSave;

import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.Render.RenderViewModel;
import interface_adapter.ViewManagerModel;
import use_cases.StatusManagement.AutoSave.AutoSaveOutputBoundary;

import java.awt.image.RenderedImage;

public class AutoSavePresenter implements AutoSaveOutputBoundary {

    private AutoSaveViewModel viewModel;
    private ViewManagerModel viewManagerModel;
    private DrawingViewModel drawingViewModel;

    public AutoSavePresenter(AutoSaveViewModel viewModel,
                             ViewManagerModel viewManagerModel,
                             DrawingViewModel drawingViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.drawingViewModel = drawingViewModel;
    }

    @Override
    public void prepareSuccessView(RenderedImage drawing) {
        viewModel.getState().setStatus("Success");
        viewModel.getState().setState(drawing);
        viewModel.getState().setError(null);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.getState().setStatus("Unsuccessful");
        viewModel.getState().setError(errorMessage);
        viewModel.firePropertyChanged();
    }

    /**
     * Provides the updated ViewModel for the UI layer.
     */
    public AutoSaveViewModel getViewModel() {
        return viewModel;
    }
}
