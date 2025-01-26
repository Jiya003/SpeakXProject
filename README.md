# Android Pagination and Search Feature Implementation

So hello, Developers!!! 
## Overview
This project demonstrates how to implement pagination and a search bar in an Android app using a `ListView`. It allows users to search for items in the list, dynamically load more data when scrolling, and highlight searched text for better visibility.

## Features
1. **Pagination**:
   - Automatically loads more data when the user scrolls to the bottom of the `ListView`.
   - Avoids unnecessary duplicate requests using a loading state (`isLoading`).

2. **Search Functionality**:
   - Includes an `EditText` as a search bar at the top.
   - Filters the displayed data based on user input.
   - Highlights the searched text in each list item.

## Key Components
- **MockApiService**: Simulates fetching paginated data from an API.
- **MainActivity**: Manages the `ListView`, `Adapter`, and handles pagination and search logic.
- **CustomAdapter**: Highlights the searched text within list items.

## Classes and Functions
- **MockApiService**:
  - `fetchItems(page: Int, callback: (List<String>) -> Unit)`: Simulates API calls with a delay.

- **MainActivity**:
  - `loadData()`: Fetches and appends new data to the list.
  - `setupSearchBar()`: Filters and highlights search results.
  - `highlightSearch()`: Custom logic for text highlighting.

- **CustomAdapter**:
  - Overrides `getView` to dynamically style list items with highlighted text.

## How to Use
1. Clone the project and open it in Android Studio.
2. Download Android SDK and emulator.
3. Run the app on an emulator or physical device.
4. Use the search bar to find specific items. Matching text will be highlighted.
5. Scroll to the bottom of the list to trigger pagination and load more items.

## Notes
- Please ensure to have the required AndroidX dependencies in your `build.gradle` file.
- For highlighting, the app uses `SpannableString` to customize text appearance.

## Future Enhancements
- Adding a shimmer effect for better UX while loading data.
- Replacing `MockApiService` with a real backend API service.
- Using MVVM architecture

## License
This code is free to use for educational and personal projects. Pull requests are welcome.
