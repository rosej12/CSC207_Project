package usecases.Render;

import java.awt.*;

/**
 * DAO for the Render Use Case.
 */
public interface RenderDataAccessInterface {
    /**
     * Convert the sketch to a rendered image.
     * @param description the user's text prompt.
     * @param sketch      the user's sketch.
     * @return The rendered image.
     */
    Image getRender(String description, Image sketch);
}
