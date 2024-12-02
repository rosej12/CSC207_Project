package usecases.Render;

import java.awt.*;

public class RenderOutputData {
    private final Image renderedImage;

    public RenderOutputData(Image renderedImage) {
        this.renderedImage = renderedImage;
    }

    public Image getRenderedImage() {
        return renderedImage;
    }
}
