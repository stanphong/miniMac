package miniMac;
import tools.Subscriber;
import tools.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MiniMacPanel extends JPanel implements ActionListener {
    private ControlPanel controls;
    private ViewPanel viewPanel;
    private MiniMac mac;
    private JList<String> memoryList;
    public static String filename;
    public static JList<String> instructionList;

    public MiniMacPanel() throws Exception {
        mac = new MiniMac();
        controls = new ControlPanel();
        viewPanel = new ViewPanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container mp = frame.getContentPane();
        mp.add(this);
        frame.setJMenuBar(this.createMenuBar());
        frame.setTitle("MiniMac");
        frame.setSize(500, 300);
        frame.setVisible(true);

        this.setLayout((new GridLayout(1, 2)));
        this.add(controls);
        this.add(viewPanel);
    }

    class ControlPanel extends JPanel {
        public ControlPanel() {
            setLayout(new GridLayout(3, 1));

            JPanel buttonPanel1 = new JPanel();
            JPanel buttonPanel2 = new JPanel();
            JPanel buttonPanel3 = new JPanel();

            JButton parseInput = new JButton("Parse");
            JButton run = new JButton("Run");
            JButton clear = new JButton("Clear");

            buttonPanel1.add(parseInput);
            buttonPanel2.add(run);
            buttonPanel3.add(clear);

            add(buttonPanel1);
            add(buttonPanel2);
            add(buttonPanel3);

            parseInput.addActionListener(MiniMacPanel.this);
            run.addActionListener(MiniMacPanel.this);
            clear.addActionListener(MiniMacPanel.this);

        }
    }

    class ViewPanel extends JPanel implements Subscriber {
        DefaultListModel<String> memoryListModel;
        public ViewPanel() throws Exception {
            setLayout(new GridLayout(2, 1));

            memoryListModel = new DefaultListModel<>();
            for (int i = 0; i < mac.size; i++) {
                memoryListModel.addElement("memory[" + i + "] = " + mac.memory[i]);
            }
            memoryList = new JList<String>(memoryListModel);
            add(new JScrollPane(memoryList));

            DefaultListModel<String> listModel = new DefaultListModel<>();
            instructionList = new JList<>(listModel);
            add(new JScrollPane(instructionList));

            mac.subscribe(this);
        }
        public static void refreshContent(){
            if (filename != null && Files.exists(Paths.get(filename))) {
                try {
                    List<String> fileContent = Files.readAllLines(Paths.get(filename));
                    DefaultListModel<String> listModel = (DefaultListModel<String>) instructionList.getModel();
                    listModel.clear();
                    for (String line : fileContent) {
                        listModel.addElement(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void update() {
            memoryListModel.clear();
            for (int i = 0; i < mac.size; i++) {
                memoryListModel.addElement("memory[" + i + "] = " + mac.memory[i]);
            }
        }
    }
    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = Utilities.makeMenu("File", new String[]{"New", "Save", "Open", "Quit"}, this);
        menuBar.add(fileMenu);
        JMenu editMenu = Utilities.makeMenu("Edit", new String[]{"Parse", "Run", "Clear"}, this);
        menuBar.add(editMenu);
        JMenu helpMenu = Utilities.makeMenu("Help", new String[]{"About", "Help"}, this);
        menuBar.add(helpMenu);
        return menuBar;
    }

    public void actionPerformed(ActionEvent e){
        String cmmd = e.getActionCommand();
            try{
                switch (cmmd){
                    case "Parse": {
                        filename = JOptionPane.showInputDialog("Enter program file name:", "No .txt tail") + ".txt";
                        ViewPanel.refreshContent();
                        break;
                    }
                    case "Run": {
                        String program = new String(Files.readAllBytes(Paths.get(filename)));
                        mac.instructions = ParserUtils.MiniMacParser.parse(program);
                        mac.execute();
                        break;
                    }

                    case "Clear": {
                        mac.clear();
                        break;
                    }

                    case "About": {
                        Utilities.inform("SJSU, MiniMac Virtual Processor, 2024. All rights reserved.");
                        break;
                    }

                    case "Help": {
                        String[] cmmds = new String[]{
                                "Parse: Parse in program to execute",
                                "Run: Execute the program",
                                "Clear: Reset all memory cells to 0"
                        };
                        Utilities.inform(cmmds);
                        break;

                    }
                }
            } catch (Exception ex) {
                Utilities.error(ex);
            }
    }

    public static void main(String[] args) throws Exception {
        MiniMacPanel app = new MiniMacPanel();
    }
}
