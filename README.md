# README

# Setup

* Install Visual Studio for C/C++
* Get the path of `Windows.h` by importing `Windows.h` in Visual Studio

For me the path is:
```
C:\Program Files (x86)\Windows Kits\10\Include\10.0.22621.0\um\Windows.h
```

## Running JExtract

```
G:\Downloads\openjdk-22-jextract+3-13_windows-x64_bin.tar\jextract-22\bin\jextract --output src/main/java -t at.hannos.windows "C:\Program Files (x86)\Windows Kits\10\Include\10.0.22621.0\um\Windows.h"
```

````
G:\Downloads\openjdk-22-jextract+3-13_windows-x64_bin.tar\jextract-22\bin\jextract --output src/main/java -t at.hannos.pgquery "G:\c_projects\libpg_query\pg_query.h"
````

# Running

Run with VM option to get rid of warning

````
--enable-native-access=ALL-UNNAMED
-Djava.library.path=G:\java_projects\panama-playground
````

# PGQuery

DLLs obtained from https://github.com/premium-minds/sonar-postgres-plugin/tree/master/src/main/resources/win32-x86-64


# Links

https://docs.oracle.com/en/java/javase/21/core/call-native-functions-jextract.html#GUID-480A7E64-531A-4C88-800F-810FF87F24A1
https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createdirectorya
https://learn.microsoft.com/en-us/openspecs/windows_protocols/ms-dtyp/f8d4fe46-6be8-44c9-8823-615a21d17a61
