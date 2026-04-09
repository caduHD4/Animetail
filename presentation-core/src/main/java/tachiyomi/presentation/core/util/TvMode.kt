package tachiyomi.presentation.core.util

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * CompositionLocal that indicates whether the UI is running in TV mode
 * (Android TV, Google TV, Fire TV, etc.).
 *
 * When `true`, touch-centric interactions such as pull-to-refresh and
 * the automatic soft keyboard should be suppressed because they either
 * do not work with a D-pad remote or actively interfere with navigation.
 *
 * Default value is `false` (mobile/tablet mode).
 * Set to `true` from `MainActivity` via `CompositionLocalProvider`.
 */
val LocalTvMode: ProvidableCompositionLocal<Boolean> = staticCompositionLocalOf { false }
