package org.fpalacios.flibs.swingx.binding;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class WidthProperty extends Property<Integer> implements ComponentListener {

    private Component comp;

    public WidthProperty(Component comp) {
        this.comp = comp;
        comp.addComponentListener(this);
    }

    public void componentMoved (ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}
    public void componentShown (ComponentEvent e) {}

    public void componentResized(ComponentEvent e) {
        notifyChange( e.getComponent().getWidth() );
    }

    public void setValue(Integer val) {
        comp.setSize( new Dimension(val, comp.getHeight()) );
        notifyChange(val);
    }

    public Integer getValue() {
        return comp.getWidth();
    }

    public void reciveChange(Integer newVal) {
        comp.setSize( new Dimension ( proccessChange(newVal), comp.getHeight() ) );
    }

}
