# Cinequest
Java application designed for efficient cataloging, rating, and reviewing of your favourite movies, along with your friends

## Introduction
My love for movies has been a constant part of my life, from the enchanting stories that captivated my childhood to the cinematic adventures that shaped my teen years. This profound passion inspired a creative journey, leading me to embark on a project that seamlessly blends my academic pursuits in Database and Object-Oriented Programming courses with my genuine enthusiasm for the world of cinema.

I wanted to create a personalized space for movie enthusiasts, so I came up with an idea of developing an application, a virtual movie hub where I could curate my favorite films

The application is designed as a comprehensive solution for cataloging, rating, and reviewing movies. It is distinguished by its interactive features which make it ideal for sharing the joy of cinema with friends and family. A key highlight lies in the ability to engage with others' reviews, creating a dynamic space for discussions and shared cinematic experiences.

The collaborative nature of the app extends beyond personal connections. The application incorporates an innovative ranking system that aggregates user ratings to determine the average ranking

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
