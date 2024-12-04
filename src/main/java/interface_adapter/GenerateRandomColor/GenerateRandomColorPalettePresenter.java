package interface_adapter.GenerateRandomColor;

import use_case.GenerateRandomColorPalette.GenerateRandomColorPaletteOutputBoundary;
import use_case.GenerateRandomColorPalette.GenerateRandomColorPaletteResponseModel;

public class GenerateRandomColorPalettePresenter implements GenerateRandomColorPaletteOutputBoundary {
    private final GenerateRandomColorPaletteViewModel viewModel;

    public GenerateRandomColorPalettePresenter(GenerateRandomColorPaletteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentColorPalette(GenerateRandomColorPaletteResponseModel responseModel) {
        viewModel.getState().setColorPalette(responseModel.getColorPalette());
        viewModel.firePropertyChanged("colorPalette");
    }

    @Override
    public void presentError(String errorMessage) {
        viewModel.getState().setErrorMessage(errorMessage);
        viewModel.firePropertyChanged("error");
    }
}
