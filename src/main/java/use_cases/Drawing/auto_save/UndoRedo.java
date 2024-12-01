package use_cases.Drawing.auto_save;

import view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UndoRedo {
    private JFileChooser fileChooser = new JFileChooser();

    public void actionOnCanvas(){

    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooser(JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }
}
package use_cases.Drawing.auto_save;

import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class UndoRedo {
    private ArrayList<File> savedStats;
    private File currentFile;
    private int currentIndex = 0;

    public File Undo(){
        savedStats = AutoSave.getSavedStates();
        currentFile = savedStats.get(savedStats.size()-1);
        currentIndex = savedStats.size()-1;
        return currentFile;
    }
    public void Redo(){
        savedStats = AutoSave.getSavedStates();
        if(currentIndex < 0){
            currentIndex += 1;
            currentFile = savedStats.get(currentIndex+1);

        }
    }

}
