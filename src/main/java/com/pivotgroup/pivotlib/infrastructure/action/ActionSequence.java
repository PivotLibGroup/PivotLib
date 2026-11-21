package com.pivotgroup.pivotlib.infrastructure.action;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class ActionSequence implements Action{

    private final List<Action> actions = new ArrayList<>();

    public ActionSequence(){

    }

    public void addAction(Action action){
        actions.add(action);
    }

    @Override
    public void apply() {
        for (Action action : actions) {
            action.apply();
        }
    }
}
