package view;

import interface_adapter.Drawing.DrawingController;
import interface_adapter.Drawing.DrawingViewModel;

import javax.imageio.ImageIO;
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
import java.io.IOException;

public class DrawingView extends JFrame implements PropertyChangeListener {

    private final DrawingViewModel drawingViewModel;
    private final JButton saveButton = new JButton("Save");
    private final JButton clearButton = new JButton("Clear All");
    private int prevX, prevY;
    private DrawingController drawingController;

    public DrawingView(DrawingViewModel drawingViewModel) {
        this.drawingViewModel = drawingViewModel;
        this.drawingViewModel.addPropertyChangeListener(this);

        setTitle("Drawing Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setBackground(Color.WHITE);

        DrawingPanel panel = new DrawingPanel();
        add(panel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        buttonsPanel.add(clearButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(evt -> saveDrawing());
        clearButton.addActionListener(evt -> clearDrawing());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveDrawing() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ((DrawingPanel) getContentPane().getComponent(0)).saveImage(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearDrawing() {
        ((DrawingPanel) getContentPane().getComponent(0)).clear();
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

        public void saveImage(File file) throws IOException {
            if (image != null) {
                ImageIO.write((RenderedImage) image, "png", file);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("drawing".equals(evt.getPropertyName())) {
            repaint();
        } else if ("error".equals(evt.getPropertyName())) {
            JOptionPane.showMessageDialog(this, evt.getNewValue(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
