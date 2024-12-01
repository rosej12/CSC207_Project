package use_cases.Drawing.auto_save;

import view.DrawingView;

import javax.swing.*;
import java.awt.image.RenderedImage;
import java.io.File;

public class AutoSave {

    private static JFileChooser fileChooser;
    private Boolean isDataChanged;

    public static void autoSaveDrawing() {
        // if file not saved in a file ==> DrawingView.saveDrawing()
        // elif file saved ==> auto save
        // fileChooser == file
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            DrawingView.DrawingPanel panel = (DrawingView.DrawingPanel) getComponent(0);
            drawingController.executeSave((RenderedImage) panel.getImage(), file);
        }
    }
}
