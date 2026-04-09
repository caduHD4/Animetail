package eu.kanade.tachiyomi.ui.devicemode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import eu.kanade.domain.ui.UiPreferences
import eu.kanade.presentation.devicemode.DeviceModeChooserScreen
import eu.kanade.presentation.util.Screen
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Tela de escolha do modo de uso do dispositivo (Mobile ou TV).
 *
 * Exibida na primeira abertura do app ou quando acessada pelas configurações.
 */
class DeviceModeChooserScreen : Screen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val uiPreferences = remember { Injekt.get<UiPreferences>() }

        DeviceModeChooserScreen(
            onChoose = { mode ->
                // Salva a escolha e marca que o seletor já foi exibido
                uiPreferences.deviceMode.set(mode)
                uiPreferences.shownDeviceModeChooser.set(true)
                navigator.pop()
            },
        )
    }
}
