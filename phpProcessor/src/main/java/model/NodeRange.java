package model;

import org.antlr.v4.runtime.ParserRuleContext;

// The range of a lexical token
public class NodeRange {
    private Position start;

    private Position end;


    public NodeRange() {
    }

    public NodeRange(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Position getStart() {
        return start;
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public Position getEnd() {
        return end;
    }

    public void setEnd(Position end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d] - [%d, %d]", start.getLine(), start.getColumn(),
                end.getLine(), end.getColumn());
    }
}
