/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartlab.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
public class Recomendacao {

    @Id
    @GeneratedValue
    private Long id;

    private String nameAlgorithms;
    private Date timeStamp;
    private Double consenso;


    public Recomendacao() {
    }

    public String getNameAlgorithms() {
        return nameAlgorithms;
    }

    public void setNameAlgorithms(String nameAlgorithms) {
        this.nameAlgorithms = nameAlgorithms;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getConsenso() {
        return consenso;
    }

    public void setConsenso(Double consenso) {
        this.consenso = consenso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
