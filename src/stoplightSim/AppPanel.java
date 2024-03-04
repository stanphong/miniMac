package stoplightSim;

import tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AppPanel  extends JPanel implements ActionListener {
    private Stoplight light;
    private ControlPanel controls;
    private StoplightView view;

    public AppPanel() {
        light = new Stoplight();
        view = new StoplightView(light);
        controls = new ControlPanel();
        this.setLayout((new GridLayout(1, 2)));
        this.add(controls);
        this.add(view);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(this.createMenuBar());
        frame.setTitle("Stoplight Simulator");
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu = Utilities.makeMenu("File", new String[]{"New", "Save", "Open", "Quit"}, this);
        result.add(fileMenu);
        JMenu editMenu = Utilities.makeMenu("Edit", new String[]{"Change"}, this);
        result.add(editMenu);
        JMenu helpMenu = Utilities.makeMenu("Help", new String[]{"About", "Help"}, this);
        result.add(helpMenu);
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        String cmmd = e.getActionCommand();
        try {
            switch (cmmd) {
                case "Change":
                    light.change();
                    break;

                case "Save": {
                    String fName = Utilities.getFileName((String) null, false);
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
                    os.writeObject(this.light);
                    os.close();
                    break;
                }

                case "Open": {

                    if (Utilities.confirm("Are you sure? Unsaved changes will be lost!")) {
                        String fName = Utilities.getFileName((String) null, true);
                        ObjectInputStream is = new ObjectInputStream(new FileInputStream(fName));
                        light = (Stoplight) is.readObject();
                        view.setLight(light);
                        is.close();
                    }

                    break;

                }

                case "New": {
                    light = new Stoplight();
                    view.setLight(light);
                    break;
                }

                case "Quit": {
                    System.exit(0);
                    break;
                }

                case "About": {
                    Utilities.inform("SJSU, Stoplight Simulator, 2024. All rights reserved.");
                    break;
                }

                case "Help": {
                    String[] cmmds = new String[]{
                            "Change: Changes stop light",
                    };
                    Utilities.inform(cmmds);
                    break;

                }

                default: {
                    throw new Exception("Unrecognized command: " + cmmd);
                }
            }

        } catch (Exception ex) {
            Utilities.error(ex);
        }
    }

    class ControlPanel extends JPanel {
        public ControlPanel() {
            setBackground(Color.PINK);
            JPanel p = new JPanel();
            JButton change = new JButton("Change");
            change.addActionListener(AppPanel.this);
            p.add(change);
            add(p);
        }
    }

    public static void main(String[] args) {
        AppPanel app = new AppPanel();
    }
}