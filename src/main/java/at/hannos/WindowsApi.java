package at.hannos;

import at.hannos.windows.FILE_NOTIFY_INFORMATION;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static at.hannos.windows.windows_h.*;

public class WindowsApi {

    static {
        System.loadLibrary("kernel32");

    }


    public static void watchDir(String path) {

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment dirPath = arena.allocateFrom(path);
            final MemorySegment directoryHandle = CreateFileA(dirPath,
                    GENERIC_READ(),
                    FILE_SHARE_WRITE() | FILE_SHARE_READ(),
                    MemorySegment.NULL,
                    OPEN_EXISTING(),
                    FILE_ATTRIBUTE_DIRECTORY() | FILE_FLAG_BACKUP_SEMANTICS(),
                    MemorySegment.NULL
            );

            MemorySegment lpBytesReturned = arena.allocate(LPDWORD);
            final MemorySegment buffer = arena.allocate(DWORD, 1024);
            final MemorySegment bufferPtr = MemorySegment.ofAddress(buffer.address());

            ReadDirectoryChangesW(
                    directoryHandle,
                    bufferPtr,
                    (int) buffer.byteSize(),
                    1,
                    FILE_NOTIFY_CHANGE_FILE_NAME(),
                    lpBytesReturned,
                    MemorySegment.NULL,
                    MemorySegment.NULL
            );
            final int bufferSize = lpBytesReturned.get(C_INT, 0);
            System.out.println(bufferSize);
            parseNotify(MemorySegment.ofAddress(bufferPtr.address()).reinterpret(bufferSize));
        }
    }

    public static void parseNotify(MemorySegment buffer) {
        final int nextEntryOffset = FILE_NOTIFY_INFORMATION.NextEntryOffset(buffer);
        System.out.println(nextEntryOffset);
        final int action = FILE_NOTIFY_INFORMATION.Action(buffer);
        System.out.println(action);
        final int fileNameLength = FILE_NOTIFY_INFORMATION.FileNameLength(buffer);
        System.out.println(fileNameLength);
        System.out.println("\n----------\n");
        final MemorySegment memorySegment = FILE_NOTIFY_INFORMATION.FileName(buffer);
        final short[] charactersRaw = memorySegment.reinterpret(fileNameLength).toArray(WCHAR);
        for (short i : charactersRaw) {
            System.out.println((char) i);
        }
    }

}
