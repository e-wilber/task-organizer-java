package app;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
//runs the GUI rather than the console
        SwingUtilities.invokeLater(() -> new TaskSchedulerSwing().initAndShow());
    }
}
