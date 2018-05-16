package org.fpalacios.flibs.swingx.components;

import org.fpalacios.flibs.swingx.binding.YProperty;
import org.fpalacios.flibs.swingx.binding.XProperty;
import org.fpalacios.flibs.swingx.binding.HeightProperty;
import org.fpalacios.flibs.swingx.binding.WidthProperty;
import javax.swing.JPanel;

public class FPanel extends JPanel {

    private static final long serialVersionUID = 1l;

    public final XProperty xProperty = new XProperty(this);
    public final YProperty YProperty = new YProperty(this);

    public final WidthProperty  widthProperty  = new WidthProperty (this);
    public final HeightProperty heightProperty = new HeightProperty(this);
}
