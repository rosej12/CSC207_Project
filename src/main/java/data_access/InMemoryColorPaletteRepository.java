package data_access;

import entities.ColorPalette;
import use_cases.ImageToColorPalette.ColorPaletteRepositoryInterface;

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
        System.out.println("Saving color palette with " + colorPalette.size() + " colors");
    }

    @Override
    public ColorPalette getColorPalette() {
        System.out.println("Retrieving color palette");
        return colorPalette;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
