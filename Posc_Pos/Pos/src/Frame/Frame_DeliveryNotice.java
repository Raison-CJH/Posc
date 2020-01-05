package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import client.client;
import javazoom.jl.player.Player;

import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Frame_DeliveryNotice extends JFrame {
	private JTable menutable;
	public static DefaultTableModel tablemodel;
	public DefaultTableCellRenderer tablesort;
	private String attribute[] = {"�޴�","����","�ܰ�"};
	private Object tuple[][] = {};
	private JTextField txt_tablenum;
	private JTextField txt_phone;
	private JTextField txt_orderpay;
	
	public Frame_DeliveryNotice(String text) {
		getContentPane().setBackground(Color.WHITE);
		((JComponent)getContentPane()).setBorder(new LineBorder(new Color(53,135,145)));
		//�ּ�@@��ȭ��ȣ@@¥���!����!�ܰ�#������!����!�ܰ�@@���ֹ���@@�޼���
		String orderinfo[] = text.split("@@");
		String ordermenu[] = orderinfo[2].split("#");

		String menuname[] = new String[ordermenu.length];
		String menucnt[] = new String[ordermenu.length];
		String menuprice[] = new String[ordermenu.length];
		
		for(int i = 0; i < ordermenu.length; i++) {
			String menuinfo[] = ordermenu[i].split("!");
			menuname[i] = menuinfo[0];
			menucnt[i] = menuinfo[1];
			menuprice[i] = menuinfo[2];
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 800);
		setResizable(false);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setBackground(SystemColor.window);
		getContentPane().setLayout(null);
		
		JLabel label_poscLogo = new JLabel("Posc ���");
		label_poscLogo.setFont(new Font("���� ������ 230", Font.PLAIN, 30));
		label_poscLogo.setHorizontalAlignment(SwingConstants.CENTER);
		label_poscLogo.setBounds(54, 22, 397, 64);
		getContentPane().add(label_poscLogo);
		
		JLabel label_tablenum = new JLabel("�ּ� : ");
		label_tablenum.setFont(new Font("���� ������ 230", Font.PLAIN, 15));
		label_tablenum.setBounds(54, 118, 90, 18);
		getContentPane().add(label_tablenum);
		
		txt_tablenum = new JTextField(orderinfo[0]);
		txt_tablenum.setBorder(new LineBorder(new Color(53,135,145)));
		txt_tablenum.setFont(new Font("���� ������ 230", Font.PLAIN, 15));
		txt_tablenum.setBackground(Color.WHITE);
		txt_tablenum.setBounds(104, 115, 347, 24);
		getContentPane().add(txt_tablenum);
		txt_tablenum.setColumns(10);
		txt_tablenum.setEditable(false);
		
		JLabel label_phone = new JLabel("��ȭ��ȣ : ");
		label_phone.setFont(new Font("���� ������ 230", Font.PLAIN, 15));
		label_phone.setBounds(54, 161, 77, 18);
		getContentPane().add(label_phone);
			
		txt_phone = new JTextField(orderinfo[1]);
		txt_phone.setBorder(new LineBorder(new Color(53,135,145)));
		txt_phone.setFont(new Font("���� ������ 230", Font.PLAIN, 15));
		txt_phone.setBackground(Color.WHITE);
		txt_phone.setColumns(10);
		txt_phone.setBounds(134, 158, 317, 24);
		getContentPane().add(txt_phone);
		txt_phone.setEditable(false);
		
		JLabel label_orderinfo = new JLabel("�ֹ�");
		label_orderinfo.setFont(new Font("���� ������ 230", Font.PLAIN, 15));
		label_orderinfo.setBounds(54, 207, 62, 18);
		getContentPane().add(label_orderinfo);
		
		JLabel label_orderpay = new JLabel("�ֹ��ݾ� : ");
		label_orderpay.setFont(new Font("���� ������ 230", Font.PLAIN, 15));
		label_orderpay.setBounds(54, 440, 77, 18);
		getContentPane().add(label_orderpay);
		
		txt_orderpay = new JTextField(orderinfo[3] + "��");
		txt_orderpay.setBorder(new LineBorder(new Color(53,135,145)));
		txt_orderpay.setFont(new Font("���� ������ 230", Font.PLAIN, 15));
		txt_orderpay.setBackground(Color.WHITE);
		txt_orderpay.setHorizontalAlignment(SwingConstants.RIGHT);
		txt_orderpay.setColumns(10);
		txt_orderpay.setBounds(134, 437, 317, 24);
		getContentPane().add(txt_orderpay);
		txt_orderpay.setEditable(false);
		
		JLabel label_msg = new JLabel("�޽���");
		label_msg.setFont(new Font("���� ������ 230", Font.PLAIN, 15));
		label_msg.setBounds(54, 484, 77, 18);
		getContentPane().add(label_msg);
		
		JTextArea txta_msg = new JTextArea(orderinfo[4]);
		txta_msg.setBorder(new LineBorder(new Color(53,135,145)));
		txta_msg.setFont(new Font("���� ������ 230", Font.PLAIN, 14));
		txta_msg.setBounds(54, 514, 397, 124);
		getContentPane().add(txta_msg);
		txta_msg.setEditable(false);
		
		JButton btn_accept = new JButton("����");
		btn_accept.setForeground(Color.WHITE);
		btn_accept.setBackground(new Color(53,135,145));
		btn_accept.setFont(new Font("���� ������ 230", Font.PLAIN, 18));
		btn_accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            Thread sender = new DSender(client.socket, client.s_name, txt_phone.getText() + "@@d1");  
	            sender.start(); //������ �õ�
	            dispose();
			}
		});
		btn_accept.setBounds(54, 692, 105, 35);
		getContentPane().add(btn_accept);
		
		JButton btn_reject = new JButton("�ź�");
		btn_reject.setForeground(Color.WHITE);
		btn_reject.setBackground(new Color(53,135,145));
		btn_reject.setFont(new Font("���� ������ 230", Font.PLAIN, 18));
		btn_reject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            Thread sender = new DSender(client.socket, client.s_name, txt_phone.getText() + "@@d0");  
	            sender.start(); //������ �õ�
	            dispose();
			}
		});
		btn_reject.setBounds(346, 692, 105, 35);
		getContentPane().add(btn_reject);
		
		
		setTable();
		for(int i = 0; i < ordermenu.length; i++) {
			tablemodel.addRow(new Object[]{menuname[i], menucnt[i], menuprice[i]});
		}
		
		setVisible(true);
		try {
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream("C:\\Users\\Son\\Desktop\\Java_Posc\\Posc\\src\\Frame\\snd\\Alarm.mp3"));
			Player player = new Player(buffer);
			player.play();
		}catch(Exception e) {
			
		}
	}

	private void setTable() {
		tablemodel = new DefaultTableModel(tuple, attribute) {
			public boolean isCellEditable(int tuple, int attribute) {
				return false;
			}
		};

		menutable = new JTable(tablemodel);
		menutable.setBorder(new LineBorder(null, 0, true));
		menutable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menutable.setRowHeight(30);
		menutable.getTableHeader().setBackground(new Color(53,135,145));
		menutable.getTableHeader().setReorderingAllowed(false);
		menutable.getTableHeader().setResizingAllowed(false);
		menutable.getTableHeader().setForeground(Color.WHITE);
		menutable.getTableHeader().setFont(new Font("���� ����", Font.BOLD, 13));
		menutable.setFocusable(false);
		menutable.setRowSelectionAllowed(true);
		menutable.setFont(new Font("���� ������ 230", Font.PLAIN, 13));
		menutable.setSelectionBackground(new Color(255,255,180));
		
		menutable.getColumnModel().getColumn(0).setPreferredWidth(180);
		menutable.getColumnModel().getColumn(1).setPreferredWidth(10);
		menutable.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		tablesort = new DefaultTableCellRenderer();
		tablesort.setHorizontalAlignment(tablesort.CENTER);
		
		menutable.getColumnModel().getColumn(0).setCellRenderer(tablesort);
		menutable.getColumnModel().getColumn(1).setCellRenderer(tablesort);
		menutable.getColumnModel().getColumn(2).setCellRenderer(tablesort);

		JScrollPane sc = new JScrollPane(menutable);
		sc.setBounds(54, 237, 397, 188);
		sc.getViewport().setBackground(Color.WHITE);
		sc.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		getContentPane().add(sc);	
	}
}

//������ �޽����� �����ϴ� Ŭ����
class DSender extends Thread {
  Socket socket;
  String msg;
  DataOutputStream out;
  String name;
 
  //������ ( �Ű������� ���ϰ� ����� �̸� �޽��ϴ�. )
  public DSender(Socket socket, String name, String msg){ //���ϰ� ����� �̸��� �޴´�.
      this.socket = socket;      
      this.msg = msg;
      try{
          out = new DataOutputStream(this.socket.getOutputStream());
          this.name = name; //�޾ƿ� ������̸��� ���������� ����, �ٸ� �޼����� run()���� ����ϱ�����.
      }catch(Exception e){
          System.out.println("����:"+e);
      }
  }
 
  @Override
  public void run(){ //run()�޼ҵ� ������
     
     // Scanner s = new Scanner(System.in);
      //Ű����κ��� �Է��� �ޱ����� ��ĳ�� ��ü ����
     
//      ������ �Է��� ������̸��� �����ش�.
//      try {
//          out.writeUTF(name);
//      } catch (IOException e) {          
//          System.out.println("����:"+e);
//      }      
          try { //while�� �ȿ� try-catch���� ����� ������ while�� ���ο��� ���ܰ� �߻��ϴ���
                //��� �ݺ��Ҽ��ְ� �ϱ����ؼ��̴�.                  
             
              //���ɾ� ��� �߰�. ( /������ , /�ӼӸ� ������̵� �����Ҹ޽��� )               
              if(msg.trim().startsWith("/")){
                 
                  //Ŭ���̾�Ʈ�ܿ��� üũ
                  if(msg.equals("/������")||msg.startsWith("/�ӼӸ�")){                    
                      out.writeUTF(msg);
                  }else{
                      System.out.println("�߸��� ���ɾ��Դϴ�.");
                      //out.writeUTF("�߸��� ���ɾ��Դϴ�.");
                  }                      
                 
                  //out.writeUTF(msg); //���ɾ� ������ ����.
                 
              }else{//���ɾ �ƴϸ� Ű����κ��� �Է¹��� ���ڿ��� ������ ������.
                  out.writeUTF(msg);               
              }              
             
          }catch(SocketException e){             
               System.out.println("����:"+e);
               System.out.println("�������� ������ ������ ���������ϴ�.");
              return;              
         } catch (IOException e) {
              System.out.println("����:"+e);
         }
   
  }//run()------
}//class Sender-------

/////////////////////////////////////////////////////////////////////