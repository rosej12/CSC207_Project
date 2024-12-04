package interface_adapter.GenerateRandomColor;

import entities.ColorPalette;

public class GenerateRandomColorPaletteState {
    private ColorPalette colorPalette;
    private String errorMessage;

    /**
     * Retrieves the current color palette.
     *
     * @return The current {@link ColorPalette} object.
     */
    public ColorPalette getColorPalette() {
        return colorPalette;
    }

    /**
     * Retrieves the current error message.
     *
     * @return The current error message as a {@link String}.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message to the specified value.
     *
     * @param errorMessage The error message to set.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Updates the current color palette to the specified {@link ColorPalette}.
     *
     * @param colorPalette The new color palette to set.
     */
    public void setColorPalette(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }
}
