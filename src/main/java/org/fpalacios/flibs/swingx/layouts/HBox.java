package org.fpalacios.flibs.swingx.layouts;

import javax.swing.BoxLayout;

public class HBox extends FPanel {

    private static final long serialVersionUID = 1l;

    public HBox() {
        this.setLayout( new BoxLayout(this, BoxLayout.X_AXIS) );
    }
}
