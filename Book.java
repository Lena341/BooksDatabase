package bookDatabase;

public class Book 
{
     private String author;
     private String title;
     private String publishingHouse;
     
     public Book()
     {
    	 author="";
    	 title="";
    	 publishingHouse="";
     }
     
     public Book(String auth,String t,String pHouse)
     {
    	 author=auth;
    	 title=t;
    	 publishingHouse=pHouse;
     }
     
     public void setAuthor(String auth)
     {
    	 author=auth;
     }
     
     public void setTitle(String t)
     {
    	 title=t;
     }
     
     public void setPublishingHouse(String pHouse)
     {
    	 publishingHouse=pHouse;
     }
     
     public String getAuthor()
     {
    	 return author;
     }
     
     public String getTitle()
     {
    	 return title;
     }
     
     public String getPublishingHouse()
     {
    	 return publishingHouse;
     }
     
     public String toString()
     {
    	 return author+" "+title+" "+publishingHouse+" ";
     }
}
