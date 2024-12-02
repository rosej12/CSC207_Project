package use_cases.StatusManagement.UndoRedo;

import use_cases.StatusManagement.UndoRedo.UndoRedoInteractor;
import view.DrawingView.DrawingPanel;

import java.awt.image.RenderedImage;

public class UndoRedoOutputData implements UndoRedoOutputBoundary {

    @Override
    public DrawingPanel getUndoAction() {
        return UndoRedoInteractor.getUndoStack().pop();
    }

    @Override
    public DrawingPanel getRedoAction() {
        return UndoRedoInteractor.getRedoStack().pop();
    }
}
