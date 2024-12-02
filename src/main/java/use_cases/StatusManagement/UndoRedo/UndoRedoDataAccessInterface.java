package use_cases.StatusManagement.UndoRedo;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface UndoRedoDataAccessInterface {
    /**
     * Retrieve the most recent canvas state for undo.
     *
     * @return The most recent canvas state or null if none exists.
     * @throws IOException if loading the state fails.
     */
    BufferedImage getUndoState() throws IOException;

    /**
     * Retrieve the next canvas state for redo.
     *
     * @return The redo state or null if none exists.
     * @throws IOException if loading the state fails.
     */
    BufferedImage getRedoState() throws IOException;

    /**
     * Clear all undo and redo history.
     */
    void clearHistory();
}
