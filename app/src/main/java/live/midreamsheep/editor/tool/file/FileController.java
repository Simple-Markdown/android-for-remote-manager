package live.midreamsheep.editor.tool.file;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileController {
    //文件存储根文件
    public static File file;
    //文件存储父路径
    public static List<File> parentFiles = new LinkedList<>();
    //当前文件夹
    public static File currentFileDir;
    //当前文件
    public static File currentFile;
}
