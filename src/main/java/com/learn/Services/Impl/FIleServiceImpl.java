package com.learn.Services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.swing.text.DefaultEditorKit.CopyAction;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learn.Services.FileService;

@Service
public class FIleServiceImpl implements FileService { 

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//file name
		String name =file.getOriginalFilename();
		
		//random name generate file
		
		//full path
		String filePath = path + File.separator +name;
		
		
		//create folder if not created 
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		//filecopy
		Files.copy(file.getInputStream(),Paths.get(filePath));
		return name;
		
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String fullPath = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		//db logic to return inputstream
		
		return is;
	}

}
