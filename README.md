# About
This project was developed as part of a selection process for a Java Developer Job. It consists of a SpringBoot implementation of a simple library management system. It is important to note that it requires **JDK 17** to be executed properly.

# How to run

The files include a .jar executable on the /target folder which can be run with the java command on Linux and Windows through a command prompt. To execute the project, open a command prompt while inside the project's folder and execute the command **java -jar target/HILLOGY_library_exam-0.0.1-SNAPSHOT.jar**. It is also possible to execute **java -jar HILLOGY_library_exam-0.0.1-SNAPSHOT.jar** from within the /target folder.

This command will open a Spring CLI terminal, which we can use to execute several commands:

- **init**: creates sample books and users.
- **help**: while a default command included by Spring CLI, it has been extended to provide information about the project’s custom commands. Each of these includes an alternate name in CamelCase format and an abbreviated form, alongside alternate names for their command arguments if they have more than one. For the sake of readability, this guide only lists the “spaces as underscores” format. 

**Book management commands**

- **list_book**: lists all books in the library
- **list_available**: lists all books in the library that have not been checked out
- **find_by_ISBN**: finds a book by its ISBN. Looks for an exact match.
- **find_by_title**: finds books by their title. Its results include titles that contain the searched title.
- **find_by_author**: finds books by their author. Its results include titles that contain the searched author.
- **add_book**: adds a book. Requires an ISBN, title and author arguments. Will not allow duplicated ISBNs. Titles and author names with spaces should be given between “quotation marks”.
- **delete_books**: deletes the book that matches the ISBN given by the user. Will not allow deletion if the book is currently checked out.

**User management commands**

- **list_users**: lists all users in the library.
- **create_user**: creates a new user. The user ID number is generated automatically, so this command only requires the user name as an argument. User names with spaces should be given between “quotation marks”.
- **delete_user**: deletes an user by their id. All books currently checked out by the user will be automatically returned.
- **check_out**: makes a user check out a book. Requires the user’s id and the book’s ISBN as arguments.
- **return_book**: makes a user return a book. Requires the user’s id and the book’s ISBN as arguments.


# Project structure

The project aims to be as simple as possible due to my lack of experience with SpringBoot. The folders have been organized depending on their contents:
- **Commands**: contains the files for custom Spring CLI commands.
- **Entities**: contains typical Java classes representing Books and Users.
- **Exceptions**: contains exceptions with custom error messages.
- **Handlers**: contains classes in charge of displaying each custom exception in the localhost page.
- **Repositories**: contains the Spring interfaces for groups of the objects defined in the Entities folder.
- **Services**: contains the Spring services for managing each of the objects defined in the Entities folder.

Further details about implementation can be seen in the individual files in the form of comments.
