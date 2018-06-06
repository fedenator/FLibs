package org.fpalacios.flibs.swingx.layouts;

import javax.swing.BoxLayout;

public class VBox extends FPanel {

    private static final long serialVersionUID = 1l;

    public VBox() {
        this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
    }
}
