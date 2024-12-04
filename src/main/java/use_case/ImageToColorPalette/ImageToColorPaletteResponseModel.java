package use_case.ImageToColorPalette;

import entities.ColorPalette;

public class ImageToColorPaletteResponseModel {
    private final ColorPalette colorPalette;

    public ImageToColorPaletteResponseModel(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    /**
     * Retrieves the current color palette.
     *
     * @return the current {@link ColorPalette} stored in the repository; may be null if no palette is set.
     */
    public ColorPalette getColorPalette() {
        return colorPalette;
    }
}
