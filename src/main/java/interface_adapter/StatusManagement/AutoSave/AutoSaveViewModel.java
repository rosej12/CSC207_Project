package interface_adapter.StatusManagement.AutoSave;

import interface_adapter.ViewModel;

public class AutoSaveViewModel extends ViewModel<AutoSaveState> {
    public AutoSaveViewModel() {
        super("Status");
        setState(new AutoSaveState());
    }
}

