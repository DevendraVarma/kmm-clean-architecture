@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.time

actual class Timezone {
    actual val rawOffset: Int
        get() = TODO("Not yet implemented")

    actual companion object {
        actual fun wrap(native: Any): Timezone {
            TODO("Not yet implemented")
        }

        actual fun default(): Timezone {
            TODO("Not yet implemented")
        }

        actual fun timezoneOf(id: String): Timezone {
            TODO("Not yet implemented")
        }

    }
}