package interface_adapter.StatusManagement.UndoRedo;

import interface_adapter.ViewModel;

public class UndoRedoViewModel extends ViewModel<UndoRedoState> {
    private boolean isSuccess;
    public UndoRedoViewModel() {
        super("UndoRedo");
        setState(new UndoRedoState());
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }
}

