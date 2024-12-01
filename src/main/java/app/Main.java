package app;

import javax.swing.*;

import data_access.RenderDataAccessObject;
import use_cases.Drawing.DrawingDataAccessInterface;
import use_cases.Render.RenderDataAccessInterface;

public class Main {

    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        DrawingDataAccessInterface drawingDataAccess = new DrawingDataAccessInterface();
        RenderDataAccessInterface renderDataAccess = new RenderDataAccessObject();
        appBuilder
                .addDAO(drawingDataAccess)
                .addRenderDAO(renderDataAccess)
                .addDrawingView()
                .addRenderView()
                .addDrawingUseCase()
                .addRenderUseCase()
                .build()
                .setVisible(true);
    }
}
