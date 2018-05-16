package org.fpalacios.flibs.swingx.binding;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.JTextField;

import org.fpalacios.flibs.swingx.binding.Property;

public class JTextFieldTextProperty extends Property<String> {

    private JTextField textField;

    public JTextFieldTextProperty(JTextField target) {
        textField = target;

        target.getDocument().addDocumentListener( new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                notifyChange( textField.getText() );
            }
            public void removeUpdate(DocumentEvent e) {
                notifyChange( textField.getText() );
            }
            public void insertUpdate(DocumentEvent e) {
                notifyChange( textField.getText() );
            }
        }
        );
    }

    public void reciveChange(String text) {
        textField.setText(text);
    }

    public String getValue() {
        return textField.getText();
    }

}
