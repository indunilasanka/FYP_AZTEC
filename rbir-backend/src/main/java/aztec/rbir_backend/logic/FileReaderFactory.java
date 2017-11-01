package aztec.rbir_backend.logic;

import java.io.File;

/**
 * Created by subhahs on 28/05/2017.
 */
public final class FileReaderFactory {

    public static String read(String filePath){
        String fileType = getFileExtension(filePath);
        MyFileReader reader = getFileReaderInstance(fileType);
        if(reader != null)
            return reader.read(filePath);
        return null;
    }

    public static MyFileReader getFileReaderInstance(String fileType){
        switch (fileType){
            case "PDF":
            case "pdf":
                return new PDFMyFileReader();
            case "CSV":
            case "csv":
                return new CSVMyFileReader();
            case "DOCX":
            case "docx":
                return new DOCXMyFileReader();
            case "PPTX":
            case "pptx":
                return new PPTXMyFileReader();
            case "TXT":
            case "txt":
                return new TXTMyFileReader();
            case "XLS":
            case "xls":
                return new XLSMyFileReader();
            case "XLSX":
            case "xlsx":
                return new XLSXMyFileReader();

        }
        return null;
    }

    private static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
