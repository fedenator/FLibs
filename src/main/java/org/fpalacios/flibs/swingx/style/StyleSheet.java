package org.fpalacios.flibs.swingx.style;

import javax.swing.border.Border;

public class StyleSheet {

    public final StyleData x = new StyleData(0);
    public final StyleData y = new StyleData(0);

    public final StyleData width  = new StyleData(0);
    public final StyleData height = new StyleData(0);

    public final Margin margin = new Margin(5);

    public Border border;

    public String[] classes;

    public StyleSheet(String... classes) {
        this.classes = classes;
    }

}
