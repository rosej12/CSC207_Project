package use_cases.StatusManagement.UndoRedo;

import view.DrawingView.DrawingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.Stack;

public class UndoRedoInteractor implements UndoRedoInputBoundary {
    private UndoRedoOutputBoundary boundary;

    private final Stack<Image> undoStack = new Stack<>();
    private final Stack<Image> redoStack = new Stack<>();
    private Image currentImage = getBlankImage(100, 100);

    public UndoRedoInteractor(UndoRedoOutputBoundary boundary) {
        this.boundary = boundary;
    }

    @Override
    public void saveAction(Image image) {
        Image copy = getCopyImage(image);   // the previous image

        undoStack.push(currentImage);
        currentImage = copy;
        redoStack.clear(); // Clear redo stack on new action
    }

    @Override
    public void undo() {
        System.out.println("interactor");
        if (!undoStack.isEmpty()) {
            System.out.println("in undo interactor");
            redoStack.push(currentImage);
            currentImage = undoStack.pop();
//            displayImage(currentImage);
            boundary.changeUndoState(currentImage);
        }
        else {
            System.out.println("stack empty");
        }

//        displayImage(currentImage);
//        boundary.changeUndoState(currentImage);
    }

    @Override
    public void redo() {
        if (!redoStack.isEmpty()) {
            Image action = redoStack.pop();
            undoStack.push(action);
            boundary.changeRedoState(action);
        }
    }

    public Stack<Image> getRedoStack() {
        return redoStack;
    }

    public Stack<Image> getUndoStack() {
        return undoStack;
    }

    public Image getCopyImage(Image image) {
        Image copy = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        copy.getGraphics().drawImage(image, 0, 0, null);
        return copy;
    }

    public static void displayImage(Image image) {

        // Create a JLabel to display the image
        JLabel label = new JLabel(new ImageIcon(image));

        // Create a JFrame to hold the label
        JFrame frame = new JFrame("Image Display");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
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
