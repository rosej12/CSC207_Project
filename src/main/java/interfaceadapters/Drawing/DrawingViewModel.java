package interfaceadapters.Drawing;

import interfaceadapters.ViewModel;

public class DrawingViewModel extends ViewModel<DrawingState> {
    public DrawingViewModel() {
        super("Drawing");
        setState(new DrawingState());
    }
}
