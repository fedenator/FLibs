package org.fpalacios.flibs.swingx.binding;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.awt.Component;

import org.fpalacios.flibs.swingx.binding.Property;


public class YProperty extends Property<Integer> implements ComponentListener {
    private Component comp;

    public YProperty(Component comp) {
        this.comp = comp;
        comp.addComponentListener(this);
    }

    public void componentHidden (ComponentEvent e) {}
    public void componentShown  (ComponentEvent e) {}
    public void componentResized(ComponentEvent e) {}

    public void componentMoved (ComponentEvent e) {
        notifyChange( e.getComponent().getY() );
    }

    public void setValue(Integer val) {
        comp.setBounds(comp.getX(), val, comp.getWidth(), comp.getHeight() );
        notifyChange(val);
    }

    public Integer getValue() {
        return comp.getY();
    }

    public void reciveChange(Integer newVal) {
        comp.setBounds(comp.getX(), newVal, comp.getWidth(), comp.getHeight() );
    }
}
