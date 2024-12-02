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

    public void addColor(Color color) {
        colors.add(color);
    }

    public void removeColor(Color color) {
        colors.remove(color);
    }

    public List<Color> getColors() {
        return new ArrayList<>(colors); // Return a copy to maintain encapsulation
    }

    public int size() {
        return colors.size();
    }

    public Color getColor(int index) {
        return colors.get(index);
    }
}