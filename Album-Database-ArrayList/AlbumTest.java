public class AlbumTest {
    public static void main(String[] args) {
        // Test constructor with two strings
        Album album1 = new Album("Arctic Monkeys", "AM");
        System.out.println("album1 (2 string constructor): " + album1);

        // Test constructor with formatted string
        Album album2 = new Album("Arctic Monkeys - AM");
        System.out.println("album2 (formatted string constructor): " + album2);

        // Test accessor methods
        System.out.println("Artist of album1: " + album1.getArtist());
        System.out.println("Album name of album1: " + album1.getAlbumName());

        // Test toString()
        System.out.println("album1.toString(): " + album1.toString());

        // Test equals() returning true
        System.out.println("album1 equals album2? " + album1.equals(album2));

        // Test equals() returning false
        Album album3 = new Album("The Beatles", "Abbey Road");
        System.out.println("album1 equals album3? " + album1.equals(album3));
    }
}


    

