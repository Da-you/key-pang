package portfolio.keypang.service.s3.utils;

import java.util.UUID;
import portfolio.keypang.exception.IllegalMimeTypeException;

public class FileNameUtils {

  public static void checkImageType(String mimeType) {
    if (mimeType.equals("image/jpg") || mimeType.equals("image/jpeg")
        || mimeType.equals("image/png") || mimeType.equals("image/gif")) {
      throw new IllegalMimeTypeException();
    }
  }

  public static String fileNameConvert(String fileName) {
    StringBuilder builder = new StringBuilder();
    UUID uuid = UUID.randomUUID();
    String extension = getExtension(fileName);

    builder.append(uuid).append(".").append(extension);

    return builder.toString();
  }


  public static String getExtension(String fileName) {
    int pos = fileName.lastIndexOf(".");
    return fileName.substring(pos + 1);
  }

  public static String getFileName(String path) {
    int idx = path.lastIndexOf("/");
    return path.substring(idx + 1);
  }
}
