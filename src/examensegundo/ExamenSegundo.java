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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cavca
 */
public class ExamenSegundo extends JFrame implements ActionListener{

    /**
     * @param args the command line arguments
     */
    
    JButton bot1 = new JButton("<html><p align=center>Pedir<br/>Archivo</p></html>");
    JLabel lab1 = new JLabel("<html><p align=center>Servidores<br/>Conectados</p></html>");
    static JTable tab1 = new JTable();
    static DefaultTableModel modelo = new DefaultTableModel();
    JLabel lab2 = new JLabel("<html><p align=center>Nombre del archivo a buscar</p></html>");
    JTextField tf1 = new JTextField();
    static JLabel lab3 = new JLabel("<html><h1>Puerto : </H1></html>");
    

    public ExamenSegundo() {
        setTitle("Examen 2do parcial");
        setSize(600,500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        String columnI[] = {"Puertos de Servidores"};
        modelo.setColumnIdentifiers(columnI);
        tab1.setModel(modelo);
        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);
        tab1.getColumnModel().getColumn(0).setCellRenderer(Alinear);
        
        tab1.setAutoCreateRowSorter(true);
        
        lab1.setFont(new Font("Arial", Font.PLAIN, 15));
        lab2.setFont(new Font("Arial", Font.PLAIN, 18));
        
        bot1.setSize(150,50);
        lab1.setSize(100,50);
        tab1.setSize(200,300);
        lab2.setSize(250,50);
        tf1.setSize(300, 50);
        lab3.setSize(200,50);
        
        bot1.setLocation(250,300);
        lab1.setLocation(50,50);
        lab2.setLocation(250,120);
        tab1.setLocation(25,110);
        tf1.setLocation(250, 180);
        lab3.setLocation(200,10);
        
        add(bot1);
        add(lab1);
        add(lab2);
        add(tab1);
        add(tf1);
        add(lab3);
        
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
        int num = Integer.parseInt(JOptionPane.showInputDialog("Puerto del servidor(9000-9010)", 9000));
        System.out.println("Soy el :" + num);
        lab3.setText(lab3.getText().replace("Puerto : ", "Puerto : "+num));
        clienteM = new ClienteM(num);
        servidorD = new ServidorD(num);
        clienteD = new ClienteD(num);
        try {
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
            
            for(;;){
                ArrayList columnas = servidorM.getServidores();
//                System.out.println(modelo.getRowCount()+" - "+columnas.size());
                for(int i=modelo.getRowCount()-1;i>=0;i--){
                    modelo.removeRow(i);
                }
                for(int i=0;i<columnas.size();i++){
                    String fila[] = {columnas.get(i).toString()};
                    if(Integer.parseInt(columnas.get(i).toString())==num){
                        clienteD.puertoServidor = Integer.parseInt(columnas.get((i+1)%columnas.size()).toString());
                        servidorD.puerto2 = Integer.parseInt(columnas.get((i+1)%columnas.size()).toString());
                    }
                    
                    modelo.addRow(fila);
                }
                
//                System.out.println("bla");
                servidorM.servidores = new ArrayList<String>();
                Thread.sleep(5000);
            }

//            sm.join();
//            cm.join();
//            sd.join();
//            //cd.join();
//            sf.join();
//            cf.join();
            
            

        } catch (Exception ex) {
            Logger.getLogger(ExamenSegundo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            clienteD.setMensaje(tf1.getText().getBytes());
//            Thread cd = new Thread(clienteD);
//            cd.start();
//            cd.join();
        } catch (Exception ex) {
            Logger.getLogger(ExamenSegundo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
