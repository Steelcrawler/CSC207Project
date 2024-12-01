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

   
2. **Log In**: Log in to the application using your credentials. Log-ins are universal; that is, with this application, you can log in across devices and maintain the same watchlist.
3. **Search Movies**: Use the search functionality to find movies by title and filter by genre, rating, and keywords.
4. **Manage Watchlist**: Add movies to your watchlist and remove them as needed.
5. **Movie Info**: See details about a movie such as rating information, trailer link, and plot summary.
6. **Movie Recommendation**: Receive a relevant movie based on selected movies from a user's personal watchlist and get an AI-generated justification for the recommendation.

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
