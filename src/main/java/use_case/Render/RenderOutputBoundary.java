package use_case.Render;

/**
 * The output boundary for the Render Use Case.
 */
public interface RenderOutputBoundary {

    /**
     * Prepares the success view for the Render Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(RenderOutputData outputData);

    /**
     * Prepares the failure view for the Render Use Case.
     * @param errorMessage the output data
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the Drawing View.
     */
    void switchToDrawingView();
}
