package com.pivotgroup.pivotlib.input;

import com.pivotgroup.pivotlib.util.coordinate.Loc;


public final class ActionResult {

    private final boolean ok;
    private final String message;

    private ActionResult(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public static ActionResult ok() {
        return new ActionResult(true, "ok");
    }

    public static ActionResult ok(Loc at) {
        return new ActionResult(true, "ok@" + at);
    }

    public static ActionResult fail(String r) {
        return new ActionResult(false, r);
    }

    public boolean isOk() {
        return ok;
    }

    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return (ok ? "OK " : "FAIL ") + message;
    }
}
