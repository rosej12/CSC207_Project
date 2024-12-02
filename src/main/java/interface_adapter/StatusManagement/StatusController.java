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
    private final AutoSaveInputBoundary autoSaveInteractor;
    private final UndoRedoInteractor undoInteractor;

    public StatusController(AutoSaveInputBoundary autoSaveInteractor) {
        this.autoSaveInteractor = autoSaveInteractor;
        this.undoInteractor = new UndoRedoInteractor();
    }

    public void saveToFile(RenderedImage image, File file) {
        autoSaveInteractor.saveToFile(image, file);
    }


}
