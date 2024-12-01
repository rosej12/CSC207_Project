package entities;

public class Color {
    private final int red;
    private final int green;
    private final int blue;
    private final String hexCode;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.hexCode = String.format("#%02x%02x%02x", red, green, blue);
    }

    public Color(String hexCode) {
        this.hexCode = hexCode;
        this.red = Integer.parseInt(hexCode.substring(1, 3), 16);
        this.green = Integer.parseInt(hexCode.substring(3, 5), 16);
        this.blue = Integer.parseInt(hexCode.substring(5, 7), 16);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public String getHexCode() {
        return hexCode;
    }
}
