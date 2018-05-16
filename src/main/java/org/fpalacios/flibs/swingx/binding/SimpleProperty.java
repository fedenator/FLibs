package org.fpalacios.flibs.swingx.binding;

public class SimpleProperty<T> extends Property<T> {

    private T value;

    public SimpleProperty(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
        notifyChange(value);
    }

    public void reciveChange(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
