# PokeDex
This is a small sample Android application that provides information about Pokemon, including 
official sprites and basic stats.

- **Architecture**: This project follows the Model-View-ViewModel (MVVM) architecture.
- **API**: The data in this app is provided by [PokeApi](https://pokeapi.co).
- **Navigation**: The navigation strategy employed by this project is based on the [Android docs on
navigation and type safety](https://developer.android.com/guide/navigation/design/type-safety#type-safety)
and the [Now In Android app](https://github.com/android/nowinandroid/blob/main/app/src/main/kotlin/com/google/samples/apps/nowinandroid/ui/interests2pane/InterestsListDetailScreen.kt#L109)
- **Testing**: Unit tests for core functionality can be found in app/src/test/java/com/
example/pokedex and run with `./gradlew test`.

## Running the app
Running the app is as simple as building the project to any Android device API 23 and above (Android
6+). No unique API key is necessary to access the data, just build and enjoy!