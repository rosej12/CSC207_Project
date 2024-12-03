package use_cases.StatusManagement.UndoRedo;

import java.awt.*;
import java.awt.image.RenderedImage;

public interface UndoRedoOutputBoundary {
    void changeUndoState(Image image);
    void changeRedoState(Image image);
}
