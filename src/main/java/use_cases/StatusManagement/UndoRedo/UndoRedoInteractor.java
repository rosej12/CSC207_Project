package use_cases.StatusManagement.UndoRedo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class UndoRedoInteractor implements UndoRedoInputBoundary {
    private UndoRedoOutputBoundary boundary;

    private Stack<Image> undoStack = new Stack<>();
    private Stack<Image> redoStack = new Stack<>();
    private Image currentImage = null;

    public UndoRedoInteractor(UndoRedoOutputBoundary boundary) {
        this.boundary = boundary;
    }

    @Override
    public void saveAction(Image image) {
        Image copy = getCopyImage(image);   // the previous image

        undoStack.push(currentImage);
        currentImage = copy;
        redoStack.clear(); // Clear redo stack on new action
    }

    @Override
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentImage);
            currentImage = undoStack.pop();
            boundary.changeUndoRedoState(currentImage);
        }

    }

    @Override
    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentImage);
            currentImage = redoStack.pop();
            boundary.changeUndoRedoState(currentImage);
        }
    }

    public void setRedoStack(Stack<Image> stack) {
        redoStack = stack;
    }

    public void setUndoStack(Stack<Image> stack) {
        undoStack = stack;
    }

    public Stack<Image> getRedoStack() {
        return redoStack;
    }

    public Stack<Image> getUndoStack() {
        return undoStack;
    }

    public Image getCopyImage(Image image) {
        Image copy = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        copy.getGraphics().drawImage(image, 0, 0, null);
        return copy;
    }
}
