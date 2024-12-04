package interface_adapter.StatusManagement.AutoSave;

import interface_adapter.ViewModel;

public class AutoSaveViewModel extends ViewModel<AutoSaveState> {
    Boolean isSuccess;

    public AutoSaveViewModel() {
        super("Status");
        setState(new AutoSaveState());
    }
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }
}

