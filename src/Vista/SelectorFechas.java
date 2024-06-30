package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Clase que representa una vista para seleccionar una fecha en formato DDMMYYYY.
 */
public class SelectorFechas extends JDialog {
    private JComboBox<Integer> yearComboBox;
    private JLabel monthLabel;
    private JTable calendarTable;
    private Calendar calendar;
    private String selectedDate;
    private boolean confirmed;

    /**
     * Constructor de la vista de selección de fecha.
     *
     * @param parent La vista padre que invoca esta vista.
     */
    public SelectorFechas(JFrame parent) {
        super(parent, "Selector de Fecha", true);
        setSize(500, 300);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Image image = Toolkit.getDefaultToolkit().getImage("src/Recursos/IconoS21.png");
        setIconImage(image);

        calendar = Calendar.getInstance();

        // Crear componentes
        yearComboBox = new JComboBox<>();
        populateYearComboBox();
        yearComboBox.setSelectedItem(calendar.get(Calendar.YEAR));

        monthLabel = new JLabel(getMonthName(calendar.get(Calendar.MONTH)), JLabel.CENTER);

        JButton prevButton = new JButton("< Prev");
        JButton nextButton = new JButton("Next >");
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        yearComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendar.set(Calendar.YEAR, (Integer) yearComboBox.getSelectedItem());
                updateCalendar();
            }
        });

        calendarTable = new JTable();
        calendarTable.setCellSelectionEnabled(true);
        updateCalendar();

        // Botones de acción
        JButton cancelButton = new JButton("Cancelar");
        JButton acceptButton = new JButton("Aceptar");

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose();
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = calendarTable.getSelectedRow();
                int col = calendarTable.getSelectedColumn();
                if (row >= 0 && col >= 0 && calendarTable.getValueAt(row, col) != null) {
                    try {
                        int day = (int) calendarTable.getValueAt(row, col);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int year = (int) yearComboBox.getSelectedItem();
                        selectedDate = String.format("%04d-%02d-%02d", year, month, day);
                        confirmed = true;
                        dispose();
                    }
                    catch (Exception ex){
                        selectedDate = null;
                        confirmed = true;
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione una fecha válida.");
                }
            }
        });

        // Organizar componentes en el layout
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        topPanel.add(yearComboBox);
        topPanel.add(prevButton);
        topPanel.add(monthLabel);
        topPanel.add(nextButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancelButton);
        buttonPanel.add(acceptButton);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(calendarTable), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    /**
     * Llena el comboBox con los años desde el actual hasta 10 años en el futuro.
     */
    private void populateYearComboBox() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 10; i++) {
            yearComboBox.addItem(i);
        }
    }

    /**
     * Obtiene el nombre del mes en función de su índice.
     *
     * @param month El índice del mes.
     * @return El nombre del mes.
     */
    private String getMonthName(int month) {
        String[] monthNames = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return monthNames[month];
    }

    /**
     * Actualiza el calendario mostrado en la tabla.
     */
    private void updateCalendar() {
        monthLabel.setText(getMonthName(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.YEAR));

        String[] columnNames = {"Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1; // El índice del día de la semana comienza en 1 (domingo) en Calendar.
        int daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Object[] week = new Object[7];
        for (int i = 0; i < firstDayOfWeek; i++) {
            week[i] = "";
        }

        for (int day = 1; day <= daysInMonth; day++) {
            week[firstDayOfWeek] = day;
            firstDayOfWeek++;
            if (firstDayOfWeek == 7) {
                model.addRow(week);
                week = new Object[7];
                firstDayOfWeek = 0;
            }
        }

        if (firstDayOfWeek != 0) {
            model.addRow(week);
        }

        calendarTable.setModel(model);
    }

    /**
     * Método estático para mostrar el diálogo y obtener la fecha seleccionada.
     *
     * @param parent La vista padre que invoca este diálogo.
     * @return La fecha seleccionada en formato DDMMYYYY o null si se cancela.
     */
    public static String showDialog(JFrame parent) {
        SelectorFechas dialog = new SelectorFechas(parent);
        dialog.setVisible(true);
        return dialog.confirmed ? dialog.selectedDate : null;
    }

    /**
     * Metodo para pruebas o ejecuciones aisladas.
     * @param args
     */
    public static void main(String[] args) {
        JFrame parent = new JFrame();
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent.setSize(300, 200);
        parent.setVisible(true);

        String selectedDate = SelectorFechas.showDialog(parent);
        //System.out.println("Fecha seleccionada: " + selectedDate);
    }
}