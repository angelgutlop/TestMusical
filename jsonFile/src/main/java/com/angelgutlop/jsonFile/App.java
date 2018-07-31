package com.angelgutlop.jsonFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App {

	private static final String ID = "ID";
	private static final String COMPOSITOR = "COMPOSITOR";
	private static final String GENERO = "GENERO";
	private static final String MUSICAL_ASSET = "ARCHIVO";
	private static final String NOMBRE_CANCION = "NOMBRE_CANCION";
	private static final String AÑO = "AÑO";

	private static final String MUSIC_DB = "MUSICA";

	private static String assetsPath = "../app/src/main/assets";
	private static String songPath = assetsPath + "/canciones";
	private final static String jsonDatabaseFile = assetsPath + "/traks.json";

	public static void main(String[] args) {
		File folder = new File(songPath);

		File[] listOfFiles = folder.listFiles();
		JSONArray jsonArray = new JSONArray();

		Set<String> compositoresSet = new TreeSet<String>();

		int id = 0;
		for (int i = 0; i < listOfFiles.length; i++) {

			File file = listOfFiles[i];
			String fileExtension = getFileExtension(file);

			if (file.isFile()) {

				if (fileExtension.equals("ogg") || fileExtension.equals("mp3")) {

					try {

						JSONObject jsonObject = jsonTag(id++, file);
						jsonArray.add(jsonObject);
						String idStr = jsonObject.keySet().toArray()[0].toString();
						compositoresSet.add(((JSONObject) jsonObject.get(idStr)).get(COMPOSITOR).toString());

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}

		JSONObject jsonObjectDB = new JSONObject();
		jsonObjectDB.put(MUSIC_DB, jsonArray);

		try {

			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(jsonObjectDB.toJSONString(), Object.class);
			String mappedJsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			FileWriter fileWriter = new FileWriter(jsonDatabaseFile);
			fileWriter.write(mappedJsonStr);
			fileWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Salvadas info de canciones a " + jsonDatabaseFile);

		for (String s : compositoresSet) {
			System.out.println(s);

		}

	}

	private static JSONObject jsonTag(int id, File file)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {

		AudioFile f = AudioFileIO.read(file.getAbsoluteFile());
		Tag tag = f.getTag();

		String filename = file.getName();
		String artist = tag.getFirst(FieldKey.ARTIST);
		String songName = tag.getFirst(FieldKey.TITLE);
		String year = tag.getFirst(FieldKey.YEAR);
		String genere = tag.getFirst(FieldKey.GENRE);

		/*
		 * String[] artistNames = artist.split(" "); String artistLastName =
		 * artistNames[artistNames.length - 1].toUpperCase(); if
		 * (artistLastName.length() < 4) artistLastName = artistNames[artistNames.length
		 * - 2].toUpperCase() + " " + artistLastName;
		 */

		JSONObject jsonObjectsong = new JSONObject();

		jsonObjectsong.put(NOMBRE_CANCION, songName);
		jsonObjectsong.put(COMPOSITOR, artist);
		jsonObjectsong.put(MUSICAL_ASSET, filename);
		jsonObjectsong.put(GENERO, genere);
		jsonObjectsong.put(AÑO, year);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ID + id, jsonObjectsong);

		return jsonObject;
	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
}
