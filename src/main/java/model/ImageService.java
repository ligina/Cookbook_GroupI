package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service class for handling image-related operations in the recipe management system.
 * This class provides functionality for image file manipulation, validation, and storage.
 * 
 * @author Ziang Liu
 * @version 1.0
 * @since 1.0
 */
public class ImageService {

    /**
     * Extracts the file extension from a filename.
     * 
     * @param filename the name of the file including extension
     * @return the file extension without the dot, or empty string if no extension found
     */
    private static String getFileExtension(String filename) {
        if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * Duplicates an image file to the project's image directory with a unique timestamp-based name.
     * This method copies the source image to the dishes directory and renames it to avoid conflicts.
     * 
     * @param imageUrl the path or URL of the source image file
     * @return the Path object pointing to the duplicated image file, or null if operation failed
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
            // Copy the image file to the target directory
            Files.copy(imagePath, fullPath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return fullPath;
    }

    /**
     * Validates whether a given string is a valid URL format.
     * Supports HTTP, HTTPS, FTP, and file protocols.
     * 
     * @param url the URL string to validate
     * @return true if the URL format is valid, false otherwise
     */
    public boolean isValidURL(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        
        // Basic URL pattern check for common protocols
        String urlPattern = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return url.matches(urlPattern) || url.startsWith("file:");
    }
}