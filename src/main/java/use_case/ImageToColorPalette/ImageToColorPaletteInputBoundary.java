package use_case.ImageToColorPalette;

import java.io.File;

public interface ImageToColorPaletteInputBoundary {

    /**
     * Generates a color palette from the specified image file.
     *
     * @param imageFile the image file used to generate the color palette; must not be null.
     * @throws IllegalArgumentException if the image file is null or invalid.
     */
    void generateColorPalette(File imageFile);
}
