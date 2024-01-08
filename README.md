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

## How to use
### Hello page
First of all, LogIn into your account or Sign Up if you are new here.

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/41d6ac17-55d8-4123-b601-9cefcb7aac32)
SignUp:

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/491bcd30-7fb6-4218-b06b-63db32beba60)
LogIn:

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/4d0a84ea-c896-43cc-a0ba-ba6b82af4787)

### Now it's time to choose one of the many option in our **home page!**
Use the navigation bar for checking out:
+ Go to main page by clicking on Cinequest
+ The films you've rated
+ Your lists
+ The reviews you wrote
+ Searching for any movies
+ The films you added to your watchlist
+ LogOut
In addition of the navigation bar which will be visible on every SubPage you have the following options:
+ Add a new movie
+ Add a new director
+ Add a new list
+ See our community's favourite films
+ See my favourite films
  
![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/9158b151-527f-429b-a2a7-3443a6cc06cc)

### Add movie 
Enter the details for the movie you want to watch and click **confirm** button. 

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/2086f9ed-1836-4994-bf70-d0f22d560809)

The movie should not appear in the database but the director must already be part of it or one of the following alerts will show up:

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/5e23dfdb-6d1b-4ff4-8a0b-2065c6914a4a)

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/f1d72982-efba-4aa0-acfc-9d076ed62a33)

After pressing confirm, you will be sent to the movie you added.

### Add director
Type the first and last name for the director you want to add, remember, trying to add an existing director will triger an alert.

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/b540bcfa-0a6f-4353-a29a-7de7ae01efda)

### Add list
To add a new list, fill up the name and the description, don't worry, you will add the movies later.

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/806463d6-8f5b-480a-b2e2-3825f760d075)

After clicking **confirm** you will taken on the list's page. 
### Add movie to list
The films that have been inserted will apear in the table, to add a movie in the list, enter the title and director than click **Add movie**

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/c0e56497-4197-46a8-aa1d-2a0aef64e630)

To delete the list click on **Delete List** button, then press **YES**:

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/1f36cb48-f92c-482f-bb1d-509650798e8b)

### See movie
On the movie page we can see:
+ The details
+ The average rating
+ Number of people who watched (ranked) the movie
+ The rank in the **Cinequest top50** list

You can add a movie in your watchlist by clicking on the **add to watchlist** button. If the movie is already in your watchlist, the button will be colored green, by clicking it, the movie will be deleted from your watchlist.
If you rated the movie, the button with numbers from 1-10 will be colored acordinglly, or feel free to add a new rating.

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/cbcdbac5-74fa-4ddc-b4b5-f5d2a2893d03)

You can also add a review and see what other people had written:

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/cf84190a-7058-45a2-baef-0f6de8d2a7da)

Sort them by popularity to see the best (or worst) ones and hit **go back** to return on the movie page.
If the review contains spoilers, the thoughts will not be visible until you click on the review(**ONLY IF YOU HAVE WATCHED THE MOVIE**).

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/6233e457-a22b-491c-a3ad-b167a9b3d517)

You can like or dislike the review if you haven't already(**watch the color of the buttons**) and you can even delete the review if you've written it.

### Films page
Here you are able to see all the movies you've rated and their details, if you click on a movie, you will be taken to it's page. You can order the movies by pressing of the column header.

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/ce9ba56f-2a10-4346-8800-2204c4b28fa7)

### Lists Page
All your lists will be displayed in the table as well as the date they were created. You can see the movies in every list by clicking on them.

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/d232f96e-f8a3-4e26-b0b2-c743e8c2e4a1)

### Search 
Enter a film title (or even just a letter) and click **Search** to load the results. Click on any of them to enter the movie page.

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/79d23592-ad6b-45ca-bd3f-8cb47423f08f)

### Reviews 
Press the **reviews** button from the navigation menu to see a table with all the reviews you've written. You can even sort the by popularity (likes-dislikes) to see your best work;

![image](https://github.com/Marcu-Petric/Cinequest/assets/87765474/110f69b7-d0e0-4241-b482-08ed4a6267e1)

