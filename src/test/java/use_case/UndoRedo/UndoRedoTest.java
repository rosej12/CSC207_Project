package use_case.UndoRedo;

import interface_adapter.StatusManagement.UndoRedo.UndoRedoPresenter;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoViewModel;
import org.junit.Test;
import use_cases.StatusManagement.UndoRedo.UndoRedoInputBoundary;
import use_cases.StatusManagement.UndoRedo.UndoRedoInteractor;
import use_cases.StatusManagement.UndoRedo.UndoRedoOutputBoundary;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class UndoRedoTest {

    @Test
    public void saveActionTest() {
        Image testImage = getBlankImage(500, 500);
        UndoRedoViewModel viewModel = new UndoRedoViewModel();
        UndoRedoOutputBoundary undoRedoOutputBoundary = new UndoRedoOutputBoundary() {

            @Override
            public void changeUndoRedoState(Image image) {
                assert (image != null);
                assert (image instanceof BufferedImage);
                assert (image.equals(testImage));
            }
        };
        UndoRedoInteractor interactor = new UndoRedoInteractor(undoRedoOutputBoundary);

        interactor.saveAction(testImage);

    }

    @Test
    public void undoTest() {
        int size = 2;
        Stack<Image> undoStack = new Stack<>();
        UndoRedoViewModel viewModel = new UndoRedoViewModel();
        UndoRedoOutputBoundary undoRedoOutputBoundary = new UndoRedoOutputBoundary() {

            @Override
            public void changeUndoRedoState(Image image) {
                assert(image != null);
                assert (image instanceof BufferedImage);
                assert(undoStack.pop() != image);
            }
        };
        UndoRedoInteractor interactor = new UndoRedoInteractor(undoRedoOutputBoundary);

        for (int i = 0;i < size; i++){
            undoStack.push(getBlankImage(500, 500));
        }

        assert(undoStack.size() == size);

        interactor.undo();

    }

    @Test
    public void redoTest() {
        int size = 2;
        Stack<Image> redoStack = new Stack<>();
        UndoRedoViewModel viewModel = new UndoRedoViewModel();
        UndoRedoOutputBoundary undoRedoOutputBoundary = new UndoRedoOutputBoundary() {

            @Override
            public void changeUndoRedoState(Image image) {
                assert(image != null);
                assert (image instanceof BufferedImage);
                assert(redoStack.pop() != image);
                // check the length of undo stack after
                // check the length of redo stack after
            }
        };
        UndoRedoInteractor interactor = new UndoRedoInteractor(undoRedoOutputBoundary);

        for (int i = 0;i < size; i++){
            redoStack.push(getBlankImage(500, 500));
        }

        assert(redoStack.size() == size);

        interactor.redo();

    }

    private Image getBlankImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);    // the background color
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        return image;
    }
}
