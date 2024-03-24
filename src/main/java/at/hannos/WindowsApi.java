package at.hannos;

import at.hannos.windows.Windows_h;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

public class WindowsApi {

    static {
        System.loadLibrary("Kernel32");
    }

    public static int createDirectory(String path){
        try(Arena arena = Arena.ofConfined()){
            MemorySegment directoryPath = arena.allocateFrom(path, StandardCharsets.UTF_8);

            final int returnValue = Windows_h.CreateDirectoryA(directoryPath, MemorySegment.NULL);

            return returnValue;
        }
    }

}
