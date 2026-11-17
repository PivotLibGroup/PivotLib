package com.pivotgroup.pivotlib.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Composite component with read-only child access.
 *
 * @author xinliu
 */
public class Container extends Component {

    private final List<Component> components = new ArrayList<>();

    public List<Component> getChildren() {
        return Collections.unmodifiableList(components);
    }

    public void add(Component component) {
        Objects.requireNonNull(component, "component");
        if (component.getParent() == this && components.contains(component)) {
            return;
        }
        for (Component current = this; current != null; current = current.getParent()) {
            if (current == component) {
                throw new IllegalArgumentException("Component hierarchy cannot contain cycles");
            }
        }
        if (component.getParent() instanceof Container) {
            ((Container) component.getParent()).remove(component);
        }
        components.add(component);
        component.setParent(this);
    }

    public void remove(Component component) {
        if (component != null && components.remove(component)) {
            component.setParent(null);
        }
    }
}
