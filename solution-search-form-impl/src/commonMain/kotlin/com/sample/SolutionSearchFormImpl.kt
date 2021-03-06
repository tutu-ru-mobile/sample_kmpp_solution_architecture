package com.sample

import kotlinx.coroutines.flow.Flow

class SolutionSearchFormImpl(
    private val searchStartApi: SolutionSearchStartApi
) : SolutionSearchFormApi, SolutionWithState {

    /**
     * Цвет обводки для простоты понимая архитектуры и разбиения по Solution-ам.
     */
    fun getColor() = MyColors.SOLUTION_SEARCH_FORM

    data class State(
        val searchFrom: String = "Москва",
        val searchTo: String = "Санкт-Петербург"
    )

    sealed class Action {
        class From(val str: String) : Action()
        class To(val str: String) : Action()
        object Search : Action()
    }

    val store = createStore(State()) { s, a: Action ->
        when (a) {
            is Action.From -> {
                s.copy(
                    searchFrom = a.str
                )
            }
            is Action.To -> {
                s.copy(
                    searchTo = a.str
                )
            }
            is Action.Search -> {
                searchStartApi.startSearch(
                    searchQuery = "${s.searchFrom} - ${s.searchTo}"
                )
                s
            }

        }
    }

    override fun onStateUpdate(): Flow<*> = store.stateFlow

    val attentionBackgroundColor = HexColor(0x22_FF_FF_00)

    // Для iOS проще пользоваться не State-ом, а специальной прослойкой из helper-функий
    fun getState() = store.state
    fun send(action: Action) {
        store.send(action)
    }

    fun getActionFrom(str: String) = Action.From(str)
    fun getActionTo(str: String) = Action.To(str)
    fun getActionSearch() = Action.Search

}
