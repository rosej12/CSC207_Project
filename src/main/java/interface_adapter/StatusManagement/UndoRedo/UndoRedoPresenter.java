package interface_adapter.StatusManagement.UndoRedo;

import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.Render.RenderViewModel;
import interface_adapter.ViewManagerModel;
import use_cases.StatusManagement.UndoRedo.UndoRedoInteractor;
import view.DrawingView.DrawingPanel;
import use_cases.StatusManagement.UndoRedo.UndoRedoOutputBoundary;
import view.DrawingView;

import java.awt.*;
import java.awt.image.RenderedImage;

public class UndoRedoPresenter implements UndoRedoOutputBoundary {
    private UndoRedoViewModel viewModel;
    private final DrawingViewModel drawingViewModel;
    private final ViewManagerModel viewManagerModel;

    public UndoRedoPresenter(UndoRedoViewModel viewModel, DrawingViewModel drawingViewModel,
                             ViewManagerModel viewManagerModel) {
        this.viewModel = new UndoRedoViewModel();
        this.drawingViewModel = new DrawingViewModel();
        this.viewManagerModel = new ViewManagerModel();
    }

    /**
     * Provides the updated ViewModel for the UI layer.
     */
    public UndoRedoViewModel getViewModel() {
        return viewModel;
    }


    @Override
    public void changeUndoState(Image image) {
        System.out.println("changed");

        //RenderedImage renderedImage = image;
        viewModel.getState().setState(image);
//        viewModel.setState(viewModel.getState());
        viewModel.firePropertyChanged("undo");
    }

    @Override
    public void changeRedoState(Image image) {
        //RenderedImage renderedImage = (RenderedImage) image;
        viewModel.getState().setState(image);
        viewModel.setState(viewModel.getState());
        viewModel.firePropertyChanged();
    }
}
