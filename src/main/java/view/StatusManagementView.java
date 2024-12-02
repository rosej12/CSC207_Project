package view;

import use_cases.StatusManagement.AutoSave.AutoSaveOutputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoInteractor;

import javax.swing.*;

/**
 * The view of auto save, undo and redo action for the program. It listens for property change events
 * in the StatusManagerPanel and updates which then should be visible.
 */
public class StatusManagementView {
    private use_cases.StatusManagement.AutoSave.AutoSaveDataAccessInterface AutoSaveDataAccessInterface;
    private AutoSaveOutputBoundary AutoSaveInputBoundary;

    // Create Interactors
    UndoRedoInteractor undoRedoInteractor = new UndoRedoInteractor();

    // Create Canvas Panel
    // StatusPanel statusPanel = new StatusPanel(autoSaveInteractor, undoRedoInteractor);

    // Create Undo/Redo Buttons
    JButton undoButton = new JButton("Undo");
    JButton redoButton = new JButton("Redo");

}
