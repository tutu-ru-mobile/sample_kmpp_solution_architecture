package com.sample

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.preferredHeight
import androidx.ui.unit.dp

class SolutionSettingsAndroidImpl(
    val impl: SolutionSettingsImpl,
    val authAndroid: SolutionAuthAndroidApi,
    val bonus:SolutionBonusApiAndroid,
    val ab:SolutionAbAndroidApi
) : SolutionSettingsApiAndroid {

    @Composable
    override fun renderSettings() {
        central {
            Spacer(Modifier.preferredHeight(16.dp))
            authAndroid.renderLoginForm()
            Spacer(Modifier.preferredHeight(16.dp))
            bonus.renderBonusesAndRefillButton()
        }
        Spacer(Modifier.preferredHeight(16.dp))
        ab.renderAbSettings()
    }

}