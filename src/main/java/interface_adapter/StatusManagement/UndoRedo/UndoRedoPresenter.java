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
    public UndoRedoPresenter(UndoRedoViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Provides the updated ViewModel for the UI layer.
     */
    public UndoRedoViewModel getViewModel() {
        return viewModel;
    }


    @Override
    public void changeUndoState(Image image) {
        System.out.println("changed_1");

        //RenderedImage renderedImage = image;
        viewModel.getState().setState(image);
        System.out.println("changed state");
//        viewModel.setState(viewModel.getState());
        viewModel.firePropertyChanged("UndoRedo");
    }

    @Override
    public void changeRedoState(Image image) {
        //RenderedImage renderedImage = (RenderedImage) image;
        viewModel.getState().setState(image);
        viewModel.setState(viewModel.getState());
        viewModel.firePropertyChanged();
    }
}
