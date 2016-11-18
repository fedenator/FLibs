package util;

/**
 * Representa un dato que puede ser absoluto o relativo
 * @author fpalacios
 *
 */
public class StyleData {
	public static final byte UNIT_PIXELS=0, UNIT_PERCENTAGE=1;
	public static final byte POSITION_RELATIVE=0, POSITION_ABSOLUTE=1;
	
	public byte unit;
	public byte position;
	public int value;
	
	public StyleData(byte unit, byte position, int value) {
		this.unit = unit;
		this.position = position;
		this.value = value;
	}
	
	public StyleData(byte unit, int value) {
		this(unit, POSITION_RELATIVE, value);
	}
	
	public StyleData(int value) {
		this(UNIT_PIXELS, POSITION_RELATIVE, value);
	}
	
	public StyleData() {
		this(UNIT_PIXELS, POSITION_RELATIVE, 0);
	}
	
}
