package bookDatabase;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.sql.*;

public class BookFrame extends JFrame
{
     private JButton addButton,exportButton;
     private JPanel buttonPanel;
     private JTextArea showArea;
     private Connection con=null;
     private String sql=null;
     
     private void createBookDB()
     {
    	 try
    	 {
    		 con=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;integratedSecurity=true;");
    		 Statement stmt=con.createStatement();
    		 sql="CREATE DATABASE Books";
    		 stmt.executeUpdate(sql);
    		 System.out.println("Database was created successfully");
    		 
    	 }
    	 catch(Exception ex)
 		{
 			System.out.println(ex.getMessage());
 		}
    	 
     }
     
     public void createTable()
     {
    	 try
    	 {
    		 con=DriverManager.getConnection("jdbc:sqlserver://LAPTOP-B566P0U1:1433;databaseName=Books;integratedSecurity=true;");
    		 Statement stmt=con.createStatement();
    		 sql="CREATE TABLE Newbooks"+
    		 "(author NVARCHAR(20),\n"+
    		 "title NVARCHAR(30) NOT NULL PRIMARY KEY,\n"+
    		 "publishingHouse NVARCHAR(20) NOT NULL);";
    		 stmt.executeUpdate(sql);
    		 System.out.println("Table was created successfully");
    	 }
    	 
    	 catch(Exception ex)
    	 {
    		 System.out.println(ex.getMessage());
    	 }
     }
     
     public void selectFromDB()
     {
    	 showArea.setText("");
    	 try
    	 {
    		 sql="SELECT * FROM Newbooks";
    		 Statement stm=con.createStatement();
    		 ResultSet rs=stm.executeQuery(sql);
    		 while(rs.next())
    		 {
    			 String t=rs.getString("author")+","+rs.getString("title")+","+rs.getString("publishingHouse");
    			 showArea.setText(showArea.getText()+t+"\n");
    		 }
    	 }
    	 catch(Exception ex) 
    	 {
    		 System.out.println(ex.getMessage());
    	 }
     }
     
     public boolean bookExists(Book b)
     {
    	 try
    	 {
    		 sql="SELECT * FROM Newbooks WHERE author=?"+b.getAuthor()+"title=?"+b.getTitle()+"publishingHouse=?"+b.getPublishingHouse();
    		 Statement stm=con.createStatement();
    		 ResultSet rs=stm.executeQuery(sql);
    		 int count=0;
    		 while(rs.next())
    			 count++;
    		 if(count>0) return true;
    	}
    	 catch(Exception ex)
    	 {
    		 System.out.println(ex.getMessage());
    	 }
    	 return false;
     }
     
     public void addBooks(Book b)
     {
    	 try
    	 {
    		 sql="INSERT INTO Newbooks(author,title,publishingHouse) VALUES(?,?,?)";
        	 PreparedStatement pstmt=con.prepareStatement(sql);
        	 pstmt.setString(1, b.getAuthor());
        	 pstmt.setString(2, b.getTitle());
        	 pstmt.setString(3, b.getPublishingHouse());
        	 pstmt.executeUpdate();
        }
    	 catch(Exception ex)
    	 {
    		 System.out.println(ex.getMessage());
    	 }
     }
     
    public void exportBooks()
     {
    	 try
    	 {
    		 JFileChooser chooser=new JFileChooser();
             int ret=chooser.showSaveDialog(this);
             if(ret == JFileChooser.APPROVE_OPTION)
             {
                 String filename=chooser.getSelectedFile().getAbsolutePath();
                 FileWriter fw=new FileWriter(filename);
                 PrintWriter pw=new PrintWriter(fw);
                 sql = "SELECT * FROM Newbooks";
                 Statement stmt  = con.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql);
                 while(rs.next())
                 {
                    String t=rs.getString("author")+","+rs.getString("title")+","+rs.getDouble("publishingHouse");
                    pw.println(t);
                 }
               fw.close();       
             }
    	 }
    	 catch(Exception ex)
    	 {
    		 System.out.println(ex.getMessage());
    	 }
     }
     
     public void makeButtonsPanel()
     {
    	 buttonPanel=new JPanel();
    	 add(buttonPanel);
    	 addButton=new JButton("Add books");
    	 addButton.addActionListener(new ActionListener()
    	{
    		 public void actionPerformed(ActionEvent ae)
    		 {
    			 BookDialog dialog=new BookDialog(BookFrame.this,"Enter data");
    			 Book b=dialog.getBook();
    			 if(b!=null)
    			 {
    				 boolean a=bookExists(b);
    				 if(a)
    					 JOptionPane.showMessageDialog(null, "The book already exists");
    				 else
    				 {
    					 addBooks(b);
    					 selectFromDB();
    				 }
    			 }
    			
    			 
    		 }
    	});
    	buttonPanel.add(addButton);
    	
    	exportButton=new JButton("Export DB");
    	buttonPanel.add(exportButton);
    	exportButton.addActionListener(new ActionListener()
        {
    		public void actionPerformed(ActionEvent ae)
    		{
    			exportBooks();
    		}
    	});
    	
     }
     
     public void makeShowArea()
     {
    	 showArea=new JTextArea();
         showArea.setPreferredSize( new Dimension( 700, 300 ) );
         showArea.setEditable(false);
         JScrollPane sp=new JScrollPane(showArea);
         add(sp);
     }
     
     public BookFrame(String title)
     {
    	 super(title);
    	 setSize(700,300);
    	 setResizable(true);
    	 setLayout(new FlowLayout());
    	 makeButtonsPanel();
    	 makeShowArea();
    	 createBookDB();
    	 createTable();
    	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 setVisible(true);
    	 
     }
     
}
