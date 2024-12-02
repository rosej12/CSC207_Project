package use_cases.GenerateRandomColorPalette;

import entities.ColorPalette;

public class GenerateRandomColorPaletteResponseModel {
    private final ColorPalette colorPalette;

    public GenerateRandomColorPaletteResponseModel(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    public ColorPalette getColorPalette() {
        return colorPalette;
    }
}
