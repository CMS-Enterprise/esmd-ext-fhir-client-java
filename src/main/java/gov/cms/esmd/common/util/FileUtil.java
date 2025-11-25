package gov.cms.esmd.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class FileUtil {

    private FileUtil() {
        // Utility class
    }

    /**
     * Writes text content to a JSON file inside the target folder.
     *
     * @param baseFolder The base directory where files will be stored
     * @param folder     Subfolder inside the base directory
     * @param filename   Filename without extension
     * @param content    File content
     */
    public static void writeJsonFile(String baseFolder, String folder, String filename, String content) {
        File dir = new File(baseFolder, folder);

        if (!dir.exists() && dir.mkdirs()) {
            return;
        }

        File file = new File(dir, filename + ".json");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + file.getAbsolutePath(), e);
        }

    }
}

