package use_cases.ImageToColorPalette;

public interface ImageToColorPaletteOutputBoundary {
    void presentColorPalette(String[] colors);
    void presentError(String errorMessage);
}
