package usecases.Render;

import java.awt.Image;

/**
 * The Render Interactor.
 */
public class RenderInteractor implements RenderInputBoundary {
    private static final int DESCRIPTIONLENGTH = 5000;
    private final RenderDataAccessInterface renderDataAccessObject;
    private final RenderOutputBoundary renderPresenter;

    public RenderInteractor(RenderDataAccessInterface renderDataAccessObject, RenderOutputBoundary renderPresenter) {
        this.renderDataAccessObject = renderDataAccessObject;
        this.renderPresenter = renderPresenter;
    }

    @Override
    public void execute(RenderInputData renderInputData) {
        if (renderInputData.getDescription().length() > DESCRIPTIONLENGTH) {
            renderPresenter.prepareRenderFailView("Description is longer than 5000 characters.");
        }
        else if (renderInputData.getDescription().isEmpty()) {
            renderPresenter.prepareRenderFailView("Description is empty.");
        }
        else {
            Image renderedImage = renderDataAccessObject.getRender(
                    renderInputData.getDescription(), renderInputData.getSketch());
            final RenderOutputData renderOutputData = new RenderOutputData(renderedImage);
            renderPresenter.prepareRenderSuccessView(renderOutputData);
        }
    }

    @Override
    public void switchToDrawingView() {
        renderPresenter.switchToDrawingView();
    }

    @Override
    public void saveRender() {
//        renderPresenter.
    }
}
