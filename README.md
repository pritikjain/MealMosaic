# MealMosaic
MealMosaic is an app that make finding recipes easy that users can explore and compile into a personalized collection by adding them in favorite section.  

## Architecture
This application build with MVVM Architecture based on the latest guild lines from Google. You can take a look [here](https://developer.android.com/topic/architecture?hl=fr).For more information and [here](https://github.com/android/nowinandroid) for an official example.

## Major Highlights

- **MVVM architecture** for a clean and scalable codebase
- **Kotlin** and **Kotlin DSL**
- **Dagger Hilt** for efficient dependency injection.
- **Retrofit** for seamless networking
- **Room DB** for local storage of news articles
- **Intuit SDP** An android lib that provides a new size unit - sdp.[here](https://github.com/intuit/sdp)
- **Instant search** for quick access to relevant categories of food
- **Navigation** for smooth transitions between screens
- **Coroutines** for asynchronous programming
- Long-press to get the more info about the recipe 
- Search the recipe based on categories
- Add recipes into favorites section

<img src='/assets/app_architecture.png' title='MVVM-Architecture' width='' alt='MVVM-Architecture' />

## Video Walkthrough

Here's a walkthrough of implemented features:

<img src='MealMosaic.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap] (http://www.cockos.com/locecap/).

## Features Implemented

- Showing popular recipe on home Screen 
- Search recipe by name or categories on home screen
- Save fav recipe for future reference
- Check the details of the recipe

## Dependency Use

- XML for UI: Modern UI toolkit for building native Android UIs
- Coil for Image Loading: Efficiently loads and caches images
- Retrofit for Networking: A type-safe HTTP client for smooth network requests
- Dagger Hilt for Dependency Injection: Simplifies dependency injection
- Room for Database: A SQLite object mapping library for local data storage
- Paging Compose for Pagination: Simplifies the implementation of paginated lists

## The Complete Project Folder Structure

```
|── MyApplication.kt
├── activities
│   ├── CategoryMealsActivity.kt
│   ├── MainActivity.kt
│   ├── MealActivity.kt
├── adapters
│   ├── CategoriesAdapter.kt
│   ├── CategoryMealsAdapter.kt
│   ├── MealsAdapter.kt
│   ├── MostPopularAdapter.kt
├── bottomSheet
│   ├── MealBottomSheetFragment.kt
├── db
│   ├── MealDao.kt
│   ├── MealDatabase.kt
│   ├── MealsTypeConverter.kt
│   ├── RoomDB.kt
├── endpoint
│   ├── MealApi.kt
│   ├── RetrofitInstance.kt
├── fragments
│   ├── CategoriesFragment.kt
│   ├── FavoritesFragment.kt
│   ├── HomeFragment.kt
│   ├── SearchFragment.kt
├── model
│   ├── Category.kt
│   ├── CategoryList.kt
│   ├── Meal.kt
│   ├── MealList.kt
│   ├── MealByCategory.kt
│   ├── MealByCategoryList.kt
├── repository
│   ├── MealsRepository.kt
├── viewmodel
│   ├── CategoryMealsViewModel.kt
│   ├── HomeViewModel.kt
│   ├── MealViewModel.kt
```
## If this project helps you, show love ❤️ by putting a ⭐ on this project ✌️

## Contribute to the project

Feel free to improve or add features to the project.
Create an issue or find the pending issue. All pull requests are welcome 😄


