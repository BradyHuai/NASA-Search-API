# NASA-Search-API

## Summary
The NASA Search API is an Android application that allows users to search and view images from the NASA's Image and Video Library API. Users can enter a certain query such as "Earth", "Apollo", "Moon", and the APP will display a list of images with their titles. Users can click into the image and have a full view of the image as well as a detailed description of the image, and the date when the image was created.

## Architecture Overview
The app is built using the following components and design patterns:
- Language: Java
- Framework: Android SDK
- UI Components: SearchView, ListView, ImageView, TextView

The app follows the traditional Model View Controller design pattern, user interact with the main activity and triggers controller to send get request to NASA's Image API. The controller itself is an AsyncTask where it creates the get request using the query passed in from the view and makes the HTTP connection. The controller then receives the data, updates the model and send the model back to the view to be displayed. The model `ItemNASA` can be found in the file `app/src/main/java/com/example/nasasearchapi/data/ItemNASA.java`, it encapsulates information related to each NASA item: 
- Title of the NASA image
- Description of the NASA image
- the date when the NASA image is created
- The thumb image link for the image
- The unique id for the image

## Third-party Libraries
1. Picasso (https://github.com/square/picasso)
- Picasso is a popular image loading library for Android, it offers simple API for loading images.
- It integrates easily with the Android ImageView and image source URL, which are being used in this APP.
- It is also UI friendly which automatically handles image resizing and caching.
2. Mockito (https://site.mockito.org)
- Mockito is popular in Unit testing and it simplifies the process of creating objects by mocking them.
- In this APP, it is easy to mock a listener interface and observe the callback functions.
- The syntax is readable and intuitive, makes it easy for verifying assertions.
3. Robolectric (https://robolectric.org)
- Robolectric provides fast and lightweight unit testing directly on JVM.
- It is particularly helpful when testing AsyncTask in this APP, where we can directly test AsyncTask in unit test.
4. Espresso (https://developer.android.com/training/testing/espresso)
- Espresso has simply and intuitive API for UI testing and Instrumented tests.
- Espresso can integrate with other frameworks such as JUnit and Mockito.
- It is particularly helpful when testing UI components such as ImageView, TextView, SearchView, and ListView in this APP.

## Run/Build
### Prerequisites
- Android Studio IDE
- Android SDK
- Java Development Kit (JDK)

### Steps:
1. Clone the repository `git clone https://github.com/BradyHuai/NASA-Search-API.git`, or download it as zip file
2. Open the project in Android Studio:
- Launch Android studio
- Click on `Open an existing Android Studio Project`
- Navigate to the cloned project directory and select the root folder
3. Build and run the app:
- Select the desired target device or emulator from the toolbar.
- Click `run` button to build and run the app.
4. Interact with the app:
- enter queries on the top search bar and browse through the NASA image results

Note: This app requires an internet connection to search for images and load image data from NASA's API.

## Demonstrations

<img width="361" alt="Screenshot 1" src="https://github.com/BradyHuai/NASA-Search-API/assets/66107241/588b1be8-f369-4f5b-9732-a05e50466de8">
<img width="365" alt="Screenshot 2" src="https://github.com/BradyHuai/NASA-Search-API/assets/66107241/eb4cd500-cfca-45cb-bb7f-f3651f3b8221">
<img width="366" alt="Screenshot 3" src="https://github.com/BradyHuai/NASA-Search-API/assets/66107241/035cd816-7dd8-4dda-89d5-65867d856409">

