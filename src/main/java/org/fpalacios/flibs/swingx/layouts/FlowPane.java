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

    private int hSpace, vSpace;

    /*---------------------------- Constructors ------------------------------*/
    public FlowPane(Priority p, VDirection v, HDirection h, int hSpace, int vSpace) {
        this.priority   = p;
        this.vDirection = v;
        this.hDirection = h;

        this.hSpace = hSpace;
        this.vSpace = vSpace;

        this.setLayout(null);
        this.addComponentListener(this);
    }

    public FlowPane() {
        this(
            Priority.ByRows,
            VDirection.TopToBottom,
            HDirection.LeftToRight,
            10, 10
        );
    }

    /*----------------------------- Funciones --------------------------------*/
    public void alignComponents() {

        //Posicion inicial, largo de la fila y posicion actual de cada liea
        int lineBegin, lineLength, currentLinePos;

        //Distancia necesaria entre los bordes de la linea
        int lineBorderBegin, lineBorderEnd;
        int lineSpace, compSpace;

        if (priority == Priority.ByRows) {

            //Si la prioridad es por filas, la linea es horizontal
            if (hDirection == HDirection.LeftToRight) {
                lineBegin = 0;
                compSpace = hSpace;
            } else {
                lineBegin = getWidth();
                compSpace = -hSpace;
            }

            lineLength = getWidth();

            //Y el borde de la linea es vertical
            if (vDirection == VDirection.TopToBottom) {
                lineBorderBegin = 0;
                lineSpace       = vSpace;
            } else {
                lineBorderBegin = getHeight();
                lineSpace       = -vSpace;
            }

        } else {
            //Si la prioridad es por columnas, la linea es vertical
            if (vDirection == VDirection.TopToBottom) {
                lineBegin = 0;
                compSpace = vSpace;
            } else {
                lineBegin = getHeight();
                compSpace = -vSpace;
            }

            lineLength = getHeight();

            //Y el borde de la linea es horizontal
            lineBorderBegin = (hDirection == HDirection.LeftToRight)? 0 : getWidth();
            if (hDirection == HDirection.LeftToRight) {
                lineBorderBegin = 0;
                lineSpace       = hSpace;
            } else {
                lineBorderBegin = getWidth();
                lineSpace       = -hSpace;
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
            if (Math.abs(currentLinePos) + Math.abs(compSpace) + Math.abs(compLenght) >= lineLength) {
                lineBorderBegin = lineBorderEnd + lineSpace;
                currentLinePos  = lineBegin;
            }

            //Si la distancia entre los bordes es mayor a la guardad, la actualiza
            if ( Math.abs(lineBorderEnd - lineBorderBegin) < compThickness ) lineBorderEnd = lineBorderBegin + compThickness;

            if (priority == Priority.ByRows)
                comp.setBounds(currentLinePos, lineBorderBegin, compPrefSize.width, compPrefSize.height);
            else
                comp.setBounds(lineBorderBegin, currentLinePos, compPrefSize.width, compPrefSize.height);

            currentLinePos += compLenght + compSpace;
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
