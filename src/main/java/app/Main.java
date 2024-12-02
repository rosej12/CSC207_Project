package app;

import data_access.RenderDataAccessObject;
import data_access.DrawingFileDataAccess;
import use_cases.Drawing.DrawingDataAccessInterface;
import use_cases.Render.RenderDataAccessInterface;

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
                .addDao(drawingDataAccess)          // Injects the data access object
                .addRenderDao(renderDataAccess)     // Injects the data access object
                .addDrawingView()                   // Adds the Drawing View
                .addImageToColorPaletteView()       // Adds the ImageToColorPalette View
                .addGenerateRandomColorPaletteView()
                .addRenderView()                    // Adds the Render View
                .addDrawingUseCase()                // Sets up the Drawing Use Case
                .addImageToColorPaletteUseCase()    // Sets up the ImageToColorPalette Use Case
                .addGenerateRandomColorPaletteUseCase()
                .addRenderUseCase()                 // Sets up the Render Use Case
                .build()                            // Builds the application
                .setVisible(true);                  // Makes the application window visible
    }
}
