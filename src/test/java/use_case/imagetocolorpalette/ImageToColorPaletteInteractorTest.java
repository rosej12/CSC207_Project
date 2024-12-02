// File: src/test/java/use_cases/ImageToColorPaletteInteractorTest.java

package use_cases.ImageToColorPalette;

import entities.Color;
import entities.ColorPalette;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import use_cases.ColorPaletteRepositoryInterface;
import use_cases.ImageToColorPalette.ImageToColorPaletteDataAccessInterface;
import use_cases.ImageToColorPalette.ImageToColorPaletteInputBoundary;
import use_cases.ImageToColorPalette.ImageToColorPaletteOutputBoundary;
import use_cases.ImageToColorPalette.ImageToColorPaletteResponseModel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageToColorPaletteInteractorTest {

    private ImageToColorPaletteDataAccessInterface dataAccess;
    private ImageToColorPaletteOutputBoundary outputBoundary;
    private ColorPaletteRepositoryInterface repository;
    private ImageToColorPaletteInputBoundary interactor;

    @BeforeEach
    public void setUp() {
        dataAccess = mock(ImageToColorPaletteDataAccessInterface.class);
        outputBoundary = mock(ImageToColorPaletteOutputBoundary.class);
        repository = mock(ColorPaletteRepositoryInterface.class);
        interactor = new ImageToColorPaletteInteractor(dataAccess, outputBoundary, repository);
    }

    @Test
    public void testGenerateColorPaletteSuccess() throws IOException {
        File imageFile = new File("test_image.jpg");
        String[] hexCodes = {"#FF0000", "#00FF00", "#0000FF"};
        when(dataAccess.getColorPalette(imageFile)).thenReturn(hexCodes);

        interactor.generateColorPalette(imageFile);

        // Verify repository interaction
        ArgumentCaptor<ColorPalette> repositoryCaptor = ArgumentCaptor.forClass(ColorPalette.class);
        verify(repository).saveColorPalette(repositoryCaptor.capture());
        ColorPalette savedPalette = repositoryCaptor.getValue();
        assertEquals(3, savedPalette.size());

        // Verify output boundary interaction
        ArgumentCaptor<ImageToColorPaletteResponseModel> responseCaptor =
                ArgumentCaptor.forClass(ImageToColorPaletteResponseModel.class);
        verify(outputBoundary).presentColorPalette(responseCaptor.capture());
        ImageToColorPaletteResponseModel responseModel = responseCaptor.getValue();
        assertEquals(3, responseModel.getColorPalette().size());
    }

    @Test
    public void testGenerateColorPaletteFailure() throws IOException {
        File imageFile = new File("non_existent_file.jpg");
        when(dataAccess.getColorPalette(imageFile)).thenThrow(new IOException("File not found"));

        interactor.generateColorPalette(imageFile);

        verify(outputBoundary).presentError("Failed to generate color palette: File not found");
        verifyNoMoreInteractions(repository);
    }
}

