package view;

import interface_adapter.Drawing.DrawingController;
import interface_adapter.Drawing.DrawingState;
import interface_adapter.Drawing.DrawingViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

public class DrawingView extends JPanel implements ActionListener, PropertyChangeListener {

    private final DrawingViewModel drawingViewModel;
    private final JButton saveButton = new JButton("Save");
    private final JButton clearButton = new JButton("Clear All");
    private int prevX, prevY;
    private DrawingController drawingController;

    public DrawingView(DrawingViewModel drawingViewModel) {
        this.drawingViewModel = drawingViewModel;
        this.drawingViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        DrawingPanel panel = new DrawingPanel();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(clearButton);

        saveButton.addActionListener(evt -> saveDrawing());
        clearButton.addActionListener(evt -> clearDrawing());

        this.add(panel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
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
                g2.setColor(Color.BLACK);
            }
            g.drawImage(image, 0, 0, null);
        }

        public void clear() {
            if (g2 != null) {
                g2.setPaint(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setPaint(Color.BLACK);
                repaint();
            }
        }

        public Image getImage() {
            return image;
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
