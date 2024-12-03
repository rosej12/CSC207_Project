package app;

import javax.swing.*;

import data_access.RenderDataAccessObject;
import data_access.DrawingFileDataAccess;
import data_access.RenderDataAccessObject;
import use_cases.Drawing.DrawingDataAccessInterface;
import use_cases.Render.RenderDataAccessInterface;
import use_cases.Render.RenderDataAccessInterface;
import use_cases.StatusManagement.AutoSave.AutoSaveDataAccessInterface;

public class Main {

    /**
     * Builds and runs the Clean Architecture application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        // Create an instance of AppBuilder to assemble the application components
        final AppBuilder appBuilder = new AppBuilder();

        // Instantiate the concrete data access implementation
        DrawingDataAccessInterface drawingDataAccess = new DrawingFileDataAccess();
        RenderDataAccessInterface renderDataAccess = new RenderDataAccessObject();

        // Build the application by adding components and setting up use cases
        appBuilder
                .addDAO(drawingDataAccess)          // Injects the data access object
                .addRenderDAO(renderDataAccess)     // Injects the data access object
                .addDrawingView()                   // Adds the Drawing View
                .addImageToColorPaletteView()       // Adds the ImageToColorPalette View
                .addRenderView()                    // Adds the Render View
                .addDrawingUseCase()                // Sets up the Drawing Use Case
                .addImageToColorPaletteUseCase()    // Sets up the ImageToColorPalette Use Case
                .addRenderUseCase()                 // Sets up the Render Use Case
//                .addAutoSaveUseCase()
                .addUndoRedoUseCase()
                .build()                            // Builds the application
                .setVisible(true);                  // Makes the application window visible
    }
}
