package interface_adapter.Render;

import java.awt.Image;

/**
 * The state for the Render View Model.
 */
public class RenderState {
    private String sketchDescription = "";
    private String renderError = "";
    private Image sketch;
    private Image render;

    /**
     * Retrieves the description of the sketch.
     *
     * @return A {@link String} containing the description of the sketch.
     */
    public String getSketchDescription() {
        return sketchDescription;
    }

    /**
     * Retrieves the error message related to the render process.
     *
     * @return A {@link String} containing the render error message.
     */
    public String getRenderError() {
        return renderError;
    }

    /**
     * Retrieves the sketch image.
     *
     * @return An {@link Image} object representing the sketch.
     */
    public Image getSketch() {
        return sketch;
    }

    /**
     * Retrieves the rendered image.
     *
     * @return An {@link Image} object representing the rendered image.
     */
    public Image getRender() {
        return render;
    }

    /**
     * Sets the description of the sketch.
     *
     * @param sketchDescription A {@link String} containing the new description of the sketch.
     */
    public void setSketchDescription(String sketchDescription) {
        this.sketchDescription = sketchDescription;
    }

    /**
     * Sets the render error message.
     *
     * @param renderError A {@link String} containing the error message for the render process.
     */
    public void setRenderError(String renderError) {
        this.renderError = renderError;
    }

    /**
     * Sets the sketch image.
     *
     * @param sketch An {@link Image} object representing the new sketch.
     */
    public void setSketch(Image sketch) {
        this.sketch = sketch;
    }

    /**
     * Sets the rendered image.
     *
     * @param render An {@link Image} object representing the new rendered image.
     */
    public void setRender(Image render) {
        this.render = render;
    }

    @Override
    public String toString() {
        return "RenderState{"
                + "sketchDescription='" + sketchDescription + '\''
                + ", renderError='" + renderError + '\''
                + "}";
    }
}
