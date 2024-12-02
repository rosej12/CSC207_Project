package use_cases.StatusManagement.UndoRedo;

import view.DrawingView.DrawingPanel;

public interface UndoRedoInterface {
    void saveAction(DrawingPanel action);
    void undo();
    void redo();
}