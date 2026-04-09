package eu.kanade.domain.ui.model

import dev.icerock.moko.resources.StringResource
import tachiyomi.i18n.tail.TLMR

/**
 * Modos de uso do dispositivo: celular/tablet (mobile) ou Android TV.
 */
enum class DeviceMode(val titleRes: StringResource) {
    /** Modo padrão para celular e tablet (touch). */
    MOBILE(TLMR.strings.device_mode_mobile),

    /** Modo otimizado para Android TV / controle remoto. */
    TV(TLMR.strings.device_mode_tv),
}
