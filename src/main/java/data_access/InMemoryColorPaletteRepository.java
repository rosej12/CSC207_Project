package data_access;

import entities.ColorPalette;
import use_cases.GenerateRandomColorPalette.ColorPaletteRepositoryInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InMemoryColorPaletteRepository implements ColorPaletteRepositoryInterface {
    private ColorPalette colorPalette;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void saveColorPalette(ColorPalette colorPalette) {
        ColorPalette oldPalette = this.colorPalette;
        this.colorPalette = colorPalette;
        support.firePropertyChange("colorPalette", oldPalette, this.colorPalette);
    }

    @Override
    public ColorPalette getColorPalette() {
        return colorPalette;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}