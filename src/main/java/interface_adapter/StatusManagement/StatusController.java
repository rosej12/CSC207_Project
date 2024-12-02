package interface_adapter.StatusManagement;

import use_cases.StatusManagement.AutoSave.AutoSaveInputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.RenderedImage;
import java.io.File;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class StatusController {
    private final AutoSaveInputBoundary autosaveUseCase;

    public StatusController(AutoSaveInputBoundary autosaveUseCase) {
        this.autosaveUseCase = autosaveUseCase;
    }

    /**
     * Initiates the autosave process by calling the use case.
     */
    public void autosave(RenderedImage currentState) {
        autosaveUseCase.saveCanvasState(currentState);
    }
}
