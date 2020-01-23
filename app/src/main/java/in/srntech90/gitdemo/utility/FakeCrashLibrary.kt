/*
 *
 * Created By : Tanuj Sareen on 17 - 10 - 2019
 */
package `in`.srntech90.gitdemo.utility

/** Not a real crash reporting library!  */
class FakeCrashLibrary private constructor() {
    companion object {
        fun log(priority: Int, tag: String?, message: String?) {
            println("priority: $priority, tag: $tag, message: $message")
        }

        fun logWarning(t: Throwable?) {
        }

        fun logError(t: Throwable?) {
        }
    }

    init {
        throw AssertionError("No instances.")
    }
}