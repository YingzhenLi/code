package com.sevenEleven.ui;





import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Combo;

import com.sevenEleven.calGrading.RatingExcel;
import com.sevenEleven.dbConn.DBConn;
import com.sevenEleven.readExcelAndCalculate.CompanyExcel;

public class MainFrame {

	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private Text text_9;
	private Text text_10;
	private Text text_11;
	private Text text_12;
	private Text text_13;
	private Text text_14;
	private Combo combo;
	private Combo combo_1;
	private Table table;
	private Text text_15;
	private Text text_16;
	
	private Text text3;
	private Text text3_1;
	private Text text3_2;
	private Text text3_3;
	private Text text3_4;
	private Text text3_5;
	private Text text3_6;
	private Text text3_7;
	private Text text3_8;
	private Text text3_9;
	private Text text3_10;
	private Text text3_11;
	private Text text3_12;
	private Text text3_13;
	private Text text3_14;
	private Text text3_15;
	private Combo combo3;
	private Combo combo3_1;
	
	private String filePath;
	private Label lblNewLabel_2;

	private String filePath5;
	private Label lblNewLabel5_2;
	private Text text_17;
	private Text text_18;
	
	protected static String customerID = "";
	protected static String customerName = "";
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
			MainFrame window = new MainFrame();
			window.open();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(Display.getDefault(),SWT.CLOSE|SWT.MIN);
		shell.setSize(691, 720);
		shell.setText("银行系统");
		
		// create the composite that the pages will share
		final Composite contentPanel1 = new Composite(shell, SWT.NONE);
		contentPanel1.setBounds(0, 109, 685, 583);
		
		final FuzzyComposite contentPanel2 = new FuzzyComposite(shell, SWT.NONE);
		contentPanel2.setBounds(0, 109, 685, 583);
		//contentPanel2.setVisible(false);
		
		final NeuroComposite contentPanel3 = new NeuroComposite(shell, SWT.NONE);
		contentPanel3.setBounds(0, 109, 685, 583);
		
		final ReportComposite contentPanel4 = new ReportComposite(shell, SWT.NONE);
		contentPanel4.setBounds(0, 109, 685, 583);
		
		Group group = new Group(contentPanel1, SWT.NONE);
		group.setText("\u5BA2\u6237\u8D44\u6599\u7BA1\u7406");
		group.setBounds(24, 57, 145, 170);
		
		Group group_3 = new Group(contentPanel1, SWT.SHADOW_IN);
		group_3.setText("\u64CD\u4F5C\u754C\u9762");
		group_3.setBounds(193, 57, 466, 488);
		

		
		

		//conposite1 注册用户
		final Composite composite1 = new Composite(group_3, SWT.NONE);
		composite1.setBounds(10, 27, 446, 451);
		createCustomercomposite(composite1);
		
		//composite2 查询用户
		final Composite composite2 = new Composite(group_3, SWT.NONE);
		composite2.setBounds(10, 35, 446, 443);
		createFindCustomerComposite(composite2);
		
		//conposite3 修改用户
		final Composite composite3 = new Composite(group_3, SWT.NONE);
		composite3.setBounds(10, 27, 446, 451);
		createCustomercomposite3(composite3);		

		//composite4 录入评分
		final Composite composite4 = new Composite(group_3, SWT.NONE);
		composite4.setBounds(10, 20, 446, 458);
		createCompsite4(composite4);
		
		//composite5 录入财务报表
		final Composite composite5 = new Composite(group_3, SWT.NONE);
		composite5.setBounds(10, 20, 446, 458);
		createCompsite5(composite5);
		
		//注册用户按钮
		final Button btnNewButton_1 = new Button(group, SWT.NONE);
		btnNewButton_1.setBounds(32, 32, 80, 27);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite1.setVisible(true);
				composite2.setVisible(false);
				composite3.setVisible(false);
				composite4.setVisible(false);
				composite5.setVisible(false);
				
			}
		});
		btnNewButton_1.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
				 btnNewButton_1.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			      }
		});
		btnNewButton_1.setText("\u6CE8\u518C\u5BA2\u6237");
		
		//查询客户按钮
		final Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite1.setVisible(false);
				composite2.setVisible(true);
				composite3.setVisible(false);
				composite4.setVisible(false);
				composite5.setVisible(false);
			}
		});
		button.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
				 button.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			      }
		});
		button.setBounds(32, 79, 80, 27);
		button.setText("\u67E5\u8BE2\u5BA2\u6237");
		
		//修改客户资料按钮
		final Button btnNewButton_2 = new Button(group, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite1.setVisible(false);
				composite2.setVisible(false);
				composite3.setVisible(true);
				composite4.setVisible(false);
				composite5.setVisible(false);
			}
		});
		btnNewButton_2.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
				 btnNewButton_2.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			      }
		});
		btnNewButton_2.setBounds(32, 126, 80, 27);
		btnNewButton_2.setText("\u4FEE\u6539\u5BA2\u6237\u8D44\u6599");

		Group group_1 = new Group(contentPanel1, SWT.NONE);
		group_1.setText("\u8BC4\u5206\u89C4\u5219\u7BA1\u7406");
		group_1.setBounds(24, 233, 145, 82);
		
		//录入评分规则大按钮
		final Button button_1 = new Button(group_1, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				filePath = null;
				lblNewLabel_2.setText("输入excel文件格式请严格按照excel模板填写");
				composite1.setVisible(false);
				composite2.setVisible(false);
				composite3.setVisible(false);
				composite4.setVisible(true);
				composite5.setVisible(false);
			}
		});
		button_1.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
				 button_1.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			      }
		});
		button_1.setBounds(32, 32, 80, 27);
		button_1.setText("\u5F55\u5165\u8BC4\u5206\u89C4\u5219");
		
		Group group_2 = new Group(contentPanel1, SWT.NONE);
		group_2.setText("\u5BA2\u6237\u6570\u636E\u7BA1\u7406");
		group_2.setBounds(24, 329, 145, 82);

		//录入财务报表大按钮
		final Button button_4 = new Button(group_2, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				filePath5=null;
				lblNewLabel5_2.setText("");
				text_17.setText("");
				text_18.setText("");
				composite1.setVisible(false);
				composite2.setVisible(false);
				composite3.setVisible(false);
				composite4.setVisible(false);
				composite5.setVisible(true);
			}
		});
		button_4.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
				 button_4.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			      }
		});
		button_4.setBounds(31, 34, 80, 27);
		button_4.setText("\u5F55\u5165\u8D22\u52A1\u62A5\u8868");
		
		//返回主页面按钮
		final Button btnNewButton_3 = new Button(contentPanel1, SWT.NONE);
		btnNewButton_3.setToolTipText("\u8FD4\u56DE\u4E3B\u9875");
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				contentPanel1.setVisible(false);
			}
		});
		
		btnNewButton_3.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
				 btnNewButton_3.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			      }
		});
		
		btnNewButton_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		btnNewButton_3.setImage(SWTResourceManager.getImage("img/button_returnhome.png"));
		btnNewButton_3.setBounds(24, 434, 145, 111);
		
		//显示主页面
		contentPanel1.setVisible(false);
		contentPanel2.setVisible(false);
		contentPanel3.setVisible(false);
		contentPanel4.setVisible(false);

		Image backgroundImg = new Image(Display.getCurrent(),"img/background.png");
		shell.setBackgroundImage(backgroundImg);
	
		//主界面第一个按钮
		final Button button_6 = new Button(shell, SWT.NONE);
		button_6.setImage(SWTResourceManager.getImage("img/button1.jpg"));
		button_6.setBounds(163, 215, 198, 45);
		button_6.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
			        button_6.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			        button_6.setImage(SWTResourceManager.getImage("img/button1_2.jpg"));
			      }
		});
		
		button_6.addListener(SWT.MouseExit, new Listener(){
			 public void handleEvent(Event e) {
					button_6.setImage(SWTResourceManager.getImage("img/button1.jpg"));
			      }
		});
		
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				contentPanel1.setVisible(true);
			}
		});
		
		//主界面第二个按钮
		final Button button_7 = new Button(shell, SWT.NONE);
		button_7.setImage(SWTResourceManager.getImage("img/button2.jpg"));
		button_7.setBounds(163, 292, 196, 45);
		button_7.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
			        button_7.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			        button_7.setImage(SWTResourceManager.getImage("img/button2_2.jpg"));
			      }
		});
		
		button_7.addListener(SWT.MouseExit, new Listener(){
			 public void handleEvent(Event e) {
					button_7.setImage(SWTResourceManager.getImage("img/button2.jpg"));
			      }
		});
		
		button_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				contentPanel2.setVisible(true);
			}
		});
		
		//主界面第三个按钮
		final Button button_8 = new Button(shell, SWT.NONE);
		button_8.setImage(SWTResourceManager.getImage("img/button3.jpg"));
		button_8.setBounds(163, 366, 198, 45);
		button_8.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
			        button_8.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			        button_8.setImage(SWTResourceManager.getImage("img/button3_2.jpg"));
			      }
		});
		
		button_8.addListener(SWT.MouseExit, new Listener(){
			 public void handleEvent(Event e) {
					button_8.setImage(SWTResourceManager.getImage("img/button3.jpg"));
			      }
		});
		
		button_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				contentPanel3.setVisible(true);
			}
		});
		
		//主界面第四个按钮
		final Button button_9 = new Button(shell, SWT.NONE);
		button_9.setImage(SWTResourceManager.getImage("img/button4.jpg"));
		button_9.setBounds(163, 429, 198, 45);
		button_9.addListener(SWT.MouseEnter, new Listener(){
			 public void handleEvent(Event e) {
			        button_9.setCursor(new Cursor(Display.getCurrent(),SWT.CURSOR_HAND));
			        button_9.setImage(SWTResourceManager.getImage("img/button4_2.jpg"));
			      }
		});
		
		button_9.addListener(SWT.MouseExit, new Listener(){
			 public void handleEvent(Event e) {
					button_9.setImage(SWTResourceManager.getImage("img/button4.jpg"));
			      }
		});
		
		button_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				contentPanel4.setVisible(true);
			}
		});
	}
	
	//注册用户
	public void createCustomercomposite(final Composite composite1){
		Label lblNewLabel = new Label(composite1, SWT.NONE);
		lblNewLabel.setBounds(184, 10, 78, 27);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNewLabel.setText("\u6CE8\u518C\u5BA2\u6237");
		composite1.setVisible(false);
		
				Label label = new Label(composite1, SWT.NONE);
				label.setBounds(20, 46, 61, 17);
				label.setText("\u5BA2\u6237\u540D\u79F0");
				
				Label label_1 = new Label(composite1, SWT.NONE);
				label_1.setBounds(196, 84, 61, 17);
				label_1.setText("\u6240\u5C5E\u652F\u884C");
				
				Label lblNewLabel_1 = new Label(composite1, SWT.NONE);
				lblNewLabel_1.setBounds(20, 84, 61, 17);
				lblNewLabel_1.setText("\u6210\u7ACB\u65E5\u671F");
				
				Label label_2 = new Label(composite1, SWT.NONE);
				label_2.setBounds(20, 122, 61, 17);
				label_2.setText("\u7ECF\u6D4E\u6027\u8D28");
				
				Label label_3 = new Label(composite1, SWT.NONE);
				label_3.setBounds(196, 122, 101, 17);
				label_3.setText("\u516C\u53F8\u6CD5\u5F8B\u8D23\u4EFB\u5F62\u5F0F");
				
				Label label_4 = new Label(composite1, SWT.NONE);
				label_4.setBounds(20, 160, 61, 17);
				label_4.setText("\u6240\u5C5E\u884C\u4E1A");
				
				Label label_5 = new Label(composite1, SWT.NONE);
				label_5.setBounds(196, 160, 61, 17);
				label_5.setText("\u5BA2\u6237\u7C7B\u578B");
				
				Label label_6 = new Label(composite1, SWT.NONE);
				label_6.setBounds(20, 198, 61, 17);
				label_6.setText("\u804C\u5DE5\u4EBA\u6570");
				
				Label label_7 = new Label(composite1, SWT.NONE);
				label_7.setBounds(196, 198, 144, 17);
				label_7.setText("\u6CE8\u518C\u8D44\u672C\uFF08\u4E07\u5143\uFF0C\u4EBA\u6C11\u5E01\uFF09");
				
				Label label_8 = new Label(composite1, SWT.NONE);
				label_8.setBounds(20, 236, 61, 17);
				label_8.setText("\u6240\u5728\u5730");
				
				Label label_9 = new Label(composite1, SWT.NONE);
				label_9.setBounds(196, 236, 114, 17);
				label_9.setText("\u4E3B\u8981\u4EA7\u54C1\u53CA\u9500\u552E\u5E02\u573A");
				
				Label label_10 = new Label(composite1, SWT.NONE);
				label_10.setBounds(20, 274, 61, 17);
				label_10.setText("\u96B6\u5C5E\u5173\u7CFB");
				
				Label label_11 = new Label(composite1, SWT.NONE);
				label_11.setBounds(196, 274, 101, 17);
				label_11.setText("\u5EFA\u7ACB\u4FE1\u8D37\u5173\u7CFB\u65E5\u671F");
				
				Label label_12 = new Label(composite1, SWT.NONE);
				label_12.setBounds(20, 313, 78, 17);
				label_12.setText("\u662F\u5426\u4E0A\u5E02\u516C\u53F8");
				
				Label label_13 = new Label(composite1, SWT.NONE);
				label_13.setBounds(196, 313, 80, 17);
				label_13.setText("\u6700\u65B0\u62A5\u8868\u65F6\u95F4");
				
				Label label_14 = new Label(composite1, SWT.NONE);
				label_14.setBounds(20, 350, 89, 17);
				label_14.setText("\u8D37\u6B3E\u4E3B\u529E\u884C\u540D\u79F0");
				
				Label label_15 = new Label(composite1, SWT.NONE);
				label_15.setBounds(20, 388, 61, 17);
				label_15.setText("\u8BC4\u7EA7\u53D1\u8D77\u884C");
				
				text = new Text(composite1, SWT.BORDER);
				text.setBounds(87, 43, 337, 23);
				
				text_1 = new Text(composite1, SWT.BORDER);
				text_1.setBounds(87, 81, 89, 23);
				
				text_2 = new Text(composite1, SWT.BORDER);
				text_2.setBounds(271, 81, 153, 23);
				
				text_3 = new Text(composite1, SWT.BORDER);
				text_3.setBounds(88, 122, 89, 23);
				
				text_4 = new Text(composite1, SWT.BORDER);
				text_4.setBounds(298, 119, 126, 23);
				
				text_5 = new Text(composite1, SWT.BORDER);
				text_5.setBounds(298, 157, 126, 23);
				
				text_6 = new Text(composite1, SWT.BORDER);
				text_6.setBounds(87, 195, 89, 23);
				
				text_7 = new Text(composite1, SWT.BORDER);
				text_7.setBounds(346, 195, 78, 23);
				
				text_8 = new Text(composite1, SWT.BORDER);
				text_8.setBounds(87, 233, 89, 23);
				
				text_9 = new Text(composite1, SWT.BORDER);
				text_9.setBounds(316, 233, 108, 23);
				
				text_10 = new Text(composite1, SWT.BORDER);
				text_10.setBounds(88, 271, 88, 23);
				
				text_11 = new Text(composite1, SWT.BORDER);
				text_11.setBounds(314, 271, 110, 23);
				
				text_12 = new Text(composite1, SWT.BORDER);
				text_12.setBounds(314, 310, 110, 23);
				
				text_13 = new Text(composite1, SWT.BORDER);
				text_13.setBounds(115, 347, 309, 23);
				
				combo = new Combo(composite1, SWT.READ_ONLY);
				combo.setItems(new String[] {"\u519C\u4E1A", "\u6E14\u4E1A", "\u98DF\u54C1\u5236\u9020\u4E1A", "\u7EBA\u7EC7\u670D\u88C5\u4E1A", "\u5316\u5B66\u539F\u6599\u53CA\u5316\u5B66\u5236\u54C1", "\u6A61\u80F6", "\u5851\u6599", "\u7535\u5B50\u5668\u4EF6", "\u7535\u6C14\u673A\u68B0\u53CA\u5668\u6750\u5236\u9020", "\u5176\u4ED6\u7535\u5B50\u8BBE\u5907\u5236\u9020\u4E1A", "\u91D1\u5C5E\u5236\u54C1", "\u6709\u8272\u91D1\u5C5E", "\u975E\u91D1\u5C5E\u5236\u54C1", "\u6C34\u6CE5", "\u4E13\u7528\u8BBE\u5907\u5236\u9020\u4E1A", "\u4EA4\u901A\u8FD0\u8F93\u8BBE\u5907\u5236\u9020", "\u533B\u836F\u3001\u751F\u7269\u5236\u54C1", "\u4FE1\u606F\u4F20\u8F93\u3001\u8BA1\u7B97\u673A\u670D\u52A1\u4E0E\u8F6F\u4EF6\u4E1A", "\u96F6\u552E\u4E0E\u6279\u53D1", "\u6728\u6750", "\u5BB6\u5177\u5236\u9020", "\u9020\u7EB8", "\u5370\u5237", "\u4F20\u64AD\u4E0E\u6587\u5316\u4E8B\u4E1A", "\u5176\u4ED6\u670D\u52A1\u4E1A"});
				combo.setBounds(87, 157, 89, 25);
				combo.select(0);
				
				combo_1 = new Combo(composite1, SWT.READ_ONLY);
				combo_1.setItems(new String[] {"\u662F", "\u5426"});
				combo_1.setBounds(104, 310, 72, 25);
				combo_1.select(0);
				
				text_14 = new Text(composite1, SWT.BORDER);
				text_14.setBounds(115, 388, 309, 23);
				
				//注册客户的确定按钮
				Button button_6 = new Button(composite1, SWT.NONE);
				button_6.setBounds(110, 417, 80, 27);
				button_6.setText("\u63D0\u4EA4\u8868\u5355");
				button_6.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(text.getText().equals("") || text.getText()==null){
							MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
							mb.setText("提示");
							mb.setMessage("新建客户失败，客户名称不可为空");
							mb.open();
						}else{
							int id = DBConn.insertCustomerData(text.getText(), text_1.getText(), text_2.getText(), text_3.getText(), text_4.getText(), combo.getSelectionIndex(), text_5.getText(), text_6.getText(), text_7.getText(), text_8.getText(), text_9.getText(), text_10.getText(), text_11.getText(), combo_1.getSelectionIndex(), text_12.getText(), text_13.getText(), text_14.getText());
							if(id == 0){
								MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
								mb.setText("提示");
								mb.setMessage("新建用户失败，请检查数据库是否出错");
								int rc=mb.open();
								if(rc == SWT.OK ){
									clearComposite1();
								}
							}
							else{
								MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
								mb.setText("提示");
								mb.setMessage("成功创建新客户 "+text.getText()+" 的资料,客户ID为"+id);
								int rc=mb.open();
								if(rc == SWT.OK ){
									clearComposite1();
								}
							}
						}
					}
				});
				
				//注册客户的取消按钮
				Button button_7 = new Button(composite1, SWT.NONE);
				button_7.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						clearComposite1();
						composite1.setVisible(false);
					}
				});
				button_7.setText("\u53D6\u6D88");
				button_7.setBounds(260, 417, 80, 27);
				composite1.setVisible(false);

	}
	
	//清理用户登录页面
	public void clearComposite1(){
		text.setText("");
		text_1.setText("");
		text_2.setText("");
		text_3.setText("");
		text_4.setText("");
		text_5.setText("");
		text_6.setText("");
		text_7.setText("");
		text_8.setText("");
		text_9.setText("");
		text_10.setText("");
		text_11.setText("");
		text_12.setText("");
		text_13.setText("");
		text_14.setText("");
		combo.select(0);
		combo_1.select(0);
	}
	
	//查询客户
	public void createFindCustomerComposite(final Composite composite2){
		Label label = new Label(composite2, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label.setBounds(186, 10, 73, 21);
		label.setText("\u67E5\u8BE2\u5BA2\u6237");
		composite2.setVisible(false);

		String colname[] = {"客户代号","客户名称"};
		table = new Table(composite2, SWT.BORDER | SWT.FULL_SELECTION);
		
		TableColumn col1 = new TableColumn(table, SWT.LEFT);
		col1.setWidth(200);
		col1.setText(colname[0]);
		
		TableColumn col2 = new TableColumn(table, SWT.LEFT);
		col2.setWidth(189);
		col2.setText(colname[1]);
		
		
		
		
		table.setBounds(27, 54, 394, 270);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		text_15 = new Text(composite2, SWT.BORDER);
		text_15.setBounds(121, 330, 279, 23);
		
		Label label_1 = new Label(composite2, SWT.NONE);
		label_1.setBounds(44, 333, 71, 23);
		label_1.setText("\u5BA2\u6237\u4EE3\u53F7\uFF1A");
		
		Label label_2 = new Label(composite2, SWT.NONE);
		label_2.setBounds(44, 367, 71, 21);
		label_2.setText("\u5BA2\u6237\u540D\u79F0\uFF1A");
		
		text_16 = new Text(composite2, SWT.BORDER);
		text_16.setBounds(121, 364, 279, 23);
		
		Button button_10 = new Button(composite2, SWT.NONE);
		button_10.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String customerIdText = text_15.getText();
				int customerId = -1;
				
				try {
					if(!(customerIdText ==null || customerIdText.equals("")))
					customerId = Integer.parseInt(customerIdText);
					
					String customerName = text_16.getText();
					table.setItemCount(0);
					String[][] customerData = DBConn.findCustomerDataByIdOrName(customerId, customerName);
					
					for(int i =0;i<customerData.length;i++){
						System.out.println("id "+customerData[i][0]);
						System.out.println("name "+customerData[i][1]);
					}
					
					for(int i = 0;i<customerData.length;i++){
						TableItem tableItem = new TableItem(table, SWT.LEFT);
						tableItem.setText(customerData[i]);
					}
				}catch(java.lang.NumberFormatException e1){
					MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
					mb.setText("提示");
					mb.setMessage("id只能为数字");
					mb.open();
				}	
				
				
			}
		});
		button_10.setBounds(54, 406, 80, 27);
		button_10.setText("\u67E5\u8BE2");
		
		Button button_11 = new Button(composite2, SWT.NONE);
		button_11.setBounds(320, 406, 80, 27);
		button_11.setText("\u9000\u51FA");
		button_11.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite2.setVisible(false);
			}
		});
		
		Button button_12 = new Button(composite2, SWT.NONE);
		button_12.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				table.setItemCount(0);
				String[][] customerData = DBConn.findCustomerDataByIdOrName(-1,"");
				for(int i = 0;i<customerData.length;i++){
					TableItem tableItem = new TableItem(table, SWT.LEFT);
					tableItem.setText(customerData[i]);
				}
			}
		});
		button_12.setBounds(170, 406, 118, 27);
		button_12.setText("\u67E5\u8BE2\u6240\u6709\u5BA2\u6237\u4FE1\u606F");
	}
	
	//修改用户
	public void createCustomercomposite3(final Composite composite){
	//	Label lblNewLabel3 = new Label(composite, SWT.NONE);
//		lblNewLabel3.setBounds(184, 0, 73, 21);
//		lblNewLabel3.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
//		lblNewLabel.setText("修改客户资料");
		composite.setVisible(false);
	
				Label label3 = new Label(composite, SWT.NONE);
				label3.setBounds(20, 46, 61, 17);
				label3.setText("\u5BA2\u6237\u540D\u79F0");
				
				Label label3_1 = new Label(composite, SWT.NONE);
				label3_1.setBounds(196, 84, 61, 17);
				label3_1.setText("\u6240\u5C5E\u652F\u884C");
				
				Label lblNewLabel_1 = new Label(composite, SWT.NONE);
				lblNewLabel_1.setBounds(20, 84, 61, 17);
				lblNewLabel_1.setText("\u6210\u7ACB\u65E5\u671F");
				
				Label label3_2 = new Label(composite, SWT.NONE);
				label3_2.setBounds(20, 122, 61, 17);
				label3_2.setText("\u7ECF\u6D4E\u6027\u8D28");
				
				Label label3_3 = new Label(composite, SWT.NONE);
				label3_3.setBounds(196, 122, 101, 17);
				label3_3.setText("\u516C\u53F8\u6CD5\u5F8B\u8D23\u4EFB\u5F62\u5F0F");
				
				Label label3_4 = new Label(composite, SWT.NONE);
				label3_4.setBounds(20, 160, 61, 17);
				label3_4.setText("\u6240\u5C5E\u884C\u4E1A");
				
				Label label3_5 = new Label(composite, SWT.NONE);
				label3_5.setBounds(196, 160, 61, 17);
				label3_5.setText("\u5BA2\u6237\u7C7B\u578B");
				
				Label label3_6 = new Label(composite, SWT.NONE);
				label3_6.setBounds(20, 198, 61, 17);
				label3_6.setText("\u804C\u5DE5\u4EBA\u6570");
				
				Label label3_7 = new Label(composite, SWT.NONE);
				label3_7.setBounds(196, 198, 144, 17);
				label3_7.setText("\u6CE8\u518C\u8D44\u672C\uFF08\u4E07\u5143\uFF0C\u4EBA\u6C11\u5E01\uFF09");
				
				Label label3_8 = new Label(composite, SWT.NONE);
				label3_8.setBounds(20, 236, 61, 17);
				label3_8.setText("\u6240\u5728\u5730");
				
				Label label3_9 = new Label(composite, SWT.NONE);
				label3_9.setBounds(196, 236, 114, 17);
				label3_9.setText("\u4E3B\u8981\u4EA7\u54C1\u53CA\u9500\u552E\u5E02\u573A");
				
				Label label3_10 = new Label(composite, SWT.NONE);
				label3_10.setBounds(20, 274, 61, 17);
				label3_10.setText("\u96B6\u5C5E\u5173\u7CFB");
				
				Label label3_11 = new Label(composite, SWT.NONE);
				label3_11.setBounds(196, 274, 101, 17);
				label3_11.setText("\u5EFA\u7ACB\u4FE1\u8D37\u5173\u7CFB\u65E5\u671F");
				
				Label label3_12 = new Label(composite, SWT.NONE);
				label3_12.setBounds(20, 313, 78, 17);
				label3_12.setText("\u662F\u5426\u4E0A\u5E02\u516C\u53F8");
				
				Label label3_13 = new Label(composite, SWT.NONE);
				label3_13.setBounds(196, 313, 80, 17);
				label3_13.setText("\u6700\u65B0\u62A5\u8868\u65F6\u95F4");
				
				Label label3_14 = new Label(composite, SWT.NONE);
				label3_14.setBounds(20, 350, 89, 17);
				label3_14.setText("\u8D37\u6B3E\u4E3B\u529E\u884C\u540D\u79F0");
				
				Label label3_15 = new Label(composite, SWT.NONE);
				label3_15.setBounds(20, 388, 61, 17);
				label3_15.setText("\u8BC4\u7EA7\u53D1\u8D77\u884C");
				
				Label label3_16 = new Label(composite, SWT.NONE);
				label3_16.setBounds(20, 8, 61, 17);
				label3_16.setText("\u5BA2\u6237\u4EE3\u53F7");
				
				text3 = new Text(composite, SWT.BORDER);
				text3.setBounds(87, 43, 337, 23);
				
				
				text3_1 = new Text(composite, SWT.BORDER);
				text3_1.setBounds(87, 81, 89, 23);
				
				
				text3_2 = new Text(composite, SWT.BORDER);
				text3_2.setBounds(271, 81, 153, 23);
				
				text3_3 = new Text(composite, SWT.BORDER);
				text3_3.setBounds(88, 122, 89, 23);
				
				
				text3_4 = new Text(composite, SWT.BORDER);
				text3_4.setBounds(298, 119, 126, 23);
				
				
				text3_5 = new Text(composite, SWT.BORDER);
				text3_5.setBounds(298, 157, 126, 23);
				
				
				text3_6 = new Text(composite, SWT.BORDER);
				text3_6.setBounds(87, 195, 89, 23);
				
				
				text3_7 = new Text(composite, SWT.BORDER);
				text3_7.setBounds(346, 195, 78, 23);
				
				
				text3_8 = new Text(composite, SWT.BORDER);
				text3_8.setBounds(87, 233, 89, 23);
				
				
				text3_9 = new Text(composite, SWT.BORDER);
				text3_9.setBounds(316, 233, 108, 23);
				
				
				text3_10 = new Text(composite, SWT.BORDER);
				text3_10.setBounds(88, 271, 88, 23);
				
				
				text3_11 = new Text(composite, SWT.BORDER);
				text3_11.setBounds(314, 271, 110, 23);
				
				
				text3_12 = new Text(composite, SWT.BORDER);
				text3_12.setBounds(314, 310, 110, 23);
				
				
				text3_13 = new Text(composite, SWT.BORDER);
				text3_13.setBounds(115, 347, 309, 23);
				
				
				combo3 = new Combo(composite, SWT.READ_ONLY);
				combo3.setItems(new String[] {"\u519C\u4E1A", "\u6E14\u4E1A", "\u98DF\u54C1\u5236\u9020\u4E1A", "\u7EBA\u7EC7\u670D\u88C5\u4E1A", "\u5316\u5B66\u539F\u6599\u53CA\u5316\u5B66\u5236\u54C1", "\u6A61\u80F6", "\u5851\u6599", "\u7535\u5B50\u5668\u4EF6", "\u7535\u6C14\u673A\u68B0\u53CA\u5668\u6750\u5236\u9020", "\u5176\u4ED6\u7535\u5B50\u8BBE\u5907\u5236\u9020\u4E1A", "\u91D1\u5C5E\u5236\u54C1", "\u6709\u8272\u91D1\u5C5E", "\u975E\u91D1\u5C5E\u5236\u54C1", "\u6C34\u6CE5", "\u4E13\u7528\u8BBE\u5907\u5236\u9020\u4E1A", "\u4EA4\u901A\u8FD0\u8F93\u8BBE\u5907\u5236\u9020", "\u533B\u836F\u3001\u751F\u7269\u5236\u54C1", "\u4FE1\u606F\u4F20\u8F93\u3001\u8BA1\u7B97\u673A\u670D\u52A1\u4E0E\u8F6F\u4EF6\u4E1A", "\u96F6\u552E\u4E0E\u6279\u53D1", "\u6728\u6750", "\u5BB6\u5177\u5236\u9020", "\u9020\u7EB8", "\u5370\u5237", "\u4F20\u64AD\u4E0E\u6587\u5316\u4E8B\u4E1A", "\u5176\u4ED6\u670D\u52A1\u4E1A"});
				combo3.setBounds(87, 157, 89, 25);
				combo3.select(0);
				
				
				combo3_1 = new Combo(composite, SWT.READ_ONLY);
				combo3_1.setItems(new String[] {"\u662F", "\u5426"});
				combo3_1.setBounds(104, 310, 72, 25);
				combo3_1.select(0);
				
				text3_14 = new Text(composite, SWT.BORDER);
				text3_14.setBounds(115, 388, 309, 23);
				
				text3_15 = new Text(composite, SWT.BORDER);
				text3_15.setBounds(87, 8, 125, 23);
				
				//查询客户按钮
				Button button3_6 = new Button(composite, SWT.NONE);
				button3_6.setBounds(220, 6, 80, 27);
				button3_6.setText("查询客户");
				button3_6.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						String idText = text3_15.getText();
						if(idText.equals("")){
							MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
							mb.setText("提示");
							mb.setMessage("请输入id号进行查询客户");
							mb.open();
						}else{
							
							try {
								int id = Integer.parseInt(idText);
								String customerData[] = DBConn.getCustomerDataById(id);
								
								if(customerData==null){
									MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
									mb.setText("提示");
									mb.setMessage("未能查询到客户 "+id+" 的资料,请确认ID号");
									mb.open();
									text3.setText("");
									text3_1.setText("");
									text3_2.setText("");
									text3_3.setText("");
									text3_4.setText("");
									text3_5.setText("");
									text3_6.setText("");
									text3_7.setText("");
									text3_8.setText("");
									text3_9.setText("");
									text3_10.setText("");
									text3_11.setText("");
									text3_12.setText("");
									text3_13.setText("");
									combo3.select(0);
									combo3_1.select(0);
									text3_14.setText("");
								}else{
									text3.setText(customerData[0].toString());
									text3_1.setText(customerData[1].toString());
									text3_2.setText(customerData[2].toString());
									text3_3.setText(customerData[3].toString());
									text3_4.setText(customerData[4].toString());
									text3_5.setText(customerData[6].toString());
									text3_6.setText(customerData[7].toString());
									text3_7.setText(customerData[8].toString());
									text3_8.setText(customerData[9].toString());
									text3_9.setText(customerData[10].toString());
									text3_10.setText(customerData[11].toString());
									text3_11.setText(customerData[12].toString());
									text3_12.setText(customerData[14].toString());
									text3_13.setText(customerData[15].toString());
									combo3.select(Integer.parseInt(customerData[5]));
									combo3_1.select(Integer.parseInt(customerData[13]));
									text3_14.setText(customerData[16].toString());
								}
							}catch(java.lang.NumberFormatException e1){
								MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
								mb.setText("提示");
								mb.setMessage("id只能为数字");
								mb.open();
							}
						}
						}
						
				});
				
				//修改客户的确定按钮
				Button button3_8 = new Button(composite, SWT.NONE);
				button3_8.setBounds(110, 417, 80, 27);
				button3_8.setText("确认修改");
				button3_8.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						boolean result = DBConn.updateCustomerData(text3_15.getText(),text3.getText(), text3_1.getText(), text3_2.getText(), text3_3.getText(), text3_4.getText(), combo3.getSelectionIndex(), text3_5.getText(), text3_6.getText(), text3_7.getText(), text3_8.getText(), text3_9.getText(), text3_10.getText(), text3_11.getText(), combo3_1.getSelectionIndex(), text3_12.getText(), text3_13.getText(), text3_14.getText());					
						if(result == true){
							MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
							mb.setText("提示");
							mb.setMessage("更新客户资料成功");
							mb.open();
						}else{
							MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
							mb.setText("提示");
							mb.setMessage("更新客户资料失败，请重试，如仍然失败，请检查数据库");
							mb.open();
						}
					}
				});
				
				//取消按钮
				Button button3_7 = new Button(composite, SWT.NONE);
				button3_7.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						text3.setText("");
						text3_1.setText("");
						text3_2.setText("");
						text3_3.setText("");
						text3_4.setText("");
						text3_5.setText("");
						text3_6.setText("");
						text3_7.setText("");
						text3_8.setText("");
						text3_9.setText("");
						text3_10.setText("");
						text3_11.setText("");
						text3_12.setText("");
						text3_13.setText("");
						combo3.select(0);
						combo3_1.select(0);
						text3_14.setText("");
						text3_15.setText("");
						composite.setVisible(false);
					}
				});
				button3_7.setText("\u53D6\u6D88");
				button3_7.setBounds(260, 417, 80, 27);
				composite.setVisible(false);

	}
	
	//计算评分
	public void createCompsite4(final Composite composite4){
		composite4.setVisible(false);
		final Label label = new Label(composite4, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label.setBounds(175, 20, 96, 21);
		label.setText("\u5F55\u5165\u8BC4\u5206\u89C4\u5219");
		
		
		
		lblNewLabel_2 = new Label(composite4, SWT.NONE);
		lblNewLabel_2.setBounds(54, 119, 344, 21);
		lblNewLabel_2.setText("\u8F93\u5165excel\u6587\u4EF6\u683C\u5F0F\u8BF7\u4E25\u683C\u6309\u7167excel\u6A21\u677F\u586B\u5199");
		
		//计算评分按钮
				Button button = new Button(composite4, SWT.NONE);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(filePath==null){
							MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
							mb.setText("提示");
							mb.setMessage("请先载入Excel文件");
							mb.open();
						}else{
							lblNewLabel_2.setText("正在计算评分");
							boolean result = RatingExcel.calRatingExcel(filePath);
							if(result==false){
								MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
								mb.setText("提示");
								mb.setMessage("计算评分失败，请确保Excel格式正确");
								mb.open();
								lblNewLabel_2.setText("");
							}else{
								MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
								mb.setText("提示");
								mb.setMessage("录入评分成功");
								mb.open();
								lblNewLabel_2.setText("");
							}
						}
						
					}
				});
				button.setText("\u8BA1\u7B97\u8BC4\u5206");
				button.setBounds(266, 161, 138, 138);
				Group group_4 = new Group(composite4, SWT.NONE);
				group_4.setBounds(21, 106, 402, 229);
				
				Button btnNewButton = new Button(group_4, SWT.NONE);
				btnNewButton.setBounds(20, 55, 138, 138);
				btnNewButton.setText("\u5BFC\u5165\u8BC4\u5206\u89C4\u5219excel\u8868\u683C");
				
				Button buttona = new Button(composite4, SWT.NONE);
				buttona.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						composite4.setVisible(false);
					}
				});
				buttona.setBounds(318, 375, 80, 27);
				buttona.setText("\u53D6\u6D88");
				
				Button button_10 = new Button(composite4, SWT.NONE);
				button_10.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						composite4.setVisible(false);
					}
				});
				button_10.setBounds(164, 375, 80, 27);
				button_10.setText("\u5F55\u5165\u5B8C\u6210");
				
				btnNewButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// 读取文件路径
						FileDialog filedlg = new FileDialog(shell, SWT.OPEN);
						filedlg.setText("选择ecxel文件");
						filedlg.setFilterExtensions(new String[] { "*.xls","*.xlsx" });
						filedlg.setFilterNames(new String[] { "excel文件(*.xls)","excel文件（*.xlsx）" });
						filePath = filedlg.open();
						if(!(filePath==null||filePath.equals("")  ))
							lblNewLabel_2.setText("导入Excel文件成功，请点击计算评分按钮计算评分规则");
						else lblNewLabel_2.setText("导入Excel文件失败，请重新导入");
					}
				});
	}

	//计算评分
	public void createCompsite5(final Composite composite5){
		composite5.setVisible(false);
		final Label label5 = new Label(composite5, SWT.NONE);
		label5.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		label5.setBounds(175, 20, 96, 21);
		label5.setText("录入财务报表");
				Group group_5 = new Group(composite5, SWT.NONE);
				group_5.setBounds(24, 140, 402, 229);
				
				Button btnNewButton5 = new Button(group_5, SWT.NONE);
				btnNewButton5.setBounds(20, 55, 168, 138);
				btnNewButton5.setText("\u5BFC\u5165\u5BA2\u6237\u8D22\u52A1\u62A5\u8868(excel\u683C\u5F0F)");
				
				
				
				lblNewLabel5_2 = new Label(group_5, SWT.NONE);
				lblNewLabel5_2.setBounds(20, 28, 344, 21);
				lblNewLabel5_2.setText("\u8F93\u5165excel\u6587\u4EF6\u683C\u5F0F\u8BF7\u4E25\u683C\u6309\u7167excel\u6A21\u677F\u586B\u5199");
				
				//计算公司指标
						Button button5 = new Button(group_5, SWT.NONE);
						button5.setBounds(224, 55, 168, 138);
						button5.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								if(text_17.getText()==null || text_17.getText().equals("")){
									MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
									mb.setText("提示");
									mb.setMessage("请先填写公司ID");
									mb.open();	
								}
								
								else if(filePath5==null){
									MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
									mb.setText("提示");
									mb.setMessage("请先载入Excel文件");
									mb.open();
								}else{
									lblNewLabel5_2.setText("正在计算指标");
									try {
										String[] resultSearch = DBConn.getCustomerDataById(Integer.parseInt(text_17.getText()));
										if (resultSearch == null) {
											MessageBox mb = new MessageBox(
													shell, SWT.ICON_QUESTION| SWT.OK);
											mb.setText("提示");
											mb.setMessage("该ID不存在，请输入正确的ID");
											mb.open();
											lblNewLabel5_2.setText("");
										} else {
											String result = CompanyExcel.calCompanyData(filePath5,Integer.parseInt(text_17.getText()));
											MessageBox mb = new MessageBox(shell, SWT.ICON_QUESTION| SWT.OK);
											mb.setText("提示");
											mb.setMessage(result);
											mb.open();
											lblNewLabel5_2.setText("");
										}
									} catch(java.lang.NumberFormatException e1){
										MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
										mb.setText("提示");
										mb.setMessage("id只能为数字");
										mb.open();
									}

								}
								
							}
						});
						button5.setText("\u8BA1\u7B97\u6307\u6807");
				
				Button buttona5 = new Button(composite5, SWT.NONE);
				buttona5.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						composite5.setVisible(false);
					}
				});
				buttona5.setBounds(318, 396, 80, 27);
				buttona5.setText("\u53D6\u6D88");
				
				//录入报表按钮
				Button button5_10 = new Button(composite5, SWT.NONE);
				button5_10.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						composite5.setVisible(false);
					}
				});
				button5_10.setBounds(166, 396, 80, 27);
				button5_10.setText("完成录入");
				
				btnNewButton5.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// 读取文件路径
						FileDialog filedlg = new FileDialog(shell, SWT.OPEN);
						filedlg.setText("选择ecxel文件");
						filedlg.setFilterExtensions(new String[] { "*.xls","*.xlsx" });
						filedlg.setFilterNames(new String[] { "excel文件(*.xls)","excel文件（*.xlsx）" });
						filePath5 = filedlg.open();
						if(!( filePath5==null ||filePath5.equals("")))
							lblNewLabel5_2.setText("导入Excel文件成功，请点击计算公司指标按钮计算评分规则");
						else lblNewLabel5_2.setText("导入Excel文件失败，请重新导入");
					}
				});
				Group group_4 = new Group(composite5, SWT.NONE);
				group_4.setBounds(24, 57, 395, 77);
				
				text_17 = new Text(group_4, SWT.BORDER);
				text_17.setBounds(80, 17, 213, 23);
				
				Label lblid = new Label(group_4, SWT.NONE);
				lblid.setBounds(12, 20, 61, 17);
				lblid.setText("\u5BA2\u6237\u4EE3\u53F7");
				
				Label label = new Label(group_4, SWT.NONE);
				label.setBounds(12, 50, 61, 17);
				label.setText("\u5BA2\u6237\u540D\u79F0");
				
				text_18 = new Text(group_4, SWT.BORDER | SWT.READ_ONLY);
				text_18.setBounds(80, 46, 213, 23);
				
				Button button = new Button(group_4, SWT.NONE);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						String searchID = text_17.getText();
						if(searchID == null || searchID.equals("")){
							MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
							mb.setText("提示");
							mb.setMessage("请输入查询ID");
							mb.open();
						}else{
							try{
								String[] resultName = DBConn.getCustomerDataById(Integer.parseInt(searchID));
								if(resultName == null ){
									MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
									mb.setText("提示");
									mb.setMessage("未能查询到ID为 "+searchID+" 的客户");
									mb.open();
								}else{
									text_18.setText(resultName[0]);
								}
							}catch(java.lang.NumberFormatException e1){
								MessageBox mb = new MessageBox(shell,SWT.ICON_QUESTION | SWT.OK);
								mb.setText("提示");
								mb.setMessage("id只能为数字");
								mb.open();
							}
							
						}
					}
				});
				button.setBounds(310, 13, 61, 27);
				button.setText("\u67E5\u8BE2");
	}
	
	
}
