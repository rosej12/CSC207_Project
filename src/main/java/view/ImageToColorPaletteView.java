package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entities.Color;
import entities.ColorPalette;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteController;
import interface_adapter.ImageToColorPalette.ImageToColorPaletteViewModel;
import interface_adapter.ViewManagerModel;

public class ImageToColorPaletteView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int DIMENSIONALITY = 100;
    private final ImageToColorPaletteViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private ImageToColorPaletteController imageToColorPaletteController;

    private JPanel colorPanel;

    public ImageToColorPaletteView(ImageToColorPaletteViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Initialize and add the buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton uploadButton = new JButton("Upload Image");
        JButton backButton = new JButton("Back");
        buttonsPanel.add(uploadButton);
        buttonsPanel.add(backButton);

        uploadButton.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(ImageToColorPaletteView.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imageToColorPaletteController.generateColorPalette(file);
            }
        });

        backButton.addActionListener(event -> switchToDrawingView());

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
        }
        else if ("error".equals(evt.getPropertyName())) {
            displayError(viewModel.getState().getErrorMessage());
        }
    }

    private void displayError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void displayColors(ColorPalette colorPalette) {
        // Remove the existing color panel if it exists
        if (colorPanel != null) {
            remove(colorPanel);
        }

        // Create a new color panel
        colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (Color color : colorPalette.getColors()) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));
            label.setPreferredSize(new Dimension(DIMENSIONALITY, DIMENSIONALITY));
            colorPanel.add(label);
        }

        add(colorPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle any additional actions if necessary
    }

    /**
     * Retrieves the name of the view.
     *
     * @return The name of the view as a {@code String}.
     */
    public String getViewName() {
        return "ImageToColorPalette";
    }

    /**
     * Sets the controller responsible for handling image-to-color-palette functionality.
     *
     * @param controller The {@link ImageToColorPaletteController} to be set.
     */
    public void setImageToColorPaletteController(ImageToColorPaletteController controller) {
        this.imageToColorPaletteController = controller;
    }
}

