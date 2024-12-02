package use_cases.StatusManagement.UndoRedo;

import view.DrawingView.DrawingPanel;

import java.util.Stack;

public class UndoRedoInteractor implements UndoRedoInputBoundary {
    private static final Stack<DrawingPanel> undoStack = new Stack<>();
    private static final Stack<DrawingPanel> redoStack = new Stack<>();

    public UndoRedoInteractor() {
    }

    @Override
    public void saveAction(DrawingPanel action) {
        undoStack.push(action);
        redoStack.clear(); // Clear redo stack on new action
    }

    @Override
    public void undo() {
        if (!undoStack.isEmpty()) {
            DrawingPanel action = undoStack.pop();
            redoStack.push(action);
        }

    }

    @Override
    public void redo() {
        if (!redoStack.isEmpty()) {
            DrawingPanel action = redoStack.pop();
            undoStack.push(action);
        }

    }

    public static Stack<DrawingPanel> getRedoStack() {
        return redoStack;
    }

    public static Stack<DrawingPanel> getUndoStack() {
        return undoStack;
    }
}
