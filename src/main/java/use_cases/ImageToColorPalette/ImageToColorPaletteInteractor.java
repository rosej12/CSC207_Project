package use_cases.ImageToColorPalette;

import entities.Color;
import entities.ColorPalette;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageToColorPaletteInteractor implements ImageToColorPaletteInputBoundary {
    private final ImageToColorPaletteDataAccessInterface dataAccess;
    private final ImageToColorPaletteOutputBoundary outputBoundary;
    private final ColorPaletteRepositoryInterface colorPaletteRepository;

    public ImageToColorPaletteInteractor(ImageToColorPaletteDataAccessInterface dataAccess,
                                         ImageToColorPaletteOutputBoundary outputBoundary,
                                         ColorPaletteRepositoryInterface colorPaletteRepository) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
        this.colorPaletteRepository = colorPaletteRepository;
    }

    @Override
    public void generateColorPalette(File imageFile) {
        try {
            String[] colorHexCodes = dataAccess.getColorPalette(imageFile);

            // Convert hex codes to Color entities
            List<Color> colors = new ArrayList<>();
            for (String hexCode : colorHexCodes) {
                colors.add(new Color(hexCode));
            }

            // Create ColorPalette entity
            ColorPalette colorPalette = new ColorPalette(colors);

            // Save color palette to repository
            colorPaletteRepository.saveColorPalette(colorPalette);

            // Create response model
            ImageToColorPaletteResponseModel responseModel = new ImageToColorPaletteResponseModel(colorPalette);

            // Pass response to output boundary
            outputBoundary.presentColorPalette(responseModel);
        } catch (IOException e) {
            outputBoundary.presentError("Failed to generate color palette: " + e.getMessage());
        }
    }
}