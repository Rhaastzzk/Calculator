package 计算器;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;


public class Calculator extends JFrame{
	private static final long serialVersionUID = 1L;
	private Container container;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JTextField displayField;//计算结果显示区域
	DecimalFormat df;//设置数据精度
	String lastCommand;//保存+-*/等符号
	double result;//保存计算结果
	boolean start;//判断是否为数字的开始
	
	public Calculator() {
		super("计算器");
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		//使窗体大小不能改变
	    this.setResizable(false);
		
		container=getContentPane();
		layout=new GridBagLayout();
		container.setLayout(layout);
		
		start=true;
		result=0;
		df=new DecimalFormat("0.##############");
		lastCommand="=";
		displayField=new JTextField(20);
		displayField.setEditable(false);
		displayField.setText("0");
		displayField.setFont(new Font("Time New Roman",Font.PLAIN,20));
		displayField.setHorizontalAlignment(JTextField.RIGHT);
		
		constraints=new GridBagConstraints();
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridwidth=4;
		constraints.gridheight=1;
		constraints.fill=GridBagConstraints.BOTH;
		constraints.weightx=100;
		constraints.weighty=100;
		layout.setConstraints(displayField, constraints);
		container.add(displayField);
		ActionListener insert=new InsertAction();
		ActionListener command=new CommandAction();
		addButton("Backspace",0,1,2,1,insert);
		addButton("CE",2,1,1,1,insert);
		addButton("C",3,1,1,1,insert);
		addButton("0",0,5,1,1,insert);
		addButton("1",0,4,1,1,insert);
		addButton("2",1,4,1,1,insert);
		addButton("3",2,4,1,1,insert);
		addButton("4",0,3,1,1,insert);
		addButton("5",1,3,1,1,insert);
		addButton("6",2,3,1,1,insert);
		addButton("7",0,2,1,1,insert);
		addButton("8",1,2,1,1,insert);
		addButton("9",2,2,1,1,insert);
		addButton("+",3,5,1,1,command);
		addButton("-",3,4,1,1,command);
		addButton("*",3,3,1,1,command);
		addButton("/",3,2,1,1,command);
		addButton("=",0,6,4,1,command);
		addButton(".",2,5,1,1,insert);
		addButton("+/-",1,5,1,1,insert);//无实际价值，未实现
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void addButton(String lable,int row,int column,int width,int height,
			ActionListener listener) {
		JButton button=new JButton(lable);
		constraints.gridx=row;
		constraints.gridy=column;
		constraints.gridwidth=width;
		constraints.gridheight=height;
		constraints.fill=GridBagConstraints.BOTH;
		button.addActionListener(listener);
		layout.setConstraints(button, constraints);
		container.add(button);
	}
	
	//内部监听类InsertAction
	class InsertAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			String input=event.getActionCommand();
			if(start) {
				displayField.setText("");
				start=false;
				if(input.equals("+/-"))
					displayField.setText(displayField.getText()+"-");
			}
			if(!input.equals("+/-")) {
				if(input.equals("Backspace")) {
					String str=displayField.getText();
					if(str.length()>0)
						displayField.setText(str.substring(0,str.length()-1));
				}else if(input.equals("CE")||input.equals("C")) {
					displayField.setText("0");
					start=true;
				}else
					displayField.setText(displayField.getText()+input);
			}
		}
		
	}
	
	//内部监听类CommandAction
	class CommandAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command=e.getActionCommand();
			if(start) {
				lastCommand=command;
			}else {
				calculate(Double.parseDouble(displayField.getText()));
				lastCommand=command;
				start=true;
			}
			
		}
		
	}
	
	public void calculate(double x) {
		if(lastCommand.equals("+"))
			result+=x;
		else if(lastCommand.equals("-"))
			result-=x;
		else if(lastCommand.equals("*"))
			result*=x;
		else if(lastCommand.equals("/"))
			result/=x;
		else if(lastCommand.equals("="))
			result=x;
		displayField.setText(""+df.format(result));
	}
	
	public static void main(String[] args) {
		new Calculator();

	}

}
