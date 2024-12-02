package view;

import interface_adapter.StatusManagement.UndoRedo.UndoRedoController;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoPresenter;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoViewModel;
import use_cases.StatusManagement.AutoSave.AutoSaveOutputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The view of auto save, undo and redo action for the program. It listens for property change events
 * in the StatusManagerPanel and updates which then should be visible.
 */
public class StatusManagementView extends JPanel {
    private final JButton undoButton;
    private final JButton redoButton;
    private final JLabel statusLabel;

    private use_cases.StatusManagement.AutoSave.AutoSaveDataAccessInterface AutoSaveDataAccessInterface;
    private AutoSaveOutputBoundary AutoSaveInputBoundary;

    public StatusManagementView(UndoRedoController controller, UndoRedoPresenter undoRedoPresenter) {
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

                UndoRedoViewModel viewModel = undoRedoPresenter.getViewModel();
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
}
