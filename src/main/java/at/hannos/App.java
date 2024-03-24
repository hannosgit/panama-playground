package at.hannos;

import at.hannos.windows.Windows_h;

import java.lang.foreign.Arena;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.loadLibrary("Kernel32");
        final String path = "myDir";

        Arena arena = Arena.ofAuto();
        MemorySegment directoryPath = arena.allocateFrom(path, StandardCharsets.UTF_8);

        final int success = Windows_h.CreateDirectoryA(directoryPath, MemorySegment.NULL);

        System.out.println(success);

    }
}
