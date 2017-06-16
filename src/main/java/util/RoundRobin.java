package util;

import java.util.Iterator;
import java.util.List;

public class RoundRobin<T> implements Iterable<T> {
      private final List<T> coll;
    private int index = 0;

      public RoundRobin(List<T> coll) { this.coll = coll; }

      public Iterator<T> iterator() {
         return new Iterator<T>() {


            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                if(coll.size()==0){
                    return null;
                }
                T res = coll.get(index);
                index = (index + 1) % coll.size();
                return res;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }
}