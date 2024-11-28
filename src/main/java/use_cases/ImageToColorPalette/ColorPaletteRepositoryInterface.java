package use_cases.ImageToColorPalette;

import entities.ColorPalette;

public interface ColorPaletteRepositoryInterface {
    void saveColorPalette(ColorPalette colorPalette);

    ColorPalette getColorPalette();
}
