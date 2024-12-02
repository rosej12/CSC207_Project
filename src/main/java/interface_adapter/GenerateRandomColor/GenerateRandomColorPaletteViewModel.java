package interface_adapter.GenerateRandomColor;

import interface_adapter.ViewModel;

public class GenerateRandomColorPaletteViewModel extends ViewModel<GenerateRandomColorPaletteState> {
    public GenerateRandomColorPaletteViewModel() {
        super("colorPalette");
        setState(new GenerateRandomColorPaletteState());
    }
}
