package at.hannos;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        final String path = "myDir1";

        WindowsApi.createDirectory(path);
    }
}
