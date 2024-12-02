package interface_adapter.StatusManagement;

import interface_adapter.ViewModel;

public class StatusViewModel extends ViewModel<StatusState> {
    public StatusViewModel() {
        super("Status");
        setState(new StatusState());
    }
}

