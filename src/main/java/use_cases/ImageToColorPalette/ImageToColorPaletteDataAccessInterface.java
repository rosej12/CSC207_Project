package use_cases.ImageToColorPalette;

import java.io.File;
import java.io.IOException;

public interface ImageToColorPaletteDataAccessInterface {
    String[] getColorPalette(File imageFile) throws IOException;
}
