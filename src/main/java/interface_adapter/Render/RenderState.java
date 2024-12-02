package interface_adapter.Render;

import java.awt.*;

/**
 * The state for the Render View Model.
 */
public class RenderState {
    private String sketchDescription = "";
    private String renderError = "";
    private Image sketch;
    private Image render;

    public String getSketchDescription() {
        return sketchDescription;
    }

    public String getRenderError() {
        return renderError;
    }

    public Image getSketch() {
        return sketch;
    }

    public Image getRender() {
        return render;
    }

    public void setSketchDescription(String sketchDescription) {
        this.sketchDescription = sketchDescription;
    }

    public void setRenderError(String renderError) {
        this.renderError = renderError;
    }

    public void setSketch(Image sketch) {
        this.sketch = sketch;
    }

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
