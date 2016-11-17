package util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Un elemento que puede tener valores y sub elementos
 * Los valores soportados son boolean, double, int y String
 * @author fpalacios
 *
 */
public class FSON {
	/*---------------------- Constantes -----------------------------*/
	public static final Character tab='\t', br='\n', spliter=';';
	
	/*----------------------  Propiedades ---------------------------*/
	private FSON parent;
	private String key;
	private ArrayList<Object> values = new ArrayList<Object>();
	private ArrayList<FSON> subElements = new ArrayList<FSON>();
	
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
	
	/*--------------------- Constructores rapidos -------------------*/
	/**
	 * Un FSON que se inicia con un valor String y un padre
	 */
	public FSON(FSON parent, String key, String value) {
		this(parent, key);
		addValue(value);
	}
	/**
	 * Un FSON que se inicia con un valor int y un padre
	 */
	public FSON(FSON parent, String key, int value) {
		this(parent, key);
		addValue(value);
	}
	/**
	 * Un FSON que se inicia con un valor double u un padre
	 */
	public FSON(FSON parent, String key, double value) {
		this(parent, key);
		addValue(value);
	}
	/**
	 * Un FSON que se inicia con un valor boolean y un padre
	 */
	public FSON(FSON parent, String key, boolean value) {
		this(parent, key);
		addValue(value);
	}
	/**
	 * Un FSON que se inicia con un valor String
	 */
	public FSON(String key, String value) {
		this(key);
		addValue(value);
	}
	/**
	 * Un FSON que se inicia con un valor int
	 */
	public FSON(String key, int value) {
		this(key);
		addValue(value);
	}
	/**
	 * Un FSON que se inicia con un valor double
	 */
	public FSON(String key, double value) {
		this(key);
		addValue(value);
	}
	/**
	 * Un FSON que se inicia con un valor boolean
	 */
	public FSON(String key, boolean value) {
		this(key);
		addValue(value);
	}
	
	/*-------------------------- Funciones --------------------------*/
	public static FSON createFromFile(String path) {
		FSON fson = new FSON();
		fson.loadFromFile(path);
		return fson;
	}
	
	/**
	 * Falta hacer
	 */
	public void loadFromFile(String path) {
		
	}
	
	public void loadFromString(String str) {
		ArrayList<Character> param = new ArrayList<Character>();
		for (char character : str.toCharArray()) param.add(character);
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
		
		//borra todos los espacios saltos de linea y tabuladores
		for(Iterator<Character> it = list.iterator(); it.hasNext();) {
			Character character = it.next();
			
			if (character == '\"') inSemiclone = (inSemiclone) ? false : true;
			
			//Si es un espacio, tab o enter y no esta entre comillas los saco
			else if ((character == ' ' || character == '\n' || character == '\t') && !inSemiclone)it.remove();
		}
		
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
				
			} else { //Si es un valor
				String value = "";
				for (Character character : subElement) value += "" + character;
				if (value.startsWith("\""))flag.addValue(value.replaceAll("\"", ""));//logicamente no puede haber comillas dentro de un texto
				else if (value.equalsIgnoreCase("true")) flag.addValue(true);
				else if (value.equalsIgnoreCase("false")) flag.addValue(false);
				else if (value.contains(".")) flag.addValue(Double.parseDouble(value));
				else if (!value.isEmpty()) flag.addValue(Integer.parseInt(value));
			}
		}
		
		return flag;
	}
	
	/**
	 * falta hacer
	 */
	public void saveInFile() {
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
		
		Iterator<Object> it2 = values.iterator();
		while (it2.hasNext()) {
			Object object = it2.next();
			String line = indent;
			if (object instanceof String) {
				line += "\"" + (String) object + "\"";
			} else if (object instanceof Integer || object instanceof Double || object instanceof Boolean) {
				line += object.toString();
			}
			line += "" + spliter + br;
			flag += line;
			
		}
		
		return flag;
	}
	
	public void clear() {
		this.subElements.clear();
		this.values.clear();
	}
	
	/*--------------------- Manejo de subElementos ------------------*/
	/**
	 * A�ade un fson y le dice que el es su padre(A lo starwars) 
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
	public void addValue(String value) {
		values.add(value);
	}
	public void addValue(int value) {
		values.add(value);
	}
	public void addValue(double value) {
		values.add(value);
	}
	public void addValue(boolean value) {
		values.add(value);
	}
	/**
	 * Devuelve el primer value o null si esta vacio
	 */
	public Object getValue() {
		Object flag = null;
		
		if (values.size() > 0) flag = values.get(0);
		
		return flag;
	}
	public Object getValue(int index) {
		return values.get(index);
	}
	public Object[] getValues() {
		return values.toArray();
	}
	public void removeValue(int index) {
		values.remove(index);
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
