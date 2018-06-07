package org.fpalacios.flibs.swingx.style;

public class Margin {
    public StyleData top    = new StyleData();
    public StyleData bottom = new StyleData();
    public StyleData left   = new StyleData();
    public StyleData right  = new StyleData();

    public Margin(int top, int bottom, int left, int right) {
        this.top.value.setValue   (top);
        this.bottom.value.setValue(bottom);
        this.left.value.setValue  (left);
        this.right.value.setValue (right);
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
