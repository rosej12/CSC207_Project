package use_cases.StatusManagement.UndoRedo;

import view.DrawingView;

import java.awt.*;

public interface UndoRedoInputBoundary {
    void saveAction(Image image);
    void undo();
    void redo();
}
