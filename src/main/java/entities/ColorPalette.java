package entities;

import java.util.ArrayList;
import java.util.List;

public class ColorPalette {
    private final List<Color> colors;

    public ColorPalette() {
        this.colors = new ArrayList<>();
    }

    public ColorPalette(List<Color> colors) {
        this.colors = new ArrayList<>(colors);
    }

    // Methods to manipulate the palette

    /**
     * Adds a color to the color palette.
     *
     * @param color the {@link Color} to be added to the palette.
     */
    public void addColor(Color color) {
        colors.add(color);
    }

    /**
     * Removes a color from the color palette.
     *
     * @param color the {@link Color} to be removed from the palette.
     */
    public void removeColor(Color color) {
        colors.remove(color);
    }

    /**
     * Retrieves all colors in the color palette as a new list.
     * This ensures encapsulation by returning a copy of the internal list.
     *
     * @return a new {@link ArrayList} containing all {@link Color} objects in the palette.
     */
    public List<Color> getColors() {
        return new ArrayList<>(colors);
    }

    /**
     * Retrieves the number of colors in the color palette.
     *
     * @return the total number of {@link Color} objects in the palette.
     */
    public int size() {
        return colors.size();
    }

    /**
     * Retrieves a color from the color palette by its index.
     *
     * @param index the index of the {@link Color} to retrieve.
     * @return the {@link Color} at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size()).
     */
    public Color getColor(int index) {
        return colors.get(index);
    }
}
