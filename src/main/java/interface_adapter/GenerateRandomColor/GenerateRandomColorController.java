package interface_adapter.GenerateRandomColor;

import use_case.GenerateRandomColorPalette.GenerateRandomColorPaletteInputBoundary;

public class GenerateRandomColorController {
    private final GenerateRandomColorPaletteInputBoundary inputBoundary;

    public GenerateRandomColorController(GenerateRandomColorPaletteInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Invokes the use case to generate a random color palette.
     * Delegates the operation to the corresponding input boundary to handle the logic.
     */
    public void generateRandomColor() {
        inputBoundary.generateRandomColorPalette();
    }
}
