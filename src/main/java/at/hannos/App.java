package at.hannos;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Program started!");

        windowsExample();
    }

    private static void windowsExample(){
        final String path = "myDir1";

        WindowsApi.createDirectory(path);
    }



}
