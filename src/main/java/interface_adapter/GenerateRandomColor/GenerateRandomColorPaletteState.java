package interface_adapter.GenerateRandomColor;

import entities.ColorPalette;

public class GenerateRandomColorPaletteState {
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
