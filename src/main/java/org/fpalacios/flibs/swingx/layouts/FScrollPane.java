package org.fpalacios.flibs.swingx.layouts;

import javax.swing.JComponent;

import org.fpalacios.flibs.swingx.components.FPanel;

import org.fpalacios.flibs.swingx.binding.YProperty;
import org.fpalacios.flibs.swingx.binding.XProperty;
import org.fpalacios.flibs.swingx.binding.WidthProperty;
import org.fpalacios.flibs.swingx.binding.HeightProperty;

import javax.swing.JScrollPane;

public class FScrollPane extends JScrollPane {

    private static final long serialVersionUID = 1l;

    public JComponent content;

    public final XProperty xProperty = new XProperty(this);
    public final YProperty YProperty = new YProperty(this);

    public final WidthProperty  widthProperty  = new WidthProperty (this);
    public final HeightProperty heightProperty = new HeightProperty(this);

    public FScrollPane() {
        this( new FPanel() );
    }

    public FScrollPane(JComponent comp) {
        this.content = comp;
    }
}
