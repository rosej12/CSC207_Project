package data_access;

import use_cases.StatusManagement.UndoRedo.UndoRedoDataAccessInterface;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class UndoRedoDataAccess implements UndoRedoDataAccessInterface {



    @Override
    public BufferedImage getUndoState() throws IOException {
        ;
    }

    @Override
    public BufferedImage getRedoState() throws IOException {
        return null;
    }

    @Override
    public void clearHistory() {

    }
}
