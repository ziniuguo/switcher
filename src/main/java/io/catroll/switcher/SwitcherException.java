package io.catroll.switcher;

public class SwitcherException extends Exception {
    public SwitcherException(String msg) {
        super(msg);
    }

    public SwitcherException(String msg, Throwable e) {
        super(msg, e);
    }
}
