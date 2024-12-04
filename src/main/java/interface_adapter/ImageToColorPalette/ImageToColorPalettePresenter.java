package interface_adapter.ImageToColorPalette;

import use_case.ImageToColorPalette.ImageToColorPaletteOutputBoundary;
import use_case.ImageToColorPalette.ImageToColorPaletteResponseModel;

public class ImageToColorPalettePresenter implements ImageToColorPaletteOutputBoundary {
    private final ImageToColorPaletteViewModel viewModel;

    public ImageToColorPalettePresenter(ImageToColorPaletteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentColorPalette(ImageToColorPaletteResponseModel responseModel) {
        viewModel.getState().setColorPalette(responseModel.getColorPalette());
        viewModel.firePropertyChanged("colorPalette");
    }

    @Override
    public void presentError(String errorMessage) {
        viewModel.getState().setErrorMessage(errorMessage);
        viewModel.firePropertyChanged("error");
    }
}
