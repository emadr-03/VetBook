package it.unina.vetbook.boundary;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        VetcareStyle.initLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            new VisitorBoundary().setVisible(true);
        });
    }
}
