package use_cases.StatusManagement;

import javax.swing.*;

import interface_adapter.Drawing.DrawingState;
import view.DrawingView.DrawingPanel;

public interface UndoRedoInteractor {
    void saveAction(DrawingPanel action);
    void undo();
    void redo();
}