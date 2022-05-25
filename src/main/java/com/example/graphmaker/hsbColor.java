package com.example.graphmaker;



import java.awt.Color;

public class hsbColor {
    public static Color generate(double randB, double randE, double value)
    {
        double minHue = 300f/255;
        double maxHue = 0;
        double hue = (value*maxHue + (1-value)*minHue)*1000000;
        return new Color(Color.HSBtoRGB( (float) hue, 1, 0.5f));
    }

}
