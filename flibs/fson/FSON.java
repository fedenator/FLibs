package flibs.fson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import flibs.util.StringUtilities;
import flibs.util.StyleData;

/**
 * Un elemento que puede tener valores y sub elementos
 * Los valores soportados son boolean, double, int y String
 * Un valor tiene una key(string) y un objeto
 * @author fpalacios
 *
 */
public class FSON {
	public enum Type {
		STRING, INT, DOUBLE, BOOLEAN, STYLE_DATA;
	}
	
	
	/*---------------------- Constantes -----------------------------*/
	public static final Character tab='\t', br='\n', spliter=';', entrySpliter='=';

	/*----------------------  Propiedades ---------------------------*/
	private FSON parent;
	private String key;
	private ArrayList<FSON> subElements = new ArrayList<FSON>();
	private HashMap<String, Object> values = new HashMap<String, Object>();
	/*---------------------- Constructores --------------------------*/
	/**
	 * Constructor completo, si el padre no es null, le dice que te agregue de subelemento
	 */
	public FSON(FSON parent, String key) {
		this.key = key;
		if (parent != null) setParent(parent); //El padre despues te dice que lo agregues de padre
	}
	
	public FSON(String key) {
		this.key = key;
	}
	
	public FSON() {
		this("");
	}
	
	/*-------------------------- Funciones --------------------------*/
	public void loadFromString(String str) {
		//Saco los espacios, tabuladores y enters
		ArrayList<Character> param = removeRedundantCharacters(str);
		
		//Hack =(
		FSON fackeParent = new FSON(), fson = loadFromStringRecursive(fackeParent, param);
		this.subElements = fson.subElements;
		this.values = fson.values;
		
	}
	
	private static FSON loadFromStringRecursive(FSON parent, ArrayList<Character> list) {
		FSON flag = new FSON();
		
		int bracketCounter = 0;
		boolean inSemiclone = false;
		ArrayList<ArrayList<Character>> subElements = new ArrayList<ArrayList<Character>>();
		
		subElements.add(new ArrayList<Character>());
		
		//Separa los subelementos de mi nivel con una coma
		for (int i = 0, i2 = 0; i < list.size(); i++) {
			Character character = list.get(i);
			if      (character == '{')  bracketCounter++;
			else if (character == '}')  bracketCounter--;
			else if (character == '\"') inSemiclone = (inSemiclone) ? false : true;
			else if (character == spliter) {
				if (!inSemiclone && bracketCounter == 0) {
					subElements.add(new ArrayList<Character>());
					i2++;
				}
			}
			if (bracketCounter > 0 || character != spliter) {
				subElements.get(i2).add(character);
			}
		}
		
		subElements.remove(subElements.size()-1);
		//Asigna los valores de los nodos
		for (ArrayList<Character> subElement : subElements) {
			
			
			//Si es un subElmento
			if(subElement.contains('{')) { //Habria que revisar que las { no esten entree comillas
				String key = "";
				bracketCounter = 0;
				ArrayList<Character> arg = new ArrayList<Character>();
				boolean bracketFound = false;
				
				for (Iterator<Character> iterator = subElement.iterator(); iterator.hasNext();) {
					Character character = iterator.next();
					
					if (character == '{') {
						if (bracketCounter == 0) {
							bracketFound = true;
							iterator.remove();
							bracketCounter++;
							continue;
						}
						bracketCounter++;
					} else if (character == '}') {
						if(bracketCounter == 0) {
							iterator.remove();
							bracketCounter--;
							continue;
						}
						bracketCounter--;
					}
					
					if (!bracketFound) key += character;
					else if (bracketCounter > 0) arg.add(character);
				}
				
				
				FSON subFson = new FSON();
				subFson = FSON.loadFromStringRecursive(flag, arg);
				subFson.setKey(key);
				flag.addSubElement(subFson);
				
			} else { //Si es un entry
				String line="", key="", value="";
				for (Character character : subElement) line += "" + character;
				key = line.split("" + entrySpliter)[0];
				value = line.split("" + entrySpliter)[1];
				
				for (;;) {
				
					switch (getType(value)) {
					case STRING:
						flag.addValue(key, value.replaceAll("\"", ""));
						break;
						
					case BOOLEAN:
					case DOUBLE:
					case INT:
						flag.addValue(key, value.toString());
						break;
						
					case STYLE_DATA:
						StyleData styleData = (value.toLowerCase().contains("px"))?
								new StyleData(StyleData.UNIT_PIXELS ,Integer.parseInt(value.toLowerCase().replace("px", ""))): 
									new StyleData(StyleData.UNIT_PERCENTAGE ,Integer.parseInt(value.toLowerCase().replace("%", "")));
						flag.addValue(key, styleData);
						break;
					}	
				}
			}
		}
		
		return flag;
	}
	
	@Override
	public String toString() {
		return toStringRecursive(0);
	}
	
	private String toStringRecursive(int level) {
		String flag = "";
		String indent = "";
		//Calculo el indent
		for(int i = 0; i < level; i++) {
			indent += tab;
		}
		
		
		Iterator<FSON> it = subElements.iterator();
		while (it.hasNext()) {
			FSON fson = (FSON)it.next();
			String aux = indent + fson.getKey() + " {" + br;
			aux += fson.toStringRecursive(level + 1) + br;
			aux += indent + "}" + spliter + br;
			flag += aux;
		}
		
		Iterator<Entry<String, Object>> it2 = values.entrySet().iterator();
		while (it2.hasNext()) {
			Entry<String, Object> entry = it2.next();
			Object key = entry.getKey(), value = entry.getValue();
			String line = indent + key + entrySpliter;
			
			
			//Si es un array
			if (isArray(value)) {
				Object[] array = (Object[]) value;
				
				line += "[";
				for (Object item : array) line += getDataFormat(item) + ",";
				line = StringUtilities.removeLastCharacter(line); //Quita la ultima coma que esta de mas
				line += "]";
			} else {
				line += getDataFormat(value);
			}
			
			line += "" + spliter + br;
			flag += line;
			
		}
		
		return flag;
	}
	private static Type getType(String value) {
		Type type = null;
		
		if (value.startsWith("\"")) type = Type.STRING;//logicamente no puede haber comillas dentro de un texto
		else if (value.endsWith("%") || value.endsWith("px")) type = Type.STYLE_DATA;
		else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) type = Type.BOOLEAN;
		else if (value.contains(".")) type = Type.DOUBLE;
		else if (!value.isEmpty()) type = Type.INT;
		
		return type;
	}
	
	private String getDataFormat(Object obj) {
		String flag = "";
		if (obj instanceof String) {
			flag += "\"" + (String) obj + "\"";
		} else if (obj instanceof Integer || obj instanceof Double || obj instanceof Boolean) {
			flag += obj.toString();
		}
		return flag;
	}
	
	private ArrayList<Character> removeRedundantCharacters(String str) {
		ArrayList<Character> flag = new ArrayList<Character>();
		
		boolean inSemiclone = false;
		char[] aux = str.toCharArray();
		for (char character : aux) {
			if (character == '\"')inSemiclone = (inSemiclone) ? false : true;
			
			if ( !((character == ' ' || character == '\n' || character == '\t') && !inSemiclone) )
			flag.add(character);
		}
		
		return flag;
	}
	
	private boolean isArray(Object obj) {
		return (obj instanceof int[] || obj instanceof double[] || obj instanceof boolean[]);
	}
	
	public void clear() {
		this.subElements.clear();
		this.values.clear();
	}
	
	/*--------------------- Manejo de subElementos ------------------*/
	/**
	 * Add un fson y le dice que el es su padre(A lo starwars) 
	 */
	public void addSubElement(FSON fson){
		subElements.add(fson);
		if(fson.parent != this)fson.setParent(this);
	}
	public FSON[] getSubElements() {
		return (FSON[]) subElements.toArray();
	}
	public FSON getSubElemet(int index) {
		return subElements.get(index);
	}
	public void removeSubElement(int index) {
		subElements.remove(index);
	}
	
	/*------------------------- Manejo de valores -------------------*/
	/**
	 * No pongan llaves en el string que se rompe =P
	 * Al que no le guste que haga un commit escapando el string
	 */
	public void addValue(String key, String value) {
		values.put(key, value);
	}
	public void addValue(String key, int value) {
		values.put(key, value);
	}
	public void addValue(String key, double value) {
		values.put(key, value);
	}
	public void addValue(String key, boolean value) {
		values.put(key, value);
	}
	public void addValue(String key, StyleData value) {
		values.put(key, value);
	}
	
	public void addArray(String key, String[] value) {
		values.put(key, value);
	}
	public void addArray(String key, int[] value) {
		values.put(key, value);
	}
	public void addArray(String key, double[] value) {
		values.put(key, value);
	}
	public void addArray(String key, boolean[] value) {
		values.put(key, value);
	}
	public void addArray(String key, StyleData[] value) {
		values.put(key, value);
	}
	
	public Object getValue(String key) {
		return values.get(key);
	}
	public String getStringValue(String key) {
		return "" + values.get(key);
	}
	public int getIntValue(String key) {
		return (int) values.get(key);
	}
	public double getDoubleValue(String key) {
		return (double) values.get(key);
	}
	public boolean getBooleanValue(String key) {
		return (boolean) values.get(key);
	}
	public StyleData getStyleDataValue(String key) {
		return (StyleData) values.get(key);
	}
	
	public Object getQuickValue() {
		return values.values().toArray()[0];
	}
	public String getStringQuickValue() {
		return (String) values.values().iterator().next();
	}
	public int getIntQuickValue() {
		return (int) values.values().iterator().next();
	}
	public double getDoubleQuickValue() {
		return (double) values.values().iterator().next();
	}
	public boolean getBooleanQuickValue() {
		return (boolean) values.values().iterator().next();
	}
	public StyleData getStyleDataQuickValue() {
		return (StyleData) values.values().iterator().next();
	}
	
	public Object[] getArray(String key) {
		return (Object[]) values.get(key);
	}
	public String[] getStringArray(String key) {
		return (String[]) values.get(key);
	}
	public int[] getIntArray(String key) {
		return (int[]) values.get(key);
	}
	public double[] getDoubleArray(String key) {
		return (double[]) values.get(key);
	}
	public boolean[] getBooleanArray(String key) {
		return (boolean[]) values.get(key);
	}
	public StyleData[] getStyleDataArray(String key) {
		return (StyleData[]) values.get(key);
	}
	
	public Object[] getQuickArray() {
		return (Object[])values.values().toArray()[0];
	}
	public String[] getStringQuickArray() {
		return (String[]) values.values().toArray()[0];
	}
	public int[] getIntQuickArray() {
		return (int[])values.values().toArray()[0];
	}
	public double[] getDoubleQuickArray() {
		return (double[])values.values().toArray()[0];
	}
	public boolean[] getBooleanQuickArray() {
		return (boolean[])values.values().toArray()[0];
	}
	public StyleData[] getStyleDataQuickArray() {
		return (StyleData[])values.values().toArray()[0];
	}
	
	public String[] getKeys() {
		return (String[]) values.keySet().toArray();
	}
	
	public Object[] getAllValues() {
		return values.entrySet().toArray();
	}
	public Object[] getValues() {
		return getAllValues();
	}
	
	public int size() {
		return values.size();
	}
	public void removeValue(String key) {
		values.remove(key);
	}
	
	/*---------------------- Getters y Setters -------------------*/
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	
	public void setParent(FSON parent) {
		this.parent = parent;
		if(!parent.subElements.contains(this)) parent.addSubElement(this);
	}
	public FSON getParent() {
		return parent;
	}
	
}
