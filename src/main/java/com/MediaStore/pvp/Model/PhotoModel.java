package com.MediaStore.pvp.Model;

import java.util.Objects;

public class PhotoModel {
	private String Photo;

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Photo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhotoModel other = (PhotoModel) obj;
		return Objects.equals(Photo, other.Photo);
	}

	@Override
	public String toString() {
		return "PhotoModel [Photo=" + Photo + "]";
	}

	public PhotoModel(String photo) {
		super();
		Photo = photo;
	}

	public PhotoModel() {
		super();
	}
	
	
}
