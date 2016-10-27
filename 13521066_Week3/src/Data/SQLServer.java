/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import View.STUDENT;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author xuanxuanvu
 */
public class SQLServer {
    
    private Connection conn;
    private String hostName;
    private String Database;
    private String Username;
    private String Password;
    private String Port;
    
    public SQLServer(String dbname, String username, String password)
    {
       hostName = "DESKTOP-AFV18TR";
       Database = dbname;
       Username = username;
       Password = password;
       Port = "1433";
    }
    
    // Các phương thức get thuộc tính
    public String gethostName() {
        return hostName;
    }
    public String getDatabase() {
        return Database;
    }
    public String getUsername() {
        return Username;
    }
    public String getPort() {
        return Port;
    }
    
    //Tạo kết nối URL tới CSDL
    public boolean Connect() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://" + hostName + ":" + Port +";databaseName=" + Database;
        conn = DriverManager.getConnection(connectionURL, Username, Password);
        if (conn != null){
            return true;
        }
        return false;
    }
     
    public PreparedStatement prepareStatement(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ResultSet getQueryData(String TableName) throws SQLException {
        String sql = "Select * FROM dbo.Student";
        PreparedStatement pstm = conn.prepareStatement(sql);
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = pstm.executeQuery();
        return rs; 
    }
    
    //Insert dữ liệu
    public boolean Insert(STUDENT SV) throws SQLException{
        String sql = "{call sp_insert_student(?,?,?,?,?,?)}";
        CallableStatement cstm = conn.prepareCall(sql);
        cstm.setString(1, SV.getID());
        cstm.setString(2, SV.getName());
        cstm.setString(3, SV.getDate());
        cstm.setFloat(4, SV.getMath());
        cstm.setFloat(5, SV.getPhy());
        cstm.setFloat(6, SV.getChe());
        int count = cstm.executeUpdate();
        if (count > 0){
            return true;
        }
        return false;
    }
    
    //Update
    public boolean Update(STUDENT SV) throws SQLException
    {
        String sql = "{call sp_update_student(?,?,?,?,?,?)}";
        CallableStatement cstm = conn.prepareCall(sql);
        //Gán tham số truy vấn
        cstm.setString(1, SV.getID());
        cstm.setString(2, SV.getName());
        cstm.setString(3, SV.getDate());
        cstm.setFloat(4, SV.getMath());
        cstm.setFloat(5, SV.getPhy());
        cstm.setFloat(6, SV.getChe());
        int count = cstm.executeUpdate();
        if (count > 0){
            return true;
        }
        return false;
    }
    
    //Delete
    public boolean Delete(String Mssv) throws SQLException
    {
        String sql = "{call sp_delete_student(?)}";
        CallableStatement cstm = conn.prepareCall(sql);
        cstm.setString(1, Mssv);
        int count = cstm.executeUpdate();
        if (count > 0){
            return true;
        }
        return false;
    }
   
}
