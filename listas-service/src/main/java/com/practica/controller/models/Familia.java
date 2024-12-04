package com.practica.controller.models;

import com.practica.controller.models.enumeration.Canton;
import com.practica.controller.models.enumeration.NivelSocioeconomico;

public class Familia {
    private Integer id;
    private String codigoFamilia;
    private String apellidosRepresentantes;
    private Integer nroIntegrantes;
    private Canton canton;
    private Float ingresosMensuales;
    NivelSocioeconomico nivelSocioeconomico;



    public Familia() {}

    public Familia(FamiliaBuilder builder) {
        this.id = builder.id;
        this.codigoFamilia = builder.codigoFamilia;
        this.apellidosRepresentantes = builder.apellidosRepresentantes;
        this.nroIntegrantes = builder.nroIntegrantes;
        this.canton = builder.canton;
        this.ingresosMensuales = builder.ingresosMensuales;
        this.nivelSocioeconomico = builder.nivelSocioeconomico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNroIntegrantes() {
        return nroIntegrantes;
    }

    public void setNroIntegrantes(Integer nroIntegrantes) {
        this.nroIntegrantes = nroIntegrantes;
    }

    public NivelSocioeconomico getNivelSocioeconomico() {
        return nivelSocioeconomico;
    }
    
    public void setNivelSocioeconomico(NivelSocioeconomico nivelSocioeconomico) {
        this.nivelSocioeconomico = nivelSocioeconomico;
    }

    public void setCanton(Canton canton) {
        this.canton = canton;
    }

    public Canton getCanton() {
        return canton;
    }

    public Float getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(Float ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    public String getApellidosRepresentantes() {
        return apellidosRepresentantes;
    }
    
    public void setApellidosRepresentantes(String apellidosRepresentantes) {
        this.apellidosRepresentantes = apellidosRepresentantes;
    }

    public String getCodigoFamilia() {
        return codigoFamilia;
    }

    public void setCodigoFamilia(String codigoFamilia) {
        this.codigoFamilia = codigoFamilia;
    }

    @Override
    public String toString() {
        return "Familia: " + this.apellidosRepresentantes + "\n" + 
        "id: " + this.id + "\n" + 
        "NivelSocioEconomico: " + this.nivelSocioeconomico + "\n" +
        "Canton:" + this.canton + "\n" +
        "ingresosMensuales: " + this.ingresosMensuales + "\n";
    }

    public static class FamiliaBuilder {
        private Integer id;
        private String apellidosRepresentantes;
        private String codigoFamilia;
        private Integer nroIntegrantes;
        private Canton canton;
        private Float ingresosMensuales;
        private NivelSocioeconomico nivelSocioeconomico;
        
        
        public FamiliaBuilder() {}

        public FamiliaBuilder id(Integer id) {
            this.id  = id;
            return this;
        }

        public FamiliaBuilder codigoFamilia(String codigoFamilia) {
            this.codigoFamilia = codigoFamilia;
            return this;
        }

        public FamiliaBuilder apellidosRepresentantes(String apellidosRepresentantes) {
            this.apellidosRepresentantes = apellidosRepresentantes;
            return this;
        }

        public FamiliaBuilder nroIntegrantes(Integer nroIntegrantes) {
            this.nroIntegrantes = nroIntegrantes;
            return this;
        }

        public FamiliaBuilder canton(Canton canton) {
            this.canton = canton;
            return this;
        }

        public FamiliaBuilder ingresosMensuales(Float ingresosMensuales) {
            this.ingresosMensuales = ingresosMensuales;
            return this;
        }

        public FamiliaBuilder nivelSocioEconomico(NivelSocioeconomico nivelSocioeconomico) {
            this.nivelSocioeconomico = nivelSocioeconomico;
            return this;
        }

        public Familia build() {
            return new Familia(this);
        }
    }
    

}
