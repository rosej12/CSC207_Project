package use_case.GenerateRandomColorPalette;

import data_access.InMemoryColorPaletteRepository;
import entities.Color;
import entities.ColorPalette;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RandomColorPaletteInteractorTest {

    @Test
    public void testEmptyPalette() {
        InMemoryColorPaletteRepository repo = new InMemoryColorPaletteRepository();
        GenerateRandomColorPaletteOutputBoundary presenter = new GenerateRandomColorPaletteOutputBoundary() {
            @Override
            public void presentColorPalette(GenerateRandomColorPaletteResponseModel responseModel) {
                assertNotNull(responseModel.getColorPalette());
                assertEquals(3, responseModel.getColorPalette().getColors().size());
            }

            @Override
            public void presentError(String errorMessage) {

            }
        };

        GenerateRandomColorPaletteInteractor interactor = new GenerateRandomColorPaletteInteractor(presenter, repo);

        interactor.generateRandomColorPalette();

        ColorPalette colorPalette = repo.getColorPalette();
        assertNotNull(colorPalette);
        assertEquals(3, colorPalette.getColors().size());

    }

    @Test
    public void testGeneratePaletteWithExistingColors() {
        // Setup: Existing palette with 3 colors
        InMemoryColorPaletteRepository mockRepository = new InMemoryColorPaletteRepository();
        List<Color> existingColors = new ArrayList<>();
        existingColors.add(new Color(100, 150, 200));
        existingColors.add(new Color(50, 75, 100));
        existingColors.add(new Color(25, 50, 75));
        ColorPalette existingPalette = new ColorPalette(existingColors);
        mockRepository.saveColorPalette(existingPalette);

        GenerateRandomColorPaletteOutputBoundary presenter = new GenerateRandomColorPaletteOutputBoundary() {
            @Override
            public void presentColorPalette(GenerateRandomColorPaletteResponseModel responseModel) {
                // Verify the palette is updated
                ColorPalette updatedPalette = responseModel.getColorPalette();
                assertNotNull(updatedPalette);
                assertEquals(3, updatedPalette.getColors().size());
                // Check that colors are randomized (not original values)
                assertNotEquals(100, updatedPalette.getColors().get(0).getRed());
            }

            @Override
            public void presentError(String errorMessage) {

            }
        };

        GenerateRandomColorPaletteInteractor interactor = new GenerateRandomColorPaletteInteractor(presenter, mockRepository);

        // Act
        interactor.generateRandomColorPalette();

        // Assert
        ColorPalette savedPalette = mockRepository.getColorPalette();
        assertNotNull(savedPalette);
        assertEquals(3, savedPalette.getColors().size());
    }

    @Test
    public void testGenerateRandomColorPaletteWithLockedColors() {
        // Initialize the repository and interactor
        InMemoryColorPaletteRepository repository = new InMemoryColorPaletteRepository();

        // Setup a color palette with a locked color
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(100, 150, 200));  // Unlocked color
        Color lockedColor = new Color(0,0,0);
        lockedColor.setLocked(true);
        colors.add(lockedColor);  // Locked color
        ColorPalette initialPalette = new ColorPalette(colors);
        repository.saveColorPalette(initialPalette);

        GenerateRandomColorPaletteOutputBoundary outputBoundary = new GenerateRandomColorPaletteOutputBoundary() {
            @Override
            public void presentColorPalette(GenerateRandomColorPaletteResponseModel responseModel) {
                ColorPalette colorPalette = responseModel.getColorPalette();
                assertNotNull(colorPalette);
                assertEquals(2, colorPalette.getColors().size());  // Check that there are still 2 colors
                // Ensure that the locked color has not changed
                assertEquals(0, colorPalette.getColors().get(1).getRed());
                assertEquals(0, colorPalette.getColors().get(1).getGreen());
                assertEquals(0, colorPalette.getColors().get(1).getBlue());
            }
            @Override
            public void presentError(String errorMessage) {

            }
        };

        // Create the interactor with repository and output boundary
        GenerateRandomColorPaletteInteractor interactor = new GenerateRandomColorPaletteInteractor(outputBoundary, repository);

        // Execute the use case to generate a random color palette
        interactor.generateRandomColorPalette();

        // Ensure that the color palette was updated correctly
        ColorPalette savedPalette = repository.getColorPalette();
        assertNotNull(savedPalette);
        assertEquals(2, savedPalette.getColors().size());  // Ensure the locked color remained intact
    }
}
