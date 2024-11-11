package app;

import interface_adapter.Drawing.*;
import use_cases.Drawing.*;
import view.DrawingView;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private DrawingView drawingView;
    private DrawingController drawingController;
    private DrawingViewModel drawingViewModel;
    private DrawingPresenter drawingPresenter;
    private DrawingInteractor drawingInteractor;
    private DrawingDataAccessInterface DrawingDAO;

    public AppBuilder addDAO(DrawingDataAccessInterface drawingDataAccess) {
        this.DrawingDAO = drawingDataAccess;
        return this;
    }

    /**
     * Adds a View to the application.
     * @return this builder
     */
    public AppBuilder addView() {
        drawingViewModel = new DrawingViewModel();
        drawingView = new DrawingView(drawingViewModel);
        return this;
    }

    /**
     * Adds a Use Case to the application.
     * @return this builder
     */
    public AppBuilder addUseCase() {
        final DrawingOutputBoundary drawingOutputBoundary = new DrawingPresenter(drawingViewModel);
        drawingInteractor = new DrawingInteractor(drawingOutputBoundary, this.DrawingDAO);
        drawingController = new DrawingController(drawingInteractor);
        drawingView.setDrawingController(drawingController);
        return this;
    }


    /**
     * Creates the JFrame for the application and sets the initial view to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setTitle("Drawing Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        frame.add(drawingView);
        return frame;
    }
}
