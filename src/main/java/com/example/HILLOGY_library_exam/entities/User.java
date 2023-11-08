package com.example.HILLOGY_library_exam.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private ArrayList<String> books;

	public User() {
	}

	public User(String name) {
		this.name = name;
		this.books = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<String> books) {
		this.books = books;
	}

	// hasCode() and equals() only take the id into account due to it being an
	// unique id
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		StringBuilder toret = new StringBuilder();
		toret.append("Id: " + getId().toString() + "\n");
		toret.append("Name: " + getName() + "\n");
		if (getBooks().size()==0) {
			toret.append("This user does not have any checked out books at the moment.");
		}
		else {
			toret.append("Checked out books:\n");
			
			Iterator<String> itr = getBooks().iterator();
			while(itr.hasNext()) {
				toret.append("\t(" + itr.next() + ")\\n");
			}
		}
		toret.append("\n");
		
		return toret.toString();
	}
	
	public void checkOutBook(String ISBN) {
		books.add(ISBN);
	}
	
	public void returnBook(String ISBN) {
		books.remove(ISBN);
	}

}
