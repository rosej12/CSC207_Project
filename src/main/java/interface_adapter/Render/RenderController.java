package interface_adapter.Render;

import use_cases.Render.RenderInputBoundary;

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
    public void execute() {

    }

    /**
     * Executes the "switch to drawing view" use case
     */
    public void switchToDrawingView() {
        renderInteractor.switchToDrawingView();
    }
}
