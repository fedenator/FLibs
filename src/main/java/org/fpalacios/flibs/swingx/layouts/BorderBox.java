package org.fpalacios.flibs.swingx.layouts;

import java.awt.BorderLayout;

import javax.swing.JComponent;


public class BorderBox extends FPanel {

    private static final long serialVersionUID = 1l;

    public BorderBox() {
        setLayout( new BorderLayout() );
    }

    public void setCenter(JComponent comp) {
        this.add(comp, BorderLayout.CENTER);
    }
    public void setNorth(JComponent comp) {
        this.add(comp, BorderLayout.NORTH);
    }
    public void setSouth(JComponent comp) {
        this.add(comp, BorderLayout.SOUTH);
    }
    public void setEast(JComponent comp) {
        this.add(comp, BorderLayout.EAST);
    }
    public void setWest(JComponent comp) {
        this.add(comp, BorderLayout.WEST);
    }
}
