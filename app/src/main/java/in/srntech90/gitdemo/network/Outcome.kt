
package `in`.srntech90.gitdemo.network


 /*Sealed Classes for Type Safety in Outcome: Results */
sealed class Outcome<T> {

    data class Progress<T>(var loading: Boolean) : Outcome<T>()
    data class Success<T>(var data: T) : Outcome<T>()
    data class Failure<T>(var error: String) : Outcome<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): Outcome<T> = Progress(isLoading)

        fun <T> success(data: T): Outcome<T> = Success(data)

        fun <T> failure(error: String): Outcome<T> = Failure(error)
    }
}