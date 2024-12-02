package use_cases.StatusManagement.UndoRedo;

import view.DrawingView.DrawingPanel;

public interface UndoRedoOutputBoundary {
    DrawingPanel getUndoAction();
    DrawingPanel getRedoAction();
}
