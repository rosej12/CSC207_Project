package entities;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ColorPaletteTest {

    @Test
    public void testAddColor() {
        ColorPalette palette = new ColorPalette();
        Color color = new Color(255, 0, 0); // Red
        palette.addColor(color);
        assertEquals(1, palette.size());
        assertEquals(color, palette.getColor(0));
    }

    @Test
    public void testRemoveColor() {
        Color color1 = new Color(255, 0, 0); // Red
        Color color2 = new Color(0, 255, 0); // Green
        ColorPalette palette = new ColorPalette(Arrays.asList(color1, color2));
        palette.removeColor(color1);
        assertEquals(1, palette.size());
        assertEquals(color2, palette.getColor(0));
    }

    @Test
    public void testGetColorsReturnsCopy() {
        Color color = new Color(0, 0, 255); // Blue
        ColorPalette palette = new ColorPalette(Collections.singletonList(color));
        palette.getColors().clear(); // Attempt to modify the returned list
        assertEquals(1, palette.size()); // Original list should remain unchanged
    }
}

