package use_case.ImageToColorPalette;

public interface ImageToColorPaletteOutputBoundary {

    /**
     * Presents the generated color palette to the view.
     *
     * @param responseModel the response model containing the generated color palette; must not be null.
     */
    void presentColorPalette(ImageToColorPaletteResponseModel responseModel);

    /**
     * Presents an error message to the view.
     *
     * @param errorMessage the error message to be displayed; must not be null or empty.
     */
    void presentError(String errorMessage);
}
