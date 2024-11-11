package interface_adapter;

import entity.CommonDrawingPath;
import use_case.DrawingOutputBoundary;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class DrawingPresenter implements DrawingOutputBoundary {
    private final ViewModel<CommonDrawingPath> viewModel;
    private final List<CommonDrawingPath> paths;
    private CommonDrawingPath currentPath;

    public DrawingPresenter(ViewModel<CommonDrawingPath> viewModel) {
        this.viewModel = viewModel;
        this.paths = new ArrayList<>();
        this.currentPath = new CommonDrawingPath();
        paths.add(currentPath);
    }

    @Override
    public void startNewPath(Point startPoint){
        currentPath = new CommonDrawingPath();
        currentPath.addPoint(startPoint);
        paths.add(currentPath);
        viewModel.setState(currentPath);
        viewModel.firePropertyChanged();
    }

    @Override
    public void addPoint(Point point){
        currentPath.addPoint(point);
        viewModel.firePropertyChanged();
    }

    @Override
    public void endCurrentPath() {
        viewModel.firePropertyChanged();
    }

    public List<CommonDrawingPath> getPaths(){
        return paths;
    }
}
