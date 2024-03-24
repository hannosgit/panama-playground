package at.hannos;


import at.hannos.pgquery.PgQueryError;
import at.hannos.pgquery.PgQueryParseResult;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

import static at.hannos.pgquery.pg_query_h.*;

public class PGQuery {

    static {
        System.load("G:\\java_projects\\panama-playground\\libpg_query.so.dll");
    }

    public static void parseQuery(String query) {
        try (Arena arena = Arena.ofConfined()) {
            final MemorySegment querySegment = arena.allocateFrom(query, StandardCharsets.UTF_8);
            final MemorySegment memorySegment = pg_query_parse(arena, querySegment);
            final MemorySegment error = PgQueryParseResult.error(memorySegment);

            if (error.address() != 0L) {
                System.out.println("Error detected");
                final MemorySegment message = PgQueryError.message(error);
                final String errorString = message.getString(0, StandardCharsets.UTF_8);
                System.out.println("Error: " + errorString);
            }else{
                final String string = PgQueryParseResult.parse_tree(memorySegment).getString(0, StandardCharsets.UTF_8);
                System.out.println(string);
            }

            pg_query_free_parse_result(memorySegment);
        }

    }

}
