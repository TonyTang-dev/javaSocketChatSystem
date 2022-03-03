package ht.ui;
//主窗口
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
//启动线程 进入循环等到接收消息
public class MainUI extends JFrame implements MouseListener,ActionListener{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Account account,friendAccount;
	private JTabbedPane tab;
	private JLabel lblhead;//登陆后显示的个人资料
	private JList<?> lstFriend;
	private JList<?> lstFamily;
	private JList<?> lstclassmate;
	private JList<?> lsthmd;
	private JButton btnFind;//查找好友按钮
	private Vector<Account> vFriend,vFamily,vClassmate,vHmd,vAllDetail;
	private JPopupMenu pop;//弹出菜单
	private JMenu menu;
	private JMenuItem miChat,miLookInfo,miFriend,miFamily,miMate,miHmd,miDel;//聊天 查看资料 删除好友 移动好友
	//创建哈希表保存所有在线用户的窗口
	private LinkedHashMap<Integer,ChatUI> ht_ChatUsers;
	
	
	public MainUI()
	{
		
		
	}
	public MainUI(Account acc) throws IOException
	{
		this.account=acc;
		
		setTitle(acc.getNickName());//设置窗口标签栏标题和头像
		setIconImage(new ImageIcon(acc.getFace()).getImage());
		//获取昵称 备注 QQ号 个性签名
		String str="";
		//备注不为空时，显示备注不显示昵称
		if(!acc.getRemark().equals(""))
		{
			str=acc.getRemark()+"(";
		}
		//备注为空时，显示昵称
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
		
		
		
		refresh();//读好友列表
		
		tab=new JTabbedPane();
		tab.add("好友",new JScrollPane(lstFriend));
		tab.add("家人",new JScrollPane(lstFamily));
		tab.add("同学",new JScrollPane(lstclassmate));
		tab.add("其他",new JScrollPane(lsthmd));
		add(tab);
		createMenu();
		btnFind=new JButton("查找/添加好友");
		add(btnFind,BorderLayout.SOUTH);
 
		btnFind.addActionListener(this);
		setSize(300,680);
		setVisible(true);
		setResizable(true);
		//得到屏幕宽度,设置窗口位置
		int width=Toolkit.getDefaultToolkit().getScreenSize().width-300;
		setLocation(width,50);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//监听叉关闭
		this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
               //super.windowClosing(e);    //To change body of overridden methods use File | Settings | File Templates.
               //这里写你想实现的代码，我只写了一行println
                System.exit(0);
            }
        });
		
		//启动消息接收线程
		new ReceThread().start();
	}
	
	
	class listmodel extends AbstractListModel
	{
		Vector dats;
		public listmodel(Vector dats)
		{
			this.dats=dats;
		}
		//获取行数
		public Object getElementAt(int index)
		{
			Account user=(Account) dats.get(index);
			return user.getNickName().trim()+"【"+user.getQqCode()+"】";
		}
		//获取长度
		public int getSize()
		{
			return dats.size();
		}
		
		
	}
	
	//获取好友头像
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
			//根据列表中的好友在线状态设置头像
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
			
			
			//设置字体颜色
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
	
	//创建菜单
	public void createMenu()
	{
		pop=new JPopupMenu();
		miChat=new JMenuItem("聊天");
		miLookInfo=new JMenuItem("查看资料");
		miFriend=new JMenuItem("移动到好友");
		miFamily=new JMenuItem("移动到家人");
		miMate=new JMenuItem("移动到同学");
		miHmd=new JMenuItem("移动到黑名单");
		miDel=new JMenuItem("删除好友");
		
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
	
	//刷新界面
	public void refresh() throws IOException
	{
		vAllDetail=new DBOper().getAllInfo(account);
		//如果没有用户的情况下，直接结束函数
		if(vAllDetail==null) {
			//清楚之前所有的记录
			vFriend.clear();
			vFamily.clear();
			vClassmate.clear();
			vHmd.clear();
			return;
		}
		
		
		//清楚之前所有的记录
		vFriend.clear();
		vFamily.clear();
		vClassmate.clear();
		vHmd.clear();
		//把数据库的最新数据分别放到对应的向量
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
			
			//暂时未做处理，直接显示在好友栏
			vFriend.add(a);
			
		}
		
		//把向量放入List控件
		lstFriend.setModel(new listmodel(vFriend));//显示资料
		lstFriend.setCellRenderer(new myfind(vFriend));//显示头像
		lstFamily.setModel(new listmodel(vFamily));//显示资料
		lstFamily.setCellRenderer(new myfind(vFamily));//显示头像
		lstclassmate.setModel(new listmodel(vClassmate));//显示资料
		lstclassmate.setCellRenderer(new myfind(vClassmate));//显示头像
		lsthmd.setModel(new listmodel(vHmd));//显示资料
		lsthmd.setCellRenderer(new myfind(vHmd));//显示头像
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==lstFriend)
		{
			if(lstFriend.getSelectedIndex()==-1) {
				JOptionPane.showMessageDialog(null, "当前列表无好友", "提示", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			friendAccount=(Account)vFriend.get(lstFriend.getSelectedIndex());
			//双击
			if(e.getClickCount()==2)
			{
			//	new ChatUI(account,friendAccount);
				findWin(friendAccount.getQqCode(),null);
				
			}
			//邮件
			if(e.getButton()==3)
			{
				//好友被选中
				if(lstFriend.getSelectedIndex()>=0)
				{
					pop.show(lstFriend, e.getX(), e.getY());
				}
			}
		}
		else if(e.getSource()==lstFamily)
		{
			if(lstFamily.getSelectedIndex()==-1) {
				JOptionPane.showMessageDialog(null, "当前列表无好友", "提示", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			friendAccount=(Account)vFamily.get(lstFamily.getSelectedIndex());
			//双击
			if(e.getClickCount()==2)
			{
 
				//new ChatUI(account,friendAccount);
				findWin(friendAccount.getQqCode(),null);
			}
			//邮件
			if(e.getButton()==3)
			{
				//被选中
				if(lstFamily.getSelectedIndex()>=0)
				{
					pop.show(lstFamily, e.getX(), e.getY());
				}
			}
		}
		else if(e.getSource()==lstclassmate)
		{
			if(lstclassmate.getSelectedIndex()==-1) {
				JOptionPane.showMessageDialog(null, "当前列表无好友", "提示", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			friendAccount=(Account)vClassmate.get(lstclassmate.getSelectedIndex());
			//双击
			if(e.getClickCount()==2)
			{
 
			//	new ChatUI(account,friendAccount);
				findWin(friendAccount.getQqCode(),null);
			}
			//邮件
			if(e.getButton()==3)
			{
				//被选中
				if(lstclassmate.getSelectedIndex()>=0)
				{
					pop.show(lstclassmate, e.getX(), e.getY());
				}
			}
		}
		else if(e.getSource()==lsthmd)
		{
			if(lsthmd.getSelectedIndex()==-1) {
				JOptionPane.showMessageDialog(null, "当前列表无好友", "提示", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			friendAccount=(Account)vHmd.get(lsthmd.getSelectedIndex());
			//双击
			if(e.getClickCount()==2)
			{
				//new ChatUI(account,friendAccount);
				findWin(friendAccount.getQqCode(),null);
			}
			//右击
			if(e.getButton()==3)
			{
	
				//被选中
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
	
	
	//接收消息的线程
	class ReceThread extends Thread
	{
		public ReceThread()
		{
			//启动线程时 创建保存窗口的哈希表
			ht_ChatUsers=new LinkedHashMap<Integer,ChatUI>();
		}
		public void run()
		{
			
			try 
			{
				
				//自己端口接收数据
				DatagramSocket serverSocket=new DatagramSocket(account.getPort());//在自己端口接收数据
				
				//接收客户端发来的信息存入pack包
				while(true)
				{
				byte b[]=new byte[1024*70];	
				DatagramPacket pack=new DatagramPacket(b,b.length) ;//数据包
				serverSocket.receive(pack);
				
				//把字节数组转换成SendMsg对象
				//pack.getLength()接收到的字节数
				ByteArrayInputStream bis=new ByteArrayInputStream(b,0,pack.getLength());//字节数组输入流
				ObjectInputStream ois=new ObjectInputStream(bis);
				SendMsg msg=(SendMsg)ois.readObject();
				
				switch(msg.Cmd)
				{
					
					case Cmd.CMD_ONLINE://接收上线通知
						refresh();
						break;
					case Cmd.CMD_OFFLINE://接收离线通知
						refresh();
						break;
					case Cmd.CMD_CHAT://接收聊天信息
						
						ChatUI chat=findWin(msg.selfAccount.getQqCode(),msg);//显示窗口
						//获取聊天消息
						chat.appendView(msg.selfAccount.getNickName(),msg.doc);
						
						break;
					case Cmd.CMD_DOUDONG://接收抖动信息
						chat=findWin(msg.selfAccount.getQqCode(),msg);//显示窗口
						chat.shake();
					break;
						
					case Cmd.CMD_ADDFRIEND://添加好友
						String str=msg.selfAccount.getNickName()+"添加你为好友 ，请确认";
					
						SendMsg m=new SendMsg();
						m.friendAccount=msg.selfAccount;
						m.selfAccount=msg.friendAccount;
						
						//如果点击确定按钮就添加
						if(JOptionPane.showConfirmDialog(null,str,"添加好友",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
						{//往朋友表中添加记录
							
							new DBOper().addFriend(m.friendAccount, m.selfAccount.getQqCode());
							refresh();
							m.Cmd=Cmd.CMD_AFREEFRIEND;
						}else
						{
							m.Cmd=Cmd.CMD_REJECTFRIEND;
						}
						
					break;
						
				
					case Cmd.CMD_AFREEFRIEND://同意
						refresh();//刷新界面
						break;
					
					case Cmd.CMD_REJECTFRIEND://拒绝
						JOptionPane.showMessageDialog(null,msg.selfAccount.getNickName()+"拒接了你的好友请求");
						break;	
					
					
					case Cmd.CMD_FILESEND://接受文件
						String str1=msg.selfAccount.getNickName()+"发送了文件"+msg.sFileName;
						
						int cmd=Cmd.CMD_FILESUCC;
						if(JOptionPane.showConfirmDialog(null,str1,"接收文件",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
						{
							FileDialog dlg=new FileDialog(MainUI.this,"保存",FileDialog.SAVE);
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
						
					case Cmd.CMD_FILESUCC://回复接受成功
						JOptionPane.showMessageDialog(null,msg.selfAccount.getNickName()+"接受了文件");
						break;
						
					case Cmd.CMD_FILEFAILED://回复拒接
						
						JOptionPane.showMessageDialog(null,msg.selfAccount.getNickName()+"拒绝了文件");
						break;
				}
				
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			
		}
		
	}
		
	//查找窗口是否存在,如果不存在则创建,存在则直接显示信息在界面
			public ChatUI findWin(Integer qqcode,SendMsg msg)
			{
				ChatUI chat=null;
				//查找窗口是否存在
				chat =ht_ChatUsers.get(qqcode);
				if(chat==null)//不存在则创建聊天窗口
				{
					if(msg==null)//双击或者右键打开窗口
					{
						chat=new ChatUI(account,friendAccount);
					}else//线程打开窗口
					{
						chat=new ChatUI(msg.friendAccount,msg.selfAccount);
					}
					
					//窗口加入哈希表
					ht_ChatUsers.put(qqcode,chat);										
				}
								
				if(!chat.isVisible())
				{
					chat.show();
				}
				return chat;				
			}
			
	//修改用户状态
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