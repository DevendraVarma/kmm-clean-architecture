package io.tagd.langx.ref

actual class SoftReference<T> actual constructor(value: T) {
    actual fun get(): T? {
        TODO("Not yet implemented")
    }

    actual fun clear() {
    }

}

actual fun <T> T.soft(): SoftReference<T> {
    return SoftReference(this)
}