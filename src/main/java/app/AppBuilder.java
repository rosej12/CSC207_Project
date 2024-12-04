package app;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.ImageToColorPaletteAPI;
import data_access.InMemoryColorPaletteRepository;
import interface_adapter.Drawing.DrawingController;
import interface_adapter.Drawing.DrawingPresenter;
import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.GenerateRandomColor.GenerateRandomColorController;
import interface_adapter.GenerateRandomColor.GenerateRandomColorPalettePresenter;
import interface_adapter.GenerateRandomColor.GenerateRandomColorPaletteViewModel;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteController;
import interface_adapter.ImageToColorPalette.ImageToColorPalettePresenter;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteViewModel;
import interface_adapter.Render.RenderController;
import interface_adapter.Render.RenderPresenter;
import interface_adapter.Render.RenderViewModel;
import interface_adapter.Shape.ShapeController;
import interface_adapter.Shape.ShapePresenter;
import interface_adapter.Shape.ShapeViewModel;
import interface_adapter.ViewManagerModel;
import use_case.ColorPaletteRepositoryInterface;
import use_case.Drawing.DrawingDataAccessInterface;
import use_case.Drawing.DrawingInputBoundary;
import use_case.Drawing.DrawingInteractor;
import use_case.Drawing.DrawingOutputBoundary;
import use_case.GenerateRandomColorPalette.GenerateRandomColorPaletteInputBoundary;
import use_case.GenerateRandomColorPalette.GenerateRandomColorPaletteInteractor;
import use_case.GenerateRandomColorPalette.GenerateRandomColorPaletteOutputBoundary;
import use_case.ImageToColorPalette.ImageToColorPaletteDataAccessInterface;
import use_case.ImageToColorPalette.ImageToColorPaletteInputBoundary;
import use_case.ImageToColorPalette.ImageToColorPaletteInteractor;
import use_case.ImageToColorPalette.ImageToColorPaletteOutputBoundary;
import use_case.Render.RenderDataAccessInterface;
import use_case.Render.RenderInputBoundary;
import use_case.Render.RenderInteractor;
import use_case.Render.RenderOutputBoundary;
import use_case.Shape.ShapeInputBoundary;
import use_case.Shape.ShapeInteractor;
import use_case.Shape.ShapeOutputBoundary;
import view.DrawingView;
import view.GenerateRandomColorPaletteView;
import view.ImageToColorPaletteView;
import view.RenderView;
import view.ShapeView;
import view.ViewManager;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoController;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoPresenter;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoViewModel;
import use_cases.StatusManagement.UndoRedo.UndoRedoInputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoInteractor;
import use_cases.StatusManagement.UndoRedo.UndoRedoOutputBoundary;

public class AppBuilder {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 800;

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    private final ColorPaletteRepositoryInterface colorPaletteRepository;
    private final ShapeViewModel shapeViewModel = new ShapeViewModel();

    private DrawingView drawingView;
    private DrawingViewModel drawingViewModel;
    private DrawingDataAccessInterface drawingdao;
    private ImageToColorPaletteView imageToColorPaletteView;
    private ImageToColorPaletteViewModel imageToColorPaletteViewModel;
    private GenerateRandomColorPaletteView generateRandomColorPaletteView;
    private GenerateRandomColorPaletteViewModel generateRandomColorPaletteViewModel;

    private RenderView renderView;
    private RenderViewModel renderViewModel;
    private RenderDataAccessInterface renderDao;

    private ShapeView shapeView;

    private UndoRedoViewModel undoRedoViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        colorPaletteRepository = new InMemoryColorPaletteRepository();
    }

    /**
     * Adds a Drawing Data Access Object (DAO) to the application builder.
     *
     * <p>
     * This method sets up the {@link DrawingDataAccessInterface} to handle data storage and retrieval for drawings.
     * </p>
     *
     * @param drawingDataAccess the implementation of {@link DrawingDataAccessInterface}
     *                          used for saving and retrieving drawings.
     * @return the current instance of {@link AppBuilder}.
     */
    public AppBuilder addDao(DrawingDataAccessInterface drawingDataAccess) {
        this.drawingdao = drawingDataAccess;
        return this;
    }

    /**
     * Adds a Render Data Access Object (DAO) to the application builder.
     *
     * <p>
     * This method sets up the {@link RenderDataAccessInterface} to handle rendering operations.
     * </p>
     *
     * @param renderDataAccess the implementation of {@link RenderDataAccessInterface} used for rendering operations.
     * @return the current instance of {@link AppBuilder}.
     */
    public AppBuilder addRenderDao(RenderDataAccessInterface renderDataAccess) {
        this.renderDao = renderDataAccess;
        return this;
    }

    /**
     * Adds the Drawing View to the application.
     * @return this builder
     */
    public AppBuilder addDrawingView() {
        drawingViewModel = new DrawingViewModel();
        undoRedoViewModel = new UndoRedoViewModel();
        drawingView = new DrawingView(drawingViewModel, viewManagerModel, colorPaletteRepository, shapeViewModel, undoRedoViewModel);
        cardPanel.add(drawingView, drawingView.getViewName());
        return this;
    }

    /**
     * Adds the Image to Color Palette View to the application.
     *
     * <p>
     * This method initializes the {@link ImageToColorPaletteViewModel} and {@link ImageToColorPaletteView},
     * then adds the view to the card panel for state-based navigation.
     * </p>
     *
     * @return the current instance of {@link AppBuilder}.
     */
    public AppBuilder addImageToColorPaletteView() {
        imageToColorPaletteViewModel = new ImageToColorPaletteViewModel();
        imageToColorPaletteView = new ImageToColorPaletteView(imageToColorPaletteViewModel, viewManagerModel);
        cardPanel.add(imageToColorPaletteView, imageToColorPaletteView.getViewName());
        return this;
    }

    /**
     * Adds the Generate Random Color Palette View to the application.
     *
     * <p>
     * This method initializes the {@link GenerateRandomColorPaletteViewModel} and
     * {@link GenerateRandomColorPaletteView},
     * then adds the view to the card panel for state-based navigation.
     * </p>
     *
     * @return the current instance of {@link AppBuilder}.
     */
    public AppBuilder addGenerateRandomColorPaletteView() {
        generateRandomColorPaletteViewModel = new GenerateRandomColorPaletteViewModel();
        generateRandomColorPaletteView = new GenerateRandomColorPaletteView(generateRandomColorPaletteViewModel,
                viewManagerModel);
        cardPanel.add(generateRandomColorPaletteView, generateRandomColorPaletteView.getViewName());
        return this;
    }

    /**
     * Adds the Render View to the application.
     *
     * <p>
     * This method initializes the {@link RenderViewModel} and {@link RenderView},
     * then adds the view to the card panel for state-based navigation.
     * </p>
     *
     * @return the current instance of {@link AppBuilder}.
     */
    public AppBuilder addRenderView() {
        renderViewModel = new RenderViewModel();
        renderView = new RenderView(renderViewModel);
        cardPanel.add(renderView, renderView.getViewName());
        return this;
    }

    /**
     * Adds the Shape View to the application.
     *
     * <p>
     * This method initializes the {@link ShapeViewModel} and {@link ShapeView},
     * then adds the view to the card panel for state-based navigation.
     * </p>
     *
     * @return the current instance of {@link AppBuilder}.
     */
    public AppBuilder addShapeView() {
        ShapeOutputBoundary shapePresenter = new ShapePresenter(shapeViewModel);
        ShapeInputBoundary shapeInteractor = new ShapeInteractor(shapePresenter);
        ShapeController shapeController = new ShapeController(shapeInteractor);

        shapeView = new ShapeView(shapeController, shapeViewModel, viewManagerModel);
        cardPanel.add(shapeView, shapeView.getViewName());
        return this;
    }

    /**
     * Adds the Drawing Use Case to the application.
     * @return this builder
     */
    public AppBuilder addDrawingUseCase() {
        final DrawingOutputBoundary drawingOutputBoundary = new DrawingPresenter(viewManagerModel,
                drawingViewModel, renderViewModel);
        final DrawingInputBoundary drawingInteractor = new DrawingInteractor(drawingdao, drawingOutputBoundary);
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
        final RenderInputBoundary renderInteractor = new RenderInteractor(renderDao, renderOutputBoundary);
        final RenderController renderController = new RenderController(renderInteractor);
        renderView.setRenderController(renderController);
        return this;
    }

    public AppBuilder addUndoRedoUseCase() {
        final UndoRedoOutputBoundary undoRedoOutputBoundary = new UndoRedoPresenter(undoRedoViewModel);
        final UndoRedoInputBoundary undoRedoInteractor = new UndoRedoInteractor(undoRedoOutputBoundary);
        final UndoRedoController undoRedoController = new UndoRedoController(undoRedoInteractor);
        drawingView.setUndoRedoController(undoRedoController);
        return this;
    }

    /**
     * Configures and adds the Image to Color Palette use case to the application.
     *
     * <p>
     * This method initializes the necessary components, including:
     * <ul>
     * <li>{@link ImageToColorPalettePresenter} for presenting the output.</li>
     * <li>{@link ImageToColorPaletteAPI} as the data access implementation.</li>
     * <li>{@link ImageToColorPaletteInteractor} as the input boundary.</li>
     * <li>{@link ImageToColorPaletteController} as the controller.</li>
     * </ul>
     * The controller is then set for the {@link ImageToColorPaletteView}.
     * </p>
     *
     * @return the current instance of {@link AppBuilder}.
     */
    public AppBuilder addImageToColorPaletteUseCase() {
        final ImageToColorPaletteOutputBoundary imageToColorPaletteOutputBoundary =
                new ImageToColorPalettePresenter(imageToColorPaletteViewModel);

        // Initialize the data access implementation
        final ImageToColorPaletteDataAccessInterface dataAccess =
                new ImageToColorPaletteAPI("acc_054cca57db5a48e", "6721441a35c051ac95446e1cc94cc6a4");

        final ImageToColorPaletteInputBoundary imageToColorPaletteInteractor =
                new ImageToColorPaletteInteractor(dataAccess, imageToColorPaletteOutputBoundary,
                        colorPaletteRepository);
        final ImageToColorPaletteController imageToColorPaletteController =
                new ImageToColorPaletteController(imageToColorPaletteInteractor);
        imageToColorPaletteView.setImageToColorPaletteController(imageToColorPaletteController);
        return this;
    }

    /**
     * Configures and adds the Generate Random Color Palette use case to the application.
     *
     * <p>
     * This method initializes the necessary components, including:
     * <ul>
     * <li>{@link GenerateRandomColorPalettePresenter} for presenting the output.</li>
     * <li>{@link GenerateRandomColorPaletteInteractor} as the input boundary.</li>
     * <li>{@link GenerateRandomColorController} as the controller.</li>
     * </ul>
     * The controller is then set for the {@link GenerateRandomColorPaletteView}.
     * </p>
     *
     * @return the current instance of {@link AppBuilder}.
     */
    public AppBuilder addGenerateRandomColorPaletteUseCase() {
        final GenerateRandomColorPaletteOutputBoundary generateRandomColorPaletteOutputBoundary =
                new GenerateRandomColorPalettePresenter(generateRandomColorPaletteViewModel);

        final GenerateRandomColorPaletteInputBoundary generateRandomColorPaletteInteractor =
                new GenerateRandomColorPaletteInteractor(generateRandomColorPaletteOutputBoundary,
                        colorPaletteRepository);

        final GenerateRandomColorController generateRandomColorController =
                new GenerateRandomColorController(generateRandomColorPaletteInteractor);

        generateRandomColorPaletteView.setGenerateRandomColorController(generateRandomColorController);
        return this;
    }

    /**
     * Configures and adds the Shape use case to the application.
     *
     * <p>
     * This method initializes the necessary components, including:
     * <ul>
     * <li>{@link ShapePresenter} for presenting the output.</li>
     * <li>{@link ShapeInteractor} as the input boundary.</li>
     * <li>{@link ShapeController} as the controller.</li>
     * </ul>
     * The controller is then set for the {@link GenerateRandomColorPaletteView}.
     * </p>
     *
     * @return the current instance of {@link AppBuilder}.
     */
    public AppBuilder addShapesUseCase() {
        final ShapeOutputBoundary shapeOutputBoundary = new ShapePresenter(shapeViewModel);
        final ShapeInputBoundary shapeInteractor = new ShapeInteractor(shapeOutputBoundary);
        final ShapeController shapeController = new ShapeController(shapeInteractor);

        if (shapeView != null) {
            shapeView = new ShapeView(shapeController, shapeViewModel, viewManagerModel);
        }

        return this;
    }

    /**
     * Creates the JFrame for the application and sets the initial view to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Drawing Board");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        application.setBackground(Color.WHITE);
        application.setLocationRelativeTo(null);
        application.add(cardPanel);

        viewManagerModel.setState(drawingView.getViewName());
        viewManagerModel.firePropertyChanged();
        return application;
    }
}
