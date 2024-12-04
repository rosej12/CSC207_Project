package view;

import entities.ColorPalette;
import interface_adapter.Drawing.DrawingController;
import interface_adapter.Drawing.DrawingState;
import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoController;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoState;
import interface_adapter.StatusManagement.UndoRedo.UndoRedoViewModel;
import interface_adapter.ViewManagerModel;
import use_cases.ImageToColorPalette.ColorPaletteRepositoryInterface;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

public class DrawingView extends JPanel implements PropertyChangeListener {

    private final String viewName = "Drawing";
    private DrawingViewModel drawingViewModel;
    private DrawingController drawingController;
    private ViewManagerModel viewManagerModel;
    private ColorPaletteRepositoryInterface colorPaletteRepository;
    private UndoRedoController undoRedoController;
    private UndoRedoViewModel undoRedoViewModel;

    private final JButton saveButton = new JButton("Save");
    private final JButton clearButton = new JButton("Clear All");
    private final JButton generateColorButton = new JButton("Generate Colors");
    private final JButton imageToColorButton = new JButton("Generate Color from Image");
    private final JButton toRenderButton = new JButton("To Render");

    private final JButton undoButton = new JButton("Undo");
    private final JButton redoButton = new JButton("Redo");

    private final ButtonGroup toolButtonGroup = new ButtonGroup();
    private final JRadioButton paintButton = new JRadioButton("Paint");
    private final JRadioButton eraseButton = new JRadioButton("Erase");

    int initialSize = 1;
    SpinnerNumberModel paintModel = new SpinnerNumberModel(initialSize, initialSize, initialSize + 29, 1);
    private final JSpinner paintSizeSpinner = new JSpinner(paintModel);

    SpinnerNumberModel eraseModel = new SpinnerNumberModel(initialSize, initialSize, initialSize + 29, 1);
    private final JSpinner eraseSizeSpinner = new JSpinner(eraseModel);

    private int prevX, prevY;
    private int drawSize = 1;
    private int eraseSize;
    private int paintSize;
    private static File file = new File("src/java");
//    private static File filename = file.getAbsolutePath();

    private Color currentColor = Color.BLACK;
    private DrawingPanel drawingPanel;
    private JButton[] colorButtons;
    private JPanel buttonsPanel;

    public DrawingView(DrawingViewModel drawingViewModel, ViewManagerModel viewManagerModel,
                       ColorPaletteRepositoryInterface colorPaletteRepository, UndoRedoViewModel undoRedoViewModel) {
        this.drawingViewModel = drawingViewModel;
        this.viewManagerModel = viewManagerModel;
        this.undoRedoViewModel = undoRedoViewModel;
        this.colorPaletteRepository = colorPaletteRepository;
        this.drawingViewModel.addPropertyChangeListener(this);
        this.undoRedoViewModel.addPropertyChangeListener(evt -> undoRedoDisplay());

        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel();

        // Initialize buttonsPanel before using it in updateColorPalette
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Add other buttons to buttonsPanel
        buttonsPanel.add(saveButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(toRenderButton);
        buttonsPanel.add(generateColorButton);
        buttonsPanel.add(imageToColorButton);
        buttonsPanel.add(undoButton);
        buttonsPanel.add(redoButton);

        // Set up action listeners for buttons
        saveButton.addActionListener(e -> saveDrawing());
        clearButton.addActionListener(e -> clearDrawing());
        generateColorButton.addActionListener(e -> generateRandomColors());
        imageToColorButton.addActionListener(e -> switchToImageToColorPaletteView());
        toRenderButton.addActionListener(evt -> switchToRenderView());
        undoButton.addActionListener(evt -> undoAction());
        redoButton.addActionListener(evt -> redoAction());



        // Initialize color palette buttons
        updateColorPalette();

        colorPaletteRepository.addPropertyChangeListener(this);

        // Top panel with tools
        JPanel toolsPanel = new JPanel();
        toolButtonGroup.add(paintButton);
        toolButtonGroup.add(eraseButton);
        paintButton.setSelected(true);

        // Paint Size Spinner
        JLabel pSize = new JLabel("Paint Size");
        toolsPanel.add(pSize);
        toolsPanel.add(paintSizeSpinner);
        paintSizeSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
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
            public void stateChanged(ChangeEvent e) {
                eraseSize = (int) eraseSizeSpinner.getValue();
                drawSize = eraseSize;
            }
        });

        eraseButton.addActionListener(evt -> eraseTool());
        paintButton.addActionListener(evt -> paintTool());

        paintButton.addActionListener(e -> currentColor = Color.BLACK);
        eraseButton.addActionListener(e -> currentColor = Color.WHITE);

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

    // Placeholder method for generating random colors
    private void generateRandomColors() {
        // Implement your color generation logic here
    }

    private void eraseTool() {
        drawSize = eraseSize;
        currentColor = Color.WHITE;
    }

    private void paintTool() {
        drawSize = paintSize;
        currentColor = Color.BLACK;
    }

    private void saveToFile(String fileName, RenderedImage canva) {
        file = new File(fileName);
        drawingController.executeSave(canva, file);
    }

    public void saveDrawing() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            RenderedImage image = (RenderedImage) drawingPanel.getImage();
            drawingController.executeSave(image, file);
        }
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
                java.awt.Color awtColor = new java.awt.Color(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
                colorButton.setBackground(awtColor);
                int index = i;
                colorButton.addActionListener(e -> selectColor(index));
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
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final DrawingState state = drawingViewModel.getState();

        if ("colorPalette".equals(evt.getPropertyName())) {
            updateColorPalette();
        }

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
            state.setError(null); // Reset error after displaying
        }

        if (state.getDrawing() == null) {
            drawingPanel.clear();
        }
    }

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



    public class DrawingPanel extends JPanel {
        private Image image;
        private Graphics2D g2;

        public DrawingPanel() {
            setDoubleBuffered(false);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    prevX = e.getX();
                    prevY = e.getY();
                    g2.setColor(currentColor);
                    g2.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
                    g2.drawLine(prevX, prevY, prevX, prevY);
                    for (int i = 1; i < drawSize; i++) {
                        g2.drawLine(prevX + i, prevY + i, prevX + i, prevY + i);
                        g2.drawLine(prevX - i, prevY - i, prevX - i, prevY - i);
                    }
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
                g2.setColor(currentColor);
                undoRedoController.saveAction(image);
            }
            g.drawImage(image, 0, 0, null);
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

        public void setImage(Image i) {
            image = i;
        }

        public void drawImageToScreen(Image i) {
            g2.drawImage(i, 0, 0, null);
        }

        public Image getImage() {
            return image;
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

    }

    public DrawingController getDrawingController() {
        return drawingController;
    }

    public String getViewName() {
        return viewName;
    }
}
