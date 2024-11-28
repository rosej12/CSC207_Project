package interface_adapter.ImageToColorPalette;

import entities.ColorPalette;

public class ImageToColorPaletteState {
    private ColorPalette colorPalette;
    private String errorMessage;

    public ColorPalette getColorPalette() {
        return colorPalette;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setColorPalette(ColorPalette colorPalette) {this.colorPalette = colorPalette;}
}
