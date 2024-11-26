package view;

import interface_adapter.ImageToColorPalette.ImageToColorPaletteController;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class ImageToColorPaletteView extends JPanel implements ActionListener, PropertyChangeListener {
    private final ImageToColorPaletteController controller;
    private final ImageToColorPaletteViewModel viewModel;
    private final JButton uploadButton = new JButton("Upload Image");

    public ImageToColorPaletteView(ImageToColorPaletteController controller, ImageToColorPaletteViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(ImageToColorPaletteView.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                controller.generateColorPalette(file);
            }
        });

        add(uploadButton, BorderLayout.NORTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("colors".equals(evt.getPropertyName())) {
            displayColors((String[]) evt.getNewValue());
        }
    }

    private void displayColors(String[] colors) {
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (String color : colors) {
            JLabel label = new JLabel(color);
            label.setOpaque(true);
            label.setBackground(Color.decode(color));
            label.setPreferredSize(new Dimension(100, 100));
            colorPanel.add(label);
        }
        add(colorPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
