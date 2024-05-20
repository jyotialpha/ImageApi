package com.MediaStore.pvp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;


import com.MediaStore.pvp.DaoInterface.PhotoDao;
import com.MediaStore.pvp.Model.PhotoModel;

@RestController
public class PhotoController {
	
	@Autowired
	PhotoDao pd;
	
	@PostMapping(path = "/addPhoto")
    public String addPhotoMehod(@RequestBody PhotoModel pm) {
        try {
            // Call the DAO to add the photo to the database
            pd.addPhoto(pm);
            return "Photo added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to add photo";
        }
    }
	@GetMapping(path = "/GetImageByid/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> retrieveImage(@PathVariable int id) {
	    try {
	        // Call the DAO to retrieve the file path
	        byte[] imageData = pd.getImage(id);
	        if (imageData != null) {
	            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(500).body(null);
	    }
	}

}
