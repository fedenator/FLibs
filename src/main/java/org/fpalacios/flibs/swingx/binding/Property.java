package org.fpalacios.flibs.swingx.binding;

import java.util.function.Function;

import org.fpalacios.flibs.util.Observable;
import org.fpalacios.flibs.util.Observer;

public abstract class Property<T> extends Observable<T> implements Observer<T> {

    private Observable<T> target;
    private Function<T, T> lambda;


    public T proccessChange(T newVal) {
        return (lambda != null)? lambda.apply(newVal) : newVal;
    }

    public void bind(Observable<T> target, Function<T, T> lambda) {
        if (this.target != null) this.target.removeObserver(this);
        this.target = target;
        target.addObserver(this);
        this.lambda = lambda;
        setValue( proccessChange(target.getValue()) );
    }

    public void bind(Observable<T> target) {
        bind(target, null);
    }

    public abstract void setValue(T value);

}
