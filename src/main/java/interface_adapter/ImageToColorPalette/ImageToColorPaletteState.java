package interface_adapter.ImageToColorPalette;

public class ImageToColorPaletteState {
    private String[] colors;
    private String errorMessage;

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
