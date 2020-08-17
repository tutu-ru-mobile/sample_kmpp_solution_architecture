package com.sample

class AppDiAndroid(val common: AppDi) {
    val solutionAuthAndroid by lazy { SolutionAuthAndroidImpl(common.solutionAuth) }
    val solutionWalletAndroid by lazy { SolutionWalletAndroidImpl(common.solutionWallet) }
    val solutionOrderAndroid by lazy { SolutionOrderAndroidImpl(common.solutionOrder, solutionAuthAndroid) }
    val solutionStartSearchAndroid by lazy { SolutionSearchStartAndroidImpl(common.solutionSearchStart) }
    val solutionSearchResultAndroid by lazy {
        SolutionSearchResultAndroidImpl(
            common.solutionSearchResult,
            common.solutionSearchStart
        )
    }
    val solutionWeatherAndroid by lazy {
        SolutionWeatherAndroidImpl(common.solutionWeather)
    }
    val solutionAttentionAndroid by lazy {
        SolutionAttentionAndroidImpl(
            common.solutionAttention,
            common.solutionAuth,
            solutionWeatherAndroid,
            solutionOrderAndroid,
            solutionWalletAndroid,
            common.solutionAb
        )
    }
    val solutionSearchFormAndroid by lazy {
        SolutionSearchFormAndroidImpl(
            common.solutionSearchForm,
            solutionAttentionAndroid
        )
    }
    val solutionTabSearchAndroid by lazy {
        SolutionTabSearchAndroidImpl(
            common.solutionTabSearch,
            solutionSearchFormAndroid,
            solutionStartSearchAndroid,
            solutionSearchResultAndroid
        )
    }
    val solutionAbAndroid by lazy {
        SolutionAbAndroidImpl(common.solutionAb)
    }
    val solutionSettingsAndroid by lazy {
        SolutionSettingsAndroidImpl(common.solutionSettings, solutionAuthAndroid, solutionWalletAndroid, solutionAbAndroid)
    }
    val solutionTabsAndroid by lazy {
        SolutionTabsAndroidImpl(
            common.solutionTabs,
            solutionTabSearchAndroid,
            solutionOrderAndroid,
            solutionSettingsAndroid
        )
    }
}

