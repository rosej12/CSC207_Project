package use_cases.StatusManagement.UndoRedo;

import view.DrawingView.DrawingPanel;

import java.awt.image.RenderedImage;

public interface UndoRedoOutputBoundary {
    RenderedImage getUndoAction();
    RenderedImage getRedoAction();
}
