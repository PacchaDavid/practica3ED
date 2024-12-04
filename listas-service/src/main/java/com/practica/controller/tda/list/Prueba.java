package com.practica.controller.tda.list;

public class Prueba {

    public Prueba() {}


    public static class Entero {
        public int numero;

        public Entero() {}

        public Entero(int numero) {
            this.numero = numero;
        }

        public int getNumero() {
            return numero;
        }
    
        @Override
        public String toString() {
            return Integer.toString(numero);
        }
    }

    public static String arrayToString (Entero[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) 
            sb.append(", "); 
        }
        return sb.append("]").toString();
    }

    public static Entero[] randomArray(int size) {
        Entero[] array = new Entero[size];
        for(int i = 0; i < size; i++) {
            final Integer entero = (int)Math.round(Math.random()*1000);
            array[i] = new Entero(entero);
        }
        return array;
    }

    public static Entero[] linearArrayEntero(int size) {
        Entero[] enteros = new Entero[size];
        for(int i = 0; i < size; i++) {
            enteros[i] = new Entero(i);
        }
        return enteros;
    }


    /* public static void main(String args[]) throws Exception
    {
        LinkedList<Entero> list1 = new LinkedList<>();
        LinkedList<Entero> list2 = new LinkedList<>();
        LinkedList<Entero> list3 = new LinkedList<>();
    
        list1.fromArrayToLinkedList(randomArray(25000));
        list2.fromArrayToLinkedList(list1.toArray());
        list3.fromArrayToLinkedList(list1.toArray());
        

        System.out.println(arrayToString(list1.toArray()));
        System.out.println("************************* Ordenado **********************************");

        long time1q = System.currentTimeMillis();
        System.err.println(arrayToString(list1.quickSort("numero", 1).toArray()));
        long time2q = System.currentTimeMillis();

        long time1m = System.currentTimeMillis();
        System.err.println(arrayToString(list1.mergeSort("numero", 1).toArray()));
        long time2m = System.currentTimeMillis();

        long time1s = System.currentTimeMillis();
        System.err.println(arrayToString(list1.shellSort("numero", 1).toArray()));
        long time2s = System.currentTimeMillis();

        Long totalQ = time2q - time1q;
        Long totalM = time2m - time1m;
        Long totalS = time2s - time1s;

        System.out.println("======================== Resumen ==============================");
        System.out.println("Tiempo de ejecucion quickSort: "+ totalQ +"ms");
        System.out.println("Tiempo de ejecucion mergeSort: "+ totalM +"ms");
        System.out.println("Tiempo de ejecucion shellSort: "+ totalS +"ms");
        System.out.println("===============================================================");

    } */

    public static void main(String args[]) throws Exception
    {
        LinkedList<Entero> list1 = new LinkedList<>();
        LinkedList<Entero> list2 = new LinkedList<>();
    
        list1.fromArrayToLinkedList(linearArrayEntero(25000));
        list2.fromArrayToLinkedList(list1.toArray());

        

        System.out.println(arrayToString(list1.toArray()));

        System.out.println("===============================================================");
        long time1b = System.currentTimeMillis();
        System.out.print("Valor Encontrado con Búsqueda Binaria: ");
        System.err.println(list1.binarySearch("numero", 15));
        long time2b = System.currentTimeMillis();

        long time1lb = System.currentTimeMillis();
        System.out.print("Valor Encontrado con Búsqueda Lineal Binaria: ");
        System.err.println(list2.binaryLinearSearch("numero", 15).get(0));
        long time2lb = System.currentTimeMillis();
        Long total1 = time2b - time1b;
        Long total2 = time2lb - time1lb;


        System.out.println("======================== Resumen ==============================");
        System.out.println("Tiempo de ejecucion Búsqueda Binaria: "+ total1 +"ms");
        System.out.println("Tiempo de ejecucion Búsqueda Lineal Binaria: "+ total2 +"ms");
        System.out.println("===============================================================");

    }
}