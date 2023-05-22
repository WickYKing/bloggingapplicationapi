package com.bloggingApplication.bloggingApplication.Service.impl;

import com.bloggingApplication.bloggingApplication.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
//        // File name
//        String name =file.getOriginalFilename();
//
//        //FullPath
//        String fullpath = path+ File.separator+name;
//
//        //Create file is not created
//        File f = new File(path);
//        if(!f.exists()){
//            f.mkdir();
//        }
//        // file copy
//        Files.copy(file.getInputStream(), Paths.get(fullpath));
//        return name;


        String name = file.getOriginalFilename();
        String randomId= UUID.randomUUID().toString();
        String newFileName =randomId.concat(name.substring(name.lastIndexOf(".")));

        String filepath = path + File.separator + newFileName;

        File mainfile = new File(path);
        if(!mainfile.exists()){
            mainfile.mkdir();
        }
        Files.copy(file.getInputStream(),Paths.get(filepath));
        return newFileName;

    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String filepath = path + File.separator + fileName;
        InputStream is = new FileInputStream(filepath);
        return is;
    }
}
