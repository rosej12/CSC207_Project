package view;

import entities.Shape;
import interface_adapter.Shape.ShapeController;
import interface_adapter.Shape.ShapeViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShapeView extends JPanel implements ActionListener {
    private final ShapeController controller;
    private final ShapeViewModel viewModel;
    private final JComboBox<Shape.ShapeType> shapeTypeComboBox;
    private final JSpinner x1Spinner, y1Spinner, x2Spinner, y2Spinner, strokeWidthSpinner;
    private final JButton drawButton;
    private final JPanel canvas;
    private final ViewManagerModel viewManagerModel;
    private Color currentColor = Color.BLACK; // Default color

    public ShapeView(ShapeController controller, ShapeViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;

        setLayout(new BorderLayout());

        // control Ppnel for shape input
        JPanel controlPanel = new JPanel();
        shapeTypeComboBox = new JComboBox<>(Shape.ShapeType.values());
        x1Spinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        y1Spinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        x2Spinner = new JSpinner(new SpinnerNumberModel(100, 0, 1000, 1));
        y2Spinner = new JSpinner(new SpinnerNumberModel(100, 0, 1000, 1));
        strokeWidthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        drawButton = new JButton("Draw Shape");

        // adding components to the control panel
        controlPanel.add(new JLabel("Type:"));
        controlPanel.add(shapeTypeComboBox);
        controlPanel.add(new JLabel("X1:"));
        controlPanel.add(x1Spinner);
        controlPanel.add(new JLabel("Y1:"));
        controlPanel.add(y1Spinner);
        controlPanel.add(new JLabel("X2:"));
        controlPanel.add(x2Spinner);
        controlPanel.add(new JLabel("Y2:"));
        controlPanel.add(y2Spinner);
        controlPanel.add(new JLabel("Stroke:"));
        controlPanel.add(strokeWidthSpinner);
        controlPanel.add(drawButton);

        // add a color picker button
        JButton colorPickerButton = new JButton("Pick Color");
        colorPickerButton.setBackground(currentColor); // Set the button's background to the current color
        colorPickerButton.addActionListener(evt -> {
            Color selectedColor = JColorChooser.showDialog(this, "Pick a Color", currentColor);
            if (selectedColor != null) {
                currentColor = selectedColor; // Update currentColor
                colorPickerButton.setBackground(currentColor); // Reflect the new color on the button
            }
        });
        controlPanel.add(colorPickerButton);

        // adding back button
        JButton backButton = new JButton("Back to Drawing");
        add(backButton, BorderLayout.SOUTH); // Add button to the bottom of the layout

        backButton.addActionListener(event -> switchToDrawingView());

        // canvas panel for shape rendering
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // render all shapes from the ViewModel
                List<Shape> shapes = viewModel.getShapes();
                if (shapes != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    for (Shape shape : shapes) {
                        g2d.setColor(shape.getColor());
                        g2d.setStroke(new BasicStroke(shape.getStrokeWidth()));

                        switch (shape.getType()) {
                            case LINE:
                                g2d.drawLine(shape.getX1(), shape.getY1(), shape.getX2(), shape.getY2());
                                break;
                            case RECTANGLE:
                                g2d.drawRect(shape.getX1(), shape.getY1(),
                                        shape.getX2() - shape.getX1(), shape.getY2() - shape.getY1());
                                break;
                            case SQUARE:
                                int side = Math.min(Math.abs(shape.getX2() - shape.getX1()),
                                        Math.abs(shape.getY2() - shape.getY1()));
                                g2d.drawRect(shape.getX1(), shape.getY1(), side, side);
                                break;
                            case CIRCLE:
                                int diameter = Math.min(Math.abs(shape.getX2() - shape.getX1()),
                                        Math.abs(shape.getY2() - shape.getY1()));
                                g2d.drawOval(shape.getX1(), shape.getY1(), diameter, diameter);
                                break;
                            case TRIANGLE:
                                int[] xPoints = {shape.getX1(), shape.getX2(),
                                        (shape.getX1() + shape.getX2()) / 2};
                                int[] yPoints = {shape.getY1(), shape.getY1(), shape.getY2()};
                                g2d.drawPolygon(xPoints, yPoints, 3);
                                break;
                        }
                    }
                }
            }
        };

        // add action listeners
        drawButton.addActionListener(this);

        // add panels to the main layout
        add(controlPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Collect input for the shape
        Shape.ShapeType type = (Shape.ShapeType) shapeTypeComboBox.getSelectedItem();
        int x1 = (int) x1Spinner.getValue();
        int y1 = (int) y1Spinner.getValue();
        int x2 = (int) x2Spinner.getValue();
        int y2 = (int) y2Spinner.getValue();
        int strokeWidth = (int) strokeWidthSpinner.getValue();

        // use the selected color
        Shape shape = new Shape(type, x1, y1, x2, y2, currentColor, strokeWidth);

        // add the shape to the ViewModel
        viewModel.addShape(shape);

        // pass the shape to the controller
        controller.drawShape(shape);

        // repaint the canvas to reflect the new shape
        canvas.repaint();
    }

    private void switchToDrawingView() {
        viewManagerModel.setState("Drawing");
        viewManagerModel.firePropertyChanged();
    }

    public String getViewName() {
        return "Shape";
    }
}
