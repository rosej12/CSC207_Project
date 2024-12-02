package view;

import interface_adapter.StatusManagement.AutoSave.AutoSaveController;
import interface_adapter.StatusManagement.AutoSave.AutoSavePresenter;
import interface_adapter.StatusManagement.AutoSave.AutoSaveViewModel;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoController;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoPresenter;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoViewModel;
import use_cases.StatusManagement.AutoSave.AutoSaveDataAccessInterface;
import use_cases.StatusManagement.AutoSave.AutoSaveOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.TimerTask;

/**
 * The view of auto save, undo and redo action for the program. It listens for property change events
 * in the StatusManagerPanel and updates which then should be visible.
 */
public class AutoSaveView extends JPanel {
    private final String viewName = "autosave";

    private AutoSaveController autoSaveController;
    private AutoSaveViewModel viewModel;

    public AutoSaveView(AutoSaveViewModel viewModel) {
        this.viewModel = viewModel;

    }

    public AutoSaveController getAutoSaveController(){
        return autoSaveController;
    }

    public void setAutoSaveController(AutoSaveController autoSaveController){
        this.autoSaveController = autoSaveController;
    }

    public String getViewName() {
        return viewName;
    }
}
