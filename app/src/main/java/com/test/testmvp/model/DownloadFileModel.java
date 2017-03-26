package com.test.testmvp.model;

import java.io.File;
import java.io.IOException;

/**
 * Created by yubo on 2017/3/26.
 */

public class DownloadFileModel {

    private String fileName;
    private String fileSavePath;
    private File file;

    public DownloadFileModel(String fileName, String fileSavePath) {
        this.fileName = fileName;
        this.fileSavePath = fileSavePath;
        this.file = new File(fileSavePath + File.separator + fileName);
        if (this.file.exists()) {
            this.file.delete();
        }
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }

    public String getFilePath() {
        return this.file.getAbsolutePath();
    }

}
