package ht.ui;

import javax.swing.JTextField;
import ht.bean.Account;
import ht.cmd.Cmd;
import ht.db.DBOper;
import ht_.Send;
import ht_.SendMsg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


public class FindUI extends JFrame implements ActionListener{

	private JTextField txtQQcode,txtNickname,txtage;
	private JComboBox cbSex;
	private JTable dataTable;
	private JButton btnFind,btnClose,btnAdd;
	private Vector vdata;
	private Vector vhead;
	private Account account;
	myTable mytable=null;
	

	public FindUI(Account account) throws IOException, ClassNotFoundException
	{
		super("���Һ���");
		
		this.account=account;
		
		JPanel topPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblqqcode=new JLabel("��ϵ����: ");
		JLabel lblnickname=new JLabel("�ǳ�: ");
		JLabel lblage=new JLabel("����: ");
		JLabel lblsex=new JLabel("�Ա�: ");
		
		txtQQcode=new JTextField(10);
		txtNickname=new JTextField(10);
		txtage=new JTextField(5);
		
		cbSex=new JComboBox(new String[] {"��","Ů"});
		btnFind=new JButton("����");
		
		topPanel.add(lblqqcode);
		topPanel.add(txtQQcode);
		
		topPanel.add(lblnickname);
		topPanel.add(txtNickname);

		topPanel.add(lblage);
		topPanel.add(txtage);
		
		topPanel.add(lblsex);
		topPanel.add(cbSex);
		topPanel.add(btnFind);
		btnFind.addActionListener(this);
		add(topPanel,BorderLayout.NORTH);
		vhead=new Vector();
		vhead.add("��ϵ����");
		vhead.add("�ǳ�");
		vhead.add("IP��ַ");
		vhead.add("�˿�");
		vhead.add("����");
		vhead.add("�Ա�");
		vhead.add("����");
		vhead.add("����");
		vhead.add("ͷ��");
		vhead.add("��ע");
		vhead.add("����ǩ��");
		vhead.add("״̬");
		
		vdata=new Vector();
		
		mytable=new myTable(vdata,vhead);
		
		dataTable=new JTable(mytable); 
		
		add(new JScrollPane(dataTable));
		
		btnClose=new JButton("�ر�");
		btnAdd=new JButton("���");
		
		JPanel bottomPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(btnAdd);
		bottomPanel.add(btnClose);
		add(bottomPanel,BorderLayout.SOUTH);
		
		btnAdd.addActionListener(this);	
		btnClose.addActionListener(this);
		
		setSize(700,400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		//���������ȡ������Ϣ
		SendMsg msg=new SendMsg();
		msg.Cmd=Cmd.CMD_SERVER_GETALL;
		msg.friendAccount=null;
		msg.selfAccount=account;
		mytable.datas=new Send().send(msg);
		dataTable.updateUI();
	}
	
	public void find(Account acc) throws IOException
	{
		vdata=new DBOper().find(acc);
		if(vdata.size()==0) {
			JOptionPane.showMessageDialog(null, "���ı���û�д˺���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		//���������
//		System.out.println(vdata.get(0).getAge());
		mytable.datas=vdata;//���ݴ�������
		dataTable.updateUI();//ˢ�½���
	}
	
	
	//����JTable�ؼ���ֵ
	class myTable extends AbstractTableModel
	{	
		
		Vector datas=new Vector();
		Vector columns=new Vector();
		//���캯������ѱ�ͷ�����ݴ�����
		public myTable(Vector datas,Vector columns)
		{
			this.datas= datas;
			this.columns= columns;
		}
		//��ȡ����
		public int getColumnCount()
		{
			return columns.size();
		}
		//��ȡ����
		public String getColumnName(int columnIndex)
		{
			return columns.get(columnIndex).toString();
		}
				
		//��ȡ����
		public int getRowCount()
		{
			return datas.size();
		}
		
		
		//ÿһ��Ҫ��ʾ����������
		public Object getValueAt(int rowIndex,int columnIndex)
		{
			Vector v=(Vector)datas.get(rowIndex);//ȡ����rowindex��account����
			if(columnIndex==6)//ͷ���з���ͼƬ
				return new ImageIcon(v.get(columnIndex).toString());
			else 
			{
				return v.get(columnIndex);//��ȡ��columnindex������
			}
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==btnFind)
		{
			Account acc=new Account();
			if(!txtQQcode.getText().equals(""))
			{
				acc.setQqCode(Integer.parseInt(txtQQcode.getText().trim()));
			}
			
			if(!txtNickname.getText().equals(""))
			{
				acc.setNickName(txtNickname.getText().trim());
			}
			
			if(!txtage.getText().equals(""))
			{
				acc.setAge(Integer.parseInt(txtage.getText().trim()));
			}
			
			acc.setSex(cbSex.getSelectedItem().toString());
			
			try {
				find(acc);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		}
		else if(e.getSource()==btnAdd)
		{
			int row,col;
			row=dataTable.getSelectedRow();//�õ�ѡ����к�
			col=dataTable.getSelectedColumn();//�õ�ѡ����к�
			if(row==-1) {
				JOptionPane.showMessageDialog(null, "����ѡ��Ҫ��ӵĺ���Ŷ", "��ʾ", JOptionPane.PLAIN_MESSAGE);
				return;//���û��ѡ��ֱ���˳�����ʾ
			}
			
			Vector<String> rowData=(Vector<String>)mytable.datas.get(row);
			
			//��ȡ��һ�е�code
			int qqcode=Integer.parseInt(rowData.get(0));

			Account acc=new Account();
//				acc=mytable.datas[row];
			acc.setQqCode(qqcode);
			acc.setAge(Integer.parseInt(rowData.get(4)));
			acc.setFace(rowData.get(8));
			acc.setIpAddr(rowData.get(2));
			acc.setPort(Integer.parseInt(rowData.get(3)));
			acc.setGroupname("����");
			acc.setSex(rowData.get(5));
			acc.setNickName(rowData.get(1));
			acc.setNation(rowData.get(6));
			acc.setStatus(Integer.parseInt(rowData.get(11)));
			acc.setRemark("1");
			acc.setPwd("1");
			acc.setStar("a");
			//acc = new DBOper().findByQQcode(qqcode);
		
			SendMsg msg=new SendMsg();
			msg.Cmd=Cmd.CMD_ADDFRIEND;
			msg.friendAccount=acc;
			msg.selfAccount=account;
			new Send().send(msg);
		}
		else if(e.getSource()==btnClose)
		{
			dispose();
		}
	}
	
}
