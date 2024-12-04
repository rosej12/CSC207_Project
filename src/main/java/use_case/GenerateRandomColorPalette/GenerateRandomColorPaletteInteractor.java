package use_case.GenerateRandomColorPalette;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.Color;
import entities.ColorPalette;
import use_case.ColorPaletteRepositoryInterface;

public class GenerateRandomColorPaletteInteractor implements GenerateRandomColorPaletteInputBoundary {
    private static final int COLOR_VALUE_UPPER_LIMIT = 256;
    private static final int HEX_COLOR_COMPONENT_LENGTH = 2;
    private final GenerateRandomColorPaletteOutputBoundary outputBoundary;
    private final ColorPaletteRepositoryInterface colorPaletteRepository;

    public GenerateRandomColorPaletteInteractor(GenerateRandomColorPaletteOutputBoundary outputBoundary,
                                                ColorPaletteRepositoryInterface colorPaletteRepository) {
        this.outputBoundary = outputBoundary;
        this.colorPaletteRepository = colorPaletteRepository;
    }

    @Override
    public void generateRandomColorPalette() {
        // Retrieve existing color palette
        ColorPalette existingPalette = colorPaletteRepository.getColorPalette();
        Random rand = new Random();

        if (existingPalette != null) {
            List<Color> colors = new ArrayList<>();
            for (Color color : existingPalette.getColors()) {
                if (!color.isLocked()) {
                    color.setRed(rand.nextInt(COLOR_VALUE_UPPER_LIMIT));
                    color.setGreen(rand.nextInt(COLOR_VALUE_UPPER_LIMIT));
                    color.setBlue(rand.nextInt(COLOR_VALUE_UPPER_LIMIT));
                }
                colors.add(color);
            }
            existingPalette = new ColorPalette(colors);
        }
        else {
            List<Color> colors = new ArrayList<>();
            for (int i = 0; i < HEX_COLOR_COMPONENT_LENGTH; i++) {
                colors.add(new Color(rand.nextInt(COLOR_VALUE_UPPER_LIMIT), rand.nextInt(COLOR_VALUE_UPPER_LIMIT),
                        rand.nextInt(COLOR_VALUE_UPPER_LIMIT)));
            }
            existingPalette = new ColorPalette(colors);
        }

        // Save color palette to repository
        colorPaletteRepository.saveColorPalette(existingPalette);

        // Create response model
        GenerateRandomColorPaletteResponseModel responseModel =
                new GenerateRandomColorPaletteResponseModel(existingPalette);

        // Pass response to output boundary
        outputBoundary.presentColorPalette(responseModel);
    }
}
