// AlbumListTest.java
// Simple, no-exceptions test of every AlbumList method.
// Assumes Album has constructor: Album(String artist, String title).

public class AlbumListTest {
    public static void main(String[] args) {
        // small capacity so add() will also test grow
        AlbumArrayList list = new AlbumArrayList(2);

        // create a few albums
        Album a = new Album("Taylor Swift", "1989");
        Album b = new Album("Adele", "21");
        Album c = new Album("Kendrick Lamar", "DAMN.");
        Album d = new Album("Lorde", "Melodrama");

        System.out.println("Begin simple tests (no exceptions)");

        // add() + size()
        list.add(a);
        list.add(b);
        list.add(c); // should trigger expand2x inside
        System.out.println("size after add a,b,c: " + list.size()); // expect 3

        // get()
        System.out.println("get(0): " + list.get(0)); // a
        System.out.println("get(1): " + list.get(1)); // b
        System.out.println("get(2): " + list.get(2)); // c

        // set()
        list.set(1, d); // replace b with d
        System.out.println("after set(1,d), get(1): " + list.get(1)); // d

        // remove(int)
        Album removedByIndex = list.remove(1); // removes d
        System.out.println("remove(1) returned: " + removedByIndex); // d
        System.out.println("size after remove(1): " + list.size()); // expect 2
        // now order should be [a, c]
        System.out.println("now get(0): " + list.get(0)); // a
        System.out.println("now get(1): " + list.get(1)); // c

        // add back b so we can test remove(Album)
        list.add(b); // [a, c, b]
        System.out.println("size after adding b: " + list.size()); // expect 3

        // remove(Album)
        Album removedByValue = list.remove(b); // remove first match
        System.out.println("remove(b) returned: " + (removedByValue)); // b
        System.out.println("size after remove(b): " + list.size()); // expect 2

        // final list should be [a, c]
        System.out.println("final get(0): " + (list.get(0))); // a
        System.out.println("final get(1): " + (list.get(1))); // c

        System.out.println("Done");
    }
}


        
    

