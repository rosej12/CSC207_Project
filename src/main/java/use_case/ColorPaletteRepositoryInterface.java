package use_case;

import java.beans.PropertyChangeListener;

import entities.ColorPalette;

public interface ColorPaletteRepositoryInterface {

    /**
     * Saves the provided color palette to the repository.
     *
     * @param colorPalette the {@link ColorPalette} to be saved
     */
    void saveColorPalette(ColorPalette colorPalette);

    /**
     * Retrieves the current color palette stored in the repository.
     *
     * @return the current {@link ColorPalette}, or {@code null} if no palette is stored
     */
    ColorPalette getColorPalette();

    /**
     * Adds a property change listener to the repository. The listener is notified
     * whenever the color palette changes.
     *
     * @param listener the {@link PropertyChangeListener} to add
     */
    void addPropertyChangeListener(PropertyChangeListener listener);
}
