package bookDatabase;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookDialog extends JDialog
{
    private JTextField authorText,titleText,pubHouseText;
    private JButton okButton, cancelButton;
    private JPanel panel1,panel2;
    private boolean okflag=true;
    
    public Book getBook()
    {
		if(okflag==false)
			return null;
		else
		{
			if(authorText.getText().length()==0 || titleText.getText().length()==0 || pubHouseText.getText().length()==0)
				return null;
			return new Book(authorText.getText(),titleText.getText(),pubHouseText.getText());
		}
    	
    }
    
   
    public void makePanel1()
    {
    	panel1=new JPanel();
    	add(panel1);
    	panel1.add(new JLabel("Author"));
    	authorText=new JTextField("",20);
    	panel1.add(authorText);
    	
    	panel1.add(new JLabel("Title:"));
    	titleText=new JTextField("",30);
    	panel1.add(titleText);
    	
    	panel1.add(new JLabel("Publishing House:"));
    	pubHouseText=new JTextField("",20);
    	panel1.add(pubHouseText);
    }
    
    public void makePanel2()
    {
    	panel2=new JPanel();
    	add(panel2);
    	okButton=new JButton("OK");
    	panel2.add(okButton);
    	cancelButton=new JButton("Cancel");
    	panel2.add(cancelButton);
    	okButton.addActionListener(new ActionListener()
    			{
    		       public void actionPerformed(ActionEvent ae)
    		       {
    		    	   okflag=true;
    		    	   setVisible(true);
    		       }
    			});
    	cancelButton.addActionListener(new ActionListener()
    			{
    		       public void actionPerformed(ActionEvent ae)
    		       {
    		    	   okflag=false;
    		    	   setVisible(true);
    		       }
    			});
    	}
    
    public BookDialog(JFrame parent,String title)
    {
    	super(parent,title);
    	setModal(true);
    	setSize(700,300);
    	setResizable(true);
    	setLayout(new FlowLayout());
    	makePanel1();
    	makePanel2();
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	setVisible(true);
    }
    
}
