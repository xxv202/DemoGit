/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Data.SQLServer;
import View.STUDENT;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xuanxuanvu
 */
public class Insert {
    private SQLServer con;
    private String Student = "STUDENT";
    
    
    // <editor-fold defaultstate="collapsed" desc="Getter">
    public SQLServer getConnection() {
        return con;
    }
    // </editor-fold>
    
    public Insert (String dbname, String username, String password){
       con = new SQLServer(dbname,username,password);
    }
    
    public boolean isConnected() throws ClassNotFoundException, SQLException{
        if (con.Connect()){
            return true;
        }
        return false;
    }
    
    public ArrayList<STUDENT> getAll() throws ClassNotFoundException, SQLException{
        ArrayList<STUDENT> arrList = new ArrayList<STUDENT>();
        if (con.Connect()){
            ResultSet rs = con.getQueryData(Student);
            while (rs.next()){
                STUDENT SV = new STUDENT();
                SV.setID(rs.getString("MSSV"));
                SV.setName(rs.getString("Hoten"));
                SV.setDate(rs.getString("NgaySinh"));
                SV.setMath(rs.getFloat("DiemToan"));
                SV.setPhy(rs.getFloat("DiemLy"));
                SV.setChe(rs.getFloat("DiemHoa"));
                SV.setAver(rs.getFloat("DTB"));
                
                arrList.add(SV);
            }
        }
        return arrList;
    }
    
    public void addSV(String MSSV, String Hoten, String NgaySinh, float Toan, float Ly, float Hoa) throws Exception , ParseException{
        try{
            STUDENT SV = new STUDENT(MSSV,Hoten,NgaySinh,Toan,Ly,Hoa);
            if (con.Connect()){
                ResultSet rs = con.getQueryData(Student);
                while (rs.next()){
                    if (MSSV.equals(rs.getString("MSSV"))){
                        String format = "dd/mm/yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        Date date = sdf.parse(NgaySinh);
                        throw new Exception("MSSV " + MSSV + " đã tồn tại !");
                    }
                    
                    //Kiểm tra định dạng ngày sinh đã đúng chưa?
                    String[] arrDate = NgaySinh.split("/");
                    int month = Integer.parseInt(arrDate[0]);
                    int day = Integer.parseInt(arrDate[1]);
                    int year = Integer.parseInt(arrDate[2]);
                    
                    if ((month == 4) || (month == 6) || (month == 9) || (month == 11)){
                        if ( day < 1 || day > 30){
                            throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại \n ");
                        }
                    }
                    if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)){
                        if ( day < 1 || day > 31){
                            throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại \n");
                        }
                    }
                    if (month < 1 || month > 12){
                        throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại\n");
                    }
                    if (month == 2){
                        if (((year % 4) == 0 && (year % 100) != 0) || ((year % 400) == 0 )){
                            if (day < 1 || day > 29){
                                throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại\n"); 
                            }
                        }
                        else{
                            if (day < 1 || day > 28){
                                throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại\n"); 
                            }
                        }
                    }
                }
                if (rs.next() == false){
                    con.Insert(SV);
                }
            }
        }catch (ClassNotFoundException | SQLException ex){
            ex.getMessage();
        }
    }
    
    public void editSV(String MSSV, String Hoten, String NgaySinh, float DiemToan, float DiemLy, float DiemHoa) throws Exception{
        try{
            STUDENT SV = new STUDENT(MSSV,Hoten,NgaySinh,DiemToan,DiemLy,DiemHoa);
            if (con.Connect()){
                ResultSet rs = con.getQueryData(Student);
                while (rs.next()){
                    if (MSSV.equals(rs.getString("MSSV"))){
                    //Kiểm tra định dạng ngày sinh đã đúng chưa?
                    String[] arrDate = NgaySinh.split("/");
                    int month = Integer.parseInt(arrDate[0]);
                    int day = Integer.parseInt(arrDate[1]);
                    int year = Integer.parseInt(arrDate[2]);
                    
                    
                    if ((month == 4) || (month == 6) || (month == 9) || (month == 11)){
                        if ( day < 1 || day > 30){
                            throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại \n ");
                        }
                    }
                    if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)){
                        if ( day < 1 || day > 31){
                            throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại \n");
                        }
                    }
                    if (month < 1 || month > 12){
                        throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại\n");
                    }
                    if (month == 2){
                        if (((year % 4) == 0 && (year % 100) != 0) || ((year % 400) == 0 )){
                            if (day < 1 || day > 29){
                                throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại\n"); 
                            }
                        }
                        else{
                            if (day < 1 || day > 28){
                                throw new Exception("Ngày sinh không đúng định dạng, vui lòng nhập lại\n"); 
                            }
                        }
                    }
                        con.Update(SV);
                    }
                }
            }
        }catch (ClassNotFoundException | SQLException ex){
            ex.getMessage();
        }
    }
    
    public void delSV(String MSSV){
        try{
            if (con.Connect()){
                ResultSet rs = con.getQueryData(Student);
                while (rs.next()){
                    if (MSSV.equals(rs.getString("MSSV"))){
                        con.Delete(MSSV);
                    }
                }
            }
        }catch (SQLException ex){
            ex.getMessage();
        } catch (ClassNotFoundException ex) {
            ex.getMessage();
        }
    }

    public STUDENT searchID(String mssv){
        STUDENT SV = new STUDENT();
        try{
            if (con.Connect()){
                ResultSet rs = con.getQueryData(Student);
                while (rs.next()){
                    if (mssv.equals(rs.getString("MSSV"))){
                        SV.setID(rs.getString("MSSV"));
                        SV.setName(rs.getString("Hoten"));
                        SV.setDate(rs.getString("NgaySinh"));
                        SV.setMath(rs.getFloat("DiemToan"));
                        SV.setPhy(rs.getFloat("DiemLy"));
                        SV.setChe(rs.getFloat("DiemHoa"));
                        SV.setAver(rs.getFloat("DTB"));
                    }
                }
            }
        }catch (SQLException ex){
            ex.getMessage();
        }finally{
            return SV;
        }
    }
    
    public ArrayList<STUDENT> searchName(String HoTen){
        ArrayList<STUDENT> arrStudent = new ArrayList<STUDENT>();
        try{
            STUDENT SV = new STUDENT();
            if (con.Connect()){
                ResultSet rs = con.getQueryData(Student);
                while (rs.next()){
                    if (HoTen.equals(rs.getString("Hoten"))){
                        SV.setID(rs.getString("MSSV"));
                        SV.setName(rs.getString("Hoten"));
                        SV.setDate(rs.getString("NgaySinh"));
                        SV.setMath(rs.getFloat("DiemToan"));
                        SV.setPhy(rs.getFloat("DiemLy"));
                        SV.setChe(rs.getFloat("DiemHoa"));
                        SV.setAver(rs.getFloat("DTB"));
                        arrStudent.add(SV);
                    }
                }
            }
        } 
        catch (SQLException ex){
            ex.getMessage();
        }finally
        {
            return arrStudent;
        }
    }
    
    public STUDENT Search(String mssv, String HoTen){            
        STUDENT SV = new STUDENT();
        try{
            if (con.Connect()){
                ResultSet rs = con.getQueryData(Student);
                while (rs.next()){
                    if (HoTen.equals(rs.getString("Hoten")) && mssv.equals(rs.getString("MSSV"))){
                        SV.setID(rs.getString("MSSV"));
                        SV.setName(rs.getString("Hoten"));
                        SV.setDate(rs.getString("NgaySinh"));
                        SV.setMath(rs.getFloat("DiemToan"));
                        SV.setPhy(rs.getFloat("DiemLy"));
                        SV.setChe(rs.getFloat("DiemHoa"));
                        SV.setAver(rs.getFloat("DTB"));
                    }
                }
            }
        }catch (SQLException ex){
            ex.getMessage();
        }finally{
            return SV;
        }
    }
}
