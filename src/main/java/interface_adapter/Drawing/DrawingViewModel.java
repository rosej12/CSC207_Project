package interface_adapter.Drawing;

import interface_adapter.ViewModel;

public class DrawingViewModel extends ViewModel<DrawingState> {
    public DrawingViewModel() {
        super("drawing");
        setState(new DrawingState());
    }
}
