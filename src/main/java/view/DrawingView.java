package view;

import interface_adapter.Drawing.DrawingController;
import interface_adapter.Drawing.DrawingState;
import interface_adapter.Drawing.DrawingViewModel;

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

public class DrawingView extends JPanel implements ActionListener, PropertyChangeListener {

    private final DrawingViewModel drawingViewModel;
    private final JButton saveButton = new JButton("Save");
    private final JButton clearButton = new JButton("Clear All");
    private final ButtonGroup toolButtonGroup = new ButtonGroup();
    private final JRadioButton paintButton = new JRadioButton("Paint");
    private final JRadioButton eraseButton = new JRadioButton("Erase");

    private int prevX, prevY;
    private DrawingController drawingController;
    private Color[] colorPalette;
    private boolean[] colorLocks;
    private JButton[] colorButtons;

    // TODO: may need to move
    private Color backgroundColor = Color.WHITE;
    private Color drawingColor = Color.BLACK;
    private Color currentColor = drawingColor;

    public DrawingView(DrawingViewModel drawingViewModel) {
        this.drawingViewModel = drawingViewModel;
        this.drawingViewModel.addPropertyChangeListener(this);
        colorPalette = new Color[3];
        colorLocks = new boolean[3];
        colorButtons = new JButton[3];

        generateRandomColorPalette();

        setLayout(new BorderLayout());

        DrawingPanel panel = new DrawingPanel();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(clearButton);

        saveButton.addActionListener(evt -> saveDrawing());
        clearButton.addActionListener(evt -> clearDrawing());

        JPanel toolsPanel = new JPanel();
        toolButtonGroup.add(paintButton);
        toolButtonGroup.add(eraseButton);
        paintButton.setSelected(true);
        toolsPanel.add(paintButton);
        toolsPanel.add(eraseButton);
        eraseButton.addActionListener(evt -> eraseTool());
        paintButton.addActionListener(evt -> paintTool());

        JButton randomColorButton = new JButton("Generate Colors");
        randomColorButton.addActionListener(e -> generateRandomColorPalette());
        buttonsPanel.add(randomColorButton);

        for (int i = 0; i < colorPalette.length; i++) {
            JButton colorButton = new JButton();
            int index = i;
            colorButton.setBackground(colorPalette[index]);
            colorButton.addActionListener(e -> selectSlot(index));
            buttonsPanel.add(colorButton);
            colorButtons[i] = colorButton;
        }

        JButton colorPickerButton = new JButton("Pick Color");
        colorPickerButton.addActionListener(e -> openColorPicker());
        buttonsPanel.add(colorPickerButton);

        this.add(panel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.add(toolsPanel, BorderLayout.NORTH);
    }

    private void generateRandomColorPalette() {
        Random rand = new Random();
        for (int i = 0; i < colorPalette.length; i++) {
            if (!colorLocks[i]) {
                colorPalette[i] = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            }
        }
        updateColorButtons();
    }

    private void selectSlot(int index) {
        currentColor = colorPalette[index];
    }

    private void toggleColorLock(int index) {
        colorLocks[index] = !colorLocks[index];
    }

    private void openColorPicker() {
        Color selectedColor = JColorChooser.showDialog(this, "Select Color", currentColor);
        if (selectedColor != null) {
            for (int i = 0; i < colorPalette.length; i++) {
                if (colorButtons[i].getBackground().equals(currentColor)) {
                    colorPalette[i] = selectedColor;
                    break;
                }
            }
            updateColorButtons();
        }
    }

    private void updateColorButtons() {
        for (int i = 0; i < colorPalette.length; i++) {
            if (colorButtons[i] != null) {
                colorButtons[i].setBackground(colorPalette[i]);
            }
        }
    }

    private void eraseTool() {
        currentColor = backgroundColor;
    }

    private void paintTool() {
        currentColor = drawingColor;
    }

    private void saveDrawing() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            DrawingPanel panel = (DrawingPanel) getComponent(0);
            drawingController.executeSave((RenderedImage) panel.getImage(), file);
        }
    }

    private void clearDrawing() {
        drawingController.executeClear();
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
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    if (g2 != null) {
                        g2.setColor(currentColor);
                        g2.drawLine(prevX, prevY, x, y);
                        repaint();
                        prevX = x;
                        prevY = y;
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image == null) {
                image = createImage(getSize().width, getSize().height);
                g2 = (Graphics2D) image.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(currentColor);
            }
            g.drawImage(image, 0, 0, null);
        }

        public void clear() {
            if (g2 != null) {
                g2.setPaint(backgroundColor);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setPaint(currentColor);
                repaint();
            }
        }

        public Image getImage() {
            return image;
        }

        public boolean isDrawingEmpty() {
            if (image instanceof BufferedImage) {
                BufferedImage bufferedImage = (BufferedImage) image;
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        if (bufferedImage.getRGB(x, y) != backgroundColor.getRGB()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final DrawingState state = (DrawingState) evt.getNewValue();
        setFields(state);
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setFields(DrawingState state) {
        DrawingPanel panel = (DrawingPanel) getComponent(0);
        panel.clear();
        if (state.getDrawing() != null) {
            Graphics2D g2 = (Graphics2D) panel.getImage().getGraphics();
            g2.drawImage((Image) state.getDrawing(), 0, 0, null);
            panel.repaint();
        }
    }

    public void setDrawingController(DrawingController controller) {
        this.drawingController = controller;
    }
}
