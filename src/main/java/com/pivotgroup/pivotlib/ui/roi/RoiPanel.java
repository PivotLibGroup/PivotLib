package com.pivotgroup.pivotlib.ui.roi;

import com.pivotgroup.pivotlib.model.Rect;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * Swing view for selecting an ROI with a mouse.
 *
 * @author xinliu
 */
final class RoiPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int HANDLE_SIZE = 12;

    private static final Color ACCENT = new Color(64, 156, 255);

    private static final Color SCRIM = new Color(0, 0, 0, 90);

    private static final Color LABEL_BACKGROUND = new Color(20, 24, 30, 220);

    private final RoiSelection selection;

    private final Rect screenBounds;

    private final Consumer<Rect> roiConsumer;

    private Point dragStart;

    private Rect dragRoi;

    private RoiHandle dragHandle;

    private boolean moving;

    RoiPanel(RoiSelection selection, Rect screenBounds, Consumer<Rect> roiConsumer) {
        this.selection = selection;
        this.screenBounds = screenBounds;
        this.roiConsumer = roiConsumer;
        setOpaque(false);
        setFocusable(true);
        MouseAdapter mouseHandler = createMouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    void publishRoi() {
        roiConsumer.accept(selection.getRoi());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle roi = roiRectangle();
            paintScrim(g, roi);
            g.setColor(new Color(64, 156, 255, 36));
            g.fillRect(roi.x, roi.y, roi.width, roi.height);
            g.setColor(ACCENT);
            g.setStroke(new BasicStroke(2f));
            g.drawRect(roi.x, roi.y, roi.width - 1, roi.height - 1);
            paintHandles(g);
            paintLabels(g, roi);
        } finally {
            g.dispose();
        }
    }

    private MouseAdapter createMouseHandler() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (!SwingUtilities.isLeftMouseButton(event)) {
                    return;
                }
                RoiHandle handle = findHandle(event.getPoint());
                boolean move = handle == null && roiRectangle().contains(event.getPoint());
                if (handle == null && !move) {
                    return;
                }
                dragStart = event.getPoint();
                dragRoi = selection.getRoi();
                dragHandle = handle;
                moving = move;
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                if (dragStart == null || dragRoi == null) {
                    return;
                }
                int deltaX = event.getX() - dragStart.x;
                int deltaY = event.getY() - dragStart.y;
                if (dragHandle != null) {
                    selection.resize(dragRoi, dragHandle, deltaX, deltaY);
                } else if (moving) {
                    selection.move(dragRoi, deltaX, deltaY);
                } else {
                    return;
                }
                publishRoi();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                dragStart = null;
                dragRoi = null;
                dragHandle = null;
                moving = false;
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                updateCursor(event.getPoint());
            }

            @Override
            public void mouseExited(MouseEvent event) {
                if (dragStart == null) {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
    }

    private Rectangle roiRectangle() {
        Rect roi = selection.getRoi();
        return new Rectangle(
                roi.getX() - screenBounds.getX(),
                roi.getY() - screenBounds.getY(),
                roi.getWidth(),
                roi.getHeight());
    }

    private Rectangle handleRectangle(RoiHandle handle) {
        Rectangle roi = roiRectangle();
        int centerX = handle.isLeft() ? roi.x : roi.x + roi.width;
        int centerY = handle.isTop() ? roi.y : roi.y + roi.height;
        return new Rectangle(
                centerX - HANDLE_SIZE / 2,
                centerY - HANDLE_SIZE / 2,
                HANDLE_SIZE,
                HANDLE_SIZE);
    }

    private RoiHandle findHandle(Point point) {
        for (RoiHandle handle : RoiHandle.values()) {
            if (handleRectangle(handle).contains(point)) {
                return handle;
            }
        }
        return null;
    }

    private void paintScrim(Graphics2D g, Rectangle roi) {
        g.setColor(SCRIM);
        g.fillRect(0, 0, getWidth(), roi.y);
        g.fillRect(0, roi.y + roi.height, getWidth(), getHeight() - roi.y - roi.height);
        g.fillRect(0, roi.y, roi.x, roi.height);
        g.fillRect(roi.x + roi.width, roi.y, getWidth() - roi.x - roi.width, roi.height);
    }

    private void paintHandles(Graphics2D g) {
        for (RoiHandle handle : RoiHandle.values()) {
            Rectangle rectangle = handleRectangle(handle);
            g.setColor(Color.WHITE);
            g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            g.setColor(ACCENT);
            g.drawRect(rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height - 1);
        }
    }

    private void paintLabels(Graphics2D g, Rectangle roiRectangle) {
        Rect roi = selection.getRoi();
        String dimensions = roi.getWidth() + " × " + roi.getHeight()
                + "  (" + roi.getX() + ", " + roi.getY() + ")";
        g.setFont(getFont().deriveFont(Font.BOLD, 13f));
        FontMetrics metrics = g.getFontMetrics();
        int height = metrics.getHeight() + 8;
        int width = metrics.stringWidth(dimensions) + 16;
        int x = Math.max(0, Math.min(roiRectangle.x, getWidth() - width));
        int y = roiRectangle.y - height - 6;
        if (y < 0) {
            y = roiRectangle.y + 6;
        }
        paintLabel(g, dimensions, x, y, width, height, metrics);

        String help = "拖动移动 · 四角缩放 · Enter 确认 · Esc 取消";
        int helpWidth = metrics.stringWidth(help) + 20;
        int helpX = (getWidth() - helpWidth) / 2;
        int helpY = getHeight() - height - 20;
        paintLabel(g, help, helpX, helpY, helpWidth, height, metrics);
    }

    private void paintLabel(Graphics2D g, String text, int x, int y, int width, int height,
                            FontMetrics metrics) {
        g.setColor(LABEL_BACKGROUND);
        g.fillRoundRect(x, y, width, height, 8, 8);
        g.setColor(Color.WHITE);
        g.drawString(text, x + 8, y + 4 + metrics.getAscent());
    }

    private void updateCursor(Point point) {
        RoiHandle handle = findHandle(point);
        if (handle != null) {
            setCursor(Cursor.getPredefinedCursor(resizeCursor(handle)));
        } else if (roiRectangle().contains(point)) {
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private static int resizeCursor(RoiHandle handle) {
        return switch (handle) {
            case NORTH_WEST -> Cursor.NW_RESIZE_CURSOR;
            case NORTH_EAST -> Cursor.NE_RESIZE_CURSOR;
            case SOUTH_WEST -> Cursor.SW_RESIZE_CURSOR;
            case SOUTH_EAST -> Cursor.SE_RESIZE_CURSOR;
        };
    }
}
