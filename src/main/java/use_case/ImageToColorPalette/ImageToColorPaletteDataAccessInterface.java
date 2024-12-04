package use_case.ImageToColorPalette;

import java.io.File;
import java.io.IOException;

public interface ImageToColorPaletteDataAccessInterface {

    /**
     * Extracts a color palette from the specified image file.
     *
     * @param imageFile the image file used to extract the color palette; must not be null.
     * @return an array of color hex codes extracted from the image.
     * @throws IOException if an I/O error occurs while processing the image file.
     */
    String[] getColorPalette(File imageFile) throws IOException;
}
