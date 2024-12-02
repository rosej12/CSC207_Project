package view;

import interface_adapter.StatusManagement.AutoSave.AutoSaveController;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoController;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoPresenter;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoViewModel;
import use_cases.StatusManagement.AutoSave.AutoSaveOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The view of auto save, undo and redo action for the program. It listens for property change events
 * in the StatusManagerPanel and updates which then should be visible.
 */
public class UndoRedoView extends JPanel {
    private UndoRedoController undoRedoController;

    private String viewName;
    private final JButton undoButton;
    private final JButton redoButton;
    private final JLabel statusLabel;

    private use_cases.StatusManagement.AutoSave.AutoSaveDataAccessInterface AutoSaveDataAccessInterface;
    private AutoSaveOutputBoundary AutoSaveInputBoundary;
    private UndoRedoController controller;
    private UndoRedoPresenter presenter;

    public UndoRedoView(UndoRedoViewModel viewModel) {
        this.undoButton = new JButton("Undo");
        this.redoButton = new JButton("Redo");
        this.statusLabel = new JLabel("Status: Idle");

        this.setLayout(new FlowLayout());
        this.add(undoButton);
        this.add(redoButton);
        this.add(statusLabel);

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.undo();

                UndoRedoViewModel viewModel = presenter.getViewModel();
                updateStatus(viewModel);
            }
        });
    }


    private void updateStatus(UndoRedoViewModel viewModel) {
        String status = viewModel.getState().getStatus();
        boolean success = viewModel.isSuccess();
        statusLabel.setText(status);

        if (success) {
            statusLabel.setForeground(Color.GREEN);
        } else {
            statusLabel.setForeground(Color.RED);
        }
    }

    public UndoRedoController getUndoRedoController (){
        return undoRedoController;
    }

    public void setUndoRedoController (UndoRedoController undoRedoController) {
        this.undoRedoController = undoRedoController;
    }

    public String getViewName(){
        return viewName;
    }
}
