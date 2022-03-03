package ht.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ht.bean.Account;
import ht.cmd.Cmd;
import ht.db.DBOper;
import ht_.Send;
import ht_.SendMsg;

public class RegUsers extends JFrame implements ActionListener{

	private Account account;
	private JLabel lblbg;//����ͼƬ
	private JComboBox cbface,nation,star;
	private JTextField qqCode,nickName,port,age,remark,ipAddr;
	private JRadioButton sex1,sex2;
	private JTextArea selfsign;
	private JPasswordField pwd,cfgpwd;
	private JButton btnSave,btnClose;
	private String nations[] = {"�л�����","��������"};
	private String stars[] = {"������","ʨ����"};
	private String[] faces= {
			"face/0.jpg",
			"face/1.jpg",
			"face/2.jpg",
			"face/3.jpg",
			"face/4.jpg",
			"face/5.jpg",
			"face/6.jpg",
	};
	
	public RegUsers()
	{
		this.setUndecorated(true);
		init();
	}
	public RegUsers(Account account)
	{	
		this.setUndecorated(true);
		this.account=account;
		init();
	
	}
	//��ʼ������ ����ͼƬ����label�� labelֱ����Ϊ����  �������������ڸ�label��
	public void init()
	{
		Icon bgimg=new ImageIcon("images/bgx.jpg");
		lblbg=new JLabel(bgimg);		
		add(lblbg);
		setResizable(false);//���ܸ��Ĵ��ڴ�С
		JLabel lblTitle=new JLabel("��������");
		lblTitle.setFont(new Font("΢���ź�",Font.BOLD,20));
		lblTitle.setBounds(5,25,150,40);
		lblbg.add(lblTitle);
		int i=0;
		Icon[] face= {
				new ImageIcon(faces[i++]),
				new ImageIcon(faces[i++]),
				new ImageIcon(faces[i++]),
				new ImageIcon(faces[i++]),
				new ImageIcon(faces[i++]),
				new ImageIcon(faces[i++]),
				new ImageIcon(faces[i++]),
		};
		
		cbface=new JComboBox(face);
		cbface.setMaximumRowCount(5);
		JLabel lblface=new JLabel("ͷ��:",JLabel.RIGHT);
		lblface.setBounds(270,70,60,25);
		cbface.setBounds(330,70,80,60);
		lblbg.add(lblface);
		lblbg.add(cbface);
		
		JLabel lblqqcode=new JLabel("��ϵ����: ",JLabel.RIGHT);
		JLabel lblnickname=new JLabel("�ǳ�: ",JLabel.RIGHT);
		JLabel lblpwd=new JLabel("��¼����: ",JLabel.RIGHT);
		JLabel lblcfgpwd=new JLabel("ȷ������: ",JLabel.RIGHT);
		JLabel lblipAddr=new JLabel("������ַ: ",JLabel.RIGHT);
		JLabel lblport=new JLabel("���Ӷ˿�: ",JLabel.RIGHT);
		JLabel lblage=new JLabel("����: ",JLabel.RIGHT);
		JLabel lblsex=new JLabel("�Ա�: ",JLabel.RIGHT);
		JLabel lblnation=new JLabel("����: ",JLabel.RIGHT);
		JLabel lblstar=new JLabel("����: ",JLabel.RIGHT);
		JLabel lblremark=new JLabel("��ע: ",JLabel.RIGHT);
		JLabel lblselfsign=new JLabel("����ǩ��: ",JLabel.RIGHT);
		
		qqCode=new JTextField(15);
		lblqqcode.setBounds(10,70,100,25);
		qqCode.setBounds(110,70,100,25);
		lblbg.add(lblqqcode);
		lblbg.add(qqCode);
		
		nickName=new JTextField(15);
		lblnickname.setBounds(10,110,100,25);
		nickName.setBounds(110,110,100,25);
		lblbg.add(lblnickname);
		lblbg.add(nickName);
		
		pwd=new JPasswordField(15);
		cfgpwd=new JPasswordField(15);
		lblpwd.setBounds(50,150,60,25);
		lblcfgpwd.setBounds(270,150,60,25);
		pwd.setBounds(110,150,100,25);
		cfgpwd.setBounds(330,150,100,25);
		lblbg.add(lblpwd);
		lblbg.add(pwd);
		lblbg.add(lblcfgpwd);
		lblbg.add(cfgpwd);
		//��ȡIP��ַ
		InetAddress addr=null;
		try {
			addr=InetAddress.getLocalHost();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		ipAddr=new JTextField(addr.getHostAddress());
		lblipAddr.setBounds(50,230,60,25);
		ipAddr.setBounds(110,230,100,25);
		lblbg.add(lblipAddr);
		lblbg.add(ipAddr);
		
		port=new JTextField();
		lblport.setBounds(270,190,60,25);
		port.setBounds(330,190,60,25);
		lblbg.add(lblport);
		lblbg.add(port);
		
		age=new JTextField("21");
		lblage.setBounds(50,190,60,25);
		age.setBounds(110,190,100,25);
		lblbg.add(lblage);
		lblbg.add(age);
		
		sex1=new JRadioButton("��");
		sex2=new JRadioButton("Ů");
		sex1.setSelected(true);
		lblsex.setBounds(270,230,60,25);
		sex1.setBounds(330,230,60,25);
		sex2.setBounds(390,230,60,25);
		ButtonGroup bg=new ButtonGroup();//�ӵ�һ����ܵ�ѡ
		bg.add(sex1);
		bg.add(sex2);
		lblbg.add(lblsex);
		lblbg.add(sex1);
		lblbg.add(sex2);
		
		nation =new JComboBox(nations);
		lblnation.setBounds(50,270,60,25);
		nation.setBounds(110,270,100,25);
		lblbg.add(lblnation);
		lblbg.add(nation);
		
		
		star =new JComboBox(stars);
		lblstar.setBounds(270,270,60,25);
		star.setBounds(330,270,100,25);
		lblbg.add(lblstar);
		lblbg.add(star);
		
		remark =new JTextField();
		lblremark.setBounds(50,310,60,25);
		remark.setBounds(110,310,320,25);
		lblbg.add(lblremark);
		lblbg.add(remark);
		
		selfsign =new JTextArea();
		lblselfsign.setBounds(50,350,60,25);
		selfsign.setBounds(110,350,320,25);
		lblbg.add(lblselfsign);
		lblbg.add(selfsign);
		
		btnSave=new JButton("����");
		btnClose=new JButton("�ر�");
		btnSave.addActionListener(this);
		btnClose.addActionListener(this);
		btnSave.setBounds(170,450,80,30);
		btnClose.setBounds(270,450,80,30);
		lblbg.add(btnSave);
		lblbg.add(btnClose);
		
		
		
		
		
		
		
		setResizable(false);
		setSize(500,600);
		setVisible(true);
		setLocationRelativeTo(null);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);�˳�����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//�رյ�ǰ���� ���ر���������
		
	}
//	public static void main (String[] args)
//	{
		
//		new RegUsers();
		
		
		
		
//	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnClose)
		{
		//	System.exit(0);//�˳�ϵͳ
			dispose();//�رմ���
		}
		
		if(e.getSource()==btnSave)
		{
			Account account=new Account();
			try {
				
				Integer.parseInt(qqCode.getText().trim());
				
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "QQ�������� ����Ϊ�Ϸ�����");
				return;
			}
			//��֤�����ȷ�������Ƿ�һ��
			if(pwd.getText().trim()=="")
			{
				JOptionPane.showMessageDialog(null,"����������");
				return;
			}
			if(!pwd.getText().equals(cfgpwd.getText()))
			{
				JOptionPane.showMessageDialog(null,"��¼������ȷ�����벻һ��");
				return;
			}
			
			int nport=0;
			try
			{
				nport=Integer.parseInt(port.getText().trim());
				
				
			}catch(Exception ex)
			{
				JOptionPane.showInputDialog(null, "�˿ں��зǷ��ַ�");
				return;
			}
			if(nport<1024 || nport>65535)
			{
				JOptionPane.showInputDialog(null, "�˿ڱ�����1024--65535֮��");
				return;
				
			}
			
			int nage=0;
			try
			{
				nage=Integer.parseInt(age.getText().trim());
				
				
			}catch(Exception ex)
			{
				JOptionPane.showInputDialog(null, "���京�зǷ��ַ�");
				return;
			}
			if(nage<1 || nage>150)
			{
				JOptionPane.showInputDialog(null, "���䳬��Χ");
				return;
				
			}
			//����ֵ���õ�������
			account.setQqCode(Integer.parseInt(qqCode.getText().trim()));
			account.setNickName(nickName.getText().trim());
			account.setPwd(pwd.getText().trim());
			account.setIpAddr(ipAddr.getText().trim());
			account.setPort(Integer.parseInt(port.getText().trim()));
			account.setFace(faces[cbface.getSelectedIndex()]);
			account.setAge(Integer.parseInt(age.getText().trim()));
			if(sex1.isSelected())
			{
				account.setSex("��");
			}
			else
			{
				account.setSex("Ů");
			}
			account.setNation(nations[nation.getSelectedIndex()]);
			account.setStar(stars[star.getSelectedIndex()]);
			account.setRemark(remark.getText().trim());
			account.setSelfsign(selfsign.getText().trim());
			account.setStatus(0);
			
			DBOper db=new DBOper();
			//�ж϶˿��Ƿ�ռ��
			boolean a=false;
			try {
				a = db.isPort(account.getPort());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(a)
			{
				JOptionPane.showMessageDialog(null, "�˿��Ѿ���ռ�� �����������˿�");
				port.requestFocus();//��ȡ����
			}
			else
			{
				
			boolean b=false;
			try {
				b = db.addUser(account);
				SendMsg msg=new SendMsg();
				msg.Cmd=Cmd.CMD_SERVER;
				msg.friendAccount=null;
				msg.selfAccount=account;
				new Send().send(msg);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(b)
			{
				
				JOptionPane.showMessageDialog(null, "ע��ɹ������סqq����");
				
			}else {
				
				JOptionPane.showMessageDialog(null, "ע��ʧ��");
				
			}
			
			}
		}
		
	}
	
	
	
	
}
