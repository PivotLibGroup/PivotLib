package com.pivotgroup.pivotlib.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/16 19:48
 */

public class Container extends Component {

    List<Component> components = new ArrayList<>();

    public Container() {

    }

    public List<Component> getChildren(){
        return components;
    }

    public void add(Component comp) {
        components.add(comp);
    }

    public void remove(Component comp) {
        components.remove(comp);
    }

}
