package interface_adapter.StatusManagement;

import interface_adapter.ImageToColorPalette.ImageToColorPaletteState;
import interface_adapter.ViewModel;

public class StatusViewModel extends ViewModel<StatusPanel> {
    public StatusViewModel() {
        super("Status");
        setState(new StatusPanel());
    }
}

