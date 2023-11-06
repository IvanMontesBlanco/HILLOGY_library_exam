package com.example.HILLOGY_library_exam.commands;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class BookCommands implements CommandMarker {

    @CliCommand(value = { "list_books", "listBooks"}, help = "Lists all books in the library.")
    public String list_books() {
        return books2string("http://localhost:8080/api/library");
    }
    
    @CliCommand(value = { "list_available", "listAvailable"}, help = "Lists all books in the library that have not been checked out by users.")
    public String list_available() {
        return books2string("http://localhost:8080/api/library/listAvailable");
    }
    
    @CliCommand(value = { "find_by_ISBN", "findByISBN"}, help = "Finds a single book by its ISBN.")
    public String find_by_ISBN(@CliOption(key = { "", "ISBN" }, mandatory = true) String ISBN) {
        return books2string("http://localhost:8080/api/library/" + ISBN);
    }
    
    @CliCommand(value = { "find_by_title", "findByTitle"}, help = "Finds books by its title. Includes similar titles to the one given.")
    public String find_by_title(@CliOption(key = { "", "title" }, mandatory = true) String title) {
        return books2string("http://localhost:8080/api/library/findByTitle/" + cleanUrlText(title));
    }
    
    @CliCommand(value = { "find_by_author", "findByAuthor"}, help = "Finds books by their author. Looks for exact author name matches.")
    public String find_by_author(@CliOption(key = { "", "author" }, mandatory = true) String author) {
        return books2string("http://localhost:8080/api/library/findByAuthor/" + cleanUrlText(author));
    }
    
    @CliCommand(value = { "add_book", "addBook"}, help = "Adds a book with the given ISBN, title and author.")
    public String add_book(
    		@CliOption(key = { "", "ISBN" }, mandatory = true) String ISBN,
    		@CliOption(key = { "", "title" }, mandatory = true) String title,
    		@CliOption(key = { "", "author" }, mandatory = true) String author) {
        return books2string("http://localhost:8080/api/library/ -H 'Content-type:application/json' -d '{"+
    		"\"isbn\":" + ISBN + "\", " +
    	    "\"title\":" + cleanUrlText(title) + "\", " +
    	    "\"author\":" + cleanUrlText(author) + "\""
        	+ "}'");
    }
    
    @CliCommand(value = { "delete_book", "deleteBook"}, help = "Deletes the book with the ISBN given.")
    public String delete_book(@CliOption(key = { "", "ISBN" }, mandatory = true) String ISBN) {
        return books2string("http://localhost:8080/api/library/delete/" + ISBN);
    }
    
    // lowercase and remove spaces from input text
    private String cleanUrlText(String url_param) {
    	return url_param.toLowerCase().trim().replace(" " , "");
    }
    
    // gets JSON data from the api's URLs and displays it in text format
    private String books2string(String url) {
    	System.out.println("url: " + url);
    	
    	JSONObject jsonObj = JsonReader.url2string(url);
    	return bookJson2string(jsonObj); 
    }
    
    // turns the library's JSON into a readable text string
    private String bookJson2string(JSONObject jsonObj) {
    	System.out.println("json: " + jsonObj.toString());
    	
    	if (jsonObj.has("_embedded")){
	    	StringBuilder toret = new StringBuilder();
	    	JSONArray bookList = jsonObj.getJSONObject("_embedded").getJSONArray("bookList");
	    	Iterator<Object> itr = bookList.iterator();
	    	
	    	while(itr.hasNext()) {
	    		JSONObject book = (JSONObject)itr.next();
	    		toret.append("ISBN: " + book.get("isbn") + "\n");
	    		toret.append("Title: " + book.get("title") + "\n");
	    		toret.append("Author: " + book.get("author") + "\n");
	    		toret.append("Available: " + book.get("available") + "\n");
	    		toret.append("\n");
	    	}
	    	
	    	return toret.toString();
	    }
    	else {
    		return "Request results were empty";
    	}
    }
    
    // ISBN for testing
    // 059652068
}