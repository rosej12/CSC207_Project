package use_cases.Render;

import java.awt.*;

public class RenderInputData {
    private final String description;
    private final Image sketch;

    public RenderInputData(String description, Image sketch) {
        this.description = description;
        this.sketch = sketch;
    }

    public String getDescription() {
        return description;
    }

    public Image getSketch() {
        return sketch;
    }
}
