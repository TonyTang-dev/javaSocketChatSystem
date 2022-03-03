package ht.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import ht.db.DBOper;

public class LookUsers extends JFrame implements ActionListener,MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Account account;
	private JLabel lblbg;//����ͼƬ


	public LookUsers(Account account)
	{	
		this.setUndecorated(true);
		this.account=account;
		init();
	
	}
	//��ʼ������
	public void init()
	{
		Icon bgimg=new ImageIcon("images/bj_hyzl.jpg");
		lblbg=new JLabel(bgimg);		
		add(lblbg);
		setResizable(false);//���ܸ��Ĵ��ڴ�С
		JLabel lblTitle=new JLabel("��������");
		lblTitle.setFont(new Font("΢���ź�",Font.BOLD,15));
		lblTitle.setBounds(5,15,150,40);
		lblbg.add(lblTitle);
		
		JLabel lblface=new JLabel(new ImageIcon(account.getFace()),JLabel.RIGHT);
		lblface.setBounds(310,70,80,80);
		
		lblbg.add(lblface);
		
		
		JLabel lblqqcode=new JLabel("��ϵ����: "+account.getQqCode(),JLabel.LEFT);
		JLabel lblnickname=new JLabel("�ǳ�: "+account.getNickName(),JLabel.LEFT);
		JLabel lblage=new JLabel("����: "+account.getAge(),JLabel.LEFT);
		JLabel lblsex=new JLabel("�Ա�: "+account.getSex(),JLabel.LEFT);
		JLabel lblnation=new JLabel("����: "+account.getNation(),JLabel.LEFT);
		JLabel lblstar=new JLabel("����: "+account.getStar(),JLabel.LEFT);
		JLabel lblremark=new JLabel("��ע: "+account.getRemark(),JLabel.LEFT);
		JLabel lblselfsign=new JLabel("����ǩ��: "+account.getSelfsign(),JLabel.LEFT);
		

		lblqqcode.setBounds(90,70,160,25);
		lblbg.add(lblqqcode);
	
	
		lblnickname.setBounds(90,110,160,25);
		lblbg.add(lblnickname);

		lblage.setBounds(90,150,160,25);
		lblbg.add(lblage);
		
		lblsex.setBounds(310,150,160,25);
		ButtonGroup bg=new ButtonGroup();//�ӵ�һ����ܵ�ѡ
		lblbg.add(lblsex);
		
	
		lblnation.setBounds(90,190,160,25);
		lblbg.add(lblnation);
	
		lblstar.setBounds(310,190,160,25);
		lblbg.add(lblstar);

		lblremark.setBounds(90,230,160,25);
		lblbg.add(lblremark);
	
		lblselfsign.setBounds(90,270,160,25);
		lblbg.add(lblselfsign);

		lblbg.addMouseListener(this);
		
		setResizable(false);
		setSize(500,440);
		setVisible(true);
		setLocationRelativeTo(null);
	}


	public void actionPerformed(ActionEvent e) {
		dispose();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==lblbg)
		{
			if(e.getClickCount()==2)
			{
				dispose();
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
	
}
