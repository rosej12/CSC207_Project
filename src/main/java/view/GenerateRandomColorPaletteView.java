package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entities.Color;
import entities.ColorPalette;
import interface_adapter.GenerateRandomColor.GenerateRandomColorController;
import interface_adapter.GenerateRandomColor.GenerateRandomColorPaletteViewModel;
import interface_adapter.ViewManagerModel;

public class GenerateRandomColorPaletteView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int DIMENSIONALITY = 100;
    private final String viewName = "GenerateRandomColors";
    private final GenerateRandomColorPaletteViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final JButton backButton = new JButton("Back");
    private final JButton generateButton = new JButton("Generate");
    private GenerateRandomColorController generateRandomColorController;
    private JPanel colorPanel;

    public GenerateRandomColorPaletteView(GenerateRandomColorPaletteViewModel viewModel,
                                          ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);
        this.viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(backButton);
        buttonsPanel.add(generateButton);

        generateButton.addActionListener(event -> generateRandomColorController.generateRandomColor());

        backButton.addActionListener(event -> switchToGenerateRandomColorsView());

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
        }
        else if ("error".equals(evt.getPropertyName())) {
            displayError(viewModel.getState().getErrorMessage());
        }
    }

    private void displayError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void displayColors(ColorPalette colorPalette) {
        colorPanel.removeAll();

        for (Color color : colorPalette.getColors()) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));
            label.setPreferredSize(new Dimension(DIMENSIONALITY, DIMENSIONALITY));

            JButton lockButton = new JButton();
            if (color.isLocked()) {
                lockButton.setText("Unlock");
            }
            else {
                lockButton.setText("Lock");
            }

            lockButton.addActionListener(event -> {
                boolean isCurrentlyLocked = color.isLocked();
                color.setLocked(!isCurrentlyLocked);
                if (color.isLocked()) {
                    lockButton.setText("Unlock");
                }
                else {
                    lockButton.setText("Lock");
                }
            });

            JButton pickColorButton = new JButton("Pick Color");
            pickColorButton.addActionListener(event -> {
                java.awt.Color newColor = JColorChooser.showDialog(null, "Choose a Color", label.getBackground());
                if (newColor != null) {
                    color.setRed(newColor.getRed());
                    color.setGreen(newColor.getGreen());
                    color.setBlue(newColor.getBlue());
                    label.setBackground(newColor);
                }
            });

            JPanel colorContainer = new JPanel(new BorderLayout());
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

    /**
     * Retrieves the name of the view.
     *
     * @return The name of the view.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the controller for generating random colors.
     *
     * @param generateRandomColorController The controller to handle generating random colors.
     */
    public void setGenerateRandomColorController(GenerateRandomColorController generateRandomColorController) {
        this.generateRandomColorController = generateRandomColorController;
    }

}
