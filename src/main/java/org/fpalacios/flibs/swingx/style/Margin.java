package org.fpalacios.flibs.swingx.style;

public class Margin {
    public int top, bottom, left, right;

    public Margin(int top, int bottom, int left, int right) {
        this.top    = top;
        this.bottom = bottom;
        this.left   = left;
        this.right  = right;
    }

    public Margin(int hMargin, int vMargin) {
        this(vMargin, vMargin, hMargin, hMargin);
    }

    public Margin(int margin) {
        this(margin, margin, margin, margin);
    }

    public Margin() {
        this(0);
    }
}
