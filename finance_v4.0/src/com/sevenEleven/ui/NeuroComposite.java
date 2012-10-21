package com.sevenEleven.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.wb.swt.SWTResourceManager;

import com.sevenEleven.dbConn.DBConn;

import com.sevenEleven.neuro.*;

import org.eclipse.swt.widgets.Text;


public class NeuroComposite extends Composite {

	// 设置网络类
	private SetNet ns;
	// BP算法类
	private NetAlgo na;

	private Group page1;
	private Group page2;
	
	private int id;
	private Text idText;
	private Label nameLabel;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public NeuroComposite(final Composite parent, int style) {
		super(parent, style);

		Group group = new Group(this, SWT.NONE);
		group.setText("定量评价");
		group.setBounds(20, 40, 145, 270);

		Group panel = new Group(this, SWT.NONE);
		panel.setText("操作界面");
		panel.setBounds(180, 35, 490, 500);

		page1 = new Group(panel, SWT.SHADOW_IN);
		page1.setBounds(5, 15, 486, 480);
		//page1.setText("page1");

		page2 = new Group(panel, SWT.SHADOW_IN);
		page2.setBounds(5, 15, 486, 480);
		//page2.setText("page2");

		page2.setVisible(false);
		page1.setVisible(false);
		
		Group operateGroup = new Group(page1, SWT.NONE);		
		operateGroup.setBounds(20, 120, 400, 300);
		
		Label lblNewLabel = new Label(operateGroup, SWT.NONE);
		lblNewLabel.setBounds(50, 60, 150, 17);
		lblNewLabel.setText("对企业财务数据定量分析");
		
		
		
		Label lblNewLabel_1 = new Label(operateGroup, SWT.NONE);
		lblNewLabel_1.setBounds(160, 100, 61, 17);
		lblNewLabel_1.setText("得分");
		
		final Label scoreLabel = new Label(operateGroup, SWT.NONE);
		scoreLabel.setBounds(220, 100, 61, 17);
		//lblNewLabel_2.setText("");
		
		Button btnNewButton = new Button(operateGroup, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				id = Integer.parseInt(idText.getText());
				//scoreLabel.setText("76.937");
				/*double score = 76.937;
				if (DBConn.insertExpertDataByNeural(id, score)) {
					//page2.setVisible(true);
					//page1.setVisible(false);
				} else {
					MessageBox msgBox = new MessageBox((Shell) parent,
							SWT.ICON_WARNING);
					msgBox.setText("Error");
					msgBox.setMessage("你确定已经导入公司财务报表了吗？\n请检查.");
					msgBox.open();
				}*/
				// 初始化设计类
				ns = new SetNet();
				// 初始化BP算法类
				na = new NetAlgo();
				initNet();// 初始化主界面
				System.out.println("test na" +na.getNeuroResult());
				scoreLabel.setText(na.getNeuroResult()+"");
				if (DBConn.insertExpertDataByNeural(id, na.getNeuroResult())) {
					//page2.setVisible(true);
					//page1.setVisible(false);
				} else {
					MessageBox msgBox = new MessageBox((Shell) parent,
							SWT.ICON_WARNING);
					msgBox.setText("Error");
					msgBox.setMessage("你确定已经导入公司财务报表了吗？\n请检查.");
					msgBox.open();
				}
				
			}
		});
		btnNewButton.setBounds(220, 55, 80, 27);
		btnNewButton.setText("执行");

		// 定量分析按钮
		final Button btnNewButton_1 = new Button(group, SWT.NONE);
		btnNewButton_1.setBounds(32, 32, 80, 27);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				page1.setVisible(true);
				page2.setVisible(false);
				createFindCustomerNeuroComposite(parent, page1);
			}
		});
		btnNewButton_1.addListener(SWT.MouseEnter, new Listener() {
			public void handleEvent(Event e) {
				btnNewButton_1.setCursor(new Cursor(Display.getCurrent(),
						SWT.CURSOR_HAND));
			}
		});
		btnNewButton_1.setText("定量分析");
		
		// 返回主页面按钮
		final Button btnNewButton_4 = new Button(this, SWT.NONE);
		btnNewButton_4.setToolTipText("\u8FD4\u56DE\u4E3B\u9875");
		btnNewButton_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setVisible(false);
			}
		});

		btnNewButton_4.addListener(SWT.MouseEnter, new Listener() {
			public void handleEvent(Event e) {
				btnNewButton_4.setCursor(new Cursor(Display.getCurrent(),
						SWT.CURSOR_HAND));
			}
		});

		btnNewButton_4.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		btnNewButton_4.setImage(SWTResourceManager
				.getImage("img/button_returnhome.png"));
		btnNewButton_4.setBounds(20, 420, 145, 111);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void initNet() {
		ImpoLearnData();
		ImpoGenerData();
		na.setExecType(1);// 设置算法运行方式是学习
		na.setNoNet();// 设置在新网学习
		na.setLayers(ns.getLayers());
		na.setParameter(ns.getParameter());
		na.exe();

		na.setExecType(2);// 设置算法运行方式是泛化
		na.exe();

	}

	// 从mysql中导入学习数据 训练
	private void ImpoLearnData() {
		double[][] tempData = new double[1000][100];
		int i = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			// Class.forName("org.gjt.mm.mysql.Driver");
			// System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/finance", "root", "root");
			// 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码
			// System.out.println("Success connect Mysql server!");
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from companydata");
			ResultSetMetaData data = rs.getMetaData();
			int tmp = data.getColumnCount() -1;
			while (rs.next()) {
				for (int count = 4; count <= tmp; count++) {
					tempData[i][count - 4] = rs.getDouble(count);
					// System.out.println(tempData[i][count-4]);
				}
				i++;

			}
			// 只保留有价值数据
			int learnNum = i;
			double[][] learnData = new double[learnNum][tmp];
			for (int j = 0; j < learnNum; j++) {
				for (int count = 4; count <= tmp; count++) {
					learnData[j][count - 4] = tempData[j][count - 4];
				}
			}
			System.out.print("learndata are imported!\n");
			na.setLearnData(learnData);// 传递原始学习数据集
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
	}

	// 导入泛化数据监听器类 测试
	private void ImpoGenerData() {
		double[][] tempData = new double[1000][100];
		int i = 0;

		//int id = 1;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			// Class.forName("org.gjt.mm.mysql.Driver");
			// System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/finance", "root", "root");
			// 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码
			// System.out.println("Success connect Mysql server!");
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from companydata where company_name=" + id);
			ResultSetMetaData data = rs.getMetaData();
			int tmp = data.getColumnCount() -1;
			while (rs.next()) {
				for (int count = 4; count <= tmp; count++) {
					tempData[i][count - 4] = rs.getDouble(count);
					// System.out.println(tempData[i][count-4]);
				}
				i++;

			}
			// 只保留有价值数据
			// double[][] generData = new double[i][data.getColumnCount()];
			double[][] generData = new double[i][tmp];
			for (int j = i; j < i; j++) {
				for (int count = 4; count <= tmp; count++) {
					generData[j -i][count - 4] = tempData[j][count - 4];
				}
			}
			System.out.print("generdata are imported!\n");
			na.setGenarData(generData);// 传递原始学习数据集
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
	}
	
	// 查询客户
	public void createFindCustomerNeuroComposite(final Composite shell, Composite composite) {
		Label label3 = new Label(composite, SWT.NONE);
		label3.setBounds(30, 46, 61, 17);
		label3.setText("客户代号");

		idText = new Text(composite, SWT.BORDER);
		idText.setBounds(100, 45, 125, 23);
		idText.setText(MainFrame.customerID);

		Label label4 = new Label(composite, SWT.NONE);
		label4.setBounds(30, 80, 61, 17);
		label4.setText("客户名称");

		nameLabel = new Label(composite, SWT.BORDER);
		nameLabel.setBounds(100, 80, 200, 23);
		nameLabel.setText(MainFrame.customerName);

		// 查询客户按钮
		Button button3_6 = new Button(composite, SWT.NONE);
		button3_6.setBounds(240, 40, 80, 27);
		button3_6.setText("查询客户");
		button3_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// String id = idText.getText();
				if (idText.getText().equals("")) {
					MessageBox mb = new MessageBox((Shell) shell,
							SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
					mb.setText("提示");
					mb.setMessage("请输入id号进行查询客户");
					mb.open();
				} else {

					try {
						System.out.println("idText.getText() "
								+ idText.getText());
						int id = Integer.parseInt(idText.getText());
						String customerData[] = DBConn.getCustomerDataById(id);

						if (customerData == null) {
							MessageBox mb = new MessageBox((Shell) shell,
									SWT.ICON_QUESTION | SWT.OK);
							mb.setText("提示");
							mb.setMessage("未能查询到客户 " + id + " 的资料,请确认ID号");
							mb.open();
						} else {
							// idText.setText(customerData[0].toString());
							MainFrame.customerID = Integer.toString(id);
							MainFrame.customerName = customerData[0].toString();
							nameLabel.setText(customerData[0].toString());
						}
					} catch (java.lang.NumberFormatException e1) {
						MessageBox mb = new MessageBox((Shell) shell,
								SWT.ICON_QUESTION | SWT.OK);
						mb.setText("提示");
						mb.setMessage("id只能为数字");
						mb.open();
					}
				}
			}
		});
	}
}
