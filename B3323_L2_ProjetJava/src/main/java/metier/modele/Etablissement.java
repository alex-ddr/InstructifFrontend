/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import com.google.maps.model.LatLng;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import metier.service.Service;
import util.EducNetApi ; 
import util.GeoNetApi;
/**
 *
 * @author ytaharaste
 */
@Entity
public class Etablissement {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    
    @Column(nullable = false, unique = true)
    String code; 
    
    Float ips;
    
    String denomination ;
    
    LatLng coord ; 
 
    
    public Etablissement() {
    }
    
    public Etablissement(String code, String denomination, Float ips, LatLng coord) {
        this.code = code;
        this.denomination = denomination;
        this.ips = ips;
        this.coord = coord;
    }

    public LatLng getCoord() {
        return coord;
    }

    public void setCoord(LatLng coord) {
        this.coord = coord;
    }

   
    
    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDenomination() {
        return denomination;
    }

    public Float getIps() {
        return ips;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIps(Float ips) {
        this.ips = ips;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Override
    public String toString() {
        return "Etablissement{" + "id=" + id + ", code=" + code + ", ips=" + ips + ", denomination=" + denomination + ", coord=" + coord + '}';
    }

    

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Etablissement other = (Etablissement) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
}
