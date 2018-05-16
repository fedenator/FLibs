package org.fpalacios.flibs.util;

public interface Observer<T> {
    public void reciveChange(T newVal);
}
