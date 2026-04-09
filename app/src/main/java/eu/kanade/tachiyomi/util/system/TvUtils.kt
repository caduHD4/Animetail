package eu.kanade.tachiyomi.util.system

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import eu.kanade.domain.ui.UiPreferences
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Verifica se o modo TV está ativo, levando em conta a preferência salva pelo usuário
 * e, opcionalmente, a detecção automática de hardware de TV.
 *
 * Retorna `true` se:
 * 1. O usuário escolheu explicitamente o Modo TV nas preferências, OU
 * 2. Nenhuma escolha foi feita ainda e o hardware é detectado como TV.
 */
fun isTv(context: Context): Boolean {
    val uiPreferences = Injekt.get<UiPreferences>()
    // Se o usuário já escolheu um modo, respeita a escolha dele
    if (uiPreferences.shownDeviceModeChooser.get()) {
        return uiPreferences.deviceMode.get() == eu.kanade.domain.ui.model.DeviceMode.TV
    }
    // Caso contrário, detecta automaticamente pelo hardware
    return isTvHardware(context)
}

/**
 * Detecta se o dispositivo é uma TV por hardware (UiModeManager + heurísticas).
 * Não leva em conta a preferência do usuário.
 */
fun isTvHardware(context: Context): Boolean {
    val uiModeManager = context.getSystemService(UiModeManager::class.java)
    if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
        return true
    }
    // Detecta set-top boxes Android que não reportam UI_MODE_TYPE_TELEVISION
    return isTvBox(context)
}

fun isTvBox(context: Context): Boolean {
    val pm: PackageManager = context.packageManager

    // TV for sure
    if (
        context.getSystemService(UiModeManager::class.java)
            .getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION
    ) {
        return true
    }

    // Missing Files app (DocumentsUI) means box (some boxes still have non functional app or stub)
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.setType("video/*")
    if (intent.resolveActivity(pm) == null) {
        return true
    }

    // Legacy storage no longer works on Android 11 (level 30)
    if (Build.VERSION.SDK_INT < 30) {
        // (Some boxes still report touchscreen feature)
        if (!pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)) {
            return true
        }
        if (pm.hasSystemFeature("android.hardware.hdmi.cec")) {
            return true
        }
        if (Build.MANUFACTURER.equals("zidoo", ignoreCase = true)) {
            return true
        }
    }

    // Default: No TV - use SAF
    return false
}

