package com.MediaStore.pvp.Daoimp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.sql.DataSource;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.MediaStore.pvp.Constraint.AppConstants;
import com.MediaStore.pvp.DaoInterface.PhotoDao;
import com.MediaStore.pvp.Model.PhotoModel;

@Repository
public class PhotoDoimplimentation implements PhotoDao {
	@Autowired
	DataSource dataSource;

	private static final String INSERT_PHOTO_ENDPOINT_SQL = "INSERT INTO photos (photo_base) VALUES (?)";

	@Override
	public void addPhoto(PhotoModel pm) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			con = dataSource.getConnection();
			sql = INSERT_PHOTO_ENDPOINT_SQL;
			
			ps = con.prepareStatement(sql);

			// Decode base64 and store the file
			
			//For static
//			String filename = storeFile(pm.getPhoto());
			
			//For dynamic
            String filename = storeFile(pm.getPhoto(), "dynamic_images/");



			// Store the filename in the database
			ps.setString(1, filename);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close resources in a finally block
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
/*
	private String storeFile(String base64String) throws IOException {
		// Logic to store the file
		String newFileName = null;

		try {
			String s = AppConstants.FILE_STORAGE_LOCATION_APP_SRC_JYOTI;
			String name = System.currentTimeMillis() + "app_src.jpeg";
			newFileName = s + name;

			// Decode base64 and store the file
			FileOutputStream fos = new FileOutputStream(newFileName);
			byte[] byteArray = Base64.decodeBase64(base64String);
			fos.write(byteArray);
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw e; // Propagate the exception for better error handling
		}
		return newFileName;
	}
*/
//............Dynamic Store the Path................................
	private String storeFile(String base64String, String subdirectory) throws IOException {
	    // Logic to store the file
	    String newFileName = null;

	    try {
	        // Get the classpath resource location
	        String classpath = getClass().getClassLoader().getResource("").getPath();
	        
	        //To see The class Path
	        System.out.println(classpath);

	        // Create a dynamic subdirectory within static
	        String staticDirectory = classpath.replace("/target/classes/", "/src/main/resources/static/");
	        File subdirectoryFile = new File(staticDirectory + subdirectory);
	        if (!subdirectoryFile.exists()) {
	            subdirectoryFile.mkdirs();
	        }

	        // Generate a unique file name
	        String name = System.currentTimeMillis() + "_app_src.jpeg";
	        newFileName = staticDirectory + subdirectory + name;

	        // Decode base64 and store the file
	        FileOutputStream fos = new FileOutputStream(newFileName);
	        byte[] byteArray = Base64.decodeBase64(base64String);
	        fos.write(byteArray);
	        fos.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	        throw e; // Propagate the exception for better error handling
	    }
	    return newFileName;
	}

	

	// Override Method to getphoto by id
	 public byte[] getImage(int id) throws IOException {
	        Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            con = dataSource.getConnection();
	            String sql = "SELECT photo_base FROM photos WHERE id=?";
	            ps = con.prepareStatement(sql);
	            ps.setInt(1, id);
	            rs = ps.executeQuery();

	            if (rs.next()) {
	                String photoBase = rs.getString("photo_base");
	                // Extract the file name and path from the photoBase string
	                String fileName = photoBase.substring(photoBase.lastIndexOf('/') + 1);
	                String filePath = photoBase.substring(0, photoBase.lastIndexOf('/') + 1);

	                // Read the image file as a byte[]
	                File imageFile = new File(filePath + fileName);
	                byte[] imageData = new byte[(int) imageFile.length()];
	                FileInputStream fileInputStream = new FileInputStream(imageFile);
	                fileInputStream.read(imageData);
	                fileInputStream.close();

	                return imageData;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (ps != null) {
	                    ps.close();
	                }
	                if (con != null) {
	                    con.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return null;
	    }

}
