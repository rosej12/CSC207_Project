package interface_adapter.ImageToColorPalette;

import use_cases.ImageToColorPalette.ImageToColorPaletteInputBoundary;

import java.io.File;

public class ImageToColorPaletteController {
    private final ImageToColorPaletteInputBoundary inputBoundary;

    public ImageToColorPaletteController(ImageToColorPaletteInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void generateColorPalette(File imageFile) {
        inputBoundary.generateColorPalette(imageFile);
    }
}
