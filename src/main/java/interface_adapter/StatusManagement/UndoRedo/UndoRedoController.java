package interface_adapter.StatusManagement.UndoRedo;

import use_cases.StatusManagement.AutoSave.AutoSaveInputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoInputBoundary;

import java.awt.image.RenderedImage;

public class UndoRedoController {
    private final UndoRedoInputBoundary undoUseCase;
    private final UndoRedoInputBoundary redoUseCase;

    public UndoRedoController(UndoRedoInputBoundary undoUseCase, UndoRedoInputBoundary redoUseCase) {
        this.undoUseCase = undoUseCase;
        this.redoUseCase = redoUseCase;
    }

    /**
     * Handles the undo action by invoking the undo use case.
     */
    public void undo() {
        undoUseCase.undo();
    }

    /**
     * Handles the redo action by invoking the redo use case.
     */
    public void redo() {
        redoUseCase.redo();
    }
}
