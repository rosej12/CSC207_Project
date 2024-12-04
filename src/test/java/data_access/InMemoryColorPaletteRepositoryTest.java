package data_access;

import entities.Color;
import entities.ColorPalette;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryColorPaletteRepositoryTest {

    private InMemoryColorPaletteRepository repository;
    private TestPropertyChangeListener listener;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryColorPaletteRepository();
        listener = new TestPropertyChangeListener();
        repository.addPropertyChangeListener(listener);
    }

    @Test
    public void testSaveAndGetColorPalette() {
        ColorPalette palette = new ColorPalette(Arrays.asList(
                new Color("#FF0000"),
                new Color("#00FF00"),
                new Color("#0000FF")
        ));

        repository.saveColorPalette(palette);
        ColorPalette retrievedPalette = repository.getColorPalette();

        assertEquals(palette, retrievedPalette);
        assertEquals("colorPalette", listener.event.getPropertyName());
        assertNull(listener.event.getOldValue());
        assertEquals(palette, listener.event.getNewValue());
    }

    @Test
    public void testPropertyChangeListener() {
        assertNull(listener.event);

        ColorPalette palette = new ColorPalette();
        repository.saveColorPalette(palette);

        assertNotNull(listener.event);
        assertEquals("colorPalette", listener.event.getPropertyName());
    }

    // Helper class for listening to property changes
    private static class TestPropertyChangeListener implements PropertyChangeListener {
        PropertyChangeEvent event;

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            this.event = evt;
        }
    }
}
