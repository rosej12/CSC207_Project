package use_case.Render;

import java.awt.Image;

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
