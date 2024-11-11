package view;

import interface_adapter.DrawingController;
import interface_adapter.DrawingPresenter;
import interface_adapter.ViewModel;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import entity.CommonDrawingPath;

public class CanvasView extends JPanel {
    private final ViewModel<CommonDrawingPath> viewModel;
    private DrawingController controller;

    public CanvasView(ViewModel<CommonDrawingPath> viewModel) {
        this.viewModel = viewModel;

        setBackground(Color.WHITE);
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (controller != null) {
                    controller.addPointToPath(e.getPoint());
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (controller != null) {
                    controller.startNewPath(e.getPoint());
                    controller.addPointToPath(e.getPoint());
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (controller != null) {
                    controller.endCurrentPath();
                    repaint();
                }
            }
        });
    }

    public void setDrawingController(DrawingController controller) {
        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        DrawingPresenter presenter = (DrawingPresenter) controller.getDrawingPresenter();
        for (CommonDrawingPath path : presenter.getPaths()) {
            Point prevPoint = null;
            for (Point point : path.getPoints()) {
                if (prevPoint != null) {
                    g.drawLine(prevPoint.x, prevPoint.y, point.x, point.y);
                }
                prevPoint = point;
            }
        }
    }

    public String getViewName() {
        return viewModel.getViewName();
    }
}
