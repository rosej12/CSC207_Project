package interface_adapter.StatusManagement;

import use_cases.StatusManagement.AutoSave.AutoSaveOutputBoundary;

import java.awt.image.RenderedImage;

public class StatusPresenter implements AutoSaveOutputBoundary {
    private StatusViewModel viewModel;

    public StatusPresenter(StatusViewModel viewModel) {
        this.viewModel = new StatusViewModel();
    }

    @Override
    public void prepareSuccessView(RenderedImage drawing) {
        viewModel.getState().setDrawing(drawing);
        viewModel.getState().setError(null);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.getState().setError(errorMessage);
        viewModel.firePropertyChanged();
    }

    /**
     * Provides the updated ViewModel for the UI layer.
     */
    public StatusViewModel getViewModel() {
        return viewModel;
    }
}
