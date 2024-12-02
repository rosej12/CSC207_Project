package app;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import dataaccesses.ImageToColorPaletteAPI;
import dataaccesses.InMemoryColorPaletteRepository;
import interfaceadapters.Drawing.DrawingController;
import interfaceadapters.Drawing.DrawingPresenter;
import interfaceadapters.Drawing.DrawingViewModel;
import interfaceadapters.GenerateRandomColor.GenerateRandomColorController;
import interfaceadapters.GenerateRandomColor.GenerateRandomColorPalettePresenter;
import interfaceadapters.GenerateRandomColor.GenerateRandomColorPaletteViewModel;
import interfaceadapters.ImageToColorPalette.ImageToColorPaletteController;
import interfaceadapters.ImageToColorPalette.ImageToColorPalettePresenter;
import interfaceadapters.ImageToColorPalette.ImageToColorPaletteViewModel;
import interfaceadapters.Render.RenderController;
import interfaceadapters.Render.RenderPresenter;
import interfaceadapters.Render.RenderViewModel;
import interfaceadapters.ViewManagerModel;
import usecases.ColorPaletteRepositoryInterface;
import usecases.Drawing.DrawingDataAccessInterface;
import usecases.Drawing.DrawingInputBoundary;
import usecases.Drawing.DrawingInteractor;
import usecases.Drawing.DrawingOutputBoundary;
import usecases.GenerateRandomColorPalette.GenerateRandomColorPaletteInputBoundary;
import usecases.GenerateRandomColorPalette.GenerateRandomColorPaletteInteractor;
import usecases.GenerateRandomColorPalette.GenerateRandomColorPaletteOutputBoundary;
import usecases.ImageToColorPalette.ImageToColorPaletteDataAccessInterface;
import usecases.ImageToColorPalette.ImageToColorPaletteInputBoundary;
import usecases.ImageToColorPalette.ImageToColorPaletteInteractor;
import usecases.ImageToColorPalette.ImageToColorPaletteOutputBoundary;
import usecases.Render.RenderDataAccessInterface;
import usecases.Render.RenderInputBoundary;
import usecases.Render.RenderInteractor;
import usecases.Render.RenderOutputBoundary;
import view.DrawingView;
import view.GenerateRandomColorPaletteView;
import view.ImageToColorPaletteView;
import view.RenderView;
import view.ViewManager;

public class AppBuilder {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 800;

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    private final ColorPaletteRepositoryInterface colorPaletteRepository;

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
        drawingView = new DrawingView(drawingViewModel, viewManagerModel, colorPaletteRepository);
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
