package use_cases.Render;

import java.awt.*;

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
        if (renderInputData.getDescription().length() > 5000) {
            renderPresenter.prepareFailView("Description is longer than 5000 characters.");
        } else if (renderInputData.getDescription().isEmpty()) {
            renderPresenter.prepareFailView("Description is empty.");
        } else {
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
