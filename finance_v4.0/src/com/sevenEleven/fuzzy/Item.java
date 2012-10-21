package com.sevenEleven.fuzzy;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;


public class Item {
	private Label contentLabel;
	private Label noteLabel;
	private Text scoreText;
	
	public Item(Group group, int order, String content, String note){
		contentLabel = new Label(group, SWT.NONE);
		contentLabel.setText(content);
		contentLabel.setBounds(10, 20+100*(order-1), 250, 17);
		
		scoreText = new Text(group, SWT.BORDER);
		scoreText.setBounds(300, 20+100*(order-1), 50, 23);
		//scoreText.set
		
		noteLabel = new Label(group, SWT.NONE);
		noteLabel.setText(note);
		noteLabel.setBounds(10, 48+100*(order-1), 440, 54);
		noteLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
	}
	
	public void setTextColor(int color){
		scoreText.setForeground(SWTResourceManager.getColor(color));
	}
	
	public void setTextBackground(int color){		
		scoreText.setBackground(SWTResourceManager.getColor(color));
	}
	
	public boolean isValidInput(){
		double score;
		try{
			score = Double.parseDouble(scoreText.getText());
		}catch(NumberFormatException e){
			return false;
		}
		if(score <0 || score >10)
			return false;
		return true;
		
	}
	
	public String getTextContent(){
		return scoreText.getText();
	}
	
	public double getScore(){
		return Double.parseDouble(scoreText.getText());
	}

}
