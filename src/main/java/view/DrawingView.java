package view;

import interface_adapter.Drawing.DrawingController;
import interface_adapter.Drawing.DrawingState;
import interface_adapter.Drawing.DrawingViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.Random;

public class DrawingView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Drawing";
    private final DrawingViewModel drawingViewModel;
    private DrawingController drawingController;
    private final ViewManagerModel viewManagerModel;

    private final JButton saveButton = new JButton("Save");
    private final JButton clearButton = new JButton("Clear All");
    private final JButton generateColorButton = new JButton("Generate Colors");
    private final JButton imageToColorButton = new JButton("Generate Color from Image");

    private final ButtonGroup toolButtonGroup = new ButtonGroup();
    private final JRadioButton paintButton = new JRadioButton("Paint");
    private final JRadioButton eraseButton = new JRadioButton("Erase");

    private Color currentColor = Color.BLACK;
    private DrawingPanel drawingPanel;

    public DrawingView(DrawingViewModel drawingViewModel, ViewManagerModel viewManagerModel) {
        this.drawingViewModel = drawingViewModel;
        this.viewManagerModel = viewManagerModel;
        this.drawingViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel();

        // Top panel with tools
        JPanel toolsPanel = new JPanel();
        toolButtonGroup.add(paintButton);
        toolButtonGroup.add(eraseButton);
        paintButton.setSelected(true);
        toolsPanel.add(paintButton);
        toolsPanel.add(eraseButton);

        paintButton.addActionListener(e -> currentColor = Color.BLACK);
        eraseButton.addActionListener(e -> currentColor = Color.WHITE);

        // Bottom panel with buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(generateColorButton);
        buttonsPanel.add(imageToColorButton);

        saveButton.addActionListener(e -> saveDrawing());
        clearButton.addActionListener(e -> clearDrawing());
        generateColorButton.addActionListener(e -> generateRandomColors());
        imageToColorButton.addActionListener(e -> switchToImageToColorPaletteView());

        this.add(drawingPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.add(toolsPanel, BorderLayout.NORTH);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final DrawingState state = drawingViewModel.getState();

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
            state.setError(null); // Reset error after displaying
        }

        if (state.getDrawing() == null) {
            drawingPanel.clear();
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setDrawingController(DrawingController controller) {
        this.drawingController = controller;
    }

    private class DrawingPanel extends JPanel {
        private Image image;
        private Graphics2D g2;

        public DrawingPanel() {
            setDoubleBuffered(false);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    g2.setColor(currentColor);
                    g2.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
                    repaint();
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    g2.setColor(currentColor);
                    g2.fillOval(e.getX(), e.getY(), 5, 5);
                    repaint();
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
}
