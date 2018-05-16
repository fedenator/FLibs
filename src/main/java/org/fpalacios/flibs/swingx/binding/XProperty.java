package org.fpalacios.flibs.swingx.binding;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.awt.Component;

import org.fpalacios.flibs.swingx.binding.Property;


public class XProperty extends Property<Integer> implements ComponentListener {
    private Component comp;

    public XProperty(Component comp) {
        this.comp = comp;
        comp.addComponentListener(this);
    }


    public void componentHidden (ComponentEvent e) {}
    public void componentShown  (ComponentEvent e) {}
    public void componentResized(ComponentEvent e) {}

    public void componentMoved (ComponentEvent e) {
        notifyChange( e.getComponent().getX() );
    }

    public void setValue(Integer val) {
        comp.setBounds(val, comp.getY(), comp.getWidth(), comp.getHeight() );
        notifyChange(val);
    }

    public Integer getValue() {
        return comp.getX();
    }

    public void reciveChange(Integer newVal) {
        comp.setBounds(newVal, comp.getY(), comp.getWidth(), comp.getHeight() );
    }
}
