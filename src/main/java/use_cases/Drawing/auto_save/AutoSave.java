package use_cases.Drawing.auto_save;

import view.DrawingView;

import javax.swing.*;
import java.awt.image.RenderedImage;
import java.io.File;

public class AutoSave {

    private static JFileChooser fileChooser;
    private Boolean isDataChanged;

    public static void fileFinder() {
        // if file not saved in a file ==> DrawingView.saveDrawing()
        // elif file saved ==> auto save
    }

    public Boolean fileChangeFinder(){
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }
}
