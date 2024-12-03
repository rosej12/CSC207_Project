package usecases.Render;

/**
 * The input boundary for actions related to rendering.
 */
public interface RenderInputBoundary {

    /**
     * Executes the render use case.
     * @param renderInputData the input data
     */
    void execute(RenderInputData renderInputData);

    /**
     * Executes the "switch to drawing view" use case.
     */
    void switchToDrawingView();

    /**
     * Executes the save render use case.
     */
    void saveRender();
}
