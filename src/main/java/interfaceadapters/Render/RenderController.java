package interfaceadapters.Render;

import usecases.Render.RenderInputBoundary;
import usecases.Render.RenderInputData;

import java.awt.*;

/**
 * Controller for the Render Use Case
 */
public class RenderController {
    private final RenderInputBoundary renderInteractor;

    public RenderController(RenderInputBoundary renderInteractor) {
        this.renderInteractor = renderInteractor;
    }

    /**
     * Executes the Render Use Case.
     */
    public void execute(String description, Image sketch) {
        final RenderInputData renderInputData = new RenderInputData(description, sketch);
        renderInteractor.execute(renderInputData);
    }

    /**
     * Executes the "switch to drawing view" use case
     */
    public void switchToDrawingView() {
        renderInteractor.switchToDrawingView();
    }
}
