package use_cases.ImageToColorPalette;

import java.io.File;
import java.io.IOException;

public class ImageToColorPaletteInteractor implements ImageToColorPaletteInputBoundary {
    private final ImageToColorPaletteDataAccessInterface dataAccess;
    private final ImageToColorPaletteOutputBoundary outputBoundary;

    public ImageToColorPaletteInteractor(ImageToColorPaletteDataAccessInterface dataAccess,
                                         ImageToColorPaletteOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void generateColorPalette(File imageFile) {
        try {
            String[] colors = dataAccess.getColorPalette(imageFile);
            outputBoundary.presentColorPalette(colors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}