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

	// ���Է�������
	private int itemsNum = 13;
	private int groupsNum = 8;
	// ����group
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
		group.setText("��������");
		group.setBounds(20, 40, 145, 270);

		Group panel = new Group(this, SWT.NONE);
		panel.setText("��������");
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

		// ����һ�����Composite���ô�������������������
		final Composite composite = new Composite(page1, SWT.NONE);
		composite.setBounds(10, 10, 400, 400);
		// composite.setText("page1");
		page1.setContent(composite);// ����composite��scrolledComposite����

		// �������밴ť
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
		btnNewButton_1.setText("��������");

		// ���Խ����ť
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
		btnNewButton_2.setText("���Խ��");

		// ������ҳ�水ť
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

		// ���Է�������
		// createItems(parent, composite);
		// createFindCustomerFuzzyComposite(parent, page2);

		// �ύ��ť
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
					// ��ר��ϵͳÿ���������
					/*
					 * for(int i=0;i<fuzzyOutputs.length;i++){
					 * System.out.println(fuzzyOutputs[i]); }
					 */
					System.out.println(getFuzzyScore());

				}

			}
		});
		btnNewButton_3.setBounds(200, 1600, 72, 22);
		btnNewButton_3.setText("�ύ");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/*******************************************/

	public void createItems(final Composite shell, Composite composite) {

		createFindCustomer(shell, composite);

		// ����group
		groups = new Group[groupsNum];
		for (int i = 0; i < groupsNum; i++) {
			groups[i] = new Group(composite, SWT.None);
			groups[i].setFont(SWTResourceManager.getFont("΢���ź�", 10, SWT.BOLD));
		}

		items = new Item[itemsNum];
		int xstart = 8;
		int ystart = 110;
		int width = 453;
		int height = 110;

		groups[0].setText("���������ʺ�����");
		groups[0].setBounds(xstart, ystart + height * 0, width, height * 2);
		items[0] = new Item(
				groups[0],
				1,
				"����������",
				"0-3 ���������ʲ��ߣ������߽�����˸��ʣ�̸����˾��չĿ���ս�Է��벻����\n 3-7 ����������һ�㣺������̸�½�Ϊ��ʵ��̸����˾��չĿ���ս�Է��������\n 7-10 ���������ʺܸߣ�������̸�³�ʵ��̸����˾��չĿ���ս�Է��������");
		items[1] = new Item(groups[0], 2, "�����ߴ�ҵ����",
				"0-3 �����ߴ�ҵ������0-2��\n 3-7 �����ߴ�ҵ������3-6��\n 7-10 �����ߴ�ҵ������7�꼰����");

		groups[1].setText("���������");
		groups[1].setBounds(xstart, ystart + height * 2, width, height * 1);
		items[2] = new Item(groups[1], 1, "�����ѧ��",
				"0-3 �����ƽ��ѧ���ڸ��м�����\n 3-7 ������ƽ��ѧ���ڱ��ƽ׶�\n 7-10 ������ƽ��ѧ����˶ʿ������");

		groups[2].setText("��������");
		groups[2].setBounds(xstart, ystart + height * 3, width, height * 1);
		items[3] = new Item(groups[2], 1, "�������ü�¼",
				"0-3 �����Ŵ���ȥ�в�����¼\n3-7 �޸��˲����Ŵ���¼ ��Ŀǰ���д���δ����\n7-10 �޸��˲����Ŵ���¼ Ŀǰû����������");

		groups[3].setText("Ա������");
		groups[3].setBounds(xstart, ystart + height * 4, width, height * 1);
		items[4] = new Item(groups[3], 1, "Ա��ѧ��",
				"0-3 Ա��ƽ��ѧ���ڳ��м�����\n3-7 Ա��ƽ��ѧ���ڸ��н׶�\n7-10 Ա��ƽ��ѧ���ڴ�ѧ������");

		groups[4].setText("����ˮƽ");
		groups[4].setBounds(xstart, ystart + height * 5, width, height * 2);
		items[5] = new Item(
				groups[4],
				1,
				"�������ü�¼",
				"0-3 �������ü�¼���ã��������ù�ȥ�в�����¼\n3-7 �������ü�¼һ�㣺�����в����Ŵ���¼ ��Ŀǰ��˾���д���δ����\n7-10 �������ü�¼�ܺã������в����Ŵ���¼ Ŀǰû����������");
		items[6] = new Item(
				groups[4],
				2,
				"����ҵ���ü�¼",
				"0-3 ����ҵ���ü�¼���ã�����ҵ�Ŵ���ȥ�в�����¼\n3-7 ����ҵ���ü�¼һ�㣺�޹���ҵ�����Ŵ���¼ ��Ŀǰ��˾���д���δ����\n7-10 ����ҵ���ü�¼�ܺã��޹���ҵ�����Ŵ���¼ Ŀǰû����������");

		groups[5].setText("����ˮƽ");
		groups[5].setBounds(xstart, ystart + height * 7, width, height * 2);
		items[7] = new Item(
				groups[5],
				1,
				"�������",
				"0-3 �������ˮƽ���ߣ���������������ã������淶\n3-7 �������ˮƽһ�㣺�����������һ�㣬����Ϲ淶\n7-10�������ˮƽ�ܸߣ�������������ܺã�����ܹ淶");
		items[8] = new Item(
				groups[5],
				2,
				"��������",
				"0-3 �������Ʋ���ȫ����˾�������³̣��޹�˾��ֵ�۵�����\n3-7 ��������һ�㣬��˾�³���ȷ��һ�㣬��˾��ֵ�۵����Ա���ִ᳹�ж�һ��\n7-10 �������ƺܽ�ȫ����˾�³̺���ȷ����˾��ֵ�۵����Ա���ܺõعִ᳹��");

		groups[6].setText("��չǱ��");
		groups[6].setBounds(xstart, ystart + height * 9, width, height * 2);
		items[9] = new Item(
				groups[6],
				1,
				"��۾��û���",
				"0-3 ��۾��û������ã����������в飬�������߽���\n3-7 ��۾��û���һ�㣬����������һ�㣬�������߽Ͽ���\n7-10 ��۾��û����ܺã����������Ըߣ��������ߺܿ���");
		items[10] = new Item(
				groups[6],
				2,
				"��ҵ��չǰ��",
				"0-3 ��ҵ��չǰ�����ã��ܹ��Һ�����ߴ�ѹ���г��Ѿ��ǳ����ͻ���Ϧ����ҵ\n3-7 ��ҵ��չǰ���Ϻã��������߸���һ�����ֻ��г������ڷ�չ������\n7-10 ��ҵ��չǰ���ܺã��������߸���ܴ�ķ��ֻ��г������ڿ�����");

		groups[7].setText("��������");
		groups[7].setBounds(xstart, ystart + height * 11, width, height * 2);
		items[11] = new Item(groups[7], 1, "����Ͷ��",
				"0-3 ����Ͷ�������ͬ��ҵƽ��ˮƽ����\n3-7 ����Ͷ�������ͬ��ҵƽ��ˮƽһ��\n7-10 ����Ͷ�������ͬ��ҵƽ��ˮƽ�ܶ�");
		items[12] = new Item(
				groups[7],
				2,
				"����Ч��",
				"0-3 ����Ч�����ã�û�дﵽԤ��������Ŀ��\n3-7 ����Ч��һ�㣬��Ȼ�ﵽԤ�����������ɱ����������\n7-10 ����Ч���ܺã��ﵽԤ������Ŀ���ҳɱ������û������");

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

	// ��ѯ�ͻ�
	public void createFindCustomer(final Composite shell, Composite composite) {
		Label label3 = new Label(composite, SWT.NONE);
		label3.setBounds(30, 46, 61, 17);
		label3.setText("�ͻ�����");

		idText = new Text(composite, SWT.BORDER);
		idText.setBounds(100, 45, 125, 23);
		idText.setText(MainFrame.customerID);

		Label label4 = new Label(composite, SWT.NONE);
		label4.setBounds(30, 80, 61, 17);
		label4.setText("�ͻ�����");

		nameLabel = new Label(composite, SWT.BORDER);
		nameLabel.setBounds(100, 80, 200, 23);
		nameLabel.setText(MainFrame.customerName);

		// ��ѯ�ͻ���ť
		Button button3_6 = new Button(composite, SWT.NONE);
		button3_6.setBounds(240, 40, 80, 27);
		button3_6.setText("��ѯ�ͻ�");
		button3_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// String id = idText.getText();
				if (idText.getText().equals("")) {
					MessageBox mb = new MessageBox((Shell) shell,
							SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
					mb.setText("��ʾ");
					mb.setMessage("������id�Ž��в�ѯ�ͻ�");
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
							mb.setText("��ʾ");
							mb.setMessage("δ�ܲ�ѯ���ͻ� " + id + " ������,��ȷ��ID��");
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
						mb.setText("��ʾ");
						mb.setMessage("idֻ��Ϊ����");
						mb.open();
					}
				}
			}
		});
	}

	// ��ѯ�ͻ����Ե÷�
	public void createFindCustomerFuzzyComposite(final Composite shell,
			Composite composite) {

		// createFindCustomer(shell, composite);
		Label label3 = new Label(composite, SWT.NONE);
		label3.setBounds(30, 46, 61, 17);
		label3.setText("�ͻ�����");

		idText = new Text(composite, SWT.BORDER);
		idText.setBounds(100, 45, 125, 23);
		idText.setText(MainFrame.customerID);

		Label label4 = new Label(composite, SWT.NONE);
		label4.setBounds(30, 80, 61, 17);
		label4.setText("�ͻ�����");

		nameLabel = new Label(composite, SWT.BORDER);
		nameLabel.setBounds(100, 80, 200, 23);
		nameLabel.setText(MainFrame.customerName);

		Label label = new Label(composite, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("΢���ź�", 12, SWT.NORMAL));
		label.setBounds(150, 110, 100, 21);
		label.setText("�ͻ����Ե÷�");
		composite.setVisible(false);

		String colname[] = { "���Է�����", "�÷֣�����10�֣�" };
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

		// ��ѯ�ͻ���ť
		Button button3_6 = new Button(composite, SWT.NONE);
		button3_6.setBounds(240, 40, 80, 27);
		button3_6.setText("��ѯ�ͻ�");
		button3_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// String id = idText.getText();
				if (idText.getText().equals("")) {
					MessageBox mb = new MessageBox((Shell) shell,
							SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
					mb.setText("��ʾ");
					mb.setMessage("������id�Ž��в�ѯ�ͻ�");
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
							mb.setText("��ʾ");
							mb.setMessage("δ�ܲ�ѯ���ͻ� " + id + " ������,��ȷ��ID��");
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
								mb.setText("��ʾ");
								mb.setMessage("�ͻ� " + id + " ��δ�ж��Է������");
								mb.open();
							} else {
								TableItem[] tableItem = new TableItem[9];
								for (int i = 0; i < tableItem.length; i++) {
									tableItem[i] = new TableItem(table,
											SWT.LEFT);
								}

								String[] t0 = { "���������ʺ�����",
										Double.toString(expertData[0]) };
								tableItem[0].setText(t0);
								String[] t1 = { "���������",
										Double.toString(expertData[1]) };
								tableItem[1].setText(t1);
								String[] t2 = { "��������",
										Double.toString(expertData[2]) };
								tableItem[2].setText(t2);
								String[] t3 = { "Ա������",
										Double.toString(expertData[3]) };
								tableItem[3].setText(t3);
								String[] t4 = { "����ˮƽ",
										Double.toString(expertData[4]) };
								tableItem[4].setText(t4);
								String[] t5 = { "����ˮƽ",
										Double.toString(expertData[5]) };
								tableItem[5].setText(t5);
								String[] t6 = { "��չǱ��",
										Double.toString(expertData[6]) };
								tableItem[6].setText(t6);
								String[] t7 = { "��������",
										Double.toString(expertData[7]) };
								tableItem[7].setText(t7);
								String[] t8 = { "���Է����ܵ÷�",
										Double.toString(expertData[8]) + "/80" };
								tableItem[8].setText(t8);
								// tableItem.setText(customerData[i]);
							}
						}
					} catch (java.lang.NumberFormatException e1) {
						MessageBox mb = new MessageBox((Shell) shell,
								SWT.ICON_QUESTION | SWT.OK);
						mb.setText("��ʾ");
						mb.setMessage("idֻ��Ϊ����");
						mb.open();
					}
				}
			}
		});
	}
}
