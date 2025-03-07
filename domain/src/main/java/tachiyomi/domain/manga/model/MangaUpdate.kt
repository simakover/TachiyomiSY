package tachiyomi.domain.manga.model

import eu.kanade.tachiyomi.source.model.UpdateStrategy

data class MangaUpdate(
    val id: Long,
    val source: Long? = null,
    val favorite: Boolean? = null,
    val lastUpdate: Long? = null,
    val dateAdded: Long? = null,
    val viewerFlags: Long? = null,
    val chapterFlags: Long? = null,
    val coverLastModified: Long? = null,
    val url: String? = null,
    val title: String? = null,
    val artist: String? = null,
    val author: String? = null,
    val description: String? = null,
    val genre: List<String>? = null,
    val status: Long? = null,
    val thumbnailUrl: String? = null,
    val updateStrategy: UpdateStrategy? = null,
    val initialized: Boolean? = null,
    // SY -->
    val filteredScanlators: List<String>? = null,
    // SY <--
)

fun Manga.toMangaUpdate(): MangaUpdate {
    return MangaUpdate(
        id = id,
        source = source,
        favorite = favorite,
        lastUpdate = lastUpdate,
        dateAdded = dateAdded,
        viewerFlags = viewerFlags,
        chapterFlags = chapterFlags,
        coverLastModified = coverLastModified,
        url = url,
        // SY -->
        title = ogTitle,
        artist = ogArtist,
        author = ogAuthor,
        description = ogDescription,
        genre = ogGenre,
        status = ogStatus,
        // SY <--
        thumbnailUrl = thumbnailUrl,
        updateStrategy = updateStrategy,
        initialized = initialized,
        // SY -->
        filteredScanlators = filteredScanlators,
        // SY <--
    )
}
