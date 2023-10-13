package krain.env;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class SimpleGUI extends JFrame {
    private int gridSpacing = 20;
    private double zoom = 1.0;
    private double minZoom = 0.1;
    private double maxZoom = 2.0;
    private int translateX = 0;
    private int translateY = 0;
    private Point dragStart;

    public SimpleGUI() {
        setTitle("Simple Java GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(Color.WHITE);
                int gridSize = (int) (gridSpacing * zoom);

                for (int x = -translateX % gridSize; x < getWidth(); x += gridSize) {
                    for (int y = -translateY % gridSize; y < getHeight(); y += gridSize) {
                        g2d.drawRect(x, y, gridSize, gridSize);
                    }
                }
            }
        };

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragStart != null) {
                    translateX -= e.getX() - dragStart.x;
                    translateY -= e.getY() - dragStart.y;
                    repaint();
                }
                dragStart = e.getPoint();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragStart = null;
            }
        });

        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double delta = 0.1 * e.getPreciseWheelRotation(); // Invert scroll direction here
                zoom -= delta;
                if (zoom < minZoom) {
                    zoom = minZoom;
                }
                if (zoom > maxZoom) {
                    zoom = maxZoom;
                }
                repaint();
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleGUI gui = new SimpleGUI();
            gui.setVisible(true);
        });
    }
}
