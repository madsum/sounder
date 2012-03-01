package com.renaudbaivier.sounder;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable
{
  
  private String titre;
  private String album;
  private String artist;
  private String filePath;
  private String album_art;
  
  
/**
 * @return the album_art
 */
public String getAlbum_art() {
	return album_art;
}

/**
 * @param album_art the album_art to set
 */
public void setAlbum_art(String album_art) {
	this.album_art = album_art;
}

public Song( String titre, String album, String artist,
		String filePath,String album_art) {
	super();
	
	this.titre = titre;
	this.album = album;
	this.artist = artist;
	this.filePath = filePath;
	this.album_art=album_art;
}

//appele a la deserialisation
public Song(Parcel in) {
    
    
	this.titre = in.readString();
	this.album = in.readString();
	this.artist = in.readString();
	this.filePath = in.readString();
	this.album_art = in.readString();
}



@Override
public int describeContents() {
	// retourne le nombre d'objets spéciaux contenu dans la classe
	return 0;
}
@Override
public void writeToParcel(Parcel dest, int flags) {
	// Sérialisation Explicite
	
	    dest.writeString(titre);
	    dest.writeString(album);
	    dest.writeString(artist);
	    dest.writeString(filePath);
	    dest.writeString(album_art);
	
}
  
public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>()
{
    @Override
    public Song createFromParcel(Parcel source)
    {
        return new Song(source);
    }
 
    @Override
    public Song[] newArray(int size)
    {
    return new Song[size];
    }
};
 


/**
 * @return the titre
 */
public String getTitre() {
	return titre;
}
/**
 * @return the album
 */
public String getAlbum() {
	return album;
}
/**
 * @return the artist
 */
public String getArtist() {
	return artist;
}
/**
 * @return the filePath
 */
public String getFilePath() {
	return filePath;
}

/**
 * @param titre the titre to set
 */
public void setTitre(String titre) {
	this.titre = titre;
}
/**
 * @param album the album to set
 */
public void setAlbum(String album) {
	this.album = album;
}
/**
 * @param artist the artist to set
 */
public void setArtist(String artist) {
	this.artist = artist;
}
/**
 * @param filePath the filePath to set
 */
public void setFilePath(String filePath) {
	this.filePath = filePath;
}

}
