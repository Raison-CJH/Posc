package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.SocketException;
import Frame.Frame_DeliveryNotice;
import Frame.Frame_ReservationNotice;
 
public class client {
	public static Socket socket;
	public static String s_name = "19-00001";
	
	public client() {
		 try{
	            String ServerIP = "192.168.0.2";
	            socket = new Socket(ServerIP, 9190); //���� ��ü ����        
	            System.out.println("������ ������ �Ǿ����ϴ�......");
	            //����ڷκ��� ���� ���ڿ��� ������ �������ִ� ������ �ϴ� ������.
	           
	            /////////////* ����� �̸� �ߺ�üũ (�׽�Ʈ��)*///////////////
	            DataInputStream dis = new DataInputStream(socket.getInputStream());
	            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	            //Scanner s = new Scanner(System.in);  
	            while(true){
	                dos.writeUTF(s_name);
	                dos.flush();
	                String chk_code = dis.readUTF();
	                //System.out.println("chk_code=>"+chk_code);
	                if(!chk_code.equals("!errCode404")){
	                    break;
	                }
	                System.out.println("�ߺ����̵��Դϴ�.");
	            }
	           
	            /////////////////////////////////////////////////////////////
	                  
	            //�������� ������ �޽����� ������� �ֿܼ� ����ϴ� ������.
	            Thread receiver = new Receiver(socket);        
	            System.out.println("ä�ù濡 �����Ͽ����ϴ�.");
	               
	            receiver.start(); //������ �õ�
	           
	        }catch(Exception e){
	            System.out.println("����[MultiClient class]:"+e);
	        }
	}
}//End class MultiClient
 
 
/////////////////////////////////////////////////////////////////////
 
//�����κ��� �޽����� �д� Ŭ����
class Receiver extends Thread{
   
    Socket socket;
    DataInputStream in;
   
    //Socket�� �Ű������� �޴� ������.
    public Receiver(Socket socket){
        this.socket = socket;
       
        try{
            in = new DataInputStream(this.socket.getInputStream());
        }catch(Exception e){
            //System.out.println("����:"+e);
        }
    }//������ --------------------
   
    @Override
    public void run(){ //run()�޼ҵ� ������
       
        while(in!=null){ //�Է½�Ʈ���� null�� �ƴϸ�..�ݺ�
            try{        
                String msg=in.readUTF();
                
                if(msg.equals("!errCode404")){
                    System.out.println("ä�ù濡 ������ �̸��� �����մϴ�.");
                }else{
                    System.out.println(msg);
                    String order[] = msg.split("@@");
                    if(order[5].equals("r")) {
                    	 new Frame_ReservationNotice(msg);
                    }else {
                    	 new Frame_DeliveryNotice(msg);
                    }
                }
               
                //�����κ��� �о�� �����͸� �ֿܼ� ���
               
            }catch(SocketException e){             
                 System.out.println("�������� ������ ������ ���������ϴ�.");
            } catch(Exception e){              
            }
        }//while----
    }//run()------
}//class Receiver -------