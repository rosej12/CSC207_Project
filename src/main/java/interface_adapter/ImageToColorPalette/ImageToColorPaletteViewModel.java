package interface_adapter.ImageToColorPalette;

import interface_adapter.ViewModel;

public class ImageToColorPaletteViewModel extends ViewModel<ImageToColorPaletteState> {
    public ImageToColorPaletteViewModel() {
        super("colorPalette");
        setState(new ImageToColorPaletteState());
    }
}
