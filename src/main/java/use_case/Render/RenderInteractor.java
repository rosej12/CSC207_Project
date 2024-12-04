package use_case.Render;

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
            renderPresenter.prepareFailView("Description is longer than 5000 characters.");
        }
        else if (renderInputData.getDescription().isEmpty()) {
            renderPresenter.prepareFailView("Description is empty.");
        }
        else {
            Image renderedImage = renderDataAccessObject.getRender(
                    renderInputData.getDescription(), renderInputData.getSketch());
            final RenderOutputData renderOutputData = new RenderOutputData(renderedImage);
            renderPresenter.prepareSuccessView(renderOutputData);
        }
    }

    @Override
    public void switchToDrawingView() {
        renderPresenter.switchToDrawingView();
    }
}
