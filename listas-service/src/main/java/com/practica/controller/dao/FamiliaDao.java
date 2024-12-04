package com.practica.controller.dao;

import com.practica.controller.dao.implement.AdapterDao;
import com.practica.controller.dao.implement.JsonFileManager;
import com.practica.controller.exception.DuplicateDataException;
import com.practica.controller.exception.EmptyFieldException;
import com.practica.controller.models.Familia;
import com.practica.controller.models.enumeration.Canton;
import com.practica.controller.models.enumeration.NivelSocioeconomico;
import com.practica.controller.tda.list.LinkedList;

public class FamiliaDao extends AdapterDao<Familia> {
    Familia familia;
    
    public FamiliaDao() {
        super(Familia.class);
    }

    // Get & Set ==============================================================================

    public Familia getFamilia() {
        if(this.familia == null) {
            this.familia = new Familia();
        }
        return this.familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Familia familiaFromJson(String familia) {
        this.familia = gson.fromJson(familia, Familia.class);
        return this.getFamilia();
    }

    // LÓGICA DE NIVEL SOCIOECONOMICO ===============================================================
    

    //TODO: ESTUDIAR NORMALIZACIÓN, MEDIA PONDERADA, (QUÉ ES PONDERACIÓN?)
    public void asignarNivelSocioEconomico() {
        Float ingresosMensuales = this.getFamilia().getIngresosMensuales();
        Integer nroIntegrantes = this.getFamilia().getNroIntegrantes();

        float ingresosNormalizados = (ingresosMensuales - 5000) / (10000 - 5000);
        ingresosNormalizados = Math.max(0, Math.min(1, ingresosNormalizados)); 

        float integrantesNormalizados = (nroIntegrantes - 1) / (10 - 1);
        integrantesNormalizados = Math.max(0, Math.min(1, integrantesNormalizados)); 

        float indiceSocioeconomico = (ingresosNormalizados * 0.7f) + (integrantesNormalizados * 0.3f);


        if (indiceSocioeconomico >= 0.7f) {
            this.getFamilia().setNivelSocioeconomico(NivelSocioeconomico.ALTO);
        } else if (indiceSocioeconomico >= 0.4f) {
            this.getFamilia().setNivelSocioeconomico(NivelSocioeconomico.MEDIO);
        } else {
            this.getFamilia().setNivelSocioeconomico(NivelSocioeconomico.BAJO);
        }
    }

    // CRUD OPERATIONS ==================================================================================
    
    public Familia save() throws Exception {
        asignarNivelSocioEconomico();
        validateDataAndRegisterOperation("SAVE");
        Integer currentId = JsonFileManager.readAndUpdateCurrentIdOf(className); //Classname = Familia
        this.getFamilia().setId(currentId);
        return persist(familia);
    }

    public Familia getFamiliaById(Integer id) throws Exception {
        return this.get(id);
    }

    public Familia updateFamilia() throws Exception {
        final Integer id = this.getFamilia().getId();
        asignarNivelSocioEconomico();
        if (!isThereAreAllFields()) 
            throw new EmptyFieldException("Campo vacío en FamiliaDao.updateFamilia()");
        if(existsFamilyCode(true)) throw new DuplicateDataException("Ya existe Familia con el código: " + this.getFamilia().getCodigoFamilia()); 
        new CrudOperationManager().registerNewCrudOperation(this.getFamilia(), "UPDATE");
        return merge(this.getFamilia(), id);
    }

    public Familia deleteFamilia(Integer id) throws Exception {
        new CrudOperationManager().registerNewCrudOperation(this.get(id), "DELETE");
        new TransaccionDao().cascade(id, className);
        return remove(id);
    }

    // VALIDADORES ===================================================================================

    public Boolean isThereAreAllFields() {
        if(this.getFamilia().getApellidosRepresentantes() == null) return false;
        if(this.getFamilia().getNivelSocioeconomico() == null) return false;
        if(this.getFamilia().getNroIntegrantes() == null) return false;
        if(this.getFamilia().getIngresosMensuales() == null) return false;
        if(this.getFamilia().getCanton() == null) return false;
        if(this.getFamilia().getCodigoFamilia() == null) return false;
        return true;
    }

    public Boolean familiaCodeisCorrect() {
        final String codigo = this.getFamilia().getCodigoFamilia();
        if (codigo.length() != 5) {
            return false;
        }
        return true;
    }

    public Boolean existsFamilyCode(Boolean forUpdate) {
        if (!forUpdate) {
            return !listAll().binaryLinearSearch("codigoFamilia", this.getFamilia().getCodigoFamilia()).isEmpty();
        } else {
            LinkedList<Familia> list = listAll();
            try {
                list.delete(list.getIndexOf("id", this.getFamilia().getId()));
            } catch (Exception e) {
                return false;
            }
            return !list.binaryLinearSearch("codigoFamilia", this.getFamilia().getCodigoFamilia()).isEmpty();
        }
    }
    
    public Boolean existsFamilyCode() {
        return existsFamilyCode(false);
    }

    public void validateFamiiliaData() throws Exception {
        if (!isThereAreAllFields()) {
            throw new EmptyFieldException("Campo vacío en FamiliaDao.updateFamilia()");
        } else if (existsFamilyCode()) {
            throw new DuplicateDataException("Ya existe Familia con el código: " + this.getFamilia().getCodigoFamilia());
        } else if(!familiaCodeisCorrect()) {
            throw new EmptyFieldException("Codigo de Familia incorrecto");
        } 
    }

    public void validateDataAndRegisterOperation(String crudOperation) throws Exception {
        validateFamiiliaData();
        new CrudOperationManager().registerNewCrudOperation(this.getFamilia(), crudOperation);
    }

    public void validateDataAndRegisterOperation(Integer id, String crudOperation) throws Exception {
        this.familia = this.getFamiliaById(id);
        validateDataAndRegisterOperation(crudOperation);
    }


    /// BEGIN ORDENACIÓN Y BÚSQUEDA ==========================================================================
    
    public Familia[] sort(String attribute, Integer orden, Integer tipoOrdenacion) throws Exception
    {
        LinkedList<Familia> list = listAll();
        switch (tipoOrdenacion) { //quick, merge o shell
            case 0:
                list.quickSort(attribute, orden);
                break;
            case 1:
                list.mergeSort(attribute, orden);
                break;
            case 2:
                list.shellSort(attribute,orden);
                break;
            default:
                System.out.println("USUARIO BURRO!");
                break;
        }
        return list.toArray(this.getFamilia().getClass());
    }


    public Familia[] search(String attribute, String x) throws Exception {
        LinkedList<Familia> list = listAll();
        if(attribute.equalsIgnoreCase("codigoFamilia"))
            return list.binaryLinearSearch(attribute, x).toArray(this.getFamilia().getClass());  
        if(attribute.equalsIgnoreCase("apellidosRepresentantes"))
            return list.searchBy(attribute, x).toArray(this.getFamilia().getClass()); 
        else if (attribute.equalsIgnoreCase("nivelSocioEconomico")) 
            return list.binaryLinearSearch(attribute, NivelSocioeconomico.valueOf(x)).toArray(getFamilia().getClass());
        else if (attribute.equalsIgnoreCase("canton")) 
            return list.binaryLinearSearch(attribute, Canton.valueOf(x)).toArray(getFamilia().getClass());
        else if (attribute.equalsIgnoreCase("ingresosMensuales")) 
            return list.binaryLinearSearch(attribute, Float.valueOf(x)).toArray(getFamilia().getClass());
        else 
            return list.searchBy(attribute, Integer.valueOf(x)).toArray(this.getFamilia().getClass());
    }

    /// END ORDENACIÓN Y BÚSQUEDA ==========================================================================
    
    
    
}
