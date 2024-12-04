package interface_adapters.imagetocolorpalette;

import entities.Color;
import entities.ColorPalette;
import interface_adapter.ImageToColorPalette.ImageToColorPalettePresenter;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.ImageToColorPalette.ImageToColorPaletteResponseModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ImageToColorPalettePresenterTest {

    private ImageToColorPalettePresenter presenter;
    private ImageToColorPaletteViewModel viewModel;
    private TestPropertyChangeListener listener;

    @BeforeEach
    public void setUp() {
        viewModel = new ImageToColorPaletteViewModel();
        presenter = new ImageToColorPalettePresenter(viewModel);
        listener = new TestPropertyChangeListener();
        viewModel.addPropertyChangeListener(listener);
    }

    @Test
    public void testPresentColorPalette() {
        ColorPalette palette = new ColorPalette(Arrays.asList(
                new Color("#FF0000"), new Color("#00FF00"), new Color("#0000FF")
        ));
        ImageToColorPaletteResponseModel responseModel = new ImageToColorPaletteResponseModel(palette);

        presenter.presentColorPalette(responseModel);

        assertEquals(palette, viewModel.getState().getColorPalette());
        assertEquals("colorPalette", listener.event.getPropertyName());
    }

    @Test
    public void testPresentError() {
        String errorMessage = "An error occurred";

        presenter.presentError(errorMessage);

        assertEquals(errorMessage, viewModel.getState().getErrorMessage());
        assertEquals("error", listener.event.getPropertyName());
    }

    // Helper class to listen for property changes
    private static class TestPropertyChangeListener implements PropertyChangeListener {
        PropertyChangeEvent event;

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            this.event = evt;
        }
    }
}
