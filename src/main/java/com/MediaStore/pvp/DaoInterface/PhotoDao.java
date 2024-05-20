package com.MediaStore.pvp.DaoInterface;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.MediaStore.pvp.Model.PhotoModel;

public interface PhotoDao {
	
	//Add photo in base64
	public void addPhoto(PhotoModel pm);
	
	
    byte[] getImage(int id) throws FileNotFoundException, IOException;
}
