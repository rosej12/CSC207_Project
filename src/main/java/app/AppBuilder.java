package app;

import frameworks_and_drivers.ImageToColorPaletteAPI;
import interface_adapter.Drawing.*;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteViewModel;
import interface_adapter.ViewManagerModel;
import use_cases.Drawing.*;
import use_cases.ImageToColorPalette.*;
import view.DrawingView;
import view.ImageToColorPaletteView;
import view.ViewManager;
import interface_adapter.ImageToColorPalette.*;

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
    private ImageToColorPaletteView imageToColorPaletteView;
    private ImageToColorPaletteViewModel imageToColorPaletteViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addDAO(DrawingDataAccessInterface drawingDataAccess) {
        this.drawingDAO = drawingDataAccess;
        return this;
    }

    public AppBuilder addDrawingView() {
        drawingViewModel = new DrawingViewModel();
        drawingView = new DrawingView(drawingViewModel, viewManagerModel);
        cardPanel.add(drawingView, drawingView.getViewName());
        return this;
    }

    public AppBuilder addImageToColorPaletteView() {
        imageToColorPaletteViewModel = new ImageToColorPaletteViewModel();
        imageToColorPaletteView = new ImageToColorPaletteView(imageToColorPaletteViewModel, viewManagerModel);
        cardPanel.add(imageToColorPaletteView, imageToColorPaletteView.getViewName());
        return this;
    }

    public AppBuilder addDrawingUseCase() {
        final DrawingOutputBoundary drawingOutputBoundary = new DrawingPresenter(drawingViewModel);
        final DrawingInputBoundary drawingInteractor = new DrawingInteractor(drawingDAO, drawingOutputBoundary);
        final DrawingController drawingController = new DrawingController(drawingInteractor);
        drawingView.setDrawingController(drawingController);
        return this;
    }

    public AppBuilder addImageToColorPaletteUseCase() {
        final ImageToColorPaletteOutputBoundary imageToColorPaletteOutputBoundary =
                new ImageToColorPalettePresenter(imageToColorPaletteViewModel);

        // Initialize the data access implementation
        final ImageToColorPaletteDataAccessInterface dataAccess =
                new ImageToColorPaletteAPI("acc_054cca57db5a48e", "6721441a35c051ac95446e1cc94cc6a4");

        final ImageToColorPaletteInputBoundary imageToColorPaletteInteractor =
                new ImageToColorPaletteInteractor(dataAccess, imageToColorPaletteOutputBoundary);
        final ImageToColorPaletteController imageToColorPaletteController =
                new ImageToColorPaletteController(imageToColorPaletteInteractor);
        imageToColorPaletteView.setImageToColorPaletteController(imageToColorPaletteController);
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