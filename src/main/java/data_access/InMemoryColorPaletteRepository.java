package data_access;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import entities.ColorPalette;
import use_case.ColorPaletteRepositoryInterface;

public class InMemoryColorPaletteRepository implements ColorPaletteRepositoryInterface {
    private ColorPalette colorPalette;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void saveColorPalette(ColorPalette colorPalette1) {
        ColorPalette oldPalette = this.colorPalette;
        this.colorPalette = colorPalette1;
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
