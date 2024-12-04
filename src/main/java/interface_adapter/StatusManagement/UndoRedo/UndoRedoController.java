package interface_adapter.StatusManagement.UndoRedo;

import use_cases.StatusManagement.AutoSave.AutoSaveInputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoInputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoInteractor;

import java.awt.*;
import java.awt.image.RenderedImage;

public class UndoRedoController {
    private final UndoRedoInputBoundary interactor;

    public UndoRedoController(UndoRedoInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Handles the undo action by invoking the undo use case.
     */
    public void undo() {
        interactor.undo();
    }

    /**
     * Handles the redo action by invoking the redo use case.
     */
    public void redo() {

        interactor.redo();
    }

    public void saveAction(Image image){
        interactor.saveAction(image);
    }
}
