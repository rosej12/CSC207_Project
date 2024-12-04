package use_case.Render;

import java.awt.Image;

public class RenderInputData {
    private final String description;
    private final Image sketch;

    public RenderInputData(String description, Image sketch) {
        this.description = description;
        this.sketch = sketch;
    }

    /**
     * Retrieves the description of the render input.
     *
     * @return A {@link String} representing the textual description of the render input.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the sketch associated with the render input.
     *
     * @return An {@link Image} object representing the sketch.
     */
    public Image getSketch() {
        return sketch;
    }
}
