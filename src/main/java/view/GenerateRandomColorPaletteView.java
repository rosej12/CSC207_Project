package view;

import entities.ColorPalette;
import entities.Color;
import interface_adapter.Drawing.DrawingController;
import interface_adapter.GenerateRandomColor.GenerateRandomColorController;
import interface_adapter.GenerateRandomColor.GenerateRandomColorPaletteViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GenerateRandomColorPaletteView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "GenerateRandomColors";
    private final GenerateRandomColorPaletteViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final JButton backButton = new JButton("Back");
    private final JButton generateButton = new JButton("Generate");
    private GenerateRandomColorController generateRandomColorController;
    private JPanel colorPanel;

    public GenerateRandomColorPaletteView(GenerateRandomColorPaletteViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);
        this.viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(backButton);
        buttonsPanel.add(generateButton);

        generateButton.addActionListener(e -> generateRandomColorController.GenerateRandomColor());

        backButton.addActionListener(e -> switchToGenerateRandomColorsView());

        add(buttonsPanel, BorderLayout.NORTH);

        colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(colorPanel, BorderLayout.CENTER);
    }
    private void switchToGenerateRandomColorsView() {
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
        colorPanel.removeAll();

        for (Color color : colorPalette.getColors()) {
            JPanel colorContainer = new JPanel(new BorderLayout());
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));
            label.setPreferredSize(new Dimension(100, 100));

            JButton lockButton = new JButton(color.isLocked() ? "Unlock" : "Lock");
            lockButton.addActionListener(e -> {
                color.setLocked(!color.isLocked());
                lockButton.setText(color.isLocked() ? "Unlock" : "Lock");
            });

            JButton pickColorButton = new JButton("Pick Color");
            pickColorButton.addActionListener(e -> {
                java.awt.Color newColor = JColorChooser.showDialog(null, "Choose a Color", label.getBackground());
                if (newColor != null) {
                    color.setRed(newColor.getRed());
                    color.setGreen(newColor.getGreen());
                    color.setBlue(newColor.getBlue());
                    label.setBackground(newColor);
                }
            });

            colorContainer.add(label, BorderLayout.CENTER);
            colorContainer.add(lockButton, BorderLayout.SOUTH);
            colorContainer.add(pickColorButton, BorderLayout.NORTH);
            colorPanel.add(colorContainer);
        }

        colorPanel.revalidate();
        colorPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public String getViewName() {
        return viewName;
    }

    public void setGenerateRandomColorController(GenerateRandomColorController generateRandomColorController) {
        this.generateRandomColorController = generateRandomColorController;
    }

}
