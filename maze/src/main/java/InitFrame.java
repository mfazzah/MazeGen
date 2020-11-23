import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InitFrame extends ListenerPanel {
    public static final String GENERATE = "Generate maze";

    public InitFrame() {
        this.setLayout(new GridLayout(2, 1, 0, 10));
        JPanel dimensions = new JPanel();
        dimensions.setLayout(new GridLayout(2, 2, 5, 0 ));
        final JTextField width = new JTextField();
        final JTextField height = new JTextField();
        dimensions.add(new JLabel("Width"));
        dimensions.add(width);
        dimensions.add(new JLabel("Height"));
        dimensions.add(height);

        this.add(dimensions);
        JButton generate = new JButton("Generate");
        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String w = width.getText();
                String h = height.getText();
                try {
                    List<Integer> list = new ArrayList<>();
                    list.add(Integer.valueOf(w));
                    list.add(Integer.valueOf(h));
                    myPcs.firePropertyChange(GENERATE, null, list);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input.");
                }
            }
        });
        this.add(generate);
        //this.pack();
        this.setVisible(true);
    }
}
