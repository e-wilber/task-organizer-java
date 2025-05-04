/** 
* DATA STRUCTURES: FINAL PROJECT
* @author E Wilber
* @version 1.0
* @since 1.0
*  
* OS: Windows11
* IDE: Eclipse
*  
* Copyright : This is my own original work 
* based on specifications issued by our instructor
* Academic Honesty: I attest that this is my original work.
* I have not used unauthorized source code, either modified or
* unmodified, nor used generative AI as a final draft. 
* I have not given other fellow student(s) access to my program.
*/
package app;

import javax.swing.SwingUtilities;
/**
 * Entry point for Smart Task Scheduler
 * 
 * This class launches the Swing GUI
 * 
 */
public class Main {
/**
* Main method starts up the Swing based Task Scheduler
* 
* @param args command-line arguments (not used)
*/
    public static void main(String[] args) {
//runs the GUI rather than the console
        SwingUtilities.invokeLater(() -> new TaskSchedulerSwing().initAndShow());
    }
}
