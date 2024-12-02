package interface_adapter.Render;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Render View.
 */
public class RenderViewModel extends ViewModel<RenderState> {
    public static final String SKETCH_LABEL = "Sketch";
    public static final String RENDER_LABEL = "Render";
    public static final String DESCRIPTION_LABEL = "Enter a description (max 5000 characters):";
    public static final String RENDER_BUTTON_LABEL = "Render";
    public static final String TO_DRAWING_BUTTON_LABEL = "Back To Drawing";

    public RenderViewModel() {
        super("render");
        setState(new RenderState());
    }
}

