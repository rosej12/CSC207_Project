package use_cases.StatusManagement;

import view.DrawingView.DrawingPanel;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public class UndoRedo implements UndoRedoInteractor {
    private final Stack<DrawingPanel> undoStack = new Stack<>();
    private final Stack<DrawingPanel> redoStack = new Stack<>();

    public UndoRedo() {

    }

    @Override
    public void saveAction(DrawingPanel action) {

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


//    private ArrayList<File> savedStats;
//    private File currentFile;
//    private int currentIndex = 0;
//
//    public File Undo(){
//        savedStats = AutoSave.getSavedStates();
//        currentFile = savedStats.get(savedStats.size()-1);
//        currentIndex = savedStats.size()-1;
//        return currentFile;
//    }
//    public void Redo(){
//        savedStats = AutoSave.getSavedStates();
//        if(currentIndex < 0){
//            currentIndex += 1;
//            currentFile = savedStats.get(currentIndex+1);
//
//        }
//    }

}
