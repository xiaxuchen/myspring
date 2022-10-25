package org.originit.hand.factory.support;

import java.util.ArrayList;
import java.util.List;

public class ConstructArgs {

    List<Object> args;

    public ConstructArgs(List<Object> args) {
        if (args == null) {
            this.args = new ArrayList<>();
        } else {
            this.args = args;
        }
    }

    public ConstructArgs() {
        this(null);
    }

    public List<Object> getArgs() {
        return args;
    }

}
