package app;

import data_access.InMemoryColorPaletteRepository;
import interface_adapter.Drawing.*;
import interface_adapter.GenerateRandomColor.GenerateRandomColorPaletteViewModel;
import interface_adapter.ViewManagerModel;
import use_cases.GenerateRandomColorPalette.GenerateRandomColorPaletteInputBoundary;
import use_cases.GenerateRandomColorPalette.GenerateRandomColorPaletteInteractor;
import use_cases.GenerateRandomColorPalette.GenerateRandomColorPaletteOutputBoundary;
import use_cases.GenerateRandomColorPalette.ColorPaletteRepositoryInterface;
import use_cases.Drawing.DrawingDataAccessInterface;
import use_cases.Drawing.DrawingInputBoundary;
import use_cases.Drawing.DrawingInteractor;
import use_cases.Drawing.DrawingOutputBoundary;
import view.DrawingView;
import view.GenerateRandomColorPaletteView;
import view.ViewManager;
import interface_adapter.GenerateRandomColor.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private DrawingView drawingView;
    private DrawingViewModel drawingViewModel;
    private DrawingDataAccessInterface drawingDAO;
    private GenerateRandomColorPaletteView generateRandomColorPaletteView;
    private GenerateRandomColorPaletteViewModel generateRandomColorPaletteViewModel;
    private ColorPaletteRepositoryInterface colorPaletteRepository;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        colorPaletteRepository = new InMemoryColorPaletteRepository();
    }

    public AppBuilder addDAO(DrawingDataAccessInterface drawingDataAccess) {
        this.drawingDAO = drawingDataAccess;
        return this;
    }

    public AppBuilder addDrawingView() {
        drawingViewModel = new DrawingViewModel();
        drawingView = new DrawingView(drawingViewModel, viewManagerModel, colorPaletteRepository);
        cardPanel.add(drawingView, drawingView.getViewName());
        return this;
    }

    public AppBuilder addGenerateRandomColorPaletteView() {
        generateRandomColorPaletteViewModel = new GenerateRandomColorPaletteViewModel();
        generateRandomColorPaletteView = new GenerateRandomColorPaletteView(generateRandomColorPaletteViewModel, viewManagerModel);
        cardPanel.add(generateRandomColorPaletteView, generateRandomColorPaletteView.getViewName());
        return this;
    }

    public AppBuilder addDrawingUseCase() {
        final DrawingOutputBoundary drawingOutputBoundary = new DrawingPresenter(drawingViewModel);
        final DrawingInputBoundary drawingInteractor = new DrawingInteractor(drawingOutputBoundary, drawingDAO);
        final DrawingController drawingController = new DrawingController(drawingInteractor);
        drawingView.setDrawingController(drawingController);
        return this;
    }

    public AppBuilder addGenerateRandomColorPaletteUseCase() {
        final GenerateRandomColorPaletteOutputBoundary generateRandomColorPaletteOutputBoundary =
                new GenerateRandomColorPalettePresenter(generateRandomColorPaletteViewModel);

        final GenerateRandomColorPaletteInputBoundary generateRandomColorPaletteInteractor =
                new GenerateRandomColorPaletteInteractor(generateRandomColorPaletteOutputBoundary, colorPaletteRepository);

        final GenerateRandomColorController generateRandomColorController =
                new GenerateRandomColorController(generateRandomColorPaletteInteractor);

        generateRandomColorPaletteView.setGenerateRandomColorController(generateRandomColorController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Drawing Board");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setSize(1000, 800);
        application.setBackground(Color.WHITE);
        application.setLocationRelativeTo(null);
        application.add(cardPanel);

        viewManagerModel.setState(drawingView.getViewName());
        viewManagerModel.firePropertyChanged();
        return application;
    }
}