package at.hannos;

import static at.hannos.windows.Windows_h.*;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

public class WindowsApi {

    static {
        System.loadLibrary("Kernel32");
    }

    /**
     * Creates a directory using the Windows API.
     * Implementation see: <a href="https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createdirectorya">CreateDirectoryA</a>
     *
     * @param path the path of the directory to create
     * @return return value
     */
    public static void createDirectory(String path) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment directoryPath = arena.allocateFrom(path, StandardCharsets.UTF_8);

            final int returnValue = CreateDirectoryA(directoryPath, MemorySegment.NULL);
            final String dw = getLastErrorWrapper(GetLastError());
            if (returnValue == 0) {
                // GetLastError does not work, because there might/are some function calls between create and getLastError -> https://mail.openjdk.org/pipermail/panama-dev/2022-August/017382.html
                // If the function fails, the return value is zero
                throw new WindowsApiException(dw);
            }
        }
    }

    /**
     * See https://learn.microsoft.com/en-us/windows/win32/api/errhandlingapi/nf-errhandlingapi-getlasterror
     *
     * @return
     */
    private static String getLastErrorWrapper(int dw) {
        // See https://learn.microsoft.com/en-us/windows/win32/Debug/retrieving-the-last-error-code

        try (Arena arena = Arena.ofConfined()) {
            final MemorySegment lpBuffer = arena.allocate(C_POINTER);
            FormatMessageA(
                    FORMAT_MESSAGE_ALLOCATE_BUFFER() |
                            FORMAT_MESSAGE_FROM_SYSTEM() |
                            FORMAT_MESSAGE_IGNORE_INSERTS(),
                    MemorySegment.NULL,
                    dw,
                    0, // ??
                    lpBuffer, //?
                    0,
                    MemorySegment.NULL
            );
            final MemorySegment memorySegment = lpBuffer.get(C_POINTER, 0);

            return "Wrong error message because getLastError does not work with panama: " + memorySegment.getString(0, StandardCharsets.UTF_8);
        }
    }

}
