package data_access;

import entities.ColorPalette;
import use_cases.ImageToColorPalette.ColorPaletteRepositoryInterface;

public class InMemoryColorPaletteRepository implements ColorPaletteRepositoryInterface {
    private ColorPalette colorPalette;

    @Override
    public void saveColorPalette(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    @Override
    public ColorPalette getColorPalette() {
        return colorPalette;
    }
}
