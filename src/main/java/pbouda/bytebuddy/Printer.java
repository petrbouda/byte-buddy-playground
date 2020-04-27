package pbouda.bytebuddy;

import net.bytebuddy.description.type.TypeDescription;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Printer {

    public static void bytecode(Map<TypeDescription, File> files) {
        for (File javaClass : files.values()) {
            bytecode(javaClass);
        }
    }

    public static void bytecode(File javaClass) {
        try {
            new ProcessBuilder()
                    .command("javap", "-v", "-p", javaClass.getAbsolutePath())
                    .inheritIO()
                    .start()
                    .waitFor(1, TimeUnit.SECONDS);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
