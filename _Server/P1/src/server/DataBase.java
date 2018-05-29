package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

public class DataBase {
	String s;
	
	public void data() {
		
	
		 try{
             Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/project","root","root");
             PreparedStatement ps = con.prepareStatement("insert into images(Image) values(?)");
             InputStream is = new FileInputStream(new File(s));
             ps.setBlob(1,is);
             ps.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Inserted");
         }catch(Exception ex){
             ex.printStackTrace();
         }
	
	
	}
}
