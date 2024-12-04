package com.practica.controller.dao;

import com.practica.controller.dao.implement.AdapterDao;
import com.practica.controller.dao.implement.JsonFileManager;
import com.practica.controller.exception.DuplicateDataException;
import com.practica.controller.exception.EmptyFieldException;
import com.practica.controller.models.Transaccion;
import com.practica.controller.models.enumeration.UsoGenerador;
import com.practica.controller.tda.list.LinkedList;

public class TransaccionDao extends AdapterDao<Transaccion> {
    Transaccion transaccion;
    
    public TransaccionDao() {
        super(Transaccion.class);
    }

    // Get & Set ========================================================================================

    public Transaccion getTransaccion() {
        if(this.transaccion == null) {
            this.transaccion = new Transaccion();
        }
        return this.transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public Transaccion transaccionFromJson(String transaccion) {
        this.transaccion = gson.fromJson(transaccion, Transaccion.class);
        return this.getTransaccion();
    }
    
    // ASIGNAR VALOR AUTOMÁTICO ============================================================================

    public void asignarPrecioFinalTransaccion() throws Exception {
        final Integer nGeneradores = this.getTransaccion().getCantidadGeneradores();
        final Integer generadorId = this.getTransaccion().getGeneradorId();
        final Float precioGenerador = new GeneradorDao().getGeneradorById(generadorId).getPrecio();

        this.getTransaccion().setPrecioFinal(precioGenerador*nGeneradores);
    }

    // OPERACIONES CRUD ======================================================================================

    public Transaccion save() throws Exception {
        this.asignarPrecioFinalTransaccion();

        if(!isThereAreAllFields()) throw new EmptyFieldException("Campo Vacío en TransaccionDao.save()");
        if(existeFamiliaConGenerador()) throw new DuplicateDataException
            ("Ya se encuentra registrado Familia "+ this.getTransaccion().getFamiliaId() + 
            " con generador " + this.getTransaccion().getGeneradorId());
        if(existeNumeroTransaccion()) throw new DuplicateDataException("Ya existe Transaccion con número: " + this.getTransaccion().getNumeroTransaccion());
        
        new FamiliaDao().getFamiliaById(this.getTransaccion().getFamiliaId());
        new GeneradorDao().getGeneradorById(this.getTransaccion().getGeneradorId());
        new CrudOperationManager().registerNewCrudOperation(this.getTransaccion(), "SAVE");

        Integer currentId = JsonFileManager.readAndUpdateCurrentIdOf(className);
        this.getTransaccion().setId(currentId);

        return persist(transaccion);
    }

    public Transaccion getTransaccionById(Integer id) throws Exception {
        new CrudOperationManager().registerNewCrudOperation(this.get(id), "QUERY");
        return this.get(id);
    }

    public Transaccion updateTransaccion(Transaccion transaccion) throws Exception {
        Integer id = this.getTransaccion().getId();
        asignarPrecioFinalTransaccion();
        if(existeNumeroTransaccion(true)) throw new DuplicateDataException("Ya existe Transaccion con número: " + this.getTransaccion().getNumeroTransaccion());
        if(!isThereAreAllFields()) throw new EmptyFieldException("Campo vacío en TransaccionDao.updateTransaccion() ");
        new CrudOperationManager().registerNewCrudOperation(this.getTransaccion(), "UPDATE");
        return merge(transaccion, id);
    }

    public Transaccion deleteTransaccion(Integer id) throws Exception {
        new CrudOperationManager().registerNewCrudOperation(this.get(id), "DELETE");
        return remove(id);
    }

    // VALIDACIONES ========================================================================================
    
    public Boolean isThereAreAllFields() {
        if(this.getTransaccion().getNumeroTransaccion() == null) return false;
        if(this.getTransaccion().getFamiliaId() == null) return false;
        if(this.getTransaccion().getUsoGenerador() == null) return false;
        if(this.getTransaccion().getCantidadGeneradores() == null) return false;
        if(this.getTransaccion().getPrecioFinal() == null) return false;
        return true;
    }    

    public Boolean existeNumeroTransaccion(Boolean forUpdate) {
        if(!forUpdate)
            return !listAll().binaryLinearSearch("numeroTransaccion", this.getTransaccion().getNumeroTransaccion()).isEmpty();
        
        LinkedList<Transaccion> list = listAll();
        try {
            list.delete(list.getIndexOf("id", this.getTransaccion().getId()));
        } catch (Exception e) {
            return false;
        }

        return !list.binaryLinearSearch("numeroTransaccion", this.getTransaccion().getNumeroTransaccion()).isEmpty();
    }

    public Boolean existeNumeroTransaccion() {
        return existeNumeroTransaccion(false);
    }

    // Relaciones (Generador, Familia) ====================================================================
    
    public Transaccion[] cascade(Integer id, String className) throws Exception {
        if(className.equals("Familia")) {
            
            // Throw Exception if FamiliaId doesn't exists
            new FamiliaDao().getFamiliaById(id);
        } else if (className.equals("Generador")) {

            new GeneradorDao().getGeneradorById(id);
        } else {
            return null;
        }
        return deleteTransacciones(allTransaccionesWith(id, className).toArray(this.getTransaccion().getClass()));
    }

    /* En este Caso classNameT puede tomar los valores: "Familia" y "Generador" */
    public LinkedList<Transaccion> allTransaccionesWith(Integer id, String classNameT) throws Exception {
        LinkedList<Transaccion> list = listAll();
        if(classNameT.equals("Generador")) {
            list = list.searchBy("GeneradorId", id);
        } else if(classNameT.equals("Familia")) {
            list = list.searchBy("FamiliaId", id);
        } else {
            return new LinkedList<>();
        }
        return list;
    }

    public Transaccion[] deleteTransacciones(Transaccion... transaccionesDelete) throws Exception {
        for(int i = 0; i < transaccionesDelete.length; i++) {
            deleteTransaccion(transaccionesDelete[i].getId());
        }
        return transaccionesDelete;
    }

    public Boolean existeFamiliaConGenerador() throws Exception {
        Transaccion[] array = getArray();
        for(Transaccion t : array) {
            if(t.getFamiliaId().equals(this.getTransaccion().getFamiliaId())) {
                if(t.getGeneradorId().equals(this.getTransaccion().getGeneradorId())) {
                    return true;
                }
            }
        }
        return false;
    }

    // ORDENACIÓN Y BÚSQUEDA =================================================================================

    public Transaccion[] sort(String attribute, Integer orden, Integer tipoOrdenacion) throws Exception
    {
        LinkedList<Transaccion> list = listAll();
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
        return list.toArray();
    }

    public Transaccion[] search(String attribute, String x) throws Exception {
        LinkedList<Transaccion> list = listAll();
        if(attribute.equalsIgnoreCase("NumeroCompra"))
            return list.binaryLinearSearch(attribute, x).toArray(getTransaccion().getClass());  
        else if (attribute.equalsIgnoreCase("familiaId") || attribute.equalsIgnoreCase("generadorId") || attribute.equalsIgnoreCase("cantidadGeneradores"))
            return list.binaryLinearSearch(attribute, Integer.parseInt(x)).toArray(getTransaccion().getClass());
        else if (attribute.equalsIgnoreCase("usoGenerador"))
            return list.binaryLinearSearch(attribute, UsoGenerador.valueOf(x)).toArray(getTransaccion().getClass());
        else 
            return list.binaryLinearSearch(attribute, Float.parseFloat(x)).toArray(getTransaccion().getClass());
    }
}
