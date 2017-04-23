package model;
// Generated 2017-04-23 12:02:08 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * PomiarTemperatura generated by hbm2java
 */
public class PomiarTemperatura  implements java.io.Serializable {


     private int pomiarId;
     private int sprzetId;
     private Date momentPomiaru;
     private BigDecimal pomiarTemp;

    public PomiarTemperatura() {
    }

	
    public PomiarTemperatura(int pomiarId, int sprzetId, Date momentPomiaru) {
        this.pomiarId = pomiarId;
        this.sprzetId = sprzetId;
        this.momentPomiaru = momentPomiaru;
    }
    public PomiarTemperatura(int pomiarId, int sprzetId, Date momentPomiaru, BigDecimal pomiarTemp) {
       this.pomiarId = pomiarId;
       this.sprzetId = sprzetId;
       this.momentPomiaru = momentPomiaru;
       this.pomiarTemp = pomiarTemp;
    }
   
    public int getPomiarId() {
        return this.pomiarId;
    }
    
    public void setPomiarId(int pomiarId) {
        this.pomiarId = pomiarId;
    }
    public int getSprzetId() {
        return this.sprzetId;
    }
    
    public void setSprzetId(int sprzetId) {
        this.sprzetId = sprzetId;
    }
    public Date getMomentPomiaru() {
        return this.momentPomiaru;
    }
    
    public void setMomentPomiaru(Date momentPomiaru) {
        this.momentPomiaru = momentPomiaru;
    }
    public BigDecimal getPomiarTemp() {
        return this.pomiarTemp;
    }
    
    public void setPomiarTemp(BigDecimal pomiarTemp) {
        this.pomiarTemp = pomiarTemp;
    }




}


