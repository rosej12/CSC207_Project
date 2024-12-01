package use_cases.Render;

/**
 * The Render Interactor.
 */
public class RenderInteractor implements RenderInputBoundary {
    private final RenderDataAccessInterface renderDataAccessObject;
    private final RenderOutputBoundary renderPresenter;

    public RenderInteractor(RenderDataAccessInterface renderDataAccessObject, RenderOutputBoundary renderPresenter) {
        this.renderDataAccessObject = renderDataAccessObject;
        this.renderPresenter = renderPresenter;
    }

    @Override
    public void execute(RenderInputData renderInputData) {
        // TODO: implement
    }

    @Override
    public void switchToDrawingView() {
        renderPresenter.switchToDrawingView();
    }
}
