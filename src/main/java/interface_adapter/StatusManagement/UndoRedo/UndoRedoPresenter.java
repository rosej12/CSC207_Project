package interface_adapter.StatusManagement.UndoRedo;

import use_cases.StatusManagement.AutoSave.AutoSaveOutputBoundary;

import java.awt.image.RenderedImage;

public class UndoRedoPresenter implements AutoSaveOutputBoundary {
    private UndoRedoViewModel viewModel;

    public UndoRedoPresenter(UndoRedoViewModel viewModel) {
        this.viewModel = new UndoRedoViewModel();
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
    public UndoRedoViewModel getViewModel() {
        return viewModel;
    }
}
