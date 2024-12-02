package use_cases.StatusManagement.UndoRedo;

import view.DrawingView;

public interface UndoRedoInputBoundary {
    void saveAction(DrawingView.DrawingPanel action);
    void undo();
    void redo();
}
