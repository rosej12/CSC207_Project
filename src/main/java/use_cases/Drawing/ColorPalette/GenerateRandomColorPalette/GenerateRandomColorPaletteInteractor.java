package use_cases.Drawing.ColorPalette.GenerateRandomColorPalette;

import entities.Color;
import entities.ColorPalette;
import use_cases.Drawing.ColorPalette.ColorPaletteRepositoryInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateRandomColorPaletteInteractor implements GenerateRandomColorPaletteInputBoundary{
    private final GenerateRandomColorPaletteOutputBoundary outputBoundary;
    private final ColorPaletteRepositoryInterface colorPaletteRepository;

    public GenerateRandomColorPaletteInteractor(GenerateRandomColorPaletteOutputBoundary outputBoundary, ColorPaletteRepositoryInterface colorPaletteRepository) {
        this.outputBoundary = outputBoundary;
        this.colorPaletteRepository = colorPaletteRepository;
    }

    @Override
    public void generateRandomColorPalette() {
        //Retrieve existing color palette
        ColorPalette existingPalette = colorPaletteRepository.getColorPalette();
        Random rand = new Random();

        if (existingPalette != null) {
            for (Color color : existingPalette.getColors()) {
                if (!color.isLocked()){
                    color.setRed(rand.nextInt(256));
                    color.setGreen(rand.nextInt(256));
                    color.setBlue(rand.nextInt(256));
                }
            }
        } else {
            List<Color> colors = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                colors.add(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            }
            existingPalette = new ColorPalette(colors);
        }

        // Save color palette to repository
        colorPaletteRepository.saveColorPalette(existingPalette);

        // Create response model
        GenerateRandomColorPaletteResponseModel responseModel = new GenerateRandomColorPaletteResponseModel(existingPalette);

        // Pass response to output boundary
        outputBoundary.presentColorPalette(responseModel);
    }
}
