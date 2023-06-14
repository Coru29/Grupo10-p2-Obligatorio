package TADs.Lista;

public class LL<T extends  Comparable<T>> implements Lista<T>{

    NodeLinkedList<T> head;


    @Override
    public void add(T value) {
        NodeLinkedList<T> nuevoNodo = new NodeLinkedList<>();
        nuevoNodo.value = value;

        // si la lista está vacía
        if (head == null) {
            head = nuevoNodo;
        } else {
            // si la lista no está vacía
            // mientras que no esté parado en el último, sigo avanzando
            NodeLinkedList<T> tempNodeLinkedList = head;
            while (tempNodeLinkedList.next != null) {
                tempNodeLinkedList = tempNodeLinkedList.next;
            }
            // una vez ya estoy en el último, en el next pongo el que quiero poner
            tempNodeLinkedList.next = nuevoNodo;
        }
    }


    @Override
    public void remove(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("La posición no puede ser negativa.");
        }

        if (head == null) {
            throw new IllegalStateException("No se puede eliminar de una lista vacía.");
        }

        if (position == 0) {
            head = head.next;
            return;
        }

        NodeLinkedList<T> tempNodeLinkedList = head;
        for (int i = 0; i < position - 1; i++) {
            if (tempNodeLinkedList.next == null) {
                throw new IndexOutOfBoundsException("La posición está fuera de los límites de la lista.");
            }
            tempNodeLinkedList = tempNodeLinkedList.next;
        }

        if (tempNodeLinkedList.next == null) {
            throw new IndexOutOfBoundsException("La posición está fuera de los límites de la lista.");
        }

        tempNodeLinkedList.next = tempNodeLinkedList.next.next;
    }




    @Override
    public T get(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("La posición no puede ser negativa.");
        }

        if (head == null) {
            throw new IllegalStateException("No se puede obtener un elemento de una lista vacía.");
        }

        NodeLinkedList<T> tempNodeLinkedList = head;
        for (int i = 0; i < position; i++) {
            if (tempNodeLinkedList.next == null) {
                throw new IndexOutOfBoundsException("La posición está fuera de los límites de la lista.");
            }
            tempNodeLinkedList = tempNodeLinkedList.next;
        }

        return tempNodeLinkedList.value;
    }

    public int size() {
        int size = 0;
        NodeLinkedList<T> current = head;

        while (current != null) {
            size++;
            current = current.next;
        }

        return size;
    }



    @Override
    public int find(T value){
        NodeLinkedList<T> tempNodeLinkedList = head;
        int i = 0;

        while (tempNodeLinkedList != null) {
            if (tempNodeLinkedList.value.equals(value)) {
                System.out.println("El valor " + value + " está en la posición " + i);
                return i;
            }

            tempNodeLinkedList = tempNodeLinkedList.next;
            i++;
        }

        System.out.println("El valor " + value + " no está! :(");
        return -1;
    }



    @Override
    public void addFirst(T value) {
        NodeLinkedList<T> nuevoNodo = new NodeLinkedList<>();
        nuevoNodo.value = value;

        nuevoNodo.next = head;
        head = nuevoNodo;
    }

    @Override
    public void addAt(T value, int position) {
        if (position < 0 || position > size()) {
            throw new IndexOutOfBoundsException("Posición inválida: " + position);
        }

        NodeLinkedList<T> nuevoNodo = new NodeLinkedList<>();
        nuevoNodo.value = value;

        if (position == 0) {
            addFirst(value);
        } else {
            NodeLinkedList<T> tempNodeLinkedList = head;
            for (int i = 0; i < position - 1; i++) {
                tempNodeLinkedList = tempNodeLinkedList.next;
            }
            nuevoNodo.next = tempNodeLinkedList.next;
            tempNodeLinkedList.next = nuevoNodo;
        }
    }



    @Override
    public void addInOrder(T value) {
        NodeLinkedList<T> nuevoNodo = new NodeLinkedList<>();
        nuevoNodo.value = value;

        // Si la lista está vacía o el nuevo nodo debe ser el primero
        if (head == null || head.value.compareTo(nuevoNodo.value) > 0) {
            nuevoNodo.next = head;
            head = nuevoNodo;
        } else {
            NodeLinkedList<T> tempNodeLinkedList = head;

            // Recorrer la lista mientras el siguiente nodo no sea null y su valor sea menor que el valor a insertar
            while (tempNodeLinkedList.next != null && tempNodeLinkedList.next.value.compareTo(value) < 0) {
                tempNodeLinkedList = tempNodeLinkedList.next;
            }

            // Insertar el nuevo nodo después del nodo temporal
            nuevoNodo.next = tempNodeLinkedList.next;
            tempNodeLinkedList.next = nuevoNodo;
        }
    }

    public boolean contains(T value) {
        NodeLinkedList<T> current = head;

        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }



    @Override
    public void imprimir() {

        NodeLinkedList<T> temporaryNodeLinkedList = head;

        while (temporaryNodeLinkedList.next != null){
            System.out.print(temporaryNodeLinkedList.value + " --> ");
            temporaryNodeLinkedList = temporaryNodeLinkedList.next;
        }
        System.out.println(temporaryNodeLinkedList.value);
    }



    public NodeLinkedList<T> getMiddle(NodeLinkedList<T> head) {
        if (head == null) {
            return head;
        }

        NodeLinkedList<T> slow = head, fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public NodeLinkedList<T> sortedMerge(NodeLinkedList<T> a, NodeLinkedList<T> b) {
        NodeLinkedList<T> result;

        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        // Más grandes primero
        if (a.value.compareTo(b.value) >= 0) {
            result = a;
            result.next = sortedMerge(a.next, b);
        } else {
            result = b;
            result.next = sortedMerge(a, b.next);
        }

        return result;
    }

    public NodeLinkedList<T> mergeSort(NodeLinkedList<T> h) {
        if (h == null || h.next == null) {
            return h;
        }

        NodeLinkedList<T> middle = getMiddle(h);
        NodeLinkedList<T> nextOfMiddle = middle.next;

        middle.next = null;

        NodeLinkedList<T> left = mergeSort(h);

        NodeLinkedList<T> right = mergeSort(nextOfMiddle);

        NodeLinkedList<T> sortedList = sortedMerge(left, right);

        return sortedList;
    }

    public void sort() {
        head = mergeSort(head);
    }



}





