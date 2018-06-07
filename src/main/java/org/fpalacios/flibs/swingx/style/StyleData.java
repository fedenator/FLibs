package org.fpalacios.flibs.swingx.style;

import org.fpalacios.flibs.swingx.binding.SimpleProperty;

/**
 * Representa un dato que puede ser absoluto o relativo
 * @author fpalacios
 *
 */
public class StyleData {
	public enum Unit { PIXELS, PERCENTAGE }

	public Unit unit;
	public final SimpleProperty<Integer> value = new SimpleProperty<>(0);

	public StyleData(Unit unit, int value) {
		this.unit = unit;
		this.value.setValue(value);
	}

	public StyleData(int value) {
		this(Unit.PIXELS, value);
	}

	public StyleData() {
		this(Unit.PIXELS, 0);
	}

	public String toString() {
		String unitString = (unit == Unit.PIXELS)? "px" :"%";
		return value + unitString;
	}

}
