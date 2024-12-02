package interface_adapter.StatusManagement.AutoSave;

import use_cases.StatusManagement.AutoSave.AutoSaveInputBoundary;

import java.awt.image.RenderedImage;

public class AutoSaveController {
    private final AutoSaveInputBoundary autosaveUseCase;

    public AutoSaveController(AutoSaveInputBoundary autosaveUseCase) {
        this.autosaveUseCase = autosaveUseCase;
    }

    /**
     * Initiates the autosave process by calling the use case.
     */
    public void autosave(RenderedImage currentState) {
        autosaveUseCase.saveCanvasState(currentState);
    }
}
