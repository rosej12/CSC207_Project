package use_cases.Drawing.ColorPalette.GenerateRandomColorPalette;

public interface GenerateRandomColorPaletteOutputBoundary {
    void presentColorPalette(GenerateRandomColorPaletteResponseModel responseModel);
    void presentError(String errorMessage);
}
