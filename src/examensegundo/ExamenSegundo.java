/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examensegundo;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author cavca
 */
public class ExamenSegundo extends JFrame implements ActionListener {

    private static ArrayList ordenarIps(ArrayList columns, ArrayList ips) {
        int indx = 0;
        ArrayList columnas = new ArrayList(columns);
        ArrayList sIps = new ArrayList();

//        System.out.println("\n");
        while (sIps.size() < ips.size()) {
//            System.out.println(columnas.size() + " , "+ ips.size()+" - " +sIps.size());
            indx = 0;
            for (int i = 1; i < columnas.size(); i++) {
                if (Integer.parseInt(columnas.get(i).toString()) < Integer.parseInt(columnas.get(indx).toString())) {
                    indx = i;
                }
            }
            sIps.add(ips.get(indx));
            columnas.remove(indx);
        }

        return sIps;
    }

    /**
     * @param args the command line arguments
     */
    JButton bot1 = new JButton("<html><p align=center>Pedir<br/>Archivo</p></html>");
    JLabel lab1 = new JLabel("<html><p align=center>Servidores Conectados</p></html>");
    static JTable tab1 = new JTable();
    static DefaultTableModel modelo1 = new DefaultTableModel();
    static JTable tab2 = new JTable();
    static DefaultTableModel modelo2 = new DefaultTableModel();
    static JTable tab3 = new JTable();
    static DefaultTableModel modelo3 = new DefaultTableModel();
    JLabel lab2 = new JLabel("<html><p align=center>Nombre del archivo a buscar</p></html>");
    JTextField tf1 = new JTextField();
    static JLabel lab3 = new JLabel("<html><h1>Puerto : </H1></html>");
    static JLabel lab4 = new JLabel("<html><h3>Anterior</h3></html>");
    static JLabel lab5 = new JLabel("<html><h3>Posterior</h3></html>");
    static JLabel lab6 = new JLabel("");
    static JLabel lab7 = new JLabel("");

    public ExamenSegundo() {
        setTitle("Examen 2do parcial");
        setSize(900, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String columnI[] = {"Ip de Servidor", "Puerto"};
        String columnA[] = {"Archivos buscados"};
        String columnA2[] = {"Respuestas"};
        modelo1.setColumnIdentifiers(columnI);
        tab1.setModel(modelo1);
        modelo2.setColumnIdentifiers(columnA);
        tab2.setModel(modelo2);
        modelo3.setColumnIdentifiers(columnA2);
        tab3.setModel(modelo3);

        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);
        tab1.getColumnModel().getColumn(0).setCellRenderer(Alinear);
        tab1.getColumnModel().getColumn(1).setCellRenderer(Alinear);
        tab2.getColumnModel().getColumn(0).setCellRenderer(Alinear);
        tab3.getColumnModel().getColumn(0).setCellRenderer(Alinear);
//        tab1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

//        tab1.setAutoCreateRowSorter(true);
//        JTableHeader th1 = new JTableHeader();
//        tab1.setTableHeader(th1);
        lab1.setFont(new Font("Arial", Font.BOLD, 15));
        lab2.setFont(new Font("Arial", Font.PLAIN, 18));

        JScrollPane sp1 = new JScrollPane(tab1);
        JScrollPane sp2 = new JScrollPane(tab2);
        JScrollPane sp3 = new JScrollPane(tab3);

        bot1.setSize(150, 70);
        lab1.setSize(200, 50);
        sp1.setSize(200, 250);
        sp2.setSize(200, 400);
        sp3.setSize(100, 400);
        lab2.setSize(250, 50);
        tf1.setSize(300, 50);
        lab3.setSize(200, 50);
        lab4.setSize(100, 50);
        lab5.setSize(100, 50);
        lab6.setSize(100, 50);
        lab7.setSize(100, 50);

        bot1.setLocation(300, 300);
        lab1.setLocation(40, 110);
        lab2.setLocation(250, 150);
        tf1.setLocation(250, 200);
        lab3.setLocation(200, 10);
        sp1.setLocation(25, 160);
        sp2.setLocation(570, 20);
        sp3.setLocation(770, 20);
        lab4.setLocation(175, 50);
        lab5.setLocation(325, 50);
        lab6.setLocation(185, 70);
        lab7.setLocation(340, 70);

        add(bot1);
        add(lab1);
        add(lab2);
        add(sp1);
        add(sp2);
        add(sp3);
        add(tf1);
        add(lab3);
        add(lab4);
        add(lab5);
        add(lab6);
        add(lab7);

        setVisible(true);

        bot1.addActionListener(this);

    }

    static ServidorM servidorM = new ServidorM();
    static ClienteM clienteM;
    static ServidorD servidorD;
    static ClienteD clienteD;
//    static ServidorF servidorF = new ServidorF();
//    static ClienteF clienteF = new ClienteF();

    public static void main(String[] args) {
        ExamenSegundo es = new ExamenSegundo();
        int num = 9000;
        try {
            num = Integer.parseInt(JOptionPane.showInputDialog("Puerto del servidor(9000-9010)", 9000));
            System.out.println("Soy el :" + num);
            lab3.setText(lab3.getText().replace("Puerto : ", "Puerto : " + num));
            lab6.setText(num + "");
            lab7.setText(num + "");
            clienteM = new ClienteM(num);
            servidorD = new ServidorD(num);
            clienteD = new ClienteD(num);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Thread sm = new Thread(servidorM);
        Thread cm = new Thread(clienteM);
        Thread sd = new Thread(servidorD);
        //Thread cd = new Thread(clienteD);
//            Thread sf = new Thread(servidorF);
//            Thread cf = new Thread(clienteF);

        sm.start();
        cm.start();
        sd.start();
        //cd.start();
//            sf.start();
//            cf.start();

        for (;;) {
            try {
                ArrayList columnas = servidorM.getServidores();
                ArrayList ips = servidorM.getServidoresIp();
//                System.out.println(modelo.getRowCount()+" - "+columnas.size());
                for (int i = modelo1.getRowCount() - 1; i >= 0; i--) {
                    modelo1.removeRow(i);
                }
                ips = ordenarIps(columnas, ips);
                columnas.sort(null);
                for (int i = 0; i < columnas.size(); i++) {
                    String fila[] = {ips.get(i).toString(), columnas.get(i).toString()};
                    if (Integer.parseInt(columnas.get(i).toString()) == num) {
                        clienteD.puertoServidor = Integer.parseInt(columnas.get((i + 1) % columnas.size()).toString());
                        clienteD.ipServidor = ips.get((i + 1) % columnas.size()).toString().substring(1);
                        servidorD.ipServidor = ips.get((i + 1) % columnas.size()).toString().substring(1);
                        servidorD.puerto2 = Integer.parseInt(columnas.get((i + 1) % columnas.size()).toString());
                        lab6.setText(columnas.get((i - 1 + columnas.size()) % columnas.size()).toString());
                        lab7.setText(columnas.get((i + 1) % columnas.size()).toString());
                    }
                    modelo1.addRow(fila);
                }
                
                String fila[]= new String[1];
                for(int i=0;i<servidorD.aBuscados.size();i++){
                    fila[0]=servidorD.aBuscados.get(i);
                    modelo2.addRow(fila);
                }
                servidorD.aBuscados.clear();
                
                for(int i=0;i<servidorD.aRespuestas.size();i++){
                    fila[0]=servidorD.aRespuestas.get(i);
                    modelo3.addRow(fila);
                }
                servidorD.aRespuestas.clear();

//                System.out.println("bla");
                servidorM.servidores.clear();
                servidorM.servidoresIp.clear();
                Thread.sleep(5000);
            } //            sm.join();
            //            cm.join();
            //            sd.join();
            //            //cd.join();
            //            sf.join();
            //            cf.join();
            catch (InterruptedException ex) {
                Logger.getLogger(ExamenSegundo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            servidorD.aBuscados.add(tf1.getText());
            servidorD.aRespuestas.add("Yo lo pedi");
            clienteD.setMensaje(tf1.getText().getBytes());
//            Thread cd = new Thread(clienteD);
//            cd.start();
//            cd.join();
        } catch (Exception ex) {
            Logger.getLogger(ExamenSegundo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
