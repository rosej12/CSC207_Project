package use_cases.ImageToColorPalette;

import entities.ColorPalette;

public class ImageToColorPaletteResponseModel {
    private final ColorPalette colorPalette;

    public ImageToColorPaletteResponseModel(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    public ColorPalette getColorPalette() {
        return colorPalette;
    }
}
