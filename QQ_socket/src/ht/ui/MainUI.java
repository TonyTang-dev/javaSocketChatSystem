package ht.ui;
//������
import ht.bean.Account;
import ht.cmd.Cmd;
import ht.db.DBOper;
import ht_.Send;
import ht_.SendMsg;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;
 
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
//�����߳� ����ѭ���ȵ�������Ϣ
public class MainUI extends JFrame implements MouseListener,ActionListener{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Account account,friendAccount;
	private JTabbedPane tab;
	private JLabel lblhead;//��½����ʾ�ĸ�������
	private JList<?> lstFriend;
	private JList<?> lstFamily;
	private JList<?> lstclassmate;
	private JList<?> lsthmd;
	private JButton btnFind;//���Һ��Ѱ�ť
	private Vector<Account> vFriend,vFamily,vClassmate,vHmd,vAllDetail;
	private JPopupMenu pop;//�����˵�
	private JMenu menu;
	private JMenuItem miChat,miLookInfo,miFriend,miFamily,miMate,miHmd,miDel;//���� �鿴���� ɾ������ �ƶ�����
	//������ϣ�������������û��Ĵ���
	private LinkedHashMap<Integer,ChatUI> ht_ChatUsers;
	
	
	public MainUI()
	{
		
		
	}
	public MainUI(Account acc) throws IOException
	{
		this.account=acc;
		
		setTitle(acc.getNickName());//���ô��ڱ�ǩ�������ͷ��
		setIconImage(new ImageIcon(acc.getFace()).getImage());
		//��ȡ�ǳ� ��ע QQ�� ����ǩ��
		String str="";
		//��ע��Ϊ��ʱ����ʾ��ע����ʾ�ǳ�
		if(!acc.getRemark().equals(""))
		{
			str=acc.getRemark()+"(";
		}
		//��עΪ��ʱ����ʾ�ǳ�
		else {
			str=acc.getNickName()+"(";
		}
		str+=acc.getQqCode()+")"+acc.getSelfsign();	
		lblhead=new JLabel(str,new ImageIcon(acc.getFace()),JLabel.LEFT);
		add(lblhead,BorderLayout.NORTH);
		
		vFriend=new Vector<Account>();
		vFamily=new Vector<Account>();
		vClassmate=new Vector<Account>();
		vHmd=new Vector<Account>();
		vAllDetail=new Vector<Account>();
		
		
		lstFamily=new JList();
		lstFriend=new JList();
		lstclassmate=new JList();
		lsthmd=new JList();
		
		lstFriend.addMouseListener(this);
		lstFamily.addMouseListener(this);
		lstclassmate.addMouseListener(this);
		lsthmd.addMouseListener(this);
		
		
		
		refresh();//�������б�
		
		tab=new JTabbedPane();
		tab.add("����",new JScrollPane(lstFriend));
		tab.add("����",new JScrollPane(lstFamily));
		tab.add("ͬѧ",new JScrollPane(lstclassmate));
		tab.add("����",new JScrollPane(lsthmd));
		add(tab);
		createMenu();
		btnFind=new JButton("����/��Ӻ���");
		add(btnFind,BorderLayout.SOUTH);
 
		btnFind.addActionListener(this);
		setSize(300,680);
		setVisible(true);
		setResizable(true);
		//�õ���Ļ���,���ô���λ��
		int width=Toolkit.getDefaultToolkit().getScreenSize().width-300;
		setLocation(width,50);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//������ر�
		this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
               //super.windowClosing(e);    //To change body of overridden methods use File | Settings | File Templates.
               //����д����ʵ�ֵĴ��룬��ֻд��һ��println
                System.exit(0);
            }
        });
		
		//������Ϣ�����߳�
		new ReceThread().start();
	}
	
	
	class listmodel extends AbstractListModel
	{
		Vector dats;
		public listmodel(Vector dats)
		{
			this.dats=dats;
		}
		//��ȡ����
		public Object getElementAt(int index)
		{
			Account user=(Account) dats.get(index);
			return user.getNickName().trim()+"��"+user.getQqCode()+"��";
		}
		//��ȡ����
		public int getSize()
		{
			return dats.size();
		}
		
		
	}
	
	//��ȡ����ͷ��
	class myfind extends DefaultListCellRenderer
	{
		Vector datas;
		public myfind(Vector datas)
		{
			this.datas=datas;
		}
		
		public Component getListCellRendererComponent(JList list,Object value, int index, boolean isSelected,boolean cellHasFocus)
		{
			Component c=super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
			
			if(index >=0 && index<datas.size())
			{
			
			Account user=(Account) datas.get(index);
			//�����б��еĺ�������״̬����ͷ��
			if(user.getStatus()==1)
			{
				setIcon(new ImageIcon(user.getFace()));
			}
			else
			{
				setIcon(new ImageIcon("face/bzx.png"));
			}
			setText(user.getNickName().trim()+"("+user.getQqCode()+")");
			}
			
			
			//����������ɫ
			if(isSelected)
			{
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}
			else
			{
				setBackground(list.getBackground());
				setForeground(list.getForeground());
				
			}
			
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			return this;
		}
		
		
	}
	
	//�����˵�
	public void createMenu()
	{
		pop=new JPopupMenu();
		miChat=new JMenuItem("����");
		miLookInfo=new JMenuItem("�鿴����");
		miFriend=new JMenuItem("�ƶ�������");
		miFamily=new JMenuItem("�ƶ�������");
		miMate=new JMenuItem("�ƶ���ͬѧ");
		miHmd=new JMenuItem("�ƶ���������");
		miDel=new JMenuItem("ɾ������");
		
		miChat.addActionListener(this);
		miLookInfo.addActionListener(this);
		miFriend.addActionListener(this);
		miFamily.addActionListener(this);
		miMate.addActionListener(this);
		miHmd.addActionListener(this);
		miDel.addActionListener(this);
		
		pop.add(miChat);
		pop.add(miLookInfo);
		pop.add(miFriend);
		pop.add(miFamily);
		pop.add(miMate);
		pop.add(miHmd);
		pop.add(miDel);
	}
	
	//ˢ�½���
	public void refresh() throws IOException
	{
		vAllDetail=new DBOper().getAllInfo(account);
		//���û���û�������£�ֱ�ӽ�������
		if(vAllDetail==null) {
			//���֮ǰ���еļ�¼
			vFriend.clear();
			vFamily.clear();
			vClassmate.clear();
			vHmd.clear();
			return;
		}
		
		
		//���֮ǰ���еļ�¼
		vFriend.clear();
		vFamily.clear();
		vClassmate.clear();
		vHmd.clear();
		//�����ݿ���������ݷֱ�ŵ���Ӧ������
		for(int i=0;i<vAllDetail.size();i++)
		{
			Account a=vAllDetail.get(i);
			if(a.getGroupname().equals(Cmd.F_FRIEND))
			{
				vFriend.add(a);
			}
			else if(a.getGroupname().equals(Cmd.F_FAMILY))
			{
				vFamily.add(a);
			}
			else if(a.getGroupname().equals(Cmd.F_CLASSMATE))
			{
				vClassmate.add(a);
			}
			else if(a.getGroupname().equals(Cmd.F_HMD))
			{
				vHmd.add(a);
			}
			
			//��ʱδ������ֱ����ʾ�ں�����
			vFriend.add(a);
			
		}
		
		//����������List�ؼ�
		lstFriend.setModel(new listmodel(vFriend));//��ʾ����
		lstFriend.setCellRenderer(new myfind(vFriend));//��ʾͷ��
		lstFamily.setModel(new listmodel(vFamily));//��ʾ����
		lstFamily.setCellRenderer(new myfind(vFamily));//��ʾͷ��
		lstclassmate.setModel(new listmodel(vClassmate));//��ʾ����
		lstclassmate.setCellRenderer(new myfind(vClassmate));//��ʾͷ��
		lsthmd.setModel(new listmodel(vHmd));//��ʾ����
		lsthmd.setCellRenderer(new myfind(vHmd));//��ʾͷ��
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==lstFriend)
		{
			if(lstFriend.getSelectedIndex()==-1) {
				JOptionPane.showMessageDialog(null, "��ǰ�б��޺���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			friendAccount=(Account)vFriend.get(lstFriend.getSelectedIndex());
			//˫��
			if(e.getClickCount()==2)
			{
			//	new ChatUI(account,friendAccount);
				findWin(friendAccount.getQqCode(),null);
				
			}
			//�ʼ�
			if(e.getButton()==3)
			{
				//���ѱ�ѡ��
				if(lstFriend.getSelectedIndex()>=0)
				{
					pop.show(lstFriend, e.getX(), e.getY());
				}
			}
		}
		else if(e.getSource()==lstFamily)
		{
			if(lstFamily.getSelectedIndex()==-1) {
				JOptionPane.showMessageDialog(null, "��ǰ�б��޺���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			friendAccount=(Account)vFamily.get(lstFamily.getSelectedIndex());
			//˫��
			if(e.getClickCount()==2)
			{
 
				//new ChatUI(account,friendAccount);
				findWin(friendAccount.getQqCode(),null);
			}
			//�ʼ�
			if(e.getButton()==3)
			{
				//��ѡ��
				if(lstFamily.getSelectedIndex()>=0)
				{
					pop.show(lstFamily, e.getX(), e.getY());
				}
			}
		}
		else if(e.getSource()==lstclassmate)
		{
			if(lstclassmate.getSelectedIndex()==-1) {
				JOptionPane.showMessageDialog(null, "��ǰ�б��޺���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			friendAccount=(Account)vClassmate.get(lstclassmate.getSelectedIndex());
			//˫��
			if(e.getClickCount()==2)
			{
 
			//	new ChatUI(account,friendAccount);
				findWin(friendAccount.getQqCode(),null);
			}
			//�ʼ�
			if(e.getButton()==3)
			{
				//��ѡ��
				if(lstclassmate.getSelectedIndex()>=0)
				{
					pop.show(lstclassmate, e.getX(), e.getY());
				}
			}
		}
		else if(e.getSource()==lsthmd)
		{
			if(lsthmd.getSelectedIndex()==-1) {
				JOptionPane.showMessageDialog(null, "��ǰ�б��޺���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			friendAccount=(Account)vHmd.get(lsthmd.getSelectedIndex());
			//˫��
			if(e.getClickCount()==2)
			{
				//new ChatUI(account,friendAccount);
				findWin(friendAccount.getQqCode(),null);
			}
			//�һ�
			if(e.getButton()==3)
			{
	
				//��ѡ��
				if(lsthmd.getSelectedIndex()>=0)
				{
					pop.show(lsthmd, e.getX(), e.getY());
				}
			}
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnFind)
		{
			try {
				new FindUI(account);
			} catch (IOException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block 
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==miChat)
		{
	//		new ChatUI(account,friendAccount);
			findWin(friendAccount.getQqCode(),null);
		}
		else if(e.getSource()==miLookInfo)
		{
			
			if(friendAccount!=null)			
			new LookUsers(friendAccount);
		}
		else if(e.getSource()==miDel)
		{
			try {
				new DBOper().delFriend(account, friendAccount.getQqCode());
				refresh();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				refresh();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==miFriend)
		{
			try {
				new DBOper().moveFriend(account, friendAccount.getQqCode(),Cmd.F_FRIEND);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				refresh();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==miFamily)
		{
			try {
				new DBOper().moveFriend(account, friendAccount.getQqCode(),Cmd.F_FAMILY);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				refresh();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==miMate)
		{
			try {
				new DBOper().moveFriend(account, friendAccount.getQqCode(),Cmd.F_CLASSMATE);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				refresh();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==miHmd)
		{
			
		}
		
	}
	
	
	//������Ϣ���߳�
	class ReceThread extends Thread
	{
		public ReceThread()
		{
			//�����߳�ʱ �������洰�ڵĹ�ϣ��
			ht_ChatUsers=new LinkedHashMap<Integer,ChatUI>();
		}
		public void run()
		{
			
			try 
			{
				
				//�Լ��˿ڽ�������
				DatagramSocket serverSocket=new DatagramSocket(account.getPort());//���Լ��˿ڽ�������
				
				//���տͻ��˷�������Ϣ����pack��
				while(true)
				{
				byte b[]=new byte[1024*70];	
				DatagramPacket pack=new DatagramPacket(b,b.length) ;//���ݰ�
				serverSocket.receive(pack);
				
				//���ֽ�����ת����SendMsg����
				//pack.getLength()���յ����ֽ���
				ByteArrayInputStream bis=new ByteArrayInputStream(b,0,pack.getLength());//�ֽ�����������
				ObjectInputStream ois=new ObjectInputStream(bis);
				SendMsg msg=(SendMsg)ois.readObject();
				
				switch(msg.Cmd)
				{
					
					case Cmd.CMD_ONLINE://��������֪ͨ
						refresh();
						break;
					case Cmd.CMD_OFFLINE://��������֪ͨ
						refresh();
						break;
					case Cmd.CMD_CHAT://����������Ϣ
						
						ChatUI chat=findWin(msg.selfAccount.getQqCode(),msg);//��ʾ����
						//��ȡ������Ϣ
						chat.appendView(msg.selfAccount.getNickName(),msg.doc);
						
						break;
					case Cmd.CMD_DOUDONG://���ն�����Ϣ
						chat=findWin(msg.selfAccount.getQqCode(),msg);//��ʾ����
						chat.shake();
					break;
						
					case Cmd.CMD_ADDFRIEND://��Ӻ���
						String str=msg.selfAccount.getNickName()+"�����Ϊ���� ����ȷ��";
					
						SendMsg m=new SendMsg();
						m.friendAccount=msg.selfAccount;
						m.selfAccount=msg.friendAccount;
						
						//������ȷ����ť�����
						if(JOptionPane.showConfirmDialog(null,str,"��Ӻ���",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
						{//�����ѱ�����Ӽ�¼
							
							new DBOper().addFriend(m.friendAccount, m.selfAccount.getQqCode());
							refresh();
							m.Cmd=Cmd.CMD_AFREEFRIEND;
						}else
						{
							m.Cmd=Cmd.CMD_REJECTFRIEND;
						}
						
					break;
						
				
					case Cmd.CMD_AFREEFRIEND://ͬ��
						refresh();//ˢ�½���
						break;
					
					case Cmd.CMD_REJECTFRIEND://�ܾ�
						JOptionPane.showMessageDialog(null,msg.selfAccount.getNickName()+"�ܽ�����ĺ�������");
						break;	
					
					
					case Cmd.CMD_FILESEND://�����ļ�
						String str1=msg.selfAccount.getNickName()+"�������ļ�"+msg.sFileName;
						
						int cmd=Cmd.CMD_FILESUCC;
						if(JOptionPane.showConfirmDialog(null,str1,"�����ļ�",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
						{
							FileDialog dlg=new FileDialog(MainUI.this,"����",FileDialog.SAVE);
							dlg.setFile(msg.sFileName);
							dlg.show();
							String sfilename=dlg.getDirectory()+"\\"+dlg.getFile();
							File file=new File(sfilename);
							if(!file.exists())
							{
								file.createNewFile();
								
							}
							FileOutputStream fos=new FileOutputStream(sfilename);
							fos.write(msg.b);
							fos.close();
							
						}else
						{
							cmd=Cmd.CMD_FILEFAILED;
						}
						SendMsg msg1=new SendMsg();
						msg1.selfAccount=msg.friendAccount;
						msg1.friendAccount=msg.selfAccount;
						msg1.Cmd=cmd;
						new Send().send(msg1);
						
						break;
						
					case Cmd.CMD_FILESUCC://�ظ����ܳɹ�
						JOptionPane.showMessageDialog(null,msg.selfAccount.getNickName()+"�������ļ�");
						break;
						
					case Cmd.CMD_FILEFAILED://�ظ��ܽ�
						
						JOptionPane.showMessageDialog(null,msg.selfAccount.getNickName()+"�ܾ����ļ�");
						break;
				}
				
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			
		}
		
	}
		
	//���Ҵ����Ƿ����,����������򴴽�,������ֱ����ʾ��Ϣ�ڽ���
			public ChatUI findWin(Integer qqcode,SendMsg msg)
			{
				ChatUI chat=null;
				//���Ҵ����Ƿ����
				chat =ht_ChatUsers.get(qqcode);
				if(chat==null)//�������򴴽����촰��
				{
					if(msg==null)//˫�������Ҽ��򿪴���
					{
						chat=new ChatUI(account,friendAccount);
					}else//�̴߳򿪴���
					{
						chat=new ChatUI(msg.friendAccount,msg.selfAccount);
					}
					
					//���ڼ����ϣ��
					ht_ChatUsers.put(qqcode,chat);										
				}
								
				if(!chat.isVisible())
				{
					chat.show();
				}
				return chat;				
			}
			
	//�޸��û�״̬
	public void UpdateStatus(Account acc) throws IOException
	{
		int size=vAllDetail.size();
		for(int i=0;i<size;i++)
		{
			Account a=vAllDetail.get(i);
			if(a.getQqCode()==acc.getQqCode())
			{
				a.setStatus(acc.getStatus());
				vAllDetail.set(i, a);
				break;
			}
		}
		refresh();
	}
}