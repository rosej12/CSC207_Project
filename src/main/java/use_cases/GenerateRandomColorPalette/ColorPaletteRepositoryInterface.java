package use_cases.GenerateRandomColorPalette;

import entities.ColorPalette;

import java.beans.PropertyChangeListener;

public interface ColorPaletteRepositoryInterface {
    void saveColorPalette(ColorPalette colorPalette);

    ColorPalette getColorPalette();

    void addPropertyChangeListener(PropertyChangeListener listener);
}
