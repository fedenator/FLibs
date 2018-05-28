package org.fpalacios.flibs.swingx.binding;

import java.awt.Dimension;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.Component;

public class HeightProperty extends Property<Integer> implements ComponentListener {

    private Component comp;

    public HeightProperty(Component comp) {
        this.comp = comp;
        comp.addComponentListener(this);
    }

    public void componentMoved (ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}
    public void componentShown (ComponentEvent e) {}

    public void componentResized(ComponentEvent e) {
        notifyChange( e.getComponent().getHeight() );
    }

    public void setValue(Integer val) {
        comp.setPreferredSize( new Dimension(comp.getWidth(), val) );
        notifyChange(val);
    }

    public Integer getValue() {
        return comp.getHeight();
    }

    public void reciveChange(Integer newVal) {
        comp.setPreferredSize( new Dimension(comp.getPreferredSize().width, proccessChange(newVal)) );
    }
}
