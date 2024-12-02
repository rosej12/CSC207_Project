package entities;

public class Color {
    private static final int HEX_SUBSTRING_START_RED = 1;
    private static final int HEX_SUBSTRING_END_RED = 3;
    private static final int HEX_SUBSTRING_START_GREEN = 3;
    private static final int HEX_SUBSTRING_END_GREEN = 5;
    private static final int HEX_SUBSTRING_START_BLUE = 5;
    private static final int HEX_SUBSTRING_END_BLUE = 7;
    private static final int HEX_RADIX = 16;

    private int red;
    private int green;
    private int blue;
    private final String hexCode;
    private boolean isLocked;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.hexCode = String.format("#%02x%02x%02x", red, green, blue);
        this.isLocked = false;
    }

    public Color(String hexCode) {
        this.hexCode = hexCode;
        this.red = Integer.parseInt(hexCode.substring(HEX_SUBSTRING_START_RED, HEX_SUBSTRING_END_RED), HEX_RADIX);
        this.green = Integer.parseInt(hexCode.substring(HEX_SUBSTRING_START_GREEN, HEX_SUBSTRING_END_GREEN), HEX_RADIX);
        this.blue = Integer.parseInt(hexCode.substring(HEX_SUBSTRING_START_BLUE, HEX_SUBSTRING_END_BLUE), HEX_RADIX);
    }

    /**
     * Retrieves the red component of the color.
     *
     * @return the red value (0-255).
     */
    public int getRed() {
        return red;
    }

    /**
     * Retrieves the green component of the color.
     *
     * @return the green value (0-255).
     */
    public int getGreen() {
        return green;
    }

    /**
     * Retrieves the blue component of the color.
     *
     * @return the blue value (0-255).
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Retrieves the hexadecimal representation of the color.
     *
     * @return the color in hexadecimal format (e.g., "#RRGGBB").
     */
    public String getHexCode() {
        return hexCode;
    }

    /**
     * Checks if the color is locked.
     *
     * @return {@code true} if the color is locked, otherwise {@code false}.
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Sets the red component of the color.
     *
     * @param red the new red value (0-255).
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     * Sets the green component of the color.
     *
     * @param green the new green value (0-255).
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     * Sets the blue component of the color.
     *
     * @param blue the new blue value (0-255).
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     * Sets the lock status of the color.
     *
     * @param locked {@code true} to lock the color, {@code false} to unlock it.
     */
    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
