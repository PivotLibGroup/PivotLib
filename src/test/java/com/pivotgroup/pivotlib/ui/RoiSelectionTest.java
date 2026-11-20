package com.pivotgroup.pivotlib.ui;

import com.pivotgroup.pivotlib.model.Rect;
import com.pivotgroup.pivotlib.ui.roi.RoiHandle;
import com.pivotgroup.pivotlib.ui.roi.RoiSelection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoiSelectionTest {

    @Test
    void createsCenteredDefaultRoi() {
        RoiSelection selection = new RoiSelection(Rect.of(0, 0, 1000, 800), null);

        assertEquals(Rect.of(300, 250, 400, 300), selection.getRoi());
    }

    @Test
    void clampsInitialRoiToScreen() {
        RoiSelection selection = new RoiSelection(
                Rect.of(100, 200, 1000, 800),
                Rect.of(0, 0, 2000, 100));

        assertEquals(Rect.of(100, 200, 1000, 100), selection.getRoi());
    }

    @Test
    void movesRoiWithinScreen() {
        RoiSelection selection = new RoiSelection(
                Rect.of(0, 0, 1000, 800),
                Rect.of(100, 100, 200, 100));
        Rect original = selection.getRoi();

        selection.move(original, 50, 60);
        assertEquals(Rect.of(150, 160, 200, 100), selection.getRoi());

        selection.move(original, 2000, -1000);
        assertEquals(Rect.of(800, 0, 200, 100), selection.getRoi());
    }

    @Test
    void resizesFromEveryCorner() {
        RoiSelection selection = new RoiSelection(
                Rect.of(0, 0, 1000, 800),
                Rect.of(100, 100, 200, 100));
        Rect original = selection.getRoi();

        selection.resize(original, RoiHandle.SOUTH_EAST, 50, 20);
        assertEquals(Rect.of(100, 100, 250, 120), selection.getRoi());

        selection.resize(original, RoiHandle.NORTH_WEST, 20, 10);
        assertEquals(Rect.of(120, 110, 180, 90), selection.getRoi());

        selection.resize(original, RoiHandle.NORTH_EAST, 50, -20);
        assertEquals(Rect.of(100, 80, 250, 120), selection.getRoi());

        selection.resize(original, RoiHandle.SOUTH_WEST, -20, 50);
        assertEquals(Rect.of(80, 100, 220, 150), selection.getRoi());
    }

    @Test
    void resizeKeepsMinimumSize() {
        RoiSelection selection = new RoiSelection(
                Rect.of(0, 0, 1000, 800),
                Rect.of(100, 100, 200, 100));

        selection.resize(selection.getRoi(), RoiHandle.SOUTH_EAST, -1000, -1000);

        assertEquals(Rect.of(100, 100, 40, 40), selection.getRoi());
    }

    @Test
    void resizeClampsToOffsetScreen() {
        RoiSelection selection = new RoiSelection(
                Rect.of(100, 200, 1000, 800),
                Rect.of(200, 300, 200, 100));

        selection.resize(selection.getRoi(), RoiHandle.NORTH_WEST, -1000, -1000);

        assertEquals(Rect.of(100, 200, 300, 200), selection.getRoi());
    }
}
