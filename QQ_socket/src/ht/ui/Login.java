package ht.ui;

import ht.bean.Account;
import ht.db.DBOper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Login extends JFrame implements ActionListener,ItemListener,MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JComboBox<?> cbqqCode;//������
	JPasswordField txtpwd;
	JLabel lblface,lblfindpwd,lblReg,lblhead;
	JButton btnReg,btnLogin,btnClose,exitLogin;
	JCheckBox cbmemory,cbautologin; 
	
	public Login()
	{	
	//	super("QQ2017");

		this.setUndecorated(true);//�����߿�
		Image icon =new ImageIcon("face/ico.png").getImage();	
		setIconImage(icon);
		lblhead=new JLabel(new ImageIcon("images/login.jpg"));
		add(lblhead);
		
		//��ȡ֮ǰ��¼��QQ���� ����������
		String qqcodes[]=readQQCode();
		if(qqcodes!=null)
		{
			cbqqCode=new JComboBox(qqcodes);
			cbqqCode.setSelectedIndex(0);
		}else
		{
			cbqqCode=new JComboBox();
		}
		
		
		
		txtpwd=new JPasswordField();
		lblfindpwd=new JLabel("�һ�����");
		lblReg=new JLabel("ע���û�");
		
		cbmemory=new JCheckBox("��ס����");
		cbautologin=new JCheckBox("�Զ���¼");
		
//		btnReg=new JButton("ע��");
		
		cbqqCode.setBounds(120, 190, 182, 35);
		cbqqCode.setEditable(true);
		
		
		lblReg.setBounds(320, 190, 182, 30);
		lblReg.setForeground(Color.BLUE);
		lblReg.setFont(new Font("΢���ź�",Font.PLAIN,12));	
		txtpwd=new JPasswordField("");
		txtpwd.setBounds(120, 230, 182, 30);
		
		lblfindpwd.setBounds(320, 230, 182, 30);
		lblfindpwd.setForeground(Color.BLUE);
		lblfindpwd.setFont(new Font("΢���ź�",Font.PLAIN,12));
		lblfindpwd.addMouseListener(this);
		cbmemory.setBounds(130, 260, 80, 30);
		cbmemory.setForeground(Color.BLUE);
		cbmemory.setFont(new Font("΢���ź�",Font.PLAIN,12));
		cbautologin.setBounds(210, 260, 80, 30);
		cbautologin.setForeground(Color.BLUE);
		cbautologin.setFont(new Font("΢���ź�",Font.PLAIN,12));
		btnLogin=new JButton("��¼");
		btnLogin.setBounds(130, 290, 75, 30);
		exitLogin=new JButton("�˳�");
		exitLogin.setBounds(215, 290, 75, 30);
		
		
		
		lblhead.add(cbqqCode);
		lblhead.add(lblReg);
		lblhead.add(txtpwd);
		lblhead.add(lblfindpwd);
		lblhead.add(cbmemory);
		lblhead.add(cbautologin);
		lblhead.add(btnLogin);
		lblhead.add(exitLogin);
		lblface=new JLabel(new ImageIcon("face/ico.png"));
		lblface.setBounds(30, 190, 60, 60);
		lblhead.add(lblface);
		
		btnLogin.addActionListener(this);
		exitLogin.addActionListener(this);
		cbmemory.addItemListener(this);
		cbmemory.setBackground(Color.white);
		cbautologin.addItemListener(this);
		cbautologin.setBackground(Color.white);
		cbqqCode.addItemListener(this);
		lblfindpwd.addMouseListener(this);
		lblReg.addMouseListener(this);
		
//		lblhead.addMouseListener(this);
		
		
		setSize(428,325);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	
	public static void main(String[] args)
	{
		new Login();
	}


	public void actionPerformed(ActionEvent e) {
		//����ǵ�����˳���ť���˳���������
		if(e.getSource()==exitLogin){
			this.dispose();
			System.exit(0);
		}
		
		//�ѽ����ϵ�ֵ���ø�javaBean
		String qqcode=cbqqCode.getSelectedItem().toString();
		String pwds=txtpwd.getText().trim();
		
		if(qqcode.equals("") || pwds.equals("")) {
			JOptionPane.showMessageDialog(null, "�˺����벻��ȷ", "��ʾ", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		Account acc=new Account();
		acc.setQqCode(Integer.parseInt(qqcode));
		acc.setPwd(pwds);
		//��¼
		try {
			acc=new DBOper().login(acc);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(acc==null) {
			JOptionPane.showMessageDialog(null, "����ע���˺��ٵ�¼Ŷ", "��ʾ", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		//��¼�ɹ�
		if(acc.getStatus()==1)
		{
			Icon ico=new ImageIcon(acc.getFace());//��ȡͷ��
			lblface.setIcon(ico);//����ͷ��Ϊ�õ�¼�û���ͷ�񣨵�¼ʱ���Ѵ����ݿ�ȡ��ͷ����������account�����У�
			
			saveQQCode(acc.getQqCode()+"");//�ѵ�¼�ɹ���qq�ű�����account.dat�ļ���
	//		JOptionPane.showMessageDialog(null, "��¼�ɹ�");
			
			try {
				new MainUI(acc);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.dispose();//�رյ�¼���� ��ʾ������
		}
		else {
			JOptionPane.showMessageDialog(null, "�˺Ż�������ִ���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
			return;
		}

	}

	//���������ݸı�ʱ�����統qq������������д��qq����ı�ʱ��
	public void itemStateChanged(ItemEvent e) {
	

		if(e.getSource()==cbqqCode)//����ͷ��
		{
			String qqcode=cbqqCode.getSelectedItem().toString();//�õ�������qq��
			String face="";
			try {
				face = new DBOper().getFace(Integer.parseInt(qqcode));
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//�����ݿ�õ�qq��Ӧͷ��
			lblface.setIcon(new ImageIcon(face));//�ı�ͷ����е�ͼ��
		}
	}


	public void mouseClicked(MouseEvent e) {
		
		//��ע���û���ǩ�ϵ����� ��������¼�����⵽  ע���û�
		if(e.getSource()==lblReg)
		{
			new RegUsers();
		}
		
		if(e.getSource()==lblfindpwd) {
			JOptionPane.showMessageDialog(null, "ûʵ��������ܣ���Ҫ��", "��ʾ", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		//�������� �ر����
//		if(e.getSource()==lblhead)
//		{
//			if(e.getClickCount()==2)
//			{
//				this.dispose();
//				System.exit(0);
//			}
//		}
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

	//��������¼����QQ�˺�
	public String[] readQQCode()
	{
		String[] qqcodes=null;
		File file=new File("account.dat");
		try
		{
			
			//�ļ�������
			if(!file.exists())
			{
				file.createNewFile();
			}
		
			BufferedReader br=new BufferedReader(new FileReader(file));//FileReader���ֽ���ת��Ϊ�ַ���
			String str=br.readLine();
			br.close();
			//�ļ�����ת��Ϊ����
			if(str==null)
				return null;
			qqcodes=str.split(",");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return qqcodes;
	}
	
	
	
	//��������QQ�˺ŵ��ļ������¼���е�֮ǰ��½���ĵ�QQ��
	public void saveQQCode(String qqcode)
	{
		
		File file=new File("account.dat");
		try
		{
		
			
			//�ļ�����ת��Ϊ����
			String[] qqcodes=readQQCode();
			//�ü�  ����Ԫ���ظ�
			Set set=new HashSet();
			String str="";
			if(qqcodes!=null)
			{				
				
				
				for(int i=0;i<qqcodes.length;i++)
				{
					set.add(qqcodes[i]);
				}
				set.add(qqcode);
			
				Iterator it=set.iterator();
				
				while(it.hasNext())
				{
					str+=it.next()+",";
				
				
				}
			}else {
				str=qqcode+"";
				
			}
			//������ļ�
			File f=new File("account.dat");
			BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			bw.write(str);
			bw.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}



}
