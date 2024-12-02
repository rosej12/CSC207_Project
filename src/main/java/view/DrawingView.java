package view;

import entities.ColorPalette;
import interface_adapter.Drawing.DrawingController;
import interface_adapter.Drawing.DrawingState;
import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.ViewManagerModel;
import use_cases.ColorPaletteRepositoryInterface;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

public class DrawingView extends JPanel implements PropertyChangeListener {

    private final String viewName = "Drawing";
    private final DrawingViewModel drawingViewModel;
    private DrawingController drawingController;
    private final ViewManagerModel viewManagerModel;
    private final ColorPaletteRepositoryInterface colorPaletteRepository;

    private final JButton saveButton = new JButton("Save");
    private final JButton clearButton = new JButton("Clear All");
    private final JButton generateColorButton = new JButton("Generate Colors");
    private final JButton imageToColorButton = new JButton("Generate Color from Image");
    private final JButton toRenderButton = new JButton("To Render");

    private final ButtonGroup toolButtonGroup = new ButtonGroup();
    private final JRadioButton paintButton = new JRadioButton("Paint");
    private final JRadioButton eraseButton = new JRadioButton("Erase");

    int initialSize = 1;
    SpinnerNumberModel paintModel = new SpinnerNumberModel(initialSize, initialSize, initialSize + 29, 1 );
    private final JSpinner paintSizeSpinner =  new JSpinner(paintModel);

    SpinnerNumberModel eraseModel = new SpinnerNumberModel(initialSize, initialSize, initialSize + 29, 1 );
    private final JSpinner eraseSizeSpinner =  new JSpinner(eraseModel);

    private int prevX, prevY;
    private int drawSize = 1;
    private int eraseSize;
    private int paintSize;

    private Color currentColor = Color.BLACK;
    private DrawingPanel drawingPanel;
    private JButton[] colorButtons;
    private JPanel buttonsPanel;

    public DrawingView(DrawingViewModel drawingViewModel, ViewManagerModel viewManagerModel,
                       ColorPaletteRepositoryInterface colorPaletteRepository) {
        this.drawingViewModel = drawingViewModel;
        this.viewManagerModel = viewManagerModel;
        this.drawingViewModel.addPropertyChangeListener(this);
        this.colorPaletteRepository = colorPaletteRepository;

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

        // Set up action listeners for buttons
        saveButton.addActionListener(e -> saveDrawing());
        clearButton.addActionListener(e -> clearDrawing());
        generateColorButton.addActionListener(e -> switchToGenerateRandomColorsView());
        imageToColorButton.addActionListener(e -> switchToImageToColorPaletteView());
        toRenderButton.addActionListener(evt -> switchToRenderView());

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
        currentColor = Color.BLACK;
    }

    private void saveDrawing() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
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

    private class DrawingPanel extends JPanel {
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
            }
            g.drawImage(image, 0, 0, null);
        }

        public void clear() {
            if (g2 != null) {
                g2.setPaint(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setPaint(currentColor);
                repaint();
            }
        }

        public Image getImage() {
            return image;
        }
    }

    public DrawingController getDrawingController(){
        return drawingController;
    }

    public String getViewName() {
        return viewName;
    }
}
