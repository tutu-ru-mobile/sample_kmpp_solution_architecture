package com.sample

data class ConsolePanel(
    val rows: MutableList<ConsoleRow> = mutableListOf(),
    val bottomRows: MutableList<ConsoleRow> = mutableListOf()
)

data class ConsoleRow(val views: MutableList<ConsoleView> = mutableListOf())

sealed class ConsoleView {
    object Spacer : ConsoleView()
    class Button(
        val label: String,
        val cmd: String?,
        val selected: Boolean,
        val onClick: () -> Unit
    ) : ConsoleView()

    class Label(val str: String) : ConsoleView()
    class Title(val str: String) : ConsoleView()
    class CheckBox(val label: String, val value: Boolean, val onClick: () -> Unit) : ConsoleView()
    class TextInput(val label: String, val value: String, val onEdit: (String) -> Unit) :
        ConsoleView()

    class PasswordInput(val label: String, val value: String, val onEdit: (String) -> Unit) :
        ConsoleView()
}

interface ConsolePanelBuilder : ConsoleBaseBuilder {
    fun row(lambda: ConsoleRowBuilder.() -> Unit)
    fun bottomRow(lambda: ConsoleRowBuilder.() -> Unit)
}

interface ConsoleRowBuilder : ConsoleBaseBuilder {

}

interface ConsoleBaseBuilder {
    fun button(label: String, cmd: String? = null, selected: Boolean = false, onClick: () -> Unit)
    fun label(str: String)
    fun title(str: String)
    fun checkBox(label: String, value: Boolean, onClick: () -> Unit)
    fun textInput(label: String, value: String, onEdit: (String) -> Unit)
    fun spacer()
    fun passwordInput(label: String, value: String, onEdit: (String) -> Unit)
}

fun consolePanelView(lambda: ConsolePanelBuilder.() -> Unit): ConsolePanel {
    val result = ConsolePanel()
    object : ConsolePanelBuilder {
        override fun row(lambda: ConsoleRowBuilder.() -> Unit) {
            val row = ConsoleRow()
            result.rows.add(row)
            object : ConsoleRowBuilder {
                override fun button(
                    label: String,
                    cmd: String?,
                    selected: Boolean,
                    onClick: () -> Unit
                ) {
                    row.views.add(ConsoleView.Button(label, cmd, selected, onClick))
                }

                override fun label(str: String) {
                    row.views.add(ConsoleView.Label(str))
                }

                override fun title(str: String) {
                    row.views.add(ConsoleView.Title(str))
                }

                override fun checkBox(label: String, value: Boolean, onClick: () -> Unit) {
                    row.views.add(ConsoleView.CheckBox(label, value, onClick))
                }

                override fun textInput(label: String, value: String, onEdit: (String) -> Unit) {
                    row.views.add(ConsoleView.TextInput(label, value, onEdit))
                }

                override fun spacer() {
                    row.views.add(ConsoleView.Spacer)
                }

                override fun passwordInput(label: String, value: String, onEdit: (String) -> Unit) {
                    row.views.add(ConsoleView.PasswordInput(label, value, onEdit))
                }

            }.lambda()
        }

        override fun bottomRow(lambda: ConsoleRowBuilder.() -> Unit) {
            val row = ConsoleRow()
            result.bottomRows.add(row)
            object : ConsoleRowBuilder {
                override fun button(
                    label: String,
                    cmd: String?,
                    selected: Boolean,
                    onClick: () -> Unit
                ) {
                    row.views.add(ConsoleView.Button(label, cmd, selected, onClick))
                }

                override fun label(str: String) {
                    row.views.add(ConsoleView.Label(str))
                }

                override fun title(str: String) {
                    row.views.add(ConsoleView.Title(str))
                }

                override fun checkBox(label: String, value: Boolean, onClick: () -> Unit) {
                    row.views.add(ConsoleView.CheckBox(label, value, onClick))
                }

                override fun textInput(label: String, value: String, onEdit: (String) -> Unit) {
                    row.views.add(ConsoleView.TextInput(label, value, onEdit))
                }

                override fun spacer() {
                    row.views.add(ConsoleView.Spacer)
                }

                override fun passwordInput(label: String, value: String, onEdit: (String) -> Unit) {
                    row.views.add(ConsoleView.PasswordInput(label, value, onEdit))
                }

            }.lambda()
        }

        override fun button(label: String, cmd: String?, selected: Boolean, onClick: () -> Unit) {
            row {
                button(label, cmd, selected, onClick)
            }
        }

        override fun label(str: String) {
            row {
                label(str)
            }
        }

        override fun title(str: String) {
            row {
                title(str)
            }
        }

        override fun checkBox(label: String, value: Boolean, onClick: () -> Unit) {
            row {
                checkBox(label, value, onClick)
            }
        }

        override fun textInput(label: String, value: String, onEdit: (String) -> Unit) {
            row {
                textInput(label, value, onEdit)
            }
        }

        override fun spacer() {
            row {
                spacer()
            }
        }

        override fun passwordInput(label: String, value: String, onEdit: (String) -> Unit) {
            row {
                passwordInput(label, value, onEdit)
            }
        }

    }.lambda()
    return result
}
