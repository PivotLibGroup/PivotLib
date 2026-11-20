package com.pivotgroup.pivotlib.ui.roi;

import com.pivotgroup.pivotlib.model.Rect;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * Top-level ROI editing window.
 *
 * @author xinliu
 */
public final class RoiOverlay extends JFrame {

    private static final long serialVersionUID = 1L;

    private final CountDownLatch closed = new CountDownLatch(1);

    private final RoiSelection selection;

    private final Runnable cancelAction;

    private final RoiPanel panel;

    public RoiOverlay(Rect initialRoi) {
        this(initialRoi, roi -> {
        }, () -> {
        });
    }

    public RoiOverlay(Rect initialRoi, Consumer<Rect> roiConsumer, Runnable cancelAction) {
        this(initialRoi, primaryScreenBounds(), roiConsumer, cancelAction);
    }

    RoiOverlay(Rect initialRoi, Rect screenBounds, Consumer<Rect> roiConsumer, Runnable cancelAction) {
        Objects.requireNonNull(screenBounds, "screenBounds");
        Objects.requireNonNull(roiConsumer, "roiConsumer");
        this.cancelAction = Objects.requireNonNull(cancelAction, "cancelAction");
        selection = new RoiSelection(screenBounds, initialRoi);
        panel = new RoiPanel(selection, screenBounds, roiConsumer);

        setUndecorated(true);
        setBounds(screenBounds.getX(), screenBounds.getY(), screenBounds.getWidth(), screenBounds.getHeight());
        setAlwaysOnTop(true);
        setFocusableWindowState(true);
        setBackground(new Color(0, 0, 0, 0));
        setContentPane(panel);
        registerKeys();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent event) {
                focusEditor();
            }

            @Override
            public void windowActivated(WindowEvent event) {
                panel.requestFocusInWindow();
            }
        });
        roiConsumer.accept(selection.getRoi());
    }

    public Rect getRoi() {
        return selection.getRoi();
    }

    public void setRoi(Rect roi) {
        selection.setRoi(roi);
        panel.publishRoi();
        panel.repaint();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            activate();
            SwingUtilities.invokeLater(() -> {
                if (isVisible()) {
                    activate();
                }
            });
        }
    }

    void activate() {
        focusEditor();
        setExtendedState(JFrame.ICONIFIED);
        setExtendedState(JFrame.NORMAL);
        focusEditor();
    }

    private void focusEditor() {
        setState(JFrame.NORMAL);
        toFront();
        requestFocusInWindow();
        panel.requestFocusInWindow();
    }

    public void awaitClose() {
        if (SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException("Cannot wait for ROI overlay on the EDT");
        }
        try {
            closed.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for ROI overlay", e);
        }
    }

    @Override
    public void dispose() {
        try {
            super.dispose();
        } finally {
            closed.countDown();
        }
    }

    private void registerKeys() {
        getRootPane().registerKeyboardAction(
                event -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        getRootPane().registerKeyboardAction(
                event -> cancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void cancel() {
        cancelAction.run();
        dispose();
    }

    private static Rect primaryScreenBounds() {
        if (GraphicsEnvironment.isHeadless()) {
            throw new IllegalStateException("ROI overlay requires a display");
        }
        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .getBounds();
        return Rect.of(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
