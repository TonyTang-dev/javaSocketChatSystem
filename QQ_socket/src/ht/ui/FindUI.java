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
		super("查找好友");
		
		this.account=account;
		
		JPanel topPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblqqcode=new JLabel("联系号码: ");
		JLabel lblnickname=new JLabel("昵称: ");
		JLabel lblage=new JLabel("年龄: ");
		JLabel lblsex=new JLabel("性别: ");
		
		txtQQcode=new JTextField(10);
		txtNickname=new JTextField(10);
		txtage=new JTextField(5);
		
		cbSex=new JComboBox(new String[] {"男","女"});
		btnFind=new JButton("查找");
		
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
		vhead.add("联系号码");
		vhead.add("昵称");
		vhead.add("IP地址");
		vhead.add("端口");
		vhead.add("年龄");
		vhead.add("性别");
		vhead.add("民族");
		vhead.add("星座");
		vhead.add("头像");
		vhead.add("备注");
		vhead.add("个性签名");
		vhead.add("状态");
		
		vdata=new Vector();
		
		mytable=new myTable(vdata,vhead);
		
		dataTable=new JTable(mytable); 
		
		add(new JScrollPane(dataTable));
		
		btnClose=new JButton("关闭");
		btnAdd=new JButton("添加");
		
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
		
		
		//向服务器获取所有信息
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
			JOptionPane.showMessageDialog(null, "您的本地没有此好友", "提示", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		//调试用输出
//		System.out.println(vdata.get(0).getAge());
		mytable.datas=vdata;//数据传给窗口
		dataTable.updateUI();//刷新界面
	}
	
	
	//设置JTable控件的值
	class myTable extends AbstractTableModel
	{	
		
		Vector datas=new Vector();
		Vector columns=new Vector();
		//构造函数必须把表头和数据传进来
		public myTable(Vector datas,Vector columns)
		{
			this.datas= datas;
			this.columns= columns;
		}
		//获取列数
		public int getColumnCount()
		{
			return columns.size();
		}
		//获取列名
		public String getColumnName(int columnIndex)
		{
			return columns.get(columnIndex).toString();
		}
				
		//获取行数
		public int getRowCount()
		{
			return datas.size();
		}
		
		
		//每一列要显示的数据类型
		public Object getValueAt(int rowIndex,int columnIndex)
		{
			Vector v=(Vector)datas.get(rowIndex);//取出第rowindex个account对象
			if(columnIndex==6)//头像列返回图片
				return new ImageIcon(v.get(columnIndex).toString());
			else 
			{
				return v.get(columnIndex);//获取第columnindex个属性
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
			row=dataTable.getSelectedRow();//得到选择的行号
			col=dataTable.getSelectedColumn();//得到选择的列号
			if(row==-1) {
				JOptionPane.showMessageDialog(null, "请先选择要添加的好友哦", "提示", JOptionPane.PLAIN_MESSAGE);
				return;//如果没有选择，直接退出并提示
			}
			
			Vector<String> rowData=(Vector<String>)mytable.datas.get(row);
			
			//获取第一列的code
			int qqcode=Integer.parseInt(rowData.get(0));

			Account acc=new Account();
//				acc=mytable.datas[row];
			acc.setQqCode(qqcode);
			acc.setAge(Integer.parseInt(rowData.get(4)));
			acc.setFace(rowData.get(8));
			acc.setIpAddr(rowData.get(2));
			acc.setPort(Integer.parseInt(rowData.get(3)));
			acc.setGroupname("好友");
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
