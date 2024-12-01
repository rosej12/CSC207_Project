package use_cases.ImageToColorPalette;

public interface ImageToColorPaletteOutputBoundary {
    void presentColorPalette(ImageToColorPaletteResponseModel responseModel);
    void presentError(String errorMessage);
}
