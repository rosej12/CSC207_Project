package use_cases.StatusManagement.AutoSave;

import java.awt.*;
import java.awt.image.RenderedImage;

public interface AutoSaveOutputBoundary {
    void prepareSuccessView(RenderedImage drawing);
    void prepareFailView(String errorMessage);
}
