package com.sevenEleven.ui;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.sevenEleven.dbConn.DBConn;
import org.eclipse.swt.widgets.Combo;

public class ReportComposite extends Composite {

	private Group page1;
	private Group page2;
	
	private Text idText;
	private Label nameLabel;
	
	private Table table;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ReportComposite(final Composite parent, int style) {
		super(parent, style);
		
		Group group = new Group(this, SWT.NONE);
		group.setText("评级报告");
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
		
		

		// 按企业评级报告按钮
		final Button btnNewButton_1 = new Button(group, SWT.NONE);
		btnNewButton_1.setBounds(32, 32, 80, 27);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				page1.setVisible(true);
				page2.setVisible(false);
				
				createFindCustomer(parent, page1);
				
			}
		});
		btnNewButton_1.addListener(SWT.MouseEnter, new Listener() {
			public void handleEvent(Event e) {
				btnNewButton_1.setCursor(new Cursor(Display.getCurrent(),
						SWT.CURSOR_HAND));
			}
		});
		btnNewButton_1.setText("企业评级查询");
		
		// 按评级级别按钮
		final Button btnNewButton_2 = new Button(group, SWT.NONE);
		btnNewButton_2.setBounds(32, 80, 80, 27);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				page2.setVisible(true);
				page1.setVisible(false);
				System.out.println("page2 test");
				storeAllLevelData();
				createFindLevel(parent, page2);
			}
		});
		btnNewButton_2.addListener(SWT.MouseEnter, new Listener() {
			public void handleEvent(Event e) {
				btnNewButton_2.setCursor(new Cursor(Display.getCurrent(),
						SWT.CURSOR_HAND));
			}
		});
		btnNewButton_2.setText("各级别企业");
		
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
	
	
	// 查询客户
	public void createFindCustomer(final Composite shell, Composite composite) {
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
		
		String colname[] = { "企业名", "得分（满分100分）","评级" };
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);

		TableColumn col1 = new TableColumn(table, SWT.LEFT);
		col1.setWidth(150);
		col1.setText(colname[0]);

		TableColumn col2 = new TableColumn(table, SWT.LEFT);
		col2.setWidth(100);
		col2.setText(colname[1]);
		
		TableColumn col3 = new TableColumn(table, SWT.LEFT);
		col3.setWidth(100);
		col3.setText(colname[2]);

		table.setBounds(27, 140, 354, 50);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		final TableItem tableItem = new TableItem(table,	SWT.LEFT);

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
							double[] expertData = DBConn.getExpertDataById(id);
							if (expertData == null) {
								MessageBox mb = new MessageBox((Shell) shell,
										SWT.ICON_QUESTION | SWT.OK);
								mb.setText("提示");
								mb.setMessage("客户 " + id + " 尚未有定性分析结果");
								mb.open();
							} else if(expertData.length < 10){
								MessageBox mb = new MessageBox((Shell) shell,
										SWT.ICON_QUESTION | SWT.OK);
								mb.setText("提示");
								mb.setMessage("客户 " + id + " 尚未有定量分析结果");
								mb.open();
							}else{
								double expertScore = expertData[8];
								double neuroScore = expertData[9];
								double sum = expertScore * 0.375 + neuroScore * 0.7;
								String level = "";
								if(sum>=90){
									level = "AAA";
								}else if(sum>=80){
									level = "AA";
								}else if(sum>=70){
									level = "A";
								}else if(sum>=60){
									level = "BBB";
								}else if(sum>=50){
									level = "BB";
								}else if(sum>=40){
									level = "B";
								}else {
									level = "C";
								}
								tableItem.setText(new String[] {customerData[0].toString(),Double.toString(sum),level});
							}
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
	
	// 查询客户
	public void createFindLevel(final Composite shell, Composite composite) {
		
		String[] levels = {"AAA","AA","A","BBB","BB","B","C"};
				
		
		Label label3 = new Label(composite, SWT.NONE);
		label3.setBounds(30, 46, 61, 17);
		label3.setText("选择级别");
		
		final Combo combo = new Combo(composite, SWT.NONE);
		combo.setBounds(100, 45, 88, 25);
		combo.setItems(levels);

				
		String colname[] = {"评级", "企业名" };
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);

		TableColumn col1 = new TableColumn(table, SWT.LEFT);
		col1.setWidth(150);
		col1.setText(colname[0]);

		TableColumn col2 = new TableColumn(table, SWT.LEFT);
		col2.setWidth(100);
		col2.setText(colname[1]);		
		

		table.setBounds(27, 80, 254, 350);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		//final TableItem tableItem;

		// 查询客户按钮
		Button button3_6 = new Button(composite, SWT.NONE);
		button3_6.setBounds(240, 40, 80, 27);
		button3_6.setText("查询客户");
		button3_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = combo.getSelectionIndex();
				String s = combo.getText();
				System.out.println("index " + index);
				int[] levelCompany = DBConn.getRatingResultCompanyByLevel(s);
				if(levelCompany != null){
					for(int i=0;i<levelCompany.length;i++){
						String customerData[] = DBConn.getCustomerDataById(levelCompany[i]);;
						
						System.out.println("levelCompany " + levelCompany[i]);
						TableItem tableItem = new TableItem(table,	SWT.LEFT);
						tableItem.setText(new String[] {s,customerData[0]});
						
					}
				}
			}
		});
	}
	
	public void storeAllLevelData(){
		Object[][] expertData = DBConn.getNotNullExpertData();
		if(expertData != null){
			for(int i=0;i<expertData.length;i++){
				double expertScore = (Double) expertData[i][1];
				double neuroScore = (Double) expertData[i][2];
				double sum = expertScore * 0.375 + neuroScore * 0.7;
				String level = "";
				if(sum>=90){
					level = "AAA";
				}else if(sum>=80){
					level = "AA";
				}else if(sum>=70){
					level = "A";
				}else if(sum>=60){
					level = "BBB";
				}else if(sum>=50){
					level = "BB";
				}else if(sum>=40){
					level = "B";
				}else {
					level = "C";
				}
				if(!DBConn.insertRatingResult((Integer) expertData[i][0], sum, level)){
					System.out.println("storeAllLevelData has error");
				}
			}
		}
		
	}
}
