package view;

import interface_adapter.Render.RenderController;
import interface_adapter.Render.RenderState;
import interface_adapter.Render.RenderViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Render Use Case
 */
public class RenderView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "render";
    private final RenderViewModel renderViewModel;
    private RenderController renderController;

    private final JButton renderButton;
    private final JButton toDrawingButton;

    public RenderView(RenderViewModel viewModel) {
        this.renderViewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        final JLabel sketchLabel = new JLabel(RenderViewModel.SKETCH_LABEL);
        final JLabel renderLabel = new JLabel(RenderViewModel.RENDER_LABEL);
        final JLabel descriptionLabel = new JLabel(RenderViewModel.DESCRIPTION_LABEL);

        renderButton = new JButton(RenderViewModel.RENDER_BUTTON_LABEL);
        toDrawingButton = new JButton(RenderViewModel.TO_DRAWING_BUTTON_LABEL);

        renderButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(renderButton)) {
                            // TODO: implement
                            System.out.println("rendering");
                        }
                    }
                }
        );

        toDrawingButton.addActionListener(this);

        this.add(sketchLabel);
        this.add(renderLabel);
        this.add(descriptionLabel);
        this.add(renderButton);
        this.add(toDrawingButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        renderController.switchToDrawingView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final RenderState state = (RenderState) evt.getNewValue();
        // TODO
    }

    public String getViewName() {
        return viewName;
    }

    public void setRenderController(RenderController renderController) {
        this.renderController = renderController;
    }
}
