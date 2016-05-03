package pl.lodz.p.adi;

import javax.swing.*;
import java.awt.*;

public class Calculator1 {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException
                    | UnsupportedLookAndFeelException ignored) {
            }

            MyJFrame ex = new MyJFrame();
            ex.setVisible(true);
        });
    }
}
