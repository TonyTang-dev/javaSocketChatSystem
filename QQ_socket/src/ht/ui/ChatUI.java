package ht.ui;
 
import ht.bean.Account;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
 
 
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
 
 
 
 
import ht.bean.Account;
import ht.cmd.Cmd;
import ht.db.DBOper;
import ht_.Send;
import ht_.SendMsg;
 
 
public class ChatUI extends JFrame implements ActionListener,ItemListener{
 
	private Account selfAccount,friendAccount;
	JTextPane recePanel,sendPanel;
	JButton btnSend,btnshake,btnsendFile,btnColor,btnClose;
	JComboBox cbFont,cbSize,cbImg;
	JLabel lblboy,lblgrid,lblfriendInfo;
	
	
	public ChatUI()
	{
		
	}
	public ChatUI(Account self,Account friend)
	{
		this.selfAccount=self;
		this.friendAccount=friend;
		ImageIcon ic=new ImageIcon(friend.getFace());
	
		setIconImage(ic.getImage());
		String str="";
		if(!friend.getRemark().equals(""))
		{
			str=friend.getRemark()+"(";
			
		}
		else
		{
			str=friend.getNickName()+"(";
		
		}
		str+= friend.getQqCode()+")"+friend.getSelfsign();
		
		setTitle(str);
		lblfriendInfo=new JLabel(str,ic,JLabel.LEFT);
 
		add(lblfriendInfo,BorderLayout.NORTH);
		//中间放接受框
		recePanel=new JTextPane();
		recePanel.setEditable(false);
		
		//下边放一排按钮
		btnSend=new JButton("发送消息");
		btnshake=new JButton(new ImageIcon("images/dd.jpg"));
		btnshake.setMargin(new Insets(0,0,0,0));//调整按钮为图片大小
		btnsendFile=new JButton("发送文件");
		btnColor=new JButton("字体颜色");
		
		String fonts[]= {"宋体","楷体","黑体","隶书","微软雅黑"};
		cbFont=new JComboBox(fonts);
		String fontsize[] = {"10","12","14","16","18"};
		cbSize=new JComboBox(fontsize);
		cbImg=new JComboBox(getImg());
		cbImg.setPreferredSize(new Dimension(100, 28));
				
		JPanel btnPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnPanel.add(cbFont);
		btnPanel.add(cbSize);
		btnPanel.add(btnColor);
//		btnPanel.add(cbImg);
		btnPanel.add(btnshake);
		btnPanel.add(btnsendFile);
		
		sendPanel=new JTextPane();
		JPanel southPanel=new JPanel(new BorderLayout());
		southPanel.add(btnPanel,BorderLayout.NORTH);
		southPanel.add(new JScrollPane(sendPanel));
 
		JPanel centerPanel=new JPanel(new GridLayout(2,1,10,10));
		
		centerPanel.add(new JScrollPane(recePanel));
		centerPanel.add(new JScrollPane(southPanel));
		add(centerPanel);
		
	//	add(southPanel,BorderLayout.SOUTH);
		
		
		
		
		btnSend=new JButton("发送(S)");
		btnSend.setMnemonic('S');
		
		btnClose=new JButton("关闭(X)");
		btnClose.setMnemonic('X');
		
		JPanel bottomPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		bottomPanel.add(btnSend);
		bottomPanel.add(btnClose);
		
		add(bottomPanel,BorderLayout.SOUTH);
		
		lblboy=new JLabel(new ImageIcon("images/boy.jpg"));
		lblgrid=new JLabel(new ImageIcon("images/girl.jpg"));
		
		JPanel rightPanel=new JPanel(new GridLayout(2,1,5,0));
		
		rightPanel.add(lblboy);
		rightPanel.add(lblgrid);
		add(rightPanel,BorderLayout.EAST);
		
		btnSend.addActionListener(this);
		btnshake.addActionListener(this);
		btnsendFile.addActionListener(this);
		btnColor.addActionListener(this);
		btnClose.addActionListener(this);
		
		cbFont.addItemListener(this);
		cbSize.addItemListener(this);
		cbImg.addItemListener(this);
		
		
		setSize(670,550);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		sendPanel.requestFocus(); 
		
		
	}
	private Icon[] getImg() {
		Icon[] icon=null;
		
		File file=new File("bq");
		String sfile[]=file.list();
		
		icon=new ImageIcon[sfile.length];
		for(int i=0;i<sfile.length;i++)
		{
			icon[i]=new ImageIcon("bq/"+sfile[i]);
		}
		
		return icon;
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==cbFont || e.getSource()==cbSize)
		{
			
			String sfont=cbFont.getSelectedItem().toString();
			int size=Integer.parseInt(cbSize.getSelectedItem().toString());
			
			sendPanel.setFont(new Font(sfont,Font.PLAIN,size));
			
		}else if(e.getSource()==cbImg)
		{
			Icon g=(Icon)cbImg.getSelectedItem();
			sendPanel.insertIcon(g);
		}
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnSend)
		{
			try {
				appendView(selfAccount.getNickName(),sendPanel.getStyledDocument());
				//生成发送类
				SendMsg msg=new SendMsg();
				msg.Cmd=Cmd.CMD_CHAT;
				msg.doc=sendPanel.getStyledDocument();//聊天内容 
				msg.selfAccount=selfAccount;
				msg.friendAccount=friendAccount;
				//发送
				new Send().send(msg);
				
				
				sendPanel.setText("");
 
			
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sendPanel.setText("");
		
		}else if(e.getSource()==btnshake)
		{
			SendMsg msg=new SendMsg();
			msg.Cmd=Cmd.CMD_DOUDONG;
			msg.selfAccount=selfAccount;
			msg.friendAccount=friendAccount;
			
			new Send().send(msg);
 
			shake();
			
		}
		else if(e.getSource()==btnsendFile)
		{	//发送文件
			//创建文件选择框
			FileDialog fdlg=new FileDialog(this,"打开64k以下的文件",FileDialog.LOAD);
			
			fdlg.show();
			String sfilename=fdlg.getDirectory()+"\\"+fdlg.getFile();
			
			//当没有选文件时直接退出
			if(sfilename.equals("null\\null")) {
				JOptionPane.showMessageDialog(null, "没有选择文件哦", "提示", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			
			File file=new File(sfilename);//创建文件
			
			//UDP文件传输一次最多64k
			if(file.length()>1024*64)
			{
				JOptionPane.showMessageDialog(this, "文件不能大于64k");
				return;
			}
			
			try {
				
			byte[] b=new byte[(int)file.length()];
			FileInputStream fis=new FileInputStream(file);
			fis.read();//读取文件内容
			fis.close();
			
			SendMsg msg=new SendMsg();
			msg.Cmd=Cmd.CMD_FILESEND;
			msg.selfAccount=selfAccount;
			msg.friendAccount=friendAccount;
			msg.sFileName=fdlg.getFile();//文件名
			msg.b=b;
			new Send().send(msg);
			
			}catch(Exception e1)
			{
				e1.printStackTrace();
			}
			
			
		}
		else if(e.getSource()==btnColor)
		{
			JColorChooser cc=new JColorChooser();
			Color c=cc.showDialog(this, "选择字体颜色", Color.BLACK);
			sendPanel.setForeground(c);
		}
		else if(e.getSource()==btnClose)
		{
			dispose();
		}
	}
	
	public void shake()
	{
 
		Point p=this.getLocationOnScreen();
		for(int i=0;i<20;i++)
		{
			if(i%2==0)
			{
				setLocation(p.x+5,p.y+5);
			}else
			{
				setLocation(p.x-5,p.y-5);
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
		}
		
		
		
	}
	
	//获取接受框内容
	public void appendView(String name, StyledDocument xx) throws BadLocationException{
		StyledDocument vdoc=recePanel.getStyledDocument();
		
		Date date=new Date();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		String time=sdf.format(date);
		SimpleAttributeSet as=new SimpleAttributeSet();
		
		
		String s=name+"    "+time+"\n";
		
		vdoc.insertString(vdoc.getLength(),s,as);
		int end=0;
		while(end<xx.getLength())
		{
			Element e0=xx.getCharacterElement(end);
			
			SimpleAttributeSet asl=new SimpleAttributeSet();
			StyleConstants.setForeground(asl,StyleConstants.getForeground(e0.getAttributes()));
			StyleConstants.setFontSize(asl,StyleConstants.getFontSize(e0.getAttributes()));
			StyleConstants.setFontFamily(asl,StyleConstants.getFontFamily(e0.getAttributes()));
			
			s=e0.getDocument().getText(end, e0.getEndOffset()-end);
			
			if("icon".equals(e0.getName()))
			{
				vdoc.insertString(vdoc.getLength(),s,e0.getAttributes());
			}
			else
			{
				vdoc.insertString(vdoc.getLength(),s,asl);
			}
			
			end=e0.getEndOffset();
 
		}
		
		vdoc.insertString(vdoc.getLength(), "\n", as);
		
		recePanel.setCaretPosition(vdoc.getLength());		
	}
	
	
}