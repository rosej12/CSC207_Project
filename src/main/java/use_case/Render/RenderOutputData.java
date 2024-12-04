package use_case.Render;

import java.awt.Image;

public class RenderOutputData {
    private final Image renderedImage;

    public RenderOutputData(Image renderedImage) {
        this.renderedImage = renderedImage;
    }

    /**
     * Retrieves the rendered image.
     *
     * @return An {@link Image} object representing the rendered image.
     */
    public Image getRenderedImage() {
        return renderedImage;
    }
}
