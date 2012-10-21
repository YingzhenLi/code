package com.sevenEleven.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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
import org.eclipse.wb.swt.SWTResourceManager;

import com.sevenEleven.dbConn.DBConn;
import com.sevenEleven.fuzzy.Fuzzy;
import com.sevenEleven.fuzzy.Item;
import org.eclipse.swt.widgets.Text;

public class FuzzyComposite extends Composite {

	// 定性分析部分
	private int itemsNum = 13;
	private int groupsNum = 8;
	// 生成group
	private Group[] groups;
	private Item[] items;
	private double[] fuzzyOutputs;
	private ScrolledComposite page1;
	private Group page2;

	// private
	private Text idText;
	private Label nameLabel;
	private Table table;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public FuzzyComposite(final Composite parent, int style) {
		super(parent, style);

		Group group = new Group(this, SWT.NONE);
		group.setText("定性评价");
		group.setBounds(20, 40, 145, 270);

		Group panel = new Group(this, SWT.NONE);
		panel.setText("操作界面");
		panel.setBounds(180, 35, 490, 500);
		// FillLayout fillLayout = new FillLayout();
		// panel.setLayout(fillLayout);

		page1 = new ScrolledComposite(panel, SWT.H_SCROLL | SWT.V_SCROLL);
		page1.setBounds(5, 15, 486, 480);
		page1.setExpandHorizontal(true);
		page1.setExpandVertical(true);
		page1.setMinWidth(460);
		page1.setMinHeight(1700);

		page2 = new Group(panel, SWT.SHADOW_IN);
		page2.setBounds(5, 15, 486, 480);
		//page2.setText("page2");
		// createFindCustomerFuzzyComposite(parent, page2);

		page2.setVisible(false);
		page1.setVisible(false);

		// 定义一个面板Composite，用此面板来容纳其他的组件
		final Composite composite = new Composite(page1, SWT.NONE);
		composite.setBounds(10, 10, 400, 400);
		// composite.setText("page1");
		page1.setContent(composite);// 设置composite被scrolledComposite控制

		// 定性输入按钮
		final Button btnNewButton_1 = new Button(group, SWT.NONE);
		btnNewButton_1.setBounds(32, 32, 80, 27);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createItems(parent, composite);
				page1.setVisible(true);
				page2.setVisible(false);
				System.out.println("page2 test");
			}
		});
		btnNewButton_1.addListener(SWT.MouseEnter, new Listener() {
			public void handleEvent(Event e) {
				btnNewButton_1.setCursor(new Cursor(Display.getCurrent(),
						SWT.CURSOR_HAND));
			}
		});
		btnNewButton_1.setText("定性输入");

		// 定性结果按钮
		final Button btnNewButton_2 = new Button(group, SWT.NONE);
		btnNewButton_2.setBounds(32, 80, 80, 27);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createFindCustomerFuzzyComposite(parent, page2);
				page2.setVisible(true);
				page1.setVisible(false);
				System.out.println("page2 test");
			}
		});
		btnNewButton_2.addListener(SWT.MouseEnter, new Listener() {
			public void handleEvent(Event e) {
				btnNewButton_2.setCursor(new Cursor(Display.getCurrent(),
						SWT.CURSOR_HAND));
			}
		});
		btnNewButton_2.setText("定性结果");

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

		// 定性分析部分
		// createItems(parent, composite);
		// createFindCustomerFuzzyComposite(parent, page2);

		// 提交按钮
		Button btnNewButton_3 = new Button(composite, SWT.NONE);
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (idText.getText().equals("") || !isInputsValid()) {

					setInputTextsBackground();
					MessageBox msgBox = new MessageBox((Shell) parent,
							SWT.ICON_WARNING);
					msgBox.setText("Invalid Inputs");
					msgBox.setMessage("There are some invalid inputs!\nPlease correct them.");
					msgBox.open();
					// System.out.println("error");
				} else {
					setFuzzyOutputs();
					int id = Integer.parseInt(MainFrame.customerID);
					if (DBConn.insertExpertDataByExpert(id, fuzzyOutputs[0],
							fuzzyOutputs[1], fuzzyOutputs[2], fuzzyOutputs[3],
							fuzzyOutputs[4], fuzzyOutputs[5], fuzzyOutputs[6],
							fuzzyOutputs[7], getFuzzyScore())) {
						page2.setVisible(true);
						page1.setVisible(false);
					} else {
						MessageBox msgBox = new MessageBox((Shell) parent,
								SWT.ICON_WARNING);
						msgBox.setText("Invalid Inputss");
						msgBox.setMessage("There are some invalid inputs!\nPlease correct them.");
						msgBox.open();
					}
					// 存专家系统每项输出数据
					/*
					 * for(int i=0;i<fuzzyOutputs.length;i++){
					 * System.out.println(fuzzyOutputs[i]); }
					 */
					System.out.println(getFuzzyScore());

				}

			}
		});
		btnNewButton_3.setBounds(200, 1600, 72, 22);
		btnNewButton_3.setText("提交");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/*******************************************/

	public void createItems(final Composite shell, Composite composite) {

		createFindCustomer(shell, composite);

		// 生成group
		groups = new Group[groupsNum];
		for (int i = 0; i < groupsNum; i++) {
			groups[i] = new Group(composite, SWT.None);
			groups[i].setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.BOLD));
		}

		items = new Item[itemsNum];
		int xstart = 8;
		int ystart = 110;
		int width = 453;
		int height = 110;

		groups[0].setText("管理者素质和能力");
		groups[0].setBounds(xstart, ystart + height * 0, width, height * 2);
		items[0] = new Item(
				groups[0],
				1,
				"管理者素质",
				"0-3 管理者素质不高：管理者讲话闪烁其词，谈及公司发展目标和战略方针不清晰\n 3-7 管理者素质一般：管理者谈吐较为诚实，谈及公司发展目标和战略方针较清晰\n 7-10 管理者素质很高：管理者谈吐诚实，谈及公司发展目标和战略方针很清晰");
		items[1] = new Item(groups[0], 2, "管理者从业经验",
				"0-3 管理者从业经验在0-2年\n 3-7 管理者从业经验在3-6年\n 7-10 管理者从业经验在7年及以上");

		groups[1].setText("管理层素质");
		groups[1].setBounds(xstart, ystart + height * 2, width, height * 1);
		items[2] = new Item(groups[1], 1, "管理层学历",
				"0-3 管理层平均学历在高中及以下\n 3-7 管理者平均学历在本科阶段\n 7-10 管理者平均学历在硕士及以上");

		groups[2].setText("个人信用");
		groups[2].setBounds(xstart, ystart + height * 3, width, height * 1);
		items[3] = new Item(groups[2], 1, "个人信用记录",
				"0-3 个人信贷过去有不良记录\n3-7 无个人不良信贷记录 但目前还有贷款未还清\n7-10 无个人不良信贷记录 目前没有其他贷款");

		groups[3].setText("员工素质");
		groups[3].setBounds(xstart, ystart + height * 4, width, height * 1);
		items[4] = new Item(groups[3], 1, "员工学历",
				"0-3 员工平均学历在初中及以下\n3-7 员工平均学历在高中阶段\n7-10 员工平均学历在大学及以上");

		groups[4].setText("信用水平");
		groups[4].setBounds(xstart, ystart + height * 5, width, height * 2);
		items[5] = new Item(
				groups[4],
				1,
				"银行信用记录",
				"0-3 银行信用记录不好：银行信用过去有不良记录\n3-7 银行信用记录一般：无银行不良信贷记录 但目前公司还有贷款未还清\n7-10 银行信用记录很好：无银行不良信贷记录 目前没有其他贷款");
		items[6] = new Item(
				groups[4],
				2,
				"工商业信用记录",
				"0-3 工商业信用记录不好：工商业信贷过去有不良记录\n3-7 工商业信用记录一般：无工商业不良信贷记录 但目前公司还有贷款未还清\n7-10 工商业信用记录很好：无工商业不良信贷记录 目前没有其他贷款");

		groups[5].setText("管理水平");
		groups[5].setBounds(xstart, ystart + height * 7, width, height * 2);
		items[7] = new Item(
				groups[5],
				1,
				"财务管理",
				"0-3 财务管理水平不高：财务管理能力不好，管理不规范\n3-7 财务管理水平一般：财务管理能力一般，管理较规范\n7-10财务管理水平很高：财务管理能力很好，管理很规范");
		items[8] = new Item(
				groups[5],
				2,
				"管理体制",
				"0-3 管理体制不健全，公司无完整章程，无公司价值观等理念\n3-7 管理体制一般，公司章程明确性一般，公司价值观等理念被员工贯彻执行度一般\n7-10 管理体制很健全，公司章程很明确，公司价值观等理念被员工很好地贯彻执行");

		groups[6].setText("发展潜力");
		groups[6].setBounds(xstart, ystart + height * 9, width, height * 2);
		items[9] = new Item(
				groups[6],
				1,
				"宏观经济环境",
				"0-3 宏观经济环境不好，银行流动行查，贷款政策紧缩\n3-7 宏观经济环境一般，银行流动性一般，贷款政策较宽松\n7-10 宏观经济环境很好，银行流动性高，贷款政策很宽松");
		items[10] = new Item(
				groups[6],
				2,
				"行业发展前景",
				"0-3 行业发展前景不好，受国家宏观政策打压或市场已经非常饱和或处于夕阳行业\n3-7 行业发展前景较好，国家政策给予一定扶持或市场正处于发展上升期\n7-10 行业发展前景很好，国家政策给予很大的扶持或市场正处于开拓期");

		groups[7].setText("创新能力");
		groups[7].setBounds(xstart, ystart + height * 11, width, height * 2);
		items[11] = new Item(groups[7], 1, "创新投入",
				"0-3 创新投入相对于同行业平均水平很少\n3-7 创新投入相对于同行业平均水平一般\n7-10 创新投入相对于同行业平均水平很多");
		items[12] = new Item(
				groups[7],
				2,
				"创新效果",
				"0-3 创新效果不好，没有达到预定的销售目标\n3-7 创新效果一般，虽然达到预定销售量但成本收入比增加\n7-10 创新效果很好，达到预定销售目标且成本收入比没有增加");

	}

	public boolean isInputsValid() {
		for (int i = 0; i < itemsNum; i++) {
			if (!items[i].isValidInput()) {
				return false;
			}
		}
		return true;
	}

	public void setInputTextsBackground() {
		for (int i = 0; i < itemsNum; i++) {
			if (!items[i].isValidInput()) {
				items[i].setTextBackground(SWT.COLOR_RED);
			} else {
				items[i].setTextBackground(SWT.COLOR_WHITE);
			}
		}
	}

	public void setFuzzyOutputs() {
		Fuzzy fuzzy = new Fuzzy();
		fuzzyOutputs = new double[groupsNum];

		fuzzyOutputs[0] = fuzzy.twoInputFuzzy(items[0].getScore(),
				items[1].getScore());
		fuzzyOutputs[1] = fuzzy.oneInputFuzzy(items[2].getScore());
		fuzzyOutputs[2] = fuzzy.oneInputFuzzy(items[3].getScore());
		fuzzyOutputs[3] = fuzzy.oneInputFuzzy(items[4].getScore());
		fuzzyOutputs[4] = fuzzy.twoInputFuzzy(items[5].getScore(),
				items[6].getScore());
		fuzzyOutputs[5] = fuzzy.twoInputFuzzy(items[7].getScore(),
				items[8].getScore());
		fuzzyOutputs[6] = fuzzy.twoInputFuzzy(items[9].getScore(),
				items[10].getScore());
		fuzzyOutputs[7] = fuzzy.twoInputFuzzy(items[11].getScore(),
				items[12].getScore());
	}

	public double getFuzzyScore() {
		double sum = 0;
		for (int i = 0; i < fuzzyOutputs.length; i++) {
			sum += fuzzyOutputs[i];
		}
		return sum;
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

	// 查询客户定性得分
	public void createFindCustomerFuzzyComposite(final Composite shell,
			Composite composite) {

		// createFindCustomer(shell, composite);
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

		Label label = new Label(composite, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label.setBounds(150, 110, 100, 21);
		label.setText("客户定性得分");
		composite.setVisible(false);

		String colname[] = { "定性分析项", "得分（满分10分）" };
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);

		TableColumn col1 = new TableColumn(table, SWT.LEFT);
		col1.setWidth(200);
		col1.setText(colname[0]);

		TableColumn col2 = new TableColumn(table, SWT.LEFT);
		col2.setWidth(189);
		col2.setText(colname[1]);

		table.setBounds(27, 140, 394, 218);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

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
							} else {
								TableItem[] tableItem = new TableItem[9];
								for (int i = 0; i < tableItem.length; i++) {
									tableItem[i] = new TableItem(table,
											SWT.LEFT);
								}

								String[] t0 = { "管理者素质和能力",
										Double.toString(expertData[0]) };
								tableItem[0].setText(t0);
								String[] t1 = { "管理层素质",
										Double.toString(expertData[1]) };
								tableItem[1].setText(t1);
								String[] t2 = { "个人信用",
										Double.toString(expertData[2]) };
								tableItem[2].setText(t2);
								String[] t3 = { "员工素质",
										Double.toString(expertData[3]) };
								tableItem[3].setText(t3);
								String[] t4 = { "信用水平",
										Double.toString(expertData[4]) };
								tableItem[4].setText(t4);
								String[] t5 = { "管理水平",
										Double.toString(expertData[5]) };
								tableItem[5].setText(t5);
								String[] t6 = { "发展潜力",
										Double.toString(expertData[6]) };
								tableItem[6].setText(t6);
								String[] t7 = { "创新能力",
										Double.toString(expertData[7]) };
								tableItem[7].setText(t7);
								String[] t8 = { "定性分析总得分",
										Double.toString(expertData[8]) + "/80" };
								tableItem[8].setText(t8);
								// tableItem.setText(customerData[i]);
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
}
