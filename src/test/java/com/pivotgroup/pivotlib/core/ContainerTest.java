package com.pivotgroup.pivotlib.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests workspace composite invariants.
 *
 * @author xinliu
 */
class ContainerTest {

    @Test
    void maintainsParentAndReadOnlyChildren() {
        Container parent = new Workspace();
        Container child = new Container();
        parent.add(child);

        assertSame(parent, child.getParent());
        assertEquals(1, parent.getChildren().size());
        assertThrows(UnsupportedOperationException.class, () -> parent.getChildren().add(new Container()));

        parent.remove(child);
        assertNull(child.getParent());
    }

    @Test
    void reparentsComponentWithoutDuplicates() {
        Container first = new Container();
        Container second = new Container();
        Component component = new Container();
        first.add(component);

        second.add(component);
        second.add(component);

        assertEquals(0, first.getChildren().size());
        assertEquals(1, second.getChildren().size());
        assertSame(second, component.getParent());
    }

    @Test
    void rejectsHierarchyCycles() {
        Container parent = new Container();
        Container child = new Container();
        parent.add(child);

        assertThrows(IllegalArgumentException.class, () -> child.add(parent));
    }
}
