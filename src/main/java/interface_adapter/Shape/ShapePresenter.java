package interface_adapter.Shape;

import use_case.Shape.ShapeOutputBoundary;

public class ShapePresenter implements ShapeOutputBoundary {

    private final ShapeViewModel viewModel;

    public ShapePresenter(ShapeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView() {
        viewModel.setShapeDrawn(true);
        viewModel.firePropertyChanged("shapeDrawn");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setError(errorMessage);
        viewModel.firePropertyChanged("error");
    }
}
