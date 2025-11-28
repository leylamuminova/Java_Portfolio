// AlbumArrayList.java is a class that implements a dynamic array list for Album objects
// It extends the abstract class AlbumList and provides methods to add, remove, get, and set albums

public class AlbumArrayList extends AlbumList {

    // Instance variables
    private int numCopies;
    private Album[] firstAlbum;

    // Constructor 1, with default capacity of 10
    public AlbumArrayList() {
    this.firstAlbum = new Album[10];
    this.numItems = 0;   
    this.numCopies = 0;
    }

    // Constructor 2, with specified initial capacity
    public AlbumArrayList(int initialCapacity) {
        if (initialCapacity < 1){
            initialCapacity = 1;
        }
        this.firstAlbum = new Album[initialCapacity];
        this.numItems = 0;  
        this.numCopies = 0;
    }

    /**
     * adds new album to list
     * @param newA the album to add
     */

    @Override
    public void add(Album newA) {
        // Check if array is full, if so, expand it by 2x
        if (numItems == firstAlbum.length){
            expand2x();
        }
        firstAlbum[numItems] = newA;
        numItems++;
    }

    /**
     * removes album from list 
     * @param targetA the album to remove
     * @return the album that was removed 
     */
        
    @Override
    public Album remove(Album targetA) {
        // Find the index of the target album
        for (int i = 0; i < numItems; i++){
            if (firstAlbum[i].equals(targetA)){
                return remove(i);
            }
        }
        return null; // If album not found
        
    }

    /**
     * removes album at certain index from list
     * @param idx int for a list
     * @return the album that was removed 
     */
    @Override
    public Album remove(int idx) {
        checkIndex(idx);
        Album removed = firstAlbum[idx];

        // Shift left
        for (int i = idx + 1; i < numItems; i++) {
            firstAlbum[i - 1] = firstAlbum[i];
        }
        firstAlbum[--numItems] = null; // Clearing tail and decrementing numItems
        return removed;
    }

    /**
     * gets certain value from the list at specific index
     * @param idx int of position in list
     * @return value from certain position of the list
     */
    @Override
    public Album get(int idx) {
        checkIndex(idx);
        return firstAlbum[idx];
    }

    /**
     * replaces album with index that is given
     * @param idx int position of the list
     * @param newA the replacement value given (new value)
     */
    @Override
    public void set(int idx, Album newA) {
        checkIndex(idx);
        firstAlbum[idx] = newA;
    }

    // Method to expand the array by 2x when it is full
    private void expand2x(){
            Album[] secondAlbum = new Album[firstAlbum.length * 2];
            for (int i = 0; i < firstAlbum.length; i++){
                secondAlbum[i] = firstAlbum[i];
                numCopies++;
            }
            firstAlbum = secondAlbum;
    }

    // Number of times the array has been copied
    public int getNumCopies() {
        return numCopies;
    }

    // Check if index is valid
    private void checkIndex(int idx) {
        if (idx < 0 || idx >= numItems){
            throw new IndexOutOfBoundsException();
        }
    }
   
}
    

