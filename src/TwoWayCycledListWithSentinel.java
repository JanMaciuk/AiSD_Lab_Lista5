import java.io.Serializable;
import java.util.AbstractList;

public class TwoWayCycledListWithSentinel<E> extends AbstractList<E> implements Serializable {
    private class Element implements Serializable {
        private E value;
        private Element next;
        private Element prev;
        public E getValue() {
            return value;
        }
        public void setValue(E value) {
            this.value = value;
        }
        public Element getNext() {
            return next;
        }
        public void setNext(Element next) {
            this.next = next;
        }
        public Element getPrev() {
            return prev;
        }
        public void setPrev(Element prev) {
            this.prev = prev;
        }
        Element(E data){
            this.value=data;
        }

        public void insertAfter(Element elem){
            elem.setNext(this.getNext());
            elem.setPrev(this);
            this.getNext().setPrev(elem);
            this.setNext(elem);
        }

        public void insertBefore(Element elem){
            elem.setNext(this);
            elem.setPrev(this.getPrev());
            this.getPrev().setNext(elem);
            this.setPrev(elem);
        }

        public void remove(){
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }
    }
    Element sentinel;
    public TwoWayCycledListWithSentinel() {
        sentinel=new Element(null);
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }
    private Element getElement(int index){
        Element elem=sentinel.getNext();
        int counter=0;
        while(elem!=sentinel && counter<index){
            counter++;
            elem=elem.getNext();
        }
        if(elem==sentinel)
            throw new IndexOutOfBoundsException();
        return elem;
    }

    private Element getElement(Object value){
        Element elem=sentinel.getNext();
        while(elem!=sentinel && !value.equals(elem.getValue())){
            elem=elem.getNext();
        }
        if(elem==sentinel)
            return null;
        return elem;
    }

    @Override
    public boolean isEmpty() { return sentinel.getNext()==sentinel; } // Jeżeli strażnik ma referencje do samego siebie, to nie ma innych elementów w liście.

    @Override
    public void clear() {
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    @Override
    public E get(int index) {
        Element elem=getElement(index);
        return elem.getValue();
    }

    @Override
    public E set(int index, E value) {
        Element elem=getElement(index);
        E retValue=elem.getValue();
        elem.setValue(value);
        return retValue;
    }

    @Override
    public boolean add(E value) { // dodawanie na koniec
        Element newElem=new Element(value);
        sentinel.insertBefore(newElem);
        return true;
    }

    @Override
    public void add(int index, E value) { // dodawanie na wskazany indeks
        Element newElem=new Element(value);
        if(index==0)
            sentinel.insertAfter(newElem);
        else{
            Element elem=getElement(index-1);
            elem.insertAfter(newElem);
        }
    }

    @Override
    public int indexOf(Object value) {
        Element elem=sentinel.getNext();
        int counter=0;
        while(elem!=sentinel && !elem.getValue().equals(value)){
            counter++;
            elem=elem.getNext();
        }
        if(elem==sentinel)
            return -1;
        return counter;
    }

    @Override
    public E remove(int index) {
        Element elem=getElement(index);
        elem.remove();
        return elem.getValue();
    }

    @Override
    public boolean remove(Object value) {
        Element elem=getElement(value);
        if(elem==null) return false;
        elem.remove();
        return true;
    }

    @Override
    public int size() {
        Element elem=sentinel.getNext();
        int counter=0;
        while(elem!=sentinel){
            counter++;
            elem=elem.getNext();
        }
        return counter;
    }



}