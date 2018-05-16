package org.fpalacios.flibs.swingx.components;

import org.fpalacios.flibs.swingx.binding.YProperty;
import org.fpalacios.flibs.swingx.binding.XProperty;
import org.fpalacios.flibs.swingx.binding.JTextFieldTextProperty;
import org.fpalacios.flibs.swingx.binding.HeightProperty;
import org.fpalacios.flibs.swingx.binding.WidthProperty;
import javax.swing.JTextField;

public class FTextField extends JTextField {
    private static final long serialVersionUID = 1l;

    public final XProperty xProperty = new XProperty(this);
    public final YProperty YProperty = new YProperty(this);

    public WidthProperty  widthProperty;
    public HeightProperty heightProperty;

    public JTextFieldTextProperty textField;

    public FTextField() {
        widthProperty  = new WidthProperty (this);
        heightProperty = new HeightProperty(this);

        textField = new JTextFieldTextProperty(this);
    }
}
