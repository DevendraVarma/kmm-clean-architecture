package io.tagd.arch.infra

interface Resource

interface INamedResource : Resource {

    val nameWithOrWithoutRelativePath: String?
}

data class NamedResource(
    /**
     * raw/a_json_file
     * drawable/bg_image
     * dimen/right_margin
     */
    override val nameWithOrWithoutRelativePath: String? = null,
) : INamedResource {

    fun invalid(): Boolean {
        return nameWithOrWithoutRelativePath.isNullOrEmpty()
    }
}

interface ICompressedResource : Resource {
    /**
     * raw
     * drawable
     * dimen
     * etc
     */
    val type: String?

    /**
     * some arbitrary identifier generated by resource compressor
     */
    val identifier: Int

    val `package`: String?
}

open class CompressedResource(
    override val type: String? = null,
    override val identifier: Int = -1,
    override val `package`: String? = null
) : ICompressedResource {

    open fun invalid(): Boolean {
        return (identifier == -1 && type.isNullOrEmpty())
    }

    fun copy(
        group: String? = this.type,
        identifier: Int = this.identifier,
        `package`: String? = this.`package`
    ): CompressedResource {

        return CompressedResource(type = group, identifier = identifier, `package` = `package`)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CompressedResource

        if (type != other.type) return false
        if (identifier != other.identifier) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type?.hashCode() ?: 0
        result = 31 * result + identifier
        return result
    }

    override fun toString(): String {
        return "$type/$identifier"
    }

}

data class UnifiedResource(
    override val nameWithOrWithoutRelativePath: String? = null,
    override val type: String? = null,
    override val identifier: Int = -1,
    override val `package`: String?,
) : CompressedResource(
    type = type,
    identifier = identifier,
    `package` = `package`
), INamedResource {

    override fun invalid(): Boolean {
        val caseIdentifierInvalid = (identifier == -1 && type.isNullOrEmpty())
        val caseNameInvalid = nameWithOrWithoutRelativePath.isNullOrEmpty()
        return caseIdentifierInvalid && caseNameInvalid
    }

    fun isNamed(): Boolean {
        return !nameWithOrWithoutRelativePath.isNullOrEmpty()
    }

    fun isCompressed(): Boolean {
        return !(identifier == -1 && type.isNullOrEmpty())
    }
}