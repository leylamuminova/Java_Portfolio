/**
 * AlbumList.java 
 * abstract class that defines a list of album objects
 * has methods to add, remove, get, set and modify albums
 */

public abstract class AlbumList{

	protected int numItems;
/**
 * adds new album to list
 * @param newA the album to add
 */
	abstract public void add(Album newA);
/**
 * removes album from list 
 * @param targetA the album to remove
 * @return the album that was removed 
 */
	abstract public Album remove(Album targetA);
/**
 * removes album at certain index from list
 * @param idx int for a list
 * @return the album that was removed 
 */
	abstract public Album remove(int idx);
/**
 * size of list
 * @return int of items in the list
 */
	public int size(){
		return numItems;
	}
/**
 * gets certain value from the list at specific index
 * @param idx int of position in list
 * @return value from certain position of the list
 */
	abstract public Album get(int idx);
/**
 * replaces album with index that is given
 * @param idx int position of the list
 * @param newA the replacement value given (new value)
 */
	abstract public void set(int idx, Album newA);
}