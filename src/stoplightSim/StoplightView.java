package stoplightSim;

import tools.Subscriber;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class StoplightView extends JPanel implements Subscriber {
    private Stoplight light;

    public StoplightView(Stoplight light) {
        this.light = light;
        light.subscribe(this);
        setSize(500, 500);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setBorder(blackline);
        setBackground(Color.LIGHT_GRAY);
    }

    public void update() {
        repaint();
    }

    public void setLight(Stoplight newLight) {
        light.unsubscribe(this);
        light = newLight;
        light.subscribe(this);
        repaint();
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Color oldColor = gc.getColor();
        StoplightComponent lightComponent = new StoplightComponent(light);
        lightComponent.paintComponent(gc);
        gc.setColor(oldColor);
    }
}
