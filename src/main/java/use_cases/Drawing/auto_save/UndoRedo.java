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
