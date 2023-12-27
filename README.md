# Cinequest
Java application designed for efficient cataloging, rating, and reviewing of your favourite movies, along with your friends

## Technologies used
+ **JavaFx for the GUI:** The graphical user interface is built using JavaFx, providing an intuitive and visually appealing experience for users.
+ **JDBC for Database Connectivity:** Cinequest seamlessly integrates with a PostgreSQL database using JDBC, facilitating secure and efficient communication between the application and the backend.
+ **PostgreSQL Database:** The application utilizes PostgreSQL as its backend database, offering a reliable and scalable solution for storing and retrieving movie-related information.

## Database
### Diagram
### Description
TThe PostgreSQL database, meticulously designed with DataGrip, serves as the backbone for Cinequest. The application establishes a seamless connection through JDBC, ensuring a robust and secure interaction. User authentication is managed through the *Log In* and *Sign Up* functionalities, with user credentials securely stored in the "user" table.

Users benefit from a comprehensive set of features, including:
+ **Adding New Movies:** Users can enrich the database by adding information about new movies.
+ **Director Management:** The application allows users to add new directors, updating the "directors" table.
+ **List Creation:** Users can organize movies into customized lists, with each list represented in the "lists" table.
+ **Adding a movie into a list:** A many to many relationship, solved though the table "list\_to\_movie"
+ **Search Functionality:** Users can easily retrieve information by searching for movies stored in the "movies" table.
+ **Rating System:** The application supports user ratings, ensuring data integrity with a unique combination of user ID and movie ID in the "rating" table.
+ **Review Functionality:** Users can express their thoughts by writing reviews, with the flexibility to provide multiple reviews for the same movie, each stored in the "reviews" table.
 
## User Interface Development
The user interface is thoughtfully designed using a combination of SceneBuilder for FXML file generation and IntelliJ for crafting the JavaFX code to control the FXML files. This ensures a seamless and visually appealing user experience, aligning with industry standards for modern and responsive application design.
