package pl.lodz.p.adi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MyJFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public MyJFrame() {

        initUI();
    }

    private void initUI() {

        AtomicInteger calculatorState = new AtomicInteger();
        AtomicDouble numberMemory = new AtomicDouble();
        AtomicReference<String> operation = new AtomicReference<String>("");

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Edycja"));
        menuBar.add(new JMenu("Widok"));
        menuBar.add(new JMenu("Pomoc"));
        setJMenuBar(menuBar);

        // --------------------------------------------

        JPanel pA = new JPanel();
        pA.setLayout(new GridLayout(1, 1));
        JTextField result = new JTextField("0");
        result.setHorizontalAlignment(JTextField.RIGHT);
        pA.add(result);

        // --------------------------------------------

        class Solver extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                JButton source = (JButton) e.getSource();

                if (source.getName().equals("CE")) {
                    result.setText("0");
                    calculatorState.set(0);
                }

                if (Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9").contains(source.getName())) {
                    if (calculatorState.get() <= 1)
                        result.setText(source.getName());
                    else if (result.getText().equals("0"))
                        result.setText(source.getName());
                    else
                        result.setText(result.getText() + source.getName());

                    calculatorState.set(2);

                }

                if (source.getName().equals(",")) {
                    if (!result.getText().contains(",")) {
                        result.setText(result.getText() + ".");
                    }
                }

                if (Arrays.asList("+", "-", "*", "/").contains(source.getName())) {
                    numberMemory.set(Double.parseDouble(result.getText()));
                    operation.set(source.getName());
                    calculatorState.set(1);
                }

                if (source.getName().equals("=")) {
                    double r = 0;
                    if (!operation.get().equals("")) {
                        if (operation.get().equals("+")) {
                            r = numberMemory.get() + Double.parseDouble(result.getText());
                        } else if (operation.get().equals("-")) {
                            r = numberMemory.get() - Double.parseDouble(result.getText());
                        } else if (operation.get().equals("*")) {
                            r = numberMemory.get() * Double.parseDouble(result.getText());
                        } else if (operation.get().equals("/")) {
                            r = numberMemory.get() / Double.parseDouble(result.getText());
                        }
                    }

                    result.setText(Double.toString(r));
                    calculatorState.set(0);
                }

            }
        }

        // --------------------------------------------

        JPanel pB = new JPanel();
        pB.setBorder(BorderFactory.createTitledBorder(""));
        pB.setLayout(new GridLayout(1, 4));
        ButtonGroup pBbg = new ButtonGroup();
        for (String name : Arrays.asList("Hex", "Dec", "Oct", "Bin")) {
            JRadioButton comp = new JRadioButton(name);
            pBbg.add(comp);
            pB.add(comp);
        }

        // --------------------------------------------

        JPanel pC = new JPanel();
        pC.setBorder(BorderFactory.createTitledBorder(""));
        pC.setLayout(new GridLayout(1, 3));
        ButtonGroup pCbg = new ButtonGroup();
        for (String name : Arrays.asList("Stopnie", "Radiany", "Gradiusy")) {
            JRadioButton comp = new JRadioButton(name);
            pCbg.add(comp);
            pC.add(comp);
        }

        // --------------------------------------------

        JPanel pD = new JPanel();
        pD.setBorder(BorderFactory.createEmptyBorder());
        pD.setLayout(new GridLayout(1, 4));

        JPanel pPDA = new JPanel();
        pPDA.setBorder(BorderFactory.createTitledBorder(""));
        pPDA.setLayout(new GridLayout(1, 2));
        pPDA.add(new JCheckBox("Inv"));
        pPDA.add(new JCheckBox("Hyp"));

        JPanel pPDB = new JPanel();
        pPDB.setBorder(BorderFactory.createEmptyBorder());
        pPDB.setLayout(new GridLayout(1, 2));
        JButton pPDBbutton1 = new JButton();
        pPDBbutton1.setMargin(new Insets(5, 5, 5, 5));
        pPDBbutton1.setPreferredSize(new Dimension(20, 20));
        JButton pPDBbutton2 = new JButton();
        pPDBbutton2.setMargin(new Insets(5, 5, 5, 5));
        pPDBbutton2.setPreferredSize(new Dimension(20, 20));
        pPDB.add(pPDBbutton1);
        pPDB.add(pPDBbutton2);

        pD.add(pPDA);
        pD.add(pPDB);

        // --------------------------------------------

        JPanel pE = new JPanel();
        pE.setBorder(BorderFactory.createEmptyBorder());
        pE.setLayout(new GridLayout(1, 3));
        for (String name : Arrays.asList("Backspace", "CE", "E")) {
            JButton comp = new JButton(name);
            pE.add(comp);

            comp.setName(name);
            comp.addMouseListener(new Solver());

            comp.setForeground(Color.RED);
        }

        // --------------------------------------------

        JPanel pABCDE = new JPanel();
        pABCDE.setBorder(BorderFactory.createEmptyBorder());
        pABCDE.setLayout(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();
        cp.ipadx = 5;
        cp.ipady = 5;
        cp.weightx = 1;
        cp.weighty = 1;
        cp.insets = new Insets(0, 5, 0, 5);

        cp.gridx = 0;
        cp.gridy = 0;
        cp.gridwidth = 11;
        cp.gridheight = 1;
        cp.anchor = GridBagConstraints.CENTER;
        cp.fill = GridBagConstraints.HORIZONTAL;
        pABCDE.add(pA, cp);

        cp.gridx = 0;
        cp.gridy = 1;
        cp.gridwidth = 1;
        cp.gridheight = 1;
        cp.anchor = GridBagConstraints.LINE_START;
        cp.fill = 0;
        pABCDE.add(pB, cp);

        cp.gridx = 1;
        cp.gridy = 1;
        cp.gridwidth = 1;
        cp.gridheight = 1;
        cp.anchor = GridBagConstraints.LINE_END;
        cp.fill = 0;
        pABCDE.add(pC, cp);

        cp.gridx = 0;
        cp.gridy = 2;
        cp.gridwidth = 1;
        cp.gridheight = 1;
        cp.anchor = GridBagConstraints.LINE_START;
        cp.fill = 0;
        pABCDE.add(pD, cp);

        cp.gridx = 1;
        cp.gridy = 2;
        cp.gridwidth = 1;
        cp.gridheight = 1;
        cp.anchor = GridBagConstraints.LINE_END;
        cp.fill = 0;
        pABCDE.add(pE, cp);

        // --------------------------------------------

        JPanel pF = new JPanel();
        pF.setBorder(BorderFactory.createEmptyBorder());
        pF.setLayout(new GridLayout(5, 1));
        for (String name : Arrays.asList("Sta", "Ave", "Sum", "s", "Dat")) {
            JButton comp = new JButton(name);
            pF.add(comp);

            comp.setMargin(new Insets(1, 1, 1, 1));
            comp.setPreferredSize(new Dimension(45, 45));
            if (!name.equals("Sta")) {
                comp.setEnabled(false);
            }
        }

        // --------------------------------------------

        JPanel pG = new JPanel();
        pG.setBorder(BorderFactory.createEmptyBorder());
        pG.setLayout(new GridLayout(5, 3));
        for (String name : Arrays.asList("F-E", "[", "]", "dms", "Exp", "in", "sin", "x^y", "log", "cos", "x^3", "n!",
                "tan", "x^2", "1/x")) {
            JButton comp = new JButton(name);
            pG.add(comp);

            comp.setMargin(new Insets(1, 1, 1, 1));
            comp.setPreferredSize(new Dimension(45, 45));
            comp.setForeground(Color.PINK);
        }

        // --------------------------------------------

        JPanel pH = new JPanel();
        pH.setBorder(BorderFactory.createEmptyBorder());
        pH.setLayout(new GridLayout(5, 1));
        for (String name : Arrays.asList("MC", "MR", "MS", "M+", "pi")) {
            JButton comp = new JButton(name);
            pH.add(comp);

            comp.setMargin(new Insets(1, 1, 1, 1));
            comp.setPreferredSize(new Dimension(45, 45));
            if (name.equals("pi")) {
                comp.setForeground(Color.RED);
            } else {
                comp.setForeground(Color.BLUE);
            }
        }

        // --------------------------------------------

        JPanel pI = new JPanel();
        pI.setBorder(BorderFactory.createEmptyBorder());
        pI.setLayout(new GridLayout(5, 6));
        for (String name : Arrays.asList("7", "8", "9", "/", "MOD", "And", "4", "5", "6", "*", "Or", "Xor", "1", "2",
                "3", "-", "Lsh", "Not", "0", "+/-", ",", "+", "=", "Int", "A", "B", "C", "D", "E", "F")) {
            JButton comp = new JButton(name);
            comp.setName(name);
            pI.add(comp);

            comp.addMouseListener(new Solver());

            comp.setMargin(new Insets(1, 1, 1, 1));
            comp.setPreferredSize(new Dimension(45, 45));
            if (Arrays.asList("A", "B", "C", "D", "E", "F").contains(name)) {
                comp.setEnabled(false);
            } else if (Arrays.asList("7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "+/-", ",").contains(name)) {
                comp.setForeground(Color.BLUE);
            } else {
                comp.setForeground(Color.RED);
            }
        }

        // --------------------------------------------

        JPanel ui = new JPanel();
        ui.setBorder(BorderFactory.createEmptyBorder());
        ui.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 5;
        c.ipady = 5;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(5, 5, 5, 5);

		/*
         * c.gridx = 0; c.gridy = 0; c.gridwidth = 11; c.gridheight = 1; c.fill
		 * = GridBagConstraints.HORIZONTAL; c.anchor =
		 * GridBagConstraints.LINE_START; ui.add(pA, c);
		 * 
		 * c.gridx = 0; c.gridy = 1; c.gridwidth = 5; c.gridheight = 1; c.fill =
		 * 0; c.anchor = GridBagConstraints.LINE_START; ui.add(pB, c);
		 * 
		 * c.gridx = 5; c.gridy = 1; c.gridwidth = 6; c.gridheight = 1; c.fill =
		 * 0; c.anchor = GridBagConstraints.LINE_END; ui.add(pC, c);
		 * 
		 * c.gridx = 0; c.gridy = 2; c.gridwidth = 5; c.gridheight = 1; c.fill =
		 * 0; c.anchor = GridBagConstraints.LINE_START; ui.add(pD, c);
		 * 
		 * c.gridx = 5; c.gridy = 2; c.gridwidth = 6; c.gridheight = 1; c.fill =
		 * 0; c.anchor = GridBagConstraints.LINE_END; ui.add(pE, c);
		 */

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 11;
        c.gridheight = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        ui.add(pABCDE, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 5;
        c.fill = 0;
        c.anchor = GridBagConstraints.LINE_START;
        ui.add(pF, c);

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 5;
        c.fill = 0;
        c.anchor = GridBagConstraints.CENTER;
        ui.add(pG, c);

        c.gridx = 4;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 5;
        c.fill = 0;
        c.anchor = GridBagConstraints.CENTER;
        ui.add(pH, c);

        c.gridx = 5;
        c.gridy = 3;
        c.gridwidth = 6;
        c.gridheight = 5;
        c.fill = 0;
        c.anchor = GridBagConstraints.LINE_END;
        ui.add(pI, c);

        add(ui);
        pack();

        setTitle("Kalkulator");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
