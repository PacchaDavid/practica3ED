package com.practica.controller.tda.list;

import com.practica.controller.exception.ListEmptyException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;

    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public Node<E> getHeader() {
        return header;
    }

    public void setHeader(Node<E> header) {
        this.header = header;
    }

    public Node<E> getLast() {
        return last;
    }

    public void setLast(Node<E> last) {
        this.last = last;
    }

    public Integer getSize() {
        return size;
    }

    public Boolean isEmpty() {
        return this.header == null || this.size == 0;
    }

    public void addHeader(E info) {
        if(isEmpty()) {
            this.header = new Node<>(info);
            this.last = this.header;
        } else {
            Node<E> aux = new Node<>(info,this.header);
            this.header = aux;
        }   
        this.size++;
    }

    public void addLast(E info) {
        if (isEmpty()) {
            addHeader(info);
        } else {
            Node<E> aux = this.last;
            this.last = new Node<>(info);
            aux.setNext(last);
            this.size++;
        }
    }

    public void add(E info) {
        this.addLast(info);
    }

    public Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if(isEmpty()) {
            throw new ListEmptyException("cannot get node, list is empty");
        } else if(index.intValue() < 0 || index.intValue() >= this.size ) {
            throw new IndexOutOfBoundsException("cannot get node, index " + index + " out of bounds " + this.size);
        } else if(index.intValue() == 0) {
            return this.header;
        } else if(index.intValue() == this.size-1) {
            return this.last;
        } else {
            Node<E> aux = this.header.getNext();
            Integer count = 1;
            while(count < index) {
                aux = aux.getNext();
                count++;
            }
            return aux;
        }
    }

    public void add(E object, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if(isEmpty() || index.intValue() == 0) {
            addHeader(object);
        } else if(index.intValue() == this.size-1) {
            addLast(object);
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Index out of range");
        } else {
            Node<E> previousNode = getNode(index-1);
            Node<E> currentNode = getNode(index);
            previousNode.setNext(new Node<>(object,currentNode));
            this.size++;
        }
    }

    public E get(Integer index) throws Exception {
        return getNode(index).getInfo();
    }

    public void deleteHeader() throws ListEmptyException {
        if(isEmpty()) {
            throw new ListEmptyException("Cannot delete header, because list is emtpy");
        } else {
            Node<E> eliminar = this.header;
            this.header = this.header.getNext();
            eliminar.setNext(null);
            eliminar = null;
            this.size--;
        }
    }

    public void deleteLast() throws ListEmptyException {
        if(isEmpty()) {
            throw new ListEmptyException("Cannot delete last, because list is empty");
        } else {
            Node<E> anterior = getNode(this.size-2);
            anterior.setNext(null);
            this.last = null;
            this.last = anterior;
            this.size--;
        }
    }


    public void delete(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if(isEmpty()) {
            throw new ListEmptyException("Cannot delete, list is empty");
        } else if(index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Cannote delete, index is out of range");
        } else if(index.intValue() == 0) {
            deleteHeader();
        } else if(index.intValue() == this.size-1) {
            deleteLast();
        } else {
            Node<E> previusNode = getNode(index-1);
            Node<E> eliminar = previusNode.getNext();
            previusNode.setNext(eliminar.getNext());
            eliminar.setNext(null);
            eliminar = null;
            size--;
        }
    }

    public void update(E object, Integer index) throws Exception {
        Node<E> updateNode = getNode(index);
        updateNode.setInfo(object);
    }

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data\n");
        Node<E> node = this.header;
        while(node != null) {
            sb.append(node.getInfo());
            if(!(node.getNext() == null)) {
                sb.append(" -> ");
            }
            node = node.getNext();
        }
        return sb.toString();
    }

    public E[] toArray(Class<?> _class) throws Exception {
        E[] array = (E[])Array.newInstance(_class,this.size);

        for(int i = 0; i < this.size; i++) {
            array[i] = get(i);
        }
        return array;
    }

    public E[] toArray() throws Exception {
        Class<?> _class = this.header.getInfo().getClass();
        return toArray(_class);
    }

    public LinkedList<E> fromArrayToLinkedList(E[] array) {
        if(array == null) return null;
        reset();
        for(int i = 0; i < array.length; i++) { 
            this.add(array[i]);
        }
        return this;
    }

    public void recorrer() {
        if(isEmpty())
            return;

        Node<E> aux = this.header;
        while(aux!=null) {
            System.out.println(aux.getInfo()+",");
            aux = aux.getNext();
        }
    }
    // BEGIN QUICK SORT ============================================================================================

    // PRINCIPAL :V (QUICKSORT)
    public LinkedList<E> quickSort(String attribute, Integer orden) throws Exception {
        if(isEmpty()) {
            return this;
        }

        E[] array = this.toArray();
        final Integer high = array.length - 1;
        final Integer low = 0;

        quickSort(attribute, array, low, high, orden);

        return this.fromArrayToLinkedList(array);
    }

    private void quickSort(String attribute, E[] array, Integer low, Integer high, Integer orden) throws Exception{
        if(low < high) {
            int pivoteIndex = partition(attribute,array,low,high,orden);
            
            quickSort(attribute, array, low, pivoteIndex - 1, orden);
            quickSort(attribute, array, pivoteIndex + 1, high, orden);
        }
    }

    private Integer partition(String attribute,E[] array, Integer low, Integer high, Integer orden) throws Exception {
        E pivote = array[high];

        Integer j = low - 1;

        for (int i = low; i <= high-1; i++) {
            if(atrribute_compare(attribute,array[i],pivote,orden)) {
                j++;
                swap(array,j,i);
            }
        }

        swap(array, j + 1, high);

        return j + 1;
    }

    private void swap(E[] array, Integer i, Integer j) {
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // END QUICKSORT ===================================================================================================


    // BEGIN MERGESORT =================================================================================================
    
    private void merge(String atribute, E arr[], int l, int m, int r, Integer orden) throws Exception
    {
        Class<?> _class = this.header.getInfo().getClass();

        int n1 = m - l + 1;
        int n2 = r - m;

        E L[] = (E[])Array.newInstance(_class, n1);
        E R[] = (E[])Array.newInstance(_class, n2);


        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (atrribute_compare(atribute, L[i], R[j], orden)) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    private void mersort(String attribute, E arr[], int l, int r, Integer orden) throws Exception
    {
        if (l < r) {

            int m = l + (r - l) / 2;

            mersort(attribute,arr, l, m,orden);
            mersort(attribute,arr, m + 1, r,orden);
            merge(attribute,arr, l, m, r,orden);
        }
    }

    // PRINCIPAL :V (MERGESORT)
    public LinkedList<E> mergeSort(String attribute, Integer orden) throws Exception {
        if(isEmpty()) return this;
        E[] array = this.toArray();
        final Integer l = 0;
        final Integer r = array.length-1;
        mersort(attribute, array, l, r, orden);
        reset();
        return this.fromArrayToLinkedList(array);
    }

    // END MERGESORT ============================================================================================

    // BEGIN SHELL SORT =========================================================================================

    private int shellSort(String attribute, E[] arr, Integer orden) throws Exception
    {
        int n = arr.length;

        for (int gap = n/2; gap > 0; gap /= 2)
        {
            for (int i = gap; i < n; i += 1)
            {

                E temp = arr[i];
                int j;
                for (j = i; j >= gap && !atrribute_compare(attribute, arr[j-gap], temp, orden); j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
        }
        return 0;
    }

    // PRINCIPAL :V (SHELLSORT)
    public LinkedList<E> shellSort(String attribute, Integer orden) throws Exception {
        if(isEmpty()) return this;
        E[] array = this.toArray();
        shellSort(attribute, array, orden);
        return this.fromArrayToLinkedList(array);
    }

    // END SHELL SORT ========================================================================================

    // BEGIN BINARY SEARCH ====================================================================================

    /* En los siguientes métodos de búsqueda, el Object x es el valor del atributo del objeto que deseamos buscar en la lista
     * Por ejemplo: String : attribute='nombre', Object : x='Pablo'
      */

    private int binarySearch(E arr[], Object x, String attribute) throws Exception
    {
        int low = 0, high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (exist_attribute(arr[mid],attribute).equals(x))
                return mid;

            if (compare(exist_attribute(arr[mid], attribute), x, 1)) {
                low = mid + 1;
            }

            else
                high = mid - 1;
        }

        return -1;
    }

    // PRINCIPAL :V (binarySearch) 
    public E binarySearch(String attribute, Object x) throws Exception {
        if(isEmpty()) return null;
        try {
            E[] arr = this.toArray();
            return arr[binarySearch(arr, x, attribute)];
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Object not found");
        }
        
    }

    public Integer getIndexOf(String attribute, Object x) throws Exception {
        if(isEmpty()) return -1;
        E[] arr = this.toArray();
        return binarySearch(arr, x, attribute);
    }

    // END BINARYSEARCH ======================================================================================

    // BEGIN BINARY LINEAR SEARCH ===========================================================================

    public LinkedList<E> binaryLinearSearch(String attribute, Object x) {
        if(isEmpty()) return new LinkedList<>();

        try {
            this.mergeSort(attribute, 1);
            
            Integer index = getIndexOf(attribute, x); 
            Integer i = index.intValue(); 
            
            E obj = get(index);

            E[] arr = this.toArray();
            LinkedList<E> list = new LinkedList<>();
            while(index >= 0 && compareObjects(exist_attribute(arr[index], attribute),exist_attribute(obj, attribute))) {
                list.add(arr[index]);
                index--;
            }

            index = i+1;
            while(index < this.size && compareObjects(exist_attribute(arr[index], attribute),exist_attribute(obj, attribute))) {
                list.add(arr[index]);
                index++;
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    // END BINARY LINEAR SEARCH  ============================================================================

    // BEGIN LINEAR SEARCH ===================================================================================

    public LinkedList<E> searchBy(String attribute, Object x) throws Exception {
        LinkedList<E> list = new LinkedList<>();
        if(isEmpty()) return list;

        E[] array = this.toArray();
        for(int i = 0; i < array.length; i++) {
            if(compareObjects(exist_attribute(array[i], attribute), x)) {
                list.add(array[i]);
            }
        }

        return list;
    }

    // END LINEAR SEARCH ======================================================================================

    // COMPARATIONS
    private Boolean compare(Object a, Object b, Integer type) {
        switch (type) {
            case 0:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() > b1.doubleValue();
                } else {
                    // "casa" > "pedro"
                    return (a.toString()).compareTo(b.toString()) > 0;
                }
                // break;

            default:
                // mayor a menor
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() < b1.doubleValue();
                } else {
                    // "casa" > "pedro"
                    return (a.toString()).compareTo(b.toString()) < 0;
                }
                // break;
        }

    }

    // compare class
    private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
    }

    private Object exist_attribute(E a, String attribute) throws Exception {
        Method method = null;
        attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        attribute = "get" + attribute;
        for (Method aux : a.getClass().getMethods()) {           
            if (aux.getName().contains(attribute)) {
                method = aux;
                break;
            }
        }
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {              
                if (aux.getName().contains(attribute)) {
                    method = aux;
                    break;
                }
            }
        }
        if (method != null) {            
            return method.invoke(a);
        }
        
        return null;
    }

    /* Método para comparar parcialmente las cadenas de texto */
    // Ejemplo: "Pedro".contains("Pe") : true;
    // También compara tipos numéricos
    private Boolean compareObjects(Object a, Object b) {
        if(a instanceof Number && b instanceof Number) {
            Number a_ = (Number)a;
            Number b_ = (Number)b;
            return a_.doubleValue() == b_.doubleValue();
        } else if (a instanceof String && b instanceof String) {
            return ((String)a).contains((String)b);
        } else {
            return false;
        }
    }

}
