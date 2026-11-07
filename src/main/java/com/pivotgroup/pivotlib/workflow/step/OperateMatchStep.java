package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.actionsimulator.ActionSimulator;
import com.pivotgroup.pivotlib.util.Anchor;
import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.StepContext;
import com.pivotgroup.pivotlib.workflow.step.matchstep.MatchInfo;
import com.pivotgroup.pivotlib.workflow.step.matchstep.MatchStep;

public class OperateMatchStep extends Step {

    private final double minScore;
    private final Action action;
    private Anchor anchor;

    public OperateMatchStep(Action action,Anchor anchor) {
        this(MatMatchUtils.DEFAULT_MIN_SCORE, action,anchor);
    }

    public OperateMatchStep(double minScore, Action action,Anchor anchor) {
        this.minScore = minScore;
        this.action = action;
        this.anchor = anchor;
    }

    @Override
    public void exec(StepContext context) {
        Object data = context.getData(MatchStep.RESULT_KEY);
        if (!(data instanceof MatchInfo info)) {
            return;
        }
        Loc loc = info.loc;
        if (loc.matchResult < minScore) {
            return;
        }
        ActionSimulator actionSimulator = context.getActionSimulator();
        if (actionSimulator == null) {
            return;
        }
        action.exec(actionSimulator, loc.x, loc.y);
    }

    public interface Action {
        void exec(ActionSimulator actionSimulator, int x, int y);
    }

    public static class Tap implements Action {

        private final int duration;

        public Tap() {
            this(50);
        }

        public Tap(int duration) {
            this.duration = duration;
        }

        @Override
        public void exec(ActionSimulator actionSimulator, int x, int y) {
            actionSimulator.tap(x, y, duration);
        }
    }

    public static class DoubleTap implements Action {

        private final int duration;

        public DoubleTap() {
            this(50);
        }

        public DoubleTap(int duration) {
            this.duration = duration;
        }

        @Override
        public void exec(ActionSimulator actionSimulator, int x, int y) {
            actionSimulator.tap(x, y, duration);
            actionSimulator.sleep(duration);
            actionSimulator.tap(x, y, duration);
        }
    }

    public static class Swipe implements Action {

        private final int dx;
        private final int dy;
        private final long totalDuration;
        private final int steps;

        public Swipe(int dx, int dy) {
            this(dx, dy, 300, 10);
        }

        public Swipe(int dx, int dy, long totalDuration, int steps) {
            this.dx = dx;
            this.dy = dy;
            this.totalDuration = totalDuration;
            this.steps = steps;
        }

        @Override
        public void exec(ActionSimulator actionSimulator, int x, int y) {
            actionSimulator.swipe(x, y, x + dx, y + dy, totalDuration, steps);
        }
    }
}
