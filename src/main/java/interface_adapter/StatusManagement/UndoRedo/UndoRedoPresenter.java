package interface_adapter.StatusManagement.UndoRedo;
import use_cases.StatusManagement.UndoRedo.UndoRedoOutputBoundary;

import java.awt.*;

public class UndoRedoPresenter implements UndoRedoOutputBoundary {
    private UndoRedoViewModel viewModel;
    public UndoRedoPresenter(UndoRedoViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Provides the updated ViewModel for the UI layer.
     */
    public UndoRedoViewModel getViewModel() {
        return viewModel;
    }


    @Override
    public void changeUndoRedoState(Image image) {
        viewModel.getState().setState(image);
        viewModel.firePropertyChanged("UndoRedo");
    }
}
