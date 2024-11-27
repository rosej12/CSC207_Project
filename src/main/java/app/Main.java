package app;

import javax.swing.*;

import frameworks_and_drivers.DrawingFileDataAccess;
import use_cases.Drawing.DrawingDataAccessInterface;

import java.awt.*;

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

        // Build the application by adding components and setting up use cases
        appBuilder
                .addDAO(drawingDataAccess)           // Injects the data access object
                .addDrawingView()                   // Adds the Drawing View
                .addImageToColorPaletteView()       // Adds the ImageToColorPalette View
                .addDrawingUseCase()                // Sets up the Drawing Use Case
                .addImageToColorPaletteUseCase()    // Sets up the ImageToColorPalette Use Case
                .build()                            // Builds the application
                .setVisible(true);                  // Makes the application window visible
    }
}
