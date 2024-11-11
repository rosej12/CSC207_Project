package app;

import javax.swing.*;

import use_cases.Drawing.DrawingDataAccessInterface;

public class Main {

    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        DrawingDataAccessInterface drawingDataAccess = new DrawingDataAccessInterface();
        appBuilder.addDAO(drawingDataAccess)
                .addView()
                .addUseCase()
                .build()
                .setVisible(true);
    }
}
