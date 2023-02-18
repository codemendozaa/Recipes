# Recipe - Compose

This app is designed with some concepts, skills and new technologies. The application allows you to obtain all the information from[Recipe API](https://demo3504668.mockable.io/recipies?page=1).

## :star: Features

- [x] Get recipes from the API and display them in a list.
- [x] Show error screen if you try to recover data and have no network connection.
- [x] Implement Pagination.
- [x] Save all recipes to local storage.
- [x] See the detail of a specific recipe.
- [x] Unit tests for domain, data and presentation layers
- [x] Implement code coverage with Jacoco

:runner: For run the app just clone the repository and execute the app on Android Studio.

### Requirements to install the app
- Use phones with Android Api 23+
- Having an internet connection

##### This application was developed using Kotlin and uses the following components:
- Jetpack compose
- Coroutines
- Clean architecture (Domain, Data, Presentation)
- MVVM
- Repository pattern
- Use cases
- Flow
- StateFlow
- Jetpack navigation
- Retrofit
- Room dabatase (Local storage)
- Pagination
- ViewModel
- Accompanist ( navigation animation)
- Dagger Hilt (Dependency injection)
- Coil (Load images)
- Retrofit (HTTP requests)
- Unit testing (Mockk, Turbine)
- Code coverage (Jacoco)


## :dart: Architecture

The application is built using Clean Architeture pattern based on [Architecture Components](https://developer.android.com/jetpack/guide#recommended-app-arch) on Android. The application is divided into three layers:

![Clean Arquitecture](https://devexperto.com/wp-content/uploads/2018/10/clean-architecture-own-layers.png)

- Domain: This layer contains the business logic of the application, here we define the data models and the use cases.
- Data: This layer contains the data layer of the application. It contains the database, network and the repository implementation.
- Presentation: This layer contains the presentation layer of the application.

## License

**@Codemendozaa**
