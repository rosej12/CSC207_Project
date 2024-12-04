package use_cases.StatusManagement.UndoRedo;

import view.DrawingView.DrawingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.Collections;
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
//        displayImage(currentImage);
        Image copy = getImageCopy(image);


        undoStack.push(currentImage);
        System.out.println(undoStack.size());
        currentImage = copy;
        redoStack.clear(); // Clear redo stack on new action
        display();
    }

    @Override
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentImage);
            currentImage = undoStack.pop();
            boundary.changeUndoState(currentImage);
        }
        display();
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

    public void display() {
        Stack<Image> undoStackCopy = (Stack<Image>) undoStack.clone();
        Stack<Image> redoStackCopy = (Stack<Image>) redoStack.clone();

        ArrayList<Image> undoArray = new ArrayList<>(undoStackCopy);

        JFrame frame = new JFrame("Undo Redo Display");
        frame.setSize(800,800);
        frame.setLayout(new FlowLayout());

        // For each element in undoStack, display
        for (int i = 0; i < undoArray.size(); i++) {
            JLabel label = new JLabel("Undo");
            Image image = shrinkImage(getBufferedImage(undoArray.get(i)), 300, 300);
            label.setVerticalTextPosition(SwingConstants.TOP);
            label.setHorizontalTextPosition(SwingConstants.CENTER);
            label.setIcon(new ImageIcon(image));
            frame.add(label);
        }
        // display currentImage
        JLabel label1 = new JLabel("current");
        Image image = shrinkImage(getBufferedImage(currentImage), 300, 300);
        label1.setIcon(new ImageIcon(image));
        label1.setVerticalTextPosition(SwingConstants.TOP);
        label1.setHorizontalTextPosition(SwingConstants.CENTER);
        frame.add(label1);

        // For each element in redoStack, display
        while (!redoStackCopy.isEmpty()) {
            JLabel label = new JLabel("Redo");
            Image image1 = shrinkImage(getBufferedImage(redoStackCopy.pop()), 300, 300);
            label.setVerticalTextPosition(SwingConstants.TOP);
            label.setHorizontalTextPosition(SwingConstants.CENTER);
            label.setIcon(new ImageIcon(image1));
            frame.add(label);
        }
        frame.pack();
        frame.setVisible(true);
    }

    private BufferedImage shrinkImage(BufferedImage image, int maxWidth, int maxHeight) {
        int newWidth = Math.min(maxWidth, image.getWidth());
        int newHeight = Math.min(maxHeight, image.getHeight());
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    private BufferedImage getBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }

    private Image getBlankImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);    // the background color
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        return image;
    }

    private Image getImageCopy(Image image) {
        Image i = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        i.getGraphics().drawImage(image, 0, 0, null);
        return i;
    }
}
