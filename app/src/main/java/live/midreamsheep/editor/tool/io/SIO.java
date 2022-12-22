package live.midreamsheep.editor.tool.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SIO {
    public static void outPutString(File file, String content){
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String inputString(File file){
        int len = -1;
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }
}
