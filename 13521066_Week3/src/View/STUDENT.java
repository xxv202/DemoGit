package View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xuanxuanvu
 */
public class STUDENT {

    /**
     * @param args the command line arguments
     */
    private String ID;
    private String Name;
    private String Date;
    private float Math;
    private float Phy;
    private float Che;
    private float Aver;

    public STUDENT() {
    }
    
    public String getID() {
        return ID;
    }
    
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public float getMath() {
        return Math;
    }

    public void setMath(float Math) {
        this.Math = Math;
    }

    public float getPhy() {
        return Phy;
    }

    public void setPhy(float Phy) {
        this.Phy = Phy;
    }

    public float getChe() {
        return Che;
    }

    public void setChe(float Che) {
        this.Che = Che;
    }

    public float getAver() {
        return Aver;
    }

    public void setAver(float Aver) {
        this.Aver = Aver;
    }
    
    public STUDENT(String ID, String Name, String Date, float Math, float Phy, float Che)
    {
        this.ID = ID;
        this.Name = Name;
        this.Date = Date;
        this.Math = Math;
        this.Phy = Phy;
        this.Che = Che;
        this.Aver = ((Math+Phy+Che)/3f);
    }
    public static void main(String[] args) {
        // TODO code application logic here
        Form sinhvien = new Form();
        sinhvien.setVisible(true);
    }
    
}
