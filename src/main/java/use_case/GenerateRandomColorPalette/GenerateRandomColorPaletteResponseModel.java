package use_case.GenerateRandomColorPalette;

import entities.ColorPalette;

public class GenerateRandomColorPaletteResponseModel {
    private final ColorPalette colorPalette;

    public GenerateRandomColorPaletteResponseModel(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    /**
     * Retrieves the current color palette.
     *
     * @return The current {@link ColorPalette} object.
     */
    public ColorPalette getColorPalette() {
        return colorPalette;
    }
}
