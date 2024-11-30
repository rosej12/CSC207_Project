package use_cases.GenerateRandomColorPalette;

public interface GenerateRandomColorPaletteOutputBoundary {
    void presentColorPalette(GenerateRandomColorPaletteResponseModel responseModel);
    void presentError(String errorMessage);
}
