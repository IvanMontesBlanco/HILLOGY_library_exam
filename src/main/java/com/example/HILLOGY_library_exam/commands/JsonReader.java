package com.example.HILLOGY_library_exam.commands;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class JsonReader {
	public static JSONObject url2string(String url) {
		try {
			String json = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
		    return new JSONObject(json);
		} catch (IOException e) {
			return new JSONObject();
		}
	}
}