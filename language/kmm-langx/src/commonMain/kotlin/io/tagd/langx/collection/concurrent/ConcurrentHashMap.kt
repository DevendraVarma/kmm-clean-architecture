@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.collection.concurrent

expect open class ConcurrentHashMap<K, V> : MutableMap<K, V> {
    constructor()
    constructor(initialCapacity: Int)
    constructor(initialCapacity: Int, loadFactor: Float)
    constructor(original: Map<out K, V>)

    // From Map

    override val size: Int
    override fun isEmpty(): Boolean
    override fun containsKey(key: K): Boolean
    override fun containsValue(value: @UnsafeVariance V): Boolean
    override operator fun get(key: K): V?

    // From MutableMap

    override fun put(key: K, value: V): V?
    override fun remove(key: K): V?
    override fun putAll(from: Map<out K, V>)
    override fun clear()
    override val keys: MutableSet<K>
    override val values: MutableCollection<V>
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>

    // From ConcurrentMap

    fun getOrDefault(key: K, defaultValue: V): V

    fun putIfAbsent(key: K, value: V): V?

    fun remove(key: K, value: V): Boolean

    fun replace(key: K, oldValue: V, newValue: V): Boolean

    fun replace(key: K, value: V): V?
}