package interface_adapter.ImageToColorPalette;

import use_cases.ImageToColorPalette.ImageToColorPaletteOutputBoundary;

public class ImageToColorPalettePresenter implements ImageToColorPaletteOutputBoundary {
    private final ImageToColorPaletteViewModel viewModel;

    public ImageToColorPalettePresenter(ImageToColorPaletteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentColorPalette(String[] colors) {
        viewModel.getState().setColors(colors);
        viewModel.firePropertyChanged("colors");
    }

    @Override
    public void presentError(String errorMessage) {
        viewModel.getState().setErrorMessage(errorMessage);
        viewModel.firePropertyChanged("error");
    }
}
