import java.util.EmptyStackException;
import java.util.List;

public class ListStack<E> {
    List<E> list;
    public ListStack(){
        list = new OneWayLinkedListWithHead<>();
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public E pop() throws EmptyStackException {
        E value= list.remove(0);
        if(value==null) throw new EmptyStackException();
        return value;
    }
    
    public void push(E elem) {
        list.add(elem);
    }
    
    public int size() {
        return list.size();
    }
    
    public E top() throws EmptyStackException {
        E value= list.get(0);
        if(value==null) throw new EmptyStackException();
        return value;
    }

}