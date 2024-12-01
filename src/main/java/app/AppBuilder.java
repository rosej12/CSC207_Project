package app;

import interface_adapter.Drawing.*;
import interface_adapter.Render.RenderController;
import interface_adapter.Render.RenderPresenter;
import interface_adapter.Render.RenderViewModel;
import interface_adapter.ViewManagerModel;
import use_cases.Drawing.*;
import use_cases.Render.RenderDataAccessInterface;
import use_cases.Render.RenderInputBoundary;
import use_cases.Render.RenderInteractor;
import use_cases.Render.RenderOutputBoundary;
import view.DrawingView;
import view.RenderView;
import view.ViewManager;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private DrawingView drawingView;
    private DrawingController drawingController;
    private DrawingViewModel drawingViewModel;
    private DrawingPresenter drawingPresenter;
    private DrawingInteractor drawingInteractor;
    private DrawingDataAccessInterface DrawingDAO;

    private RenderView renderView;
    private RenderViewModel renderViewModel;
    private RenderDataAccessInterface renderDAO;

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addDAO(DrawingDataAccessInterface drawingDataAccess) {
        this.DrawingDAO = drawingDataAccess;
        return this;
    }

    public AppBuilder addRenderDAO(RenderDataAccessInterface renderDataAccess) {
        this.renderDAO = renderDataAccess;
        return this;
    }

    /**
     * Adds the Drawing View to the application.
     * @return this builder
     */
    public AppBuilder addDrawingView() {
        drawingViewModel = new DrawingViewModel();
        drawingView = new DrawingView(drawingViewModel);
        cardPanel.add(drawingView, drawingView.getViewName());
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

        drawingInteractor = new DrawingInteractor(drawingOutputBoundary, this.DrawingDAO);
        drawingController = new DrawingController(drawingInteractor);
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

    /**
     * Creates the JFrame for the application and sets the initial view to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setTitle("Drawing App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        frame.add(cardPanel);

        viewManagerModel.setState(renderView.getViewName());
        viewManagerModel.firePropertyChanged();
        return frame;
    }
}
