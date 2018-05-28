package org.fpalacios.flibs.swingx.layouts;

import java.awt.Component;
import java.awt.Dimension;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import org.fpalacios.flibs.swingx.components.FPanel;

public class FlowPane extends FPanel implements ComponentListener {

    private static final long serialVersionUID = 1l;

    //Prioridad para llenar por filas o columnas
    public enum Priority   { ByColumns,   ByRows      }

    //Direccion vertical y horizontal
    public enum VDirection { TopToBottom, BottomToTop }
    public enum HDirection { LeftToRight, RightToLeft }

    /*----------------------------- Propiedades ------------------------------*/
    private Priority priority;

    private VDirection vDirection;
    private HDirection hDirection;

    /*---------------------------- Constructors ------------------------------*/
    public FlowPane(Priority p, VDirection v, HDirection h) {
        this.priority   = p;
        this.vDirection = v;
        this.hDirection = h;
        this.setLayout(null);
        this.addComponentListener(this);
    }

    public FlowPane() {
        this(
            Priority.ByRows,
            VDirection.TopToBottom,
            HDirection.LeftToRight
        );
    }

    /*----------------------------- Funciones --------------------------------*/
    public void alignComponents() {

        //Posicion inicial, posicion final y posicion actual de cada liea
        int lineBegin, lineEnd, currentLinePos;

        //Distancia necesaria entre los bordes de la linea
        int lineBorderBegin, lineBorderEnd;

        if (priority == Priority.ByRows) {

            //Si la prioridad es por filas, la linea es horizontal
            if (hDirection == HDirection.LeftToRight) {
                lineBegin = 0;
                lineEnd   = getWidth();
            } else {
                lineBegin = getWidth();
                lineEnd   = 0;
            }

            //Y el borde de la linea es vertical
            lineBorderBegin = (vDirection == VDirection.TopToBottom)? 0 : getHeight();

        } else {
            //Si la prioridad es por columnas, el borde de la linea es horizontal
            lineBorderBegin = (hDirection == HDirection.LeftToRight)? 0 : getWidth();

            //Y la linea es vertical
            if (vDirection == VDirection.TopToBottom) {
                 lineBegin = 0;
                 lineEnd   = getHeight();
            } else {
                lineBegin = getHeight();
                lineEnd   = 0;
            }
        }

        currentLinePos = lineBegin;
        lineBorderEnd  = lineBorderBegin;

        for ( Component comp : this.getComponents() ) {
            Dimension compPrefSize = comp.getPreferredSize();
            int compLenght, compThickness;

            //Obtener las medidas del componente segun la linea
            if (priority == Priority.ByRows) {
                compLenght    = (hDirection == HDirection.LeftToRight)? compPrefSize.width  : -compPrefSize.width;
                compThickness = (vDirection == VDirection.TopToBottom)? compPrefSize.height : -compPrefSize.height;
            } else {
                compLenght    = (vDirection == VDirection.TopToBottom)? compPrefSize.height : -compPrefSize.height;
                compThickness = (hDirection == HDirection.LeftToRight)? compPrefSize.width  : -compPrefSize.width;
            }

            //Si no entra en la linea hago un overflow a la siguente
            //TODO: Usar valores absolutos para las distancias
            if (currentLinePos + compLenght >= lineEnd) {
                lineBorderBegin = lineBorderEnd;
                currentLinePos  = lineBegin;
            }

            //Si la distancia entre los bordes es mayor a la guardad, la actualiza
            if ( lineBorderEnd - lineBorderBegin < compThickness ) lineBorderEnd = lineBorderBegin + compThickness;

            if (priority == Priority.ByRows)
                comp.setBounds(currentLinePos, lineBorderBegin, compPrefSize.width, compPrefSize.height);
            else
                comp.setBounds(lineBorderBegin, currentLinePos, compPrefSize.width, compPrefSize.height);

            currentLinePos += compLenght;
        }

        repaint();

    }

    public Component add(Component comp) {
        super.add(comp);
        alignComponents();
        return comp;
    }

    public void componentHidden (ComponentEvent e) {}

    public void componentShown(ComponentEvent e) {
        alignComponents();
    }

    public void componentResized(ComponentEvent e) {
        alignComponents();
    }

    public void componentMoved(ComponentEvent e) {
        alignComponents();
    }
}
