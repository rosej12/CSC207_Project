package interfaceadapters.ImageToColorPalette;

import java.io.File;

import usecases.ImageToColorPalette.ImageToColorPaletteInputBoundary;

public class ImageToColorPaletteController {
    private final ImageToColorPaletteInputBoundary inputBoundary;

    public ImageToColorPaletteController(ImageToColorPaletteInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Generates a color palette from the specified image file.
     *
     * @param imageFile the image file used to generate the color palette
     *                  must not be null.
     */
    public void generateColorPalette(File imageFile) {
        inputBoundary.generateColorPalette(imageFile);
    }
}
