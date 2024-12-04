package use_case.GenerateRandomColorPalette;

public interface GenerateRandomColorPaletteOutputBoundary {

    /**
     * Presents the successfully generated random color palette to the view.
     *
     * @param responseModel The response model containing the generated color palette data.
     */
    void presentColorPalette(GenerateRandomColorPaletteResponseModel responseModel);

    /**
     * Presents an error message to the view when the generation of the color palette fails.
     *
     * @param errorMessage The error message describing the failure reason.
     */
    void presentError(String errorMessage);
}
