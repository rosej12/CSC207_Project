package interface_adapter.StatusManagement.AutoSave;

import use_cases.StatusManagement.AutoSave.AutoSaveOutputBoundary;

import java.awt.image.RenderedImage;

public class AutoSavePresenter implements AutoSaveOutputBoundary {
    private AutoSaveViewModel viewModel;

    public AutoSavePresenter(AutoSaveViewModel viewModel) {
        this.viewModel = new AutoSaveViewModel();
    }

    @Override
    public void prepareSuccessView(RenderedImage drawing) {
        viewModel.getState().setState(drawing);
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
    public AutoSaveViewModel getViewModel() {
        return viewModel;
    }
}
