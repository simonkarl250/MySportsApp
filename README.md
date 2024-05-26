# Football Club Search App

## Overview

The Football Club Search App allows users to search for football clubs online using a comprehensive library database. This app was created as part of a challenge project during an interview, where I achieved a 98% skill test score. The app is built using Kotlin and Android Studio, and provides a seamless user experience for football enthusiasts to find information about their favorite clubs.
## Description of the task

You are required to implement an Android application using Kotlin and Jetpack Compose
described by the speci;ications below.
You are not allowed to use third-party libraries. The only libraries that you can use are
the standard Android API libraries found in the following URL (with the exception of Views
that you should NOT use):
h"ps://developer.android.com/reference/
It is important to follow exactly the speciGications and your implementation must
conform to these:
The application developed will be helping users to obtain information about football clubs.
The application will be using the h"ps://www.thesportsdb.com/api.php/ Web service and the
Room Library to save information about clubs.
1. When the application starts, it presents the user with 3 buttons labelled Add Leagues to DB,
   Search for Clubs By League and Search for Clubs. (2 marks)
   •
   Clicking on the Add Leagues to DB button saves all the details of a few football leagues
   (from different countries) in an SQLite database which is local to the mobile device
   using the Room library. The speci;ic information of the leagues which will be saved
   is shown in the following link and the information saved could simply be hardcoded
   in the application.
   h"ps://ddracopo.github.io/DOCUM/courses/5cosc023w/football_leagues.txt
   An appropriate database with appropriate tables should be created and populated by your
   application, based on the above data.
2. 
## Features

- **Search Football Clubs:** Users can search for football clubs using the app's intuitive search functionality.
- **Library Database:** The app utilizes a library database to store and retrieve information about football clubs.
- **User-friendly Interface:** Simple and clean interface for easy navigation and usage.
- **Fast Performance:** Efficient data retrieval for a smooth user experience.

## Technology Stack

- **Kotlin** for app development
- **Android Studio** as the development environment
- **Library Database** for data storage and retrieval

## Screenshots

### Main Screens

<table>
  <tr>
    <td><img src="/images/homepage.png" alt="Home Screen" width="300"/><br>*Home Screen*</td>
    <td><img src="/images/search_and_save.png" alt="Search Screen" width="300"/><br>*Search and save Screen*</td>
  </tr>
  <tr>
    <td><img src="/images/search_from_database.png" alt="Results Screen" width="300"/><br>*Database search Screen*</td>
    <td><img src="/images/search_from_web.png" alt="Details Screen" width="300"/><br>*Web search Screen*</td>
  </tr>
</table>

## Getting Started

### Prerequisites

- Android Studio installed on your machine
- A basic understanding of Kotlin and Android development

### Setup Instructions

1. **Clone the repository:**

    ```sh
    git clone https://github.com/simonkarl250/MySportsApp.git
    cd football-club-search-app
    ```

2. **Open the project in Android Studio:**

    - Launch Android Studio
    - Select `Open an existing Android Studio project`
    - Navigate to the cloned repository and select it

3. **Build and Run the app:**

    - Sync the project with Gradle files
    - Build and run the app on an emulator or a physical device

## Contributing

We welcome contributions to enhance the app. To contribute, please follow these steps:

### Steps to Contribute

1. **Fork the repository**

2. **Create a new branch:**

    ```sh
    git checkout -b feature-name
    ```

3. **Make your changes**

4. **Commit your changes:**

    ```sh
    git commit -m "Description of your changes"
    ```

5. **Push to your branch:**

    ```sh
    git push origin feature-name
    ```

6. **Submit a pull request**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or feedback, please reach out to:

- Your Name: your.email@example.com
- Project Link: [https://github.com/simonkarl250/MySportsApp.git](https://github.com/simonkarl250/MySportsApp.git)
