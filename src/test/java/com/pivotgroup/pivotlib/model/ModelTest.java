package com.pivotgroup.pivotlib.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests immutable value objects and match results.
 *
 * @author xinliu
 */
class ModelTest {

    @Test
    void locationSupportsValueEqualityAndCheckedOffset() {
        Location location = Location.of(10, 20);

        assertEquals(Location.of(10, 20), location);
        assertEquals(Location.of(10, 20).hashCode(), location.hashCode());
        assertEquals(Location.of(15, 5), location.offset(5, -15));
        assertThrows(ArithmeticException.class, () -> Location.of(Integer.MAX_VALUE, 0).offset(1, 0));
    }

    @Test
    void dimensionSupportsValueEquality() {
        assertEquals(Dimension.of(10, 20), Dimension.of(10, 20));
        assertNotEquals(Dimension.of(10, 20), Dimension.of(20, 10));
        assertEquals(Dimension.of(10, 20).hashCode(), Dimension.of(10, 20).hashCode());
    }

    @Test
    void resultRejectsNonFiniteScore() {
        ResultLoc result = new ResultLoc(Double.NaN, Location.of(1, 2));

        assertFalse(result.isPresent());
        assertTrue(new ResultLoc(0.9D, Location.of(1, 2)).isPresent());
    }
}
