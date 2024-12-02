package use_cases.StatusManagement;

import javax.swing.*;

public interface UndoRedoInteractor {
    void saveAction(Action action);
    void undo();
    void redo();
}