package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Image processing and file management service
 */
public class ImageService {

    /**
     * Get file extension from filename
     */
    private static String getFileExtension(String filename) {
        if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * Duplicate image to project directory
     */
    public Path duplicateImage(String imageUrl) {
        String projectPath = System.getProperty("user.dir");
        String targetPath = projectPath + "/src/images/dishes";
        String fileExtension = getFileExtension(imageUrl);
        long timestamp = System.currentTimeMillis();
        String newFileName = timestamp + "." + fileExtension;
        Path fullPath = Paths.get(targetPath).resolve(newFileName);
        Path imagePath = Paths.get(imageUrl);
        try {
            Files.copy(imagePath, fullPath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return fullPath;
    }

    /**
     * Validate if a string is a valid URL format
     */
    public boolean isValidURL(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        
        // Basic URL pattern check
        String urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return url.matches(urlPattern) || url.startsWith("file:");
    }
}