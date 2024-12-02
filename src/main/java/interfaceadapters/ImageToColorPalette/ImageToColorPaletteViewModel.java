package interfaceadapters.ImageToColorPalette;

import interfaceadapters.ViewModel;

public class ImageToColorPaletteViewModel extends ViewModel<ImageToColorPaletteState> {
    public ImageToColorPaletteViewModel() {
        super("colorPalette");
        setState(new ImageToColorPaletteState());
    }
}
