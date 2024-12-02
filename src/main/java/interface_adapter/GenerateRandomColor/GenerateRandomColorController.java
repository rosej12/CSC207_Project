package interface_adapter.GenerateRandomColor;

import use_cases.GenerateRandomColorPalette.GenerateRandomColorPaletteInputBoundary;

public class GenerateRandomColorController {
    private final GenerateRandomColorPaletteInputBoundary inputBoundary;

    public GenerateRandomColorController(GenerateRandomColorPaletteInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void GenerateRandomColor() {
        inputBoundary.generateRandomColorPalette();
    }
}
