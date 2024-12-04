package interface_adapter.ImageToColorPalette;

import entities.ColorPalette;

public class ImageToColorPaletteState {
    private ColorPalette colorPalette;
    private String errorMessage;

    /**
     * Retrieves the current color palette.
     *
     * @return the current {@link ColorPalette} instance; may be null if no palette is set.
     */
    public ColorPalette getColorPalette() {
        return colorPalette;
    }

    /**
     * Retrieves the current error message.
     *
     * @return the current error message as a {@link String}; may be null if no error is set.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage the error message to set as a {@link String}.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Sets the current color palette.
     *
     * @param colorPalette the {@link ColorPalette} to set.
     */
    public void setColorPalette(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

}
