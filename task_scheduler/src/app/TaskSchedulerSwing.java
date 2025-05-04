package app;

import model.Task;
import scheduler.TaskScheduler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.IntStream;

public class TaskSchedulerSwing {
	
    private final TaskScheduler scheduler = new TaskScheduler();
    private final DefaultListModel<Task> upcomingModel  = new DefaultListModel<>();
    private final DefaultListModel<Task> completedModel = new DefaultListModel<>();

    public void initAndShow() {
//Bulk-load + heap-sort initial tasks
        List<Task> initial = List.of(
            new Task("feed cat", LocalDate.of(2025, 5, 10), 1, ""),
            new Task("water plants", LocalDate.of(2025, 5,  3), 1, ""),
            new Task("drink full glass of water", LocalDate.of(2025, 5,  5), 2, "")
        );
        scheduler.loadAndSortTasks(initial);
//Building UI
        JFrame frame = new JFrame("TASK SCHEDULER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
// Top input panel
        JPanel input = new JPanel();
//JPANEL BACKGROUND COLOR
        input.setBackground(new Color(180, 190, 210));                 
         
        JTextField titleField = new JTextField(10);

// Date dropdowns
        String[] monthNames = {
            "Jan","Feb","Mar","Apr","May","Jun",
            "Jul","Aug","Sep","Oct","Nov","Dec"
        };
        JComboBox<String> monthCb = new JComboBox<>(monthNames);
        Integer[] days = IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new);
        JComboBox<Integer> dayCb   = new JComboBox<>(days);
        int currentYear = LocalDate.now().getYear();
        Integer[] years = IntStream.rangeClosed(currentYear, currentYear + 5)
                                   .boxed().toArray(Integer[]::new);
        JComboBox<Integer> yearCb  = new JComboBox<>(years);

        JTextField diffField  = new JTextField("1-5", 3);
        JButton addBtn        = new JButton("Add");
//COLOR OF BUTTONS        
        addBtn.setBackground(Color.DARK_GRAY);     
        addBtn.setForeground(Color.WHITE);

        input.add(new JLabel("TASK:"));  input.add(titleField);
        input.add(new JLabel("DUE DATE:"));    input.add(monthCb);
                                          input.add(dayCb);
                                          input.add(yearCb);
        input.add(new JLabel("DIFFICULTY:"));   input.add(diffField);
        input.add(addBtn);
        input.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
// Center lists & controls
        JList<Task> upcomingList  = new JList<>(upcomingModel);
        JList<Task> completedList = new JList<>(completedModel);
//COLOR OF LISTS
        upcomingList.setBackground(new Color(180, 190, 210));
        completedList.setBackground(new Color(180, 190, 210));
        
        JButton completeBtn = new JButton("COMPLETED →");
        JButton undoBtn     = new JButton("Undo");
//BUTTON COLORS        
        completeBtn.setBackground(Color.DARK_GRAY);
        completeBtn.setForeground(Color.white);    

        undoBtn.setBackground(Color.DARK_GRAY); 
        undoBtn.setForeground(Color.white);               

        JPanel ctrls = new JPanel(new GridLayout(2,1,5,5));
        ctrls.add(completeBtn);
        ctrls.add(undoBtn);

        JPanel lists = new JPanel(new BorderLayout(10, 10));
        lists.add(new JScrollPane(upcomingList), BorderLayout.WEST);
        lists.add(ctrls, BorderLayout.CENTER);
        lists.add(new JScrollPane(completedList), BorderLayout.EAST);
        lists.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
// Assemble UI
        frame.getContentPane().add(input, BorderLayout.NORTH);
        frame.getContentPane().add(lists, BorderLayout.CENTER);
// Event handlers
        addBtn.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                int month = monthCb.getSelectedIndex() + 1;
                int day   = (Integer)dayCb.getSelectedItem();
                int year  = (Integer)yearCb.getSelectedItem();
                YearMonth ym = YearMonth.of(year, month);
                if (day > ym.lengthOfMonth()) {
                    throw new IllegalArgumentException("Invalid day for " + monthNames[month-1]);
                }
                LocalDate due = LocalDate.of(year, month, day);

                int diff = Integer.parseInt(diffField.getText().trim());
                if (title.isEmpty()) {
                    throw new IllegalArgumentException("Title can't be empty");
                }

                Task t = new Task(title, due, diff, "");
                scheduler.addTask(t);
                refresh();
                titleField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                    "Input Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        completeBtn.addActionListener(e -> {
            Task sel = upcomingList.getSelectedValue();
            if (sel != null) {
                scheduler.complete(sel);
                refresh();
            }
        });

        undoBtn.addActionListener(e -> {
            scheduler.undoLast().ifPresent(x -> refresh());
        });
//Finalize and show
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        refresh();
    }

    private void refresh() {
        List<Task> up   = scheduler.listUpcoming();
        List<Task> comp = scheduler.listCompleted();

        upcomingModel.clear();
        up.forEach(upcomingModel::addElement);

        completedModel.clear();
        comp.forEach(completedModel::addElement);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskSchedulerSwing().initAndShow());
    }
}
