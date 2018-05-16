package org.fpalacios.flibs.util;

import java.util.ArrayList;

public abstract class Observable<T> {
    private Object observersLock = new Object();
    private ArrayList< Observer<T> > observers = new ArrayList<>();

    public void notifyChange(T newVal) {
        synchronized (observersLock) {
            for (Observer<T> observer : observers) observer.reciveChange(newVal);
        }
    }

    public void addObserver(Observer<T> observer) {
        synchronized (observersLock) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer<T> observer) {
        synchronized (observersLock) {
            observers.remove(observer);
        }
    }

    public abstract T getValue();

}
