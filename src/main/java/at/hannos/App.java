package at.hannos;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Program started!");

        pgQueryExample();
    }

    private static void windowsExample(){
        final String path = "myDir1";

        WindowsApi.createDirectory(path);
    }

    private static void pgQueryExample(){
        PGQuery.parseQuery("SELECT * FROM foo;");
    }

}
