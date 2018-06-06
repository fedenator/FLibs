package org.fpalacios.flibs.swingx.style;

/**
 * Representa un dato que puede ser absoluto o relativo
 * @author fpalacios
 *
 */
public class StyleData {
	public enum Unit { PIXELS, PERCENTAGE }

	public Unit unit;
	public int value;

	public StyleData(Unit unit, int value) {
		this.unit = unit;
		this.value = value;
	}


	public StyleData(int value) {
		this(Unit.PIXELS, value);
	}

	public StyleData() {
		this(Unit.PIXELS, 0);
	}

	@Override
	public String toString() {
		String unitString = (unit == Unit.PIXELS)? "px" :"%";
		return value + unitString;
	}

}
