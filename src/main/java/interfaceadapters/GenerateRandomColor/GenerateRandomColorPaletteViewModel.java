package interfaceadapters.GenerateRandomColor;

import interfaceadapters.ViewModel;

public class GenerateRandomColorPaletteViewModel extends ViewModel<GenerateRandomColorPaletteState> {
    public GenerateRandomColorPaletteViewModel() {
        super("colorPalette");
        setState(new GenerateRandomColorPaletteState());
    }
}
