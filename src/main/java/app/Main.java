package app;

import dataaccesses.DrawingFileDataAccess;
import dataaccesses.RenderDataAccessObject;
import usecases.Drawing.DrawingDataAccessInterface;
import usecases.Render.RenderDataAccessInterface;

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
                .addDao(drawingDataAccess)
                .addRenderDao(renderDataAccess)
                .addDrawingView()
                .addImageToColorPaletteView()
                .addGenerateRandomColorPaletteView()
                .addRenderView()
                .addShapeView()
                .addDrawingUseCase()
                .addImageToColorPaletteUseCase()
                .addGenerateRandomColorPaletteUseCase()
                .addRenderUseCase()
                .addShapesUseCase()
                .build()
                .setVisible(true);
    }
}
