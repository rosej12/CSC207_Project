package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;
import java.awt.Graphics2D;

import entities.ColorPalette;
import entities.Shape;
import interface_adapter.Drawing.DrawingController;
import interface_adapter.Drawing.DrawingState;
import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.Shape.ShapeViewModel;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoController;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoState;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoViewModel;
import interface_adapter.ViewManagerModel;
import use_case.ColorPaletteRepositoryInterface;

public class DrawingView extends JPanel implements PropertyChangeListener {

    private final DrawingViewModel drawingViewModel;
    private DrawingController drawingController;
    private final ViewManagerModel viewManagerModel;
    private final ColorPaletteRepositoryInterface colorPaletteRepository;
    private UndoRedoController undoRedoController;
    private UndoRedoViewModel undoRedoViewModel;

    private final JRadioButton paintButton;
    private final JRadioButton eraseButton;

    private final int initialSize = 1;
    private final SpinnerNumberModel paintModel = new SpinnerNumberModel(initialSize, initialSize,
            initialSize + 29, 1);
    private final JSpinner paintSizeSpinner = new JSpinner(paintModel);

    private final SpinnerNumberModel eraseModel = new SpinnerNumberModel(initialSize, initialSize,
            initialSize + 29, 1);
    private final JSpinner eraseSizeSpinner = new JSpinner(eraseModel);

    private int prevX;
    private int prevY;
    private int drawSize = 1;
    private int eraseSize;
    private int paintSize;

    private Color paintingColor = Color.BLACK;
    private Color currentColor = Color.BLACK;
    private final DrawingPanel drawingPanel;
    private JButton[] colorButtons;
    private final JPanel buttonsPanel;

    private ShapeViewModel shapeViewModel;

    public DrawingView(DrawingViewModel drawingViewModel, ViewManagerModel viewManagerModel,
                       ColorPaletteRepositoryInterface colorPaletteRepository, ShapeViewModel shapeViewModel, UndoRedoViewModel undoRedoViewModel) {
        this.drawingViewModel = drawingViewModel;
        this.viewManagerModel = viewManagerModel;
        this.undoRedoViewModel = undoRedoViewModel;
        this.drawingViewModel.addPropertyChangeListener(this);
        this.colorPaletteRepository = colorPaletteRepository;
        this.shapeViewModel = shapeViewModel;
        this.shapeViewModel.addPropertyChangeListener(this);
        this.undoRedoViewModel.addPropertyChangeListener(evt -> undoRedoDisplay());

        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel();

        // Initialize buttonsPanel before using it in updateColorPalette
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Add other buttons to buttonsPanel
        JButton saveButton = new JButton("Save");
        buttonsPanel.add(saveButton);
        JButton clearButton = new JButton("Clear All");
        buttonsPanel.add(clearButton);
        JButton toRenderButton = new JButton("To Render");
        buttonsPanel.add(toRenderButton);
        JButton generateColorButton = new JButton("Generate Colors");
        buttonsPanel.add(generateColorButton);
        JButton imageToColorButton = new JButton("Generate Color from Image");
        buttonsPanel.add(imageToColorButton);
        JButton shapesButton = new JButton("Shapes");
        buttonsPanel.add(shapesButton);
        JButton undoButton = new JButton("Undo");
        buttonsPanel.add(undoButton);
        JButton redoButton = new JButton("Redo");
        buttonsPanel.add(redoButton);

        // Set up action listeners for buttons
        saveButton.addActionListener(event -> saveDrawing());
        clearButton.addActionListener(event -> clearDrawing());
        generateColorButton.addActionListener(event -> switchToGenerateRandomColorsView());
        imageToColorButton.addActionListener(event -> switchToImageToColorPaletteView());
        toRenderButton.addActionListener(evt -> switchToRenderView());
        shapesButton.addActionListener(event -> switchToShapeView());
        undoButton.addActionListener(evt -> undoAction());
        redoButton.addActionListener(evt -> redoAction());

        // Initialize color palette buttons
        updateColorPalette();

        colorPaletteRepository.addPropertyChangeListener(this);

        // Top panel with tools
        ButtonGroup toolButtonGroup = new ButtonGroup();
        paintButton = new JRadioButton("Paint");
        toolButtonGroup.add(paintButton);
        eraseButton = new JRadioButton("Erase");
        toolButtonGroup.add(eraseButton);
        paintButton.setSelected(true);

        // Paint Size Spinner
        JPanel toolsPanel = new JPanel();
        JLabel pSize = new JLabel("Paint Size");
        toolsPanel.add(pSize);
        toolsPanel.add(paintSizeSpinner);
        paintSizeSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                paintSize = (int) paintSizeSpinner.getValue();
                drawSize = paintSize;
            }
        });

        toolsPanel.add(paintButton);

        // Eraser Size Spinner
        toolsPanel.add(eraseButton);

        JLabel eSize = new JLabel("Erase Size");
        toolsPanel.add(eSize);
        toolsPanel.add(eraseSizeSpinner);
        eraseSizeSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                eraseSize = (int) eraseSizeSpinner.getValue();
                drawSize = eraseSize;
            }
        });

        eraseButton.addActionListener(evt -> eraseTool());
        paintButton.addActionListener(evt -> paintTool());

        paintButton.addActionListener(event -> currentColor = Color.BLACK);
        eraseButton.addActionListener(event -> currentColor = Color.WHITE);

        this.add(drawingPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.add(toolsPanel, BorderLayout.NORTH);
    }

    private void undoAction() {
        undoRedoController.undo();
    }

    public void undoRedoDisplay(){
        final UndoRedoState undoRedoState = undoRedoViewModel.getState();

        Image image = undoRedoState.getState();
        drawingPanel.drawImageToScreen(image);
        repaint();
    }

    private void redoAction(){
        undoRedoController.redo();
    }

    // Method to switch to ImageToColorPaletteView
    private void switchToImageToColorPaletteView() {
        viewManagerModel.setState("ImageToColorPalette");
        viewManagerModel.firePropertyChanged();
    }

    // Method to switch to generating random colors
    private void switchToGenerateRandomColorsView() {
        viewManagerModel.setState("GenerateRandomColors");
        viewManagerModel.firePropertyChanged();
    }

    private void eraseTool() {
        drawSize = eraseSize;
        currentColor = Color.WHITE;
    }

    private void paintTool() {
        drawSize = paintSize;
        currentColor = paintingColor;
    }

    private void saveDrawing() {
        JFileChooser fileChooser = new JFileChooser();

        // Create separate filters for each image extension
        FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPEG Image (*.jpg, *.jpeg)", "jpg");
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Image (*.png)", "png");
        FileNameExtensionFilter gifFilter = new FileNameExtensionFilter("GIF Image (*.gif)", "gif");

        // Add filters to the file chooser
        fileChooser.addChoosableFileFilter(jpgFilter);
        fileChooser.addChoosableFileFilter(pngFilter);
        fileChooser.addChoosableFileFilter(gifFilter);

        // Set default filter
        fileChooser.setAcceptAllFileFilterUsed(false);

        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String extension = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];
            File file = new File(addExtensionIfNoneAlready(fileChooser.getSelectedFile().getAbsolutePath(), extension));
            RenderedImage image = (RenderedImage) drawingPanel.getImage();
            drawingController.executeSave(image, file);
        }
    }

    private String addExtensionIfNoneAlready(String fileName, String extension) {
        if (getFileExtension(fileName).isEmpty()) {
            return fileName + "." + extension;
        }
        return fileName;
    }

    private String getFileExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex >= 0 && extensionIndex < fileName.length() - 1) {
            return fileName.toLowerCase().substring(extensionIndex + 1);
        }
        return "";
    }

    private void clearDrawing() {
        drawingController.executeClear();
    }

    private void updateColorPalette() {
        // Retrieve the color palette from the repository
        ColorPalette colorPalette = colorPaletteRepository.getColorPalette();

        if (colorPalette != null) {
            // Update UI components (color buttons)
            // Remove existing color buttons if any
            if (colorButtons != null) {
                for (JButton colorButton : colorButtons) {
                    buttonsPanel.remove(colorButton);
                }
            }

            // Create new color buttons
            int paletteSize = colorPalette.size();
            colorButtons = new JButton[paletteSize];
            for (int i = 0; i < paletteSize; i++) {
                JButton colorButton = new JButton();
                entities.Color colorEntity = colorPalette.getColor(i);
                java.awt.Color awtColor = new java.awt.Color(colorEntity.getRed(), colorEntity.getGreen(),
                        colorEntity.getBlue());
                colorButton.setBackground(awtColor);
                int index = i;
                colorButton.addActionListener(event -> selectColor(index));
                buttonsPanel.add(colorButton);
                colorButtons[i] = colorButton;
            }

            // Refresh the panel
            buttonsPanel.revalidate();
            buttonsPanel.repaint();
        }
    }

    private void selectColor(int index) {
        entities.Color color = colorPaletteRepository.getColorPalette().getColor(index);
        currentColor = new java.awt.Color(
                color.getRed(), color.getGreen(), color.getBlue());
        paintingColor = currentColor;
        paintButton.setSelected(true);
    }

    private void switchToShapeView() {
        viewManagerModel.setState("Shape");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final DrawingState state = drawingViewModel.getState();

        if ("colorPalette".equals(evt.getPropertyName())) {
            updateColorPalette();
        }

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
            state.setError(null);
        }

        if (state.getDrawing() == null) {
            drawingPanel.clear();
        }

        if ("shapes".equals(evt.getPropertyName())) {
            drawingPanel.repaint(); // Repaint the canvas when the shapes list is updated
        }
    }

    /**
     * Draws a single shape on the drawing canvas.
     *
     * @param shape the shape to draw
     */
    private void drawShapeOnCanvas(Graphics2D g2d, Shape shape) {
        g2d.setColor(shape.getColor());
        g2d.setStroke(new BasicStroke(shape.getStrokeWidth()));

        switch (shape.getType()) {
            case LINE:
                g2d.drawLine(shape.getX1(), shape.getY1(), shape.getX2(), shape.getY2());
                break;
            case RECTANGLE:
                g2d.drawRect(shape.getX1(), shape.getY1(),
                        shape.getX2() - shape.getX1(), shape.getY2() - shape.getY1());
                break;
            case SQUARE:
                int side = Math.min(Math.abs(shape.getX2() - shape.getX1()),
                        Math.abs(shape.getY2() - shape.getY1()));
                g2d.drawRect(shape.getX1(), shape.getY1(), side, side);
                break;
            case CIRCLE:
                int diameter = Math.min(Math.abs(shape.getX2() - shape.getX1()),
                        Math.abs(shape.getY2() - shape.getY1()));
                g2d.drawOval(shape.getX1(), shape.getY1(), diameter, diameter);
                break;
            case TRIANGLE:
                int[] xPoints = {shape.getX1(), shape.getX2(), (shape.getX1() + shape.getX2()) / 2};
                int[] yPoints = {shape.getY1(), shape.getY1(), shape.getY2()};
                g2d.drawPolygon(xPoints, yPoints, 3);
                break;
            default:
                throw new IllegalArgumentException("Unsupported shape type: " + shape.getType());
        }
    }

    /**
     * Sets the DrawingController for this view.
     *
     * @param controller the DrawingController to be associated with this view.
     */
    public void setDrawingController(DrawingController controller) {
        this.drawingController = controller;
    }

    private void switchToRenderView() {
        DrawingPanel panel = (DrawingPanel) getComponent(0);
        drawingController.switchToRenderView(panel.getImage());
    }

    public void setUndoRedoController(UndoRedoController undoRedoController) {
        this.undoRedoController = undoRedoController;
    }

    /**
     * Retrieves the current DrawingController instance associated with this view.
     *
     * @return the DrawingController instance controlling this view.
     */
    public DrawingController getDrawingController() {
        return drawingController;
    }

    /**
     * Gets the name of this view.
     *
     * @return a string representing the name of the view, which is "Drawing".
     */
    public String getViewName() {
        return "Drawing";
    }

    private final class DrawingPanel extends JPanel {
        private Image image;
        private Graphics2D g2;

        private DrawingPanel() {
            setDoubleBuffered(false);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    prevX = e.getX();
                    prevY = e.getY();
                    g2.setColor(currentColor);
                    g2.drawLine(e.getX() + drawSize / 2, e.getY() + drawSize / 2, e.getX() - drawSize / 2, e.getY() - drawSize / 2);
                    repaint();
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    g2.setColor(currentColor);
                    g2.drawLine(prevX, prevY, x, y);
                    for (int i = 1; i < drawSize; i++) {
                        g2.drawLine(prevX + i, prevY + i, x + i, y + i);
                        g2.drawLine(prevX - i, prevY - i, x - i, y - i);
                    }

                    repaint();
                    prevX = x;
                    prevY = y;
                }

            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    undoRedoController.saveAction(image);

                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (image == null) {
                image = createImage(getSize().width, getSize().height);
                g2 = (Graphics2D) image.getGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                undoRedoController.saveAction(image);
            }

            g.drawImage(image, 0, 0, null);

            // Render all shapes
            List<Shape> shapes = shapeViewModel.getShapes();
            if (shapes != null) {
                Graphics2D g2d = (Graphics2D) g;
                for (Shape shape : shapes) {
                    drawShapeOnCanvas(g2d, shape); // Render shapes
                }
            }
        }

        public void clear() {
            if (g2 != null) {
                g2.setPaint(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setPaint(currentColor);
                repaint();
                undoRedoController.saveAction(image);
            }
        }

        public void drawImageToScreen(Image i) {
            g2.drawImage(i, 0, 0, null);
        }

        public Image getImage() {
            return image;
        }
    }
}
