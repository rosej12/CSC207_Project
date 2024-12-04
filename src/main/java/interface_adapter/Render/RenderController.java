package interface_adapter.Render;

import java.awt.Image;

import use_case.Render.RenderInputBoundary;
import use_case.Render.RenderInputData;

public class RenderController {
    private final RenderInputBoundary renderInteractor;

    public RenderController(RenderInputBoundary renderInteractor) {
        this.renderInteractor = renderInteractor;
    }

    /**
     * Executes the render operation using the provided description and sketch.
     *
     * @param description A textual description of the image to render.
     * @param sketch      An {@link Image} object representing the sketch to render.
     */
    public void execute(String description, Image sketch) {
        final RenderInputData renderInputData = new RenderInputData(description, sketch);
        renderInteractor.execute(renderInputData);
    }

    /**
     * Switches the application view to the drawing view.
     */
    public void switchToDrawingView() {
        renderInteractor.switchToDrawingView();
    }
}
