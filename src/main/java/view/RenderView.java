package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.Render.RenderController;
import interface_adapter.Render.RenderState;
import interface_adapter.Render.RenderViewModel;

public class RenderView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int BORDERTOP = 50;
    private static final int BORDERBOTTOM = 50;
    private static final int EMPTYBORDERBOTTOM = 150;
    private static final int TEXTROWS = 5;
    private static final int TEXTCOLS = 40;
    private final String viewName = "render";
    private final RenderViewModel renderViewModel;
    private RenderController renderController;

    private final JLabel sketchLabel;
    private final JLabel renderLabel;
    private final JTextArea descriptionTextInput;
    private final JLabel errorLabel;
    private final JButton renderButton;
    private final JButton toDrawingButton;

    private final int iconSize = 300;

    public RenderView(RenderViewModel viewModel) {
        this.renderViewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        // Set the main layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel for actions (description, text field, render button)
        final JPanel actionPanel = new JPanel();
        actionPanel.setBorder(new EmptyBorder(BORDERTOP, 0, BORDERBOTTOM, 0));

        // Description label and input field
        final JLabel descriptionLabel = new JLabel(RenderViewModel.DESCRIPTION_LABEL);
        descriptionTextInput = new JTextArea(TEXTROWS, TEXTCOLS);

        // Update state based on text input
        descriptionTextInput.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final RenderState renderState = renderViewModel.getState();
                renderState.setSketchDescription(descriptionTextInput.getText());
                renderViewModel.setState(renderState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        renderButton = new JButton(RenderViewModel.RENDER_BUTTON_LABEL);
        renderButton.addActionListener(event -> {
            if (event.getSource().equals(renderButton)) {
                final RenderState renderState = renderViewModel.getState();
                renderController.execute(renderState.getSketchDescription(), renderState.getSketch());
                System.out.println("rendering");
            }
        });

        actionPanel.add(descriptionLabel);
        actionPanel.add(descriptionTextInput);
        actionPanel.add(renderButton);

        // Panel for icons (sketch and render)
        final JPanel iconPanel = new JPanel();

        // Sketch label with icon
        sketchLabel = new JLabel(RenderViewModel.SKETCH_LABEL);
        sketchLabel.setIcon(imageToIcon(null));
        sketchLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        sketchLabel.setVerticalTextPosition(SwingConstants.TOP);
        iconPanel.add(sketchLabel);

        // Render label with icon
        renderLabel = new JLabel(RenderViewModel.RENDER_LABEL);
        renderLabel.setIcon(imageToIcon(null));
        renderLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        renderLabel.setVerticalTextPosition(SwingConstants.TOP);
        iconPanel.add(renderLabel);

        // Error label
        errorLabel = new JLabel();
        errorLabel.setBorder(new EmptyBorder(BORDERTOP, 0, EMPTYBORDERBOTTOM, 0));
        errorLabel.setForeground(Color.RED);

        // Button to drawing
        toDrawingButton = new JButton(RenderViewModel.TO_DRAWING_BUTTON_LABEL);
        toDrawingButton.addActionListener(this);

        // Add components to the main panel
        this.add(actionPanel);
        this.add(iconPanel);
        this.add(errorLabel);
        this.add(toDrawingButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        renderController.switchToDrawingView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final RenderState renderState = (RenderState) evt.getNewValue();
        sketchLabel.setIcon(imageToIcon(renderState.getSketch()));
        renderLabel.setIcon(imageToIcon(renderState.getRender()));
        errorLabel.setText(renderState.getRenderError());
    }

    /**
     * If the image exists, returns it as an icon. Otherwise return a blank image.
     * @param image The image
     * @return The icon
     */
    private ImageIcon imageToIcon(Image image) {
        if (image != null) {
            BufferedImage bufferedImage = getBufferedImage(image);
            bufferedImage = cropImageToSquare(bufferedImage);
            bufferedImage = shrinkImage(bufferedImage, iconSize, iconSize);
            return new ImageIcon(bufferedImage);
        }
        return getBlankImageIcon(iconSize, iconSize);
    }

    /**
     * Retrieves the name of the current view.
     *
     * @return A {@link String} representing the name of the view.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the {@link RenderController} for this view.
     *
     * @param renderController The {@link RenderController} instance to be associated with this view.
     */
    public void setRenderController(RenderController renderController) {
        this.renderController = renderController;
    }

    private ImageIcon getBlankImageIcon(int width, int height) {
        BufferedImage box = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = box.createGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        g.dispose();
        return new ImageIcon(box);
    }

    private BufferedImage shrinkImage(BufferedImage image, int maxWidth, int maxHeight) {
        int newWidth = Math.min(maxWidth, image.getWidth());
        int newHeight = Math.min(maxHeight, image.getHeight());
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    private BufferedImage getBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }

    private BufferedImage cropImageToSquare(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Find the longer edge
        int cropSize = Math.min(width, height);

        // Find the offset to crop the center
        int offsetX = (width - cropSize) / 2;
        int offsetY = (height - cropSize) / 2;

        // Crop
        return image.getSubimage(offsetX, offsetY, cropSize, cropSize);
    }
}
