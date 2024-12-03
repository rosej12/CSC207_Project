package use_cases.StatusManagement.UndoRedo;

import view.DrawingView.DrawingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.util.Stack;

public class UndoRedoInteractor implements UndoRedoInputBoundary {
    private UndoRedoOutputBoundary boundary;

    private static final Stack<Image> undoStack = new Stack<>();
    private static final Stack<Image> redoStack = new Stack<>();

    public UndoRedoInteractor(UndoRedoOutputBoundary boundary) {
        this.boundary = boundary;
    }

    @Override
    public void saveAction(Image image) {
        undoStack.push(image);
        redoStack.clear(); // Clear redo stack on new action
    }

    @Override
    public void undo() {
        if (!undoStack.isEmpty()) {
            System.out.println("in undo interactor");

            Image action = undoStack.pop();
            displayImage(action);
            redoStack.push(action);
            boundary.changeUndoState(action);
        }
        System.out.println("stack empty");
    }

    @Override
    public void redo() {
        if (!redoStack.isEmpty()) {
            Image action = redoStack.pop();
            undoStack.push(action);
            boundary.changeRedoState(action);
        }
    }

    public static Stack<Image> getRedoStack() {
        return redoStack;
    }

    public static Stack<Image> getUndoStack() {
        return undoStack;
    }

    public static void displayImage(Image image) {

        // Create a JLabel to display the image
        JLabel label = new JLabel(new ImageIcon(image));

        // Create a JFrame to hold the label
        JFrame frame = new JFrame("Image Display");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
