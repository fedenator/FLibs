package org.fpalacios.flibs.swingx.components;

import org.fpalacios.flibs.swingx.binding.YProperty;
import org.fpalacios.flibs.swingx.binding.XProperty;
import javax.swing.JComponent;

import org.fpalacios.flibs.swingx.binding.WidthProperty;
import org.fpalacios.flibs.swingx.binding.HeightProperty;

public class FComponent extends JComponent {

    private static final long serialVersionUID = 1l;

    public final XProperty xProperty = new XProperty(this);
    public final YProperty YProperty = new YProperty(this);

    public final WidthProperty  widthProperty  = new WidthProperty (this);
    public final HeightProperty heightProperty = new HeightProperty(this);
}
