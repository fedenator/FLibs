package org.fpalacios.flibs.swingx.components;

import org.fpalacios.flibs.swingx.binding.JTextFieldTextProperty;

import javax.swing.JTextField;

public class FTextField extends JTextField {
    private static final long serialVersionUID = 1l;

    public JTextFieldTextProperty textField;

    public FTextField() {

        textField = new JTextFieldTextProperty(this);
    }
}
