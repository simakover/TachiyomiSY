package eu.kanade.tachiyomi.ui.reader.setting

import eu.kanade.tachiyomi.ui.reader.viewer.pager.PagerConfig
import eu.kanade.tachiyomi.util.system.isReleaseBuildType
import tachiyomi.core.preference.PreferenceStore
import tachiyomi.core.preference.getEnum

class ReaderPreferences(
    private val preferenceStore: PreferenceStore,
) {

    // region General

    // SY -->
    fun pageTransitionsPager() = preferenceStore.getBoolean("pref_enable_transitions_pager_key", true)

    fun pageTransitionsWebtoon() = preferenceStore.getBoolean("pref_enable_transitions_webtoon_key", true)
    // SY <--

    fun doubleTapAnimSpeed() = preferenceStore.getInt("pref_double_tap_anim_speed", 500)

    fun showPageNumber() = preferenceStore.getBoolean("pref_show_page_number_key", true)

    fun showReadingMode() = preferenceStore.getBoolean("pref_show_reading_mode", true)

    fun trueColor() = preferenceStore.getBoolean("pref_true_color_key", false)

    fun fullscreen() = preferenceStore.getBoolean("fullscreen", true)

    fun cutoutShort() = preferenceStore.getBoolean("cutout_short", true)

    fun keepScreenOn() = preferenceStore.getBoolean("pref_keep_screen_on_key", true)

    fun defaultReadingMode() = preferenceStore.getInt("pref_default_reading_mode_key", ReadingModeType.RIGHT_TO_LEFT.flagValue)

    fun defaultOrientationType() = preferenceStore.getInt("pref_default_orientation_type_key", OrientationType.FREE.flagValue)

    // TODO: Enable in release build when the feature is stable
    fun longStripSplitWebtoon() = preferenceStore.getBoolean("pref_long_strip_split_webtoon", !isReleaseBuildType)

    fun imageScaleType() = preferenceStore.getInt("pref_image_scale_type_key", 1)

    fun zoomStart() = preferenceStore.getInt("pref_zoom_start_key", 1)

    fun readerTheme() = preferenceStore.getInt("pref_reader_theme_key", 1)

    fun alwaysShowChapterTransition() = preferenceStore.getBoolean("always_show_chapter_transition", true)

    fun cropBorders() = preferenceStore.getBoolean("crop_borders", false)

    fun navigateToPan() = preferenceStore.getBoolean("navigate_pan", true)

    fun landscapeZoom() = preferenceStore.getBoolean("landscape_zoom", true)

    fun cropBordersWebtoon() = preferenceStore.getBoolean("crop_borders_webtoon", false)

    fun webtoonSidePadding() = preferenceStore.getInt("webtoon_side_padding", 0)

    fun readerHideThreshold() = preferenceStore.getEnum("reader_hide_threshold", ReaderHideThreshold.LOW)

    fun folderPerManga() = preferenceStore.getBoolean("create_folder_per_manga", false)

    fun skipRead() = preferenceStore.getBoolean("skip_read", false)

    fun skipFiltered() = preferenceStore.getBoolean("skip_filtered", true)

    fun skipDupe() = preferenceStore.getBoolean("skip_dupe", false)

    // endregion

    // region Split two page spread

    fun dualPageSplitPaged() = preferenceStore.getBoolean("pref_dual_page_split", false)

    fun dualPageInvertPaged() = preferenceStore.getBoolean("pref_dual_page_invert", false)

    fun dualPageSplitWebtoon() = preferenceStore.getBoolean("pref_dual_page_split_webtoon", false)

    fun dualPageInvertWebtoon() = preferenceStore.getBoolean("pref_dual_page_invert_webtoon", false)

    // endregion

    // region Color filter

    fun customBrightness() = preferenceStore.getBoolean("pref_custom_brightness_key", false)

    fun customBrightnessValue() = preferenceStore.getInt("custom_brightness_value", 0)

    fun colorFilter() = preferenceStore.getBoolean("pref_color_filter_key", false)

    fun colorFilterValue() = preferenceStore.getInt("color_filter_value", 0)

    fun colorFilterMode() = preferenceStore.getInt("color_filter_mode", 0)

    fun grayscale() = preferenceStore.getBoolean("pref_grayscale", false)

    fun invertedColors() = preferenceStore.getBoolean("pref_inverted_colors", false)

    // endregion

    // region Controls

    fun readWithLongTap() = preferenceStore.getBoolean("reader_long_tap", true)

    fun readWithVolumeKeys() = preferenceStore.getBoolean("reader_volume_keys", false)

    fun readWithVolumeKeysInverted() = preferenceStore.getBoolean("reader_volume_keys_inverted", false)

    fun navigationModePager() = preferenceStore.getInt("reader_navigation_mode_pager", 0)

    fun navigationModeWebtoon() = preferenceStore.getInt("reader_navigation_mode_webtoon", 0)

    fun pagerNavInverted() = preferenceStore.getEnum("reader_tapping_inverted", TappingInvertMode.NONE)

    fun webtoonNavInverted() = preferenceStore.getEnum("reader_tapping_inverted_webtoon", TappingInvertMode.NONE)

    fun showNavigationOverlayNewUser() = preferenceStore.getBoolean("reader_navigation_overlay_new_user", true)

    fun showNavigationOverlayOnStart() = preferenceStore.getBoolean("reader_navigation_overlay_on_start", false)

    // endregion

    // SY -->

    fun readerThreads() = preferenceStore.getInt("eh_reader_threads", 2)

    fun readerInstantRetry() = preferenceStore.getBoolean("eh_reader_instant_retry", true)

    fun aggressivePageLoading() = preferenceStore.getBoolean("eh_aggressive_page_loading", false)

    fun cacheSize() = preferenceStore.getString("eh_cache_size", "75")

    fun autoscrollInterval() = preferenceStore.getFloat("eh_util_autoscroll_interval", 3f)

    fun smoothAutoScroll() = preferenceStore.getBoolean("smooth_auto_scroll", true)

    fun preserveReadingPosition() = preferenceStore.getBoolean("eh_preserve_reading_position", false)

    fun preloadSize() = preferenceStore.getInt("eh_preload_size", 10)

    fun useAutoWebtoon() = preferenceStore.getBoolean("eh_use_auto_webtoon", true)

    fun webtoonEnableZoomOut() = preferenceStore.getBoolean("webtoon_enable_zoom_out", false)

    fun continuousVerticalTappingByPage() = preferenceStore.getBoolean("continuous_vertical_tapping_by_page", false)

    fun cropBordersContinuousVertical() = preferenceStore.getBoolean("crop_borders_continues_vertical", false)

    fun forceHorizontalSeekbar() = preferenceStore.getBoolean("pref_force_horz_seekbar", false)

    fun landscapeVerticalSeekbar() = preferenceStore.getBoolean("pref_show_vert_seekbar_landscape", false)

    fun leftVerticalSeekbar() = preferenceStore.getBoolean("pref_left_handed_vertical_seekbar", false)

    fun readerBottomButtons() = preferenceStore.getStringSet("reader_bottom_buttons", ReaderBottomButton.BUTTONS_DEFAULTS)

    fun pageLayout() = preferenceStore.getInt("page_layout", PagerConfig.PageLayout.AUTOMATIC)

    fun invertDoublePages() = preferenceStore.getBoolean("invert_double_pages", false)

    fun centerMarginType() = preferenceStore.getInt("center_margin_type", PagerConfig.CenterMarginType.NONE)
    // SY <--

    enum class TappingInvertMode(val shouldInvertHorizontal: Boolean = false, val shouldInvertVertical: Boolean = false) {
        NONE,
        HORIZONTAL(shouldInvertHorizontal = true),
        VERTICAL(shouldInvertVertical = true),
        BOTH(shouldInvertHorizontal = true, shouldInvertVertical = true),
    }

    enum class ReaderHideThreshold(val threshold: Int) {
        HIGHEST(5),
        HIGH(13),
        LOW(31),
        LOWEST(47),
    }
}
