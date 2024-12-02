package use_cases.Drawing;

import java.awt.*;
import java.awt.image.RenderedImage;

public interface DrawingOutputBoundary {

    void prepareSuccessView(RenderedImage drawing);

    void prepareFailView(String errorMessage);

    void switchToRenderView(Image sketch);
}
