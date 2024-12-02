package use_cases.StatusManagement;

import view.DrawingView;

import java.awt.*;

public interface AutoSaveInteractor {
    void saveCanvasState(Image state);
}