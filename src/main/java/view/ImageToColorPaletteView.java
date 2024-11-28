package view;

import entities.ColorPalette;
import entities.Color;
import interface_adapter.Drawing.DrawingController;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteController;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class ImageToColorPaletteView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "ImageToColorPalette";
    private final ImageToColorPaletteViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final JButton backButton = new JButton("Back");
    private final JButton uploadButton = new JButton("Upload Image");
    private ImageToColorPaletteController imageToColorPaletteController;

    public ImageToColorPaletteView(ImageToColorPaletteViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Panel for buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(uploadButton);
        buttonsPanel.add(backButton);

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(ImageToColorPaletteView.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imageToColorPaletteController.generateColorPalette(file);
            }
        });

        backButton.addActionListener(e -> switchToDrawingView());

        add(buttonsPanel, BorderLayout.NORTH);
    }

    private void switchToDrawingView() {
        viewManagerModel.setState("Drawing");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("colorPalette".equals(evt.getPropertyName())) {
            displayColors(viewModel.getState().getColorPalette());
        } else if ("error".equals(evt.getPropertyName())) {
            displayError(viewModel.getState().getErrorMessage());
        }
    }

    private void displayError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void displayColors(ColorPalette colorPalette) {
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (Color color : colorPalette.getColors()) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));
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

    public String getViewName() {return viewName;}

    public void setImageToColorPaletteController(ImageToColorPaletteController controller) {
        this.imageToColorPaletteController = controller;
    }
}
