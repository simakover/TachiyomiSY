package eu.kanade.tachiyomi.data.updater

import android.content.Context
import eu.kanade.tachiyomi.BuildConfig
import eu.kanade.tachiyomi.network.GET
import eu.kanade.tachiyomi.network.NetworkHelper
import eu.kanade.tachiyomi.network.awaitSuccess
import eu.kanade.tachiyomi.network.parseAs
import eu.kanade.tachiyomi.util.system.isInstalledFromFDroid
import eu.kanade.tachiyomi.util.system.isPreviewBuildType
import eu.kanade.tachiyomi.util.system.isReleaseBuildType
import exh.syDebugVersion
import kotlinx.serialization.json.Json
import tachiyomi.core.preference.Preference
import tachiyomi.core.preference.PreferenceStore
import tachiyomi.core.util.lang.withIOContext
import uy.kohesive.injekt.injectLazy
import java.util.Date
import kotlin.time.Duration.Companion.days

class AppUpdateChecker {

    private val networkService: NetworkHelper by injectLazy()
    private val preferenceStore: PreferenceStore by injectLazy()
    private val json: Json by injectLazy()

    private val lastAppCheck: Preference<Long> by lazy {
        preferenceStore.getLong("last_app_check", 0)
    }

    suspend fun checkForUpdate(context: Context, isUserPrompt: Boolean = false): AppUpdateResult {
        // Limit checks to once every 3 days at most
        if (isUserPrompt.not() && Date().time < lastAppCheck.get() + 3.days.inWholeMilliseconds) {
            return AppUpdateResult.NoNewUpdate
        }

        return withIOContext {
            val result = with(json) {
                networkService.client
                    .newCall(GET("https://api.github.com/repos/$GITHUB_REPO/releases/latest"))
                    .awaitSuccess()
                    .parseAs<GithubRelease>()
                    .let {
                        lastAppCheck.set(Date().time)

                        // Check if latest version is different from current version
                        if (/* SY --> */ isNewVersionSY(it.version) /* SY <-- */) {
                            if (context.isInstalledFromFDroid()) {
                                AppUpdateResult.NewUpdateFdroidInstallation
                            } else {
                                AppUpdateResult.NewUpdate(it)
                            }
                        } else {
                            AppUpdateResult.NoNewUpdate
                        }
                    }
            }

            when (result) {
                is AppUpdateResult.NewUpdate -> AppUpdateNotifier(context).promptUpdate(result.release)
                is AppUpdateResult.NewUpdateFdroidInstallation -> AppUpdateNotifier(context).promptFdroidUpdate()
                else -> {}
            }

            result
        }
    }

    // SY -->
    private fun isNewVersionSY(versionTag: String) = (versionTag != BuildConfig.VERSION_NAME && isReleaseBuildType) || (isPreviewBuildType && versionTag != syDebugVersion)
    // SY <--

    private fun isNewVersion(versionTag: String): Boolean {
        // Removes prefixes like "r" or "v"
        val newVersion = versionTag.replace("[^\\d.]".toRegex(), "")

        return if (BuildConfig.DEBUG) {
            // Preview builds: based on releases in "tachiyomiorg/tachiyomi-preview" repo
            // tagged as something like "r1234"
            newVersion.toInt() > BuildConfig.COMMIT_COUNT.toInt()
        } else {
            // Release builds: based on releases in "tachiyomiorg/tachiyomi" repo
            // tagged as something like "v0.1.2"
            val oldVersion = BuildConfig.VERSION_NAME.replace("[^\\d.]".toRegex(), "")

            val newSemVer = newVersion.split(".").map { it.toInt() }
            val oldSemVer = oldVersion.split(".").map { it.toInt() }

            oldSemVer.mapIndexed { index, i ->
                if (newSemVer[index] > i) {
                    return true
                }
            }

            false
        }
    }
}

val GITHUB_REPO: String by lazy {
    // SY -->
    if (isPreviewBuildType) {
        "jobobby04/TachiyomiSYPreview"
    } else {
        "jobobby04/tachiyomiSY"
    }
    // SY <--
}
