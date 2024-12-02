package use_cases.StatusManagement;

import entities.ColorPalette;
import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.ViewManagerModel;
import use_cases.ImageToColorPalette.ColorPaletteRepositoryInterface;
import view.DrawingView;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AutoSave{
//    private final Image Image;
//    private static File folder = new File(DrawingView.saveDrawing());
//    private static ArrayList<File> savedStates = new ArrayList<>();

//    public AutoSave() {
//        super();
//        this.Image = DrawingView.DrawingPanel.getImage();
//        savedStates = new ArrayList<>();
//        createTempFolder();
//    }
//
//    private void createTempFolder() {
//        if (!folder.exists()) {
//            folder.mkdir();
//        }
//    }
//
//    public static void saveCanvasState() {
//        DrawingViewModel DrawingViewModel = new DrawingViewModel();
//        ViewManagerModel ViewManagerModel = new ViewManagerModel();
//        ColorPaletteRepositoryInterface ColorPaletteRepositoryInterface;
//        String fileName = "";
//        Date now = new Date();
//
//        // the date format for the file name
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
//
//        // the current time format
//        String timestamp = formatter.format(now);
//
//        // together name
//        String filename = timestamp + ".png";
//
//        File tempFile = new File(folder, fileName);
//        DrawingView drawingView = new DrawingView(DrawingViewModel, ViewManagerModel,);
//        DrawingView.saveToFile(tempFile.getAbsolutePath());
//        savedStates.add(tempFile);
//    }
//
//    public void finalizeSave(String finalFilePath) {
//        // Save the latest state to the final destination
//        if (!savedStates.isEmpty()) {
//            File latestState = savedStates.get(savedStates.size() - 1);
//            File finalFile = new File(finalFilePath);
//            if (finalFile.exists()) {
//                finalFile.delete();
//            }
//            latestState.renameTo(finalFile);
//        }
//
//        // Clean up the temporary files and folder
//        cleanupTempFolder();
//    }
//
//    private void cleanupTempFolder() {
//        for (File file : savedStates) {
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//        savedStates.clear();
//        File folder = new File(tempFolderPath);
//        if (folder.exists()) {
//            folder.delete();
//        }
//    }
//
//    public java.awt.Image getImage() {
//        return Image;
//    }
//
//    public static ArrayList<File> getSavedStates() {
//        return savedStates;
//    }
//
//    public static ArrayList<File> setSavedStates(ArrayList<File> savedStates) {
//        AutoSave.savedStates = savedStates;
//        return savedStates;
//    }
}
