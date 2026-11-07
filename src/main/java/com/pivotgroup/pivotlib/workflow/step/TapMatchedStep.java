package com.pivotgroup.pivotlib.workflow.step;

import com.pivotgroup.pivotlib.ActionSimulator;
import com.pivotgroup.pivotlib.util.Loc;
import com.pivotgroup.pivotlib.util.MatMatchUtils;
import com.pivotgroup.pivotlib.workflow.Step;
import com.pivotgroup.pivotlib.workflow.StepContext;

public class TapMatchedStep extends Step {

    private final double minScore;
    private final long holdMillis;

    public TapMatchedStep() {
        this(MatMatchUtils.DEFAULT_MIN_SCORE, 50);
    }

    public TapMatchedStep(double minScore, long holdMillis) {
        this.minScore = minScore;
        this.holdMillis = holdMillis;
    }

    @Override
    public void exec(StepContext context) {
        Object data = context.getData(MatchStep.RESULT_KEY);
        if (!(data instanceof Loc loc)) {
            return;
        }
        if (loc.matchResult < minScore) {
            return;
        }
        ActionSimulator simulator = context.getActionSimulator();
        if (simulator == null) {
            return;
        }
        simulator.tap(loc.x, loc.y, holdMillis);
    }
}
