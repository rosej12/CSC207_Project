package app;

import data_access.InMemoryColorPaletteRepository;
import frameworks_and_drivers.ImageToColorPaletteAPI;
import interface_adapter.Drawing.*;
import interface_adapter.Render.RenderController;
import interface_adapter.Render.RenderPresenter;
import interface_adapter.Render.RenderViewModel;
import interface_adapter.StatusManagement.AutoSave.AutoSaveController;
import interface_adapter.StatusManagement.AutoSave.AutoSavePresenter;
import interface_adapter.StatusManagement.AutoSave.AutoSaveViewModel;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoController;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoPresenter;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteViewModel;
import use_cases.Drawing.*;
import use_cases.Render.RenderDataAccessInterface;
import use_cases.Render.RenderInputBoundary;
import use_cases.Render.RenderInteractor;
import use_cases.Render.RenderOutputBoundary;
import use_cases.ImageToColorPalette.*;
import use_cases.StatusManagement.AutoSave.AutoSaveDataAccessInterface;
import use_cases.StatusManagement.AutoSave.AutoSaveInputBoundary;
import use_cases.StatusManagement.AutoSave.AutoSaveInteractor;
import use_cases.StatusManagement.AutoSave.AutoSaveOutputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoDataAccessInterface;
import use_cases.StatusManagement.UndoRedo.UndoRedoInputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoInteractor;
import use_cases.StatusManagement.UndoRedo.UndoRedoOutputBoundary;
import view.*;
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
    private ColorPaletteRepositoryInterface colorPaletteRepository;

    private RenderView renderView;
    private RenderViewModel renderViewModel;
    private RenderDataAccessInterface renderDAO;

    private UndoRedoViewModel undoRedoViewModel;
    private AutoSaveDataAccessInterface autoSaveDAO;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        colorPaletteRepository = new InMemoryColorPaletteRepository();
    }

    public AppBuilder addDAO(DrawingDataAccessInterface drawingDataAccess) {
        this.drawingDAO = drawingDataAccess;
        return this;
    }

    public AppBuilder addRenderDAO(RenderDataAccessInterface renderDataAccess) {
        this.renderDAO = renderDataAccess;
        return this;
    }

//    public AppBuilder addAutosaveDAO(AutoSaveDataAccessInterface autoSaveDataAccess) {
//        this.autoSaveDAO = autoSaveDataAccess;
//        return this;
//    }

//    public AppBuilder addUndoRedoDAO(UndoRedoDataAccessInterface undoRedoDataAccess) {
//        this.undoRedoDAO = undoRedoDataAccess;
//        return this;
//    }

    /**
     * Adds the Drawing View to the application.
     * @return this builder
     */
    public AppBuilder addDrawingView() {
        drawingViewModel = new DrawingViewModel();
        undoRedoViewModel = new UndoRedoViewModel();
        drawingView = new DrawingView(drawingViewModel, viewManagerModel, colorPaletteRepository, undoRedoViewModel);
        cardPanel.add(drawingView, drawingView.getViewName());
        return this;
    }

    public AppBuilder addImageToColorPaletteView() {
        imageToColorPaletteViewModel = new ImageToColorPaletteViewModel();
        imageToColorPaletteView = new ImageToColorPaletteView(imageToColorPaletteViewModel, viewManagerModel);
        cardPanel.add(imageToColorPaletteView, imageToColorPaletteView.getViewName());
        return this;
    }

    /**
     * Adds the Render View to the application
     * @return this builder
     */
    public AppBuilder addRenderView() {
        renderViewModel = new RenderViewModel();
        renderView = new RenderView(renderViewModel);
        cardPanel.add(renderView, renderView.getViewName());
        return this;

    }

    /**
     * Adds the Drawing Use Case to the application.
     * @return this builder
     */
    public AppBuilder addDrawingUseCase() {
        final DrawingOutputBoundary drawingOutputBoundary = new DrawingPresenter(viewManagerModel,
                drawingViewModel, renderViewModel);
        final DrawingInputBoundary drawingInteractor = new DrawingInteractor(drawingDAO, drawingOutputBoundary);
        final DrawingController drawingController = new DrawingController(drawingInteractor);
        drawingView.setDrawingController(drawingController);
        return this;
    }

    /**
     * Adds the Render Use Case to the application.
     * @return this builder
     */
    public AppBuilder addRenderUseCase() {
        final RenderOutputBoundary renderOutputBoundary = new RenderPresenter(viewManagerModel,
                renderViewModel, drawingViewModel);
        final RenderInputBoundary renderInteractor = new RenderInteractor(renderDAO, renderOutputBoundary);
        final RenderController renderController = new RenderController(renderInteractor);
        renderView.setRenderController(renderController);
        return this;
    }

//    /**
//     * Adds the AutoSave Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addAutoSaveUseCase() {
//        final AutoSaveOutputBoundary autoSaveOutputBoundary = new AutoSavePresenter(autoSaveViewModel,
//                viewManagerModel, drawingViewModel);
//        final AutoSaveInputBoundary autoSaveInteractor = new AutoSaveInteractor(autoSaveDAO, autoSaveOutputBoundary);
//        final AutoSaveController autoSaveController = new AutoSaveController(autoSaveInteractor);
//        autoSaveView.setAutoSaveController(autoSaveController);
//        return this;
//    }

    public AppBuilder addUndoRedoUseCase() {
        final UndoRedoOutputBoundary undoRedoOutputBoundary = new UndoRedoPresenter(undoRedoViewModel);
        final UndoRedoInputBoundary undoRedoInteractor = new UndoRedoInteractor(undoRedoOutputBoundary);
        final UndoRedoController undoRedoController = new UndoRedoController(undoRedoInteractor);
        drawingView.setUndoRedoController(undoRedoController);
        return this;
    }

    public AppBuilder addImageToColorPaletteUseCase() {
        final ImageToColorPaletteOutputBoundary imageToColorPaletteOutputBoundary =
                new ImageToColorPalettePresenter(imageToColorPaletteViewModel);

        // Initialize the data access implementation
        final ImageToColorPaletteDataAccessInterface dataAccess =
                new ImageToColorPaletteAPI("acc_054cca57db5a48e", "6721441a35c051ac95446e1cc94cc6a4");

        final ImageToColorPaletteInputBoundary imageToColorPaletteInteractor =
                new ImageToColorPaletteInteractor(dataAccess, imageToColorPaletteOutputBoundary, colorPaletteRepository);
        final ImageToColorPaletteController imageToColorPaletteController =
                new ImageToColorPaletteController(imageToColorPaletteInteractor);
        imageToColorPaletteView.setImageToColorPaletteController(imageToColorPaletteController);
        return this;
    }

    /**
     * Creates the JFrame for the application and sets the initial view to be displayed.
     * @return the application
     */
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