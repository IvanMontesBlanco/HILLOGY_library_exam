package com.example.HILLOGY_library_exam.entities;

import java.util.Objects;

import com.example.HILLOGY_library_exam.exceptions.ISBNInvalidException;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {
	private String title;
	private String author;
	private @Id String ISBN;
	private Boolean available;
	
	// this class does not use the typical "id" attribute due to ISBN already being an unique id
	// this class only takes the 10-digit ISBN variant into account for the sake of simplicity
	
	public Book() {
	}
	
	public Book(String title, String author, String ISBN, Boolean available) {
		super();
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		this.available = available;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	// validates if a given string is a correct ISBN number
	public Boolean validateISBN(String ISBN) {
		// check size
		if (ISBN.length() != 10) {
			return false;
		}

		// obtain sum of digits 1 to 9
		int sum = 0;
		for (int i = 0; i < 9; i++) {
			char digit = ISBN.charAt(i);

			if (!Character.isDigit(digit)) {
				return false;
			}

			sum += Character.getNumericValue(digit) * (10 - i);
		}

		// check if last digit is an X and replace it with 10 in the sum if so
		char last_digit = ISBN.charAt(9);
		if (Character.isDigit(last_digit) || last_digit == 'X') {
			sum += ((last_digit == 'X') ? 10 : Character.getNumericValue(last_digit));
		} else {
			return false;
		}

		return (sum % 11 == 0);
	}

	public String getISBN() {
		return ISBN;
	}

	// checks ISBN size and validity and updates it if correct
	public void setISBN(String ISBN) throws ISBNInvalidException {
		if (!validateISBN(ISBN)) {
			throw new ISBNInvalidException(ISBN);
		} else {
			this.ISBN = ISBN;
		}
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	// hasCode() and equals() only take the ISBN into account due to int being a
	// unique id
	@Override
	public int hashCode() {
		return Objects.hash(ISBN);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return ISBN == other.ISBN;
	}

	@Override
	public String toString() {
		return "\nTitle: " + title + "\nAuthor: " + author + "\nISBN: " + ISBN + "\nAvailable: "
				+ ((getAvailable()) ? "yes" : "no");
	}
}
