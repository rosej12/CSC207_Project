package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {

    @Test
    public void testColorCreationWithRGB() {
        Color color = new Color(255, 165, 0); // Orange
        assertEquals(255, color.getRed());
        assertEquals(165, color.getGreen());
        assertEquals(0, color.getBlue());
        assertEquals("#ffa500", color.getHexCode());
    }

    @Test
    public void testColorCreationWithHexCode() {
        Color color = new Color("#FFA500"); // Orange
        assertEquals(255, color.getRed());
        assertEquals(165, color.getGreen());
        assertEquals(0, color.getBlue());
        assertEquals("#FFA500", color.getHexCode());
    }

    @Test
    public void testHexCodeCaseInsensitivity() {
        Color color = new Color("#ffa500"); // Lowercase hex code
        assertEquals(255, color.getRed());
        assertEquals(165, color.getGreen());
        assertEquals(0, color.getBlue());
        assertEquals("#ffa500", color.getHexCode());
    }

    @Test
    public void testInvalidHexCodeFormat() {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            new Color("#ZZZZZZ");
        });
        assertTrue(exception.getMessage().contains("For input string"));
    }
}
