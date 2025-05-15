package metier.modele;


import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ytaharaste
 */
public class Stats {
    private String stat_ips;
    private String stat_geo;
    private String nb_soutiens;
    private String duree_soutiens;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Override
    public String toString() {
        return "Stats{" + "stat_ips=" + stat_ips + ", stat_geo=" + stat_geo + ", nb_soutiens=" + nb_soutiens + ", duree_soutiens=" + duree_soutiens + ", id=" + id + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Stats other = (Stats) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public void setStat_ips(String stat_ips) {
        this.stat_ips = stat_ips;
    }

    public void setStat_geo(String stat_geo) {
        this.stat_geo = stat_geo;
    }

    public void setNb_soutiens(String nb_soutiens) {
        this.nb_soutiens = nb_soutiens;
    }

    public void setDuree_soutiens(String duree_soutiens) {
        this.duree_soutiens = duree_soutiens;
    }

    public String getStat_ips() {
        return stat_ips;
    }

    public String getStat_geo() {
        return stat_geo;
    }

    public String getNb_soutiens() {
        return nb_soutiens;
    }

    public String getDuree_soutiens() {
        return duree_soutiens;
    }

    public Long getId() {
        return id;
    }
}
