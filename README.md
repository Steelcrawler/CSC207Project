# Movie Search Application
## Contributers
Aditya Rajeev: Steelcrawler <br />
Clarina Ong: clarinaong <br />
Alaya (Phuong Anh) Le: alaya-le <br />
Daniel Kuhn: DCKuhn <br />
Zheyuan Zhang: love2know <br />

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

## Introduction
The Movie Search Application is a Java-based application that allows users to search for movies, view details, and manage their watchlist. This app was created to address the common challenge of finding movies to watch and managing personal movie preferences. It recognizes that users often struggle to discover new films that align with their interests or keep track of movies they want to watch. The application uses the TMDB API to fetch movie data and provides a user-friendly interface for searching and managing movies. The application also provides an interface for the user to get new movie reccomendations based on movies currently in their watchlist, and get reasoning as to why they are benig recommended said movies. 

## Features
- **User Authentication**: Users can sign up and log in to the application.
- **Movie Search**: Search for movies by title or filter by genre, rating, and keywords.
- **Watchlist Management**: Add and remove movies from your watchlist.
- **Recomendation System**: Users can get recommendations from their watchlist, and get AI-generated reasons for why they are being recommended that movie.

## Installation
1. **Clone the repository**:
    ```sh
    git clone https://github.com/Steelcrawler/CSC207Project.git
    ```

2. **Build the project (if necessary)**:
    ```sh
    mvn clean install
    ```

3. **Run the application**:
    Run the file found at:
    ```
    src/main/java/app/Main.java
    ```

## Usage
1. **Sign Up**: Create a new account by providing a username and password.
![Signing up for an account](https://github.com/user-attachments/assets/e63cace3-5bb4-4b03-bd72-655caaf98c95)
*The sign-up screen showing fields for creating a new account with username and password*

![Logging in with the new account](https://github.com/user-attachments/assets/010cf848-8cee-4faf-b588-cd2eb931c2a4)
*The login screen where users can enter their credentials to access their account*

![The screen once you log in](https://github.com/user-attachments/assets/f0312ace-adac-4d9d-8d40-23d46a211514)
*The movie search screen once you log in*

3. **Log In**: Log in to the application using your credentials. Log-ins are universal; that is, with this application, you can log in across devices and maintain the same watchlist.
4. **Search Movies**: Use the search functionality to find movies by title and filter by genre, rating, and keywords.
![Searching for a movie by title](https://github.com/user-attachments/assets/6c87f7f1-2bd4-4670-8ab2-1fdecb5faedd)
*The search interface where users can enter movie titles and apply various filters*

6. **Manage Watchlist**: Add movies to your watchlist and remove them as needed.
![Adding a movie to the watchlist](https://github.com/user-attachments/assets/07a8f1fc-7a0e-493b-916e-dd2774defa37)
*The popup shown after adding a selected movie to your personal watchlist*

![Viewing the watchlist](https://github.com/user-attachments/assets/88e3cfb7-38a4-4d09-a9ba-f79c12a4886e)
*The watchlist view displaying all saved movies in a scrollable list*

![Pressing select in the watchlist](https://github.com/user-attachments/assets/2e4d755e-f0b3-4f8a-a7f6-6a25f2e5c244)
*The selection interface within the watchlist for choosing specific movies. Pressing the delete button will delete the selected movie*

8. **Movie Info**: See details about a movie such as rating information, trailer link, and plot summary.
![Seeing the movie info from my watchlist](https://github.com/user-attachments/assets/bb583a63-6f8a-4250-8f71-3a5ac552da52)
*Detailed movie information view showing rating, plot summary, and trailer links*

10. **Movie Recommendation**: Receive a relevant movie based on selected movies from a user's personal watchlist and get an AI-generated justification for the recommendation.
![Choosing a specific movie in the watchlist](https://github.com/user-attachments/assets/723c567e-4c8a-49ee-8866-7e806f71255e)
*Selecting movies from your watchlist for recommendations*

![Getting a recommendation from said selection](https://github.com/user-attachments/assets/e94e616a-c992-4e34-af69-29ee91666a83)
*The recommendation screen showing a suggested movie based on your selection*

![Understanding why you were recommended said movie](https://github.com/user-attachments/assets/e4a6cc1f-bc14-4008-983f-5b52677b6e88)
*The AI-generated explanation for why this specific movie was recommended*
## Usage Guide

## License
MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

**THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.**

## Feedback
We welcome your feedback to improve the application! Submit your thoughts via by opening an issue on GitHub.

Guidelines:
1. Clearly describe your feedback or any issues encountered.
2. Please be constructive and provide as much detail as possible.
3. Allow up to 2-3 business days for a response.

## Contribution
Interested in contributing? Follow these steps:

1. Fork the repository.
2. Create a new branch for your changes:
`git checkout -b feature-name`
3. Commit your changes:
`git commit -m "Description of feature"`
4. Push to your fork and submit a pull request.

Contribution Guidelines:

1. Ensure your code adheres to the project's style and conventions.
2. Write clear commit messages.
3. Test your changes thoroughly before submitting.
4. We review all contributions and aim to merge valuable changes promptly.
