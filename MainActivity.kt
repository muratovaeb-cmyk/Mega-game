package com.megagames.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {
    private val games = listOf(
        "Крестики-нолики", "Змейка", "Память", "Угадай число", "Камень, ножницы, бумага",
        "Реакция", "Математика", "2048", "Сапёр", "Пинг-понг",
        "Шашки", "Шахматы", "Пятнашки", "Слова", "Викторина",
        "Лабиринт", "Арканоид", "Прыжки", "Гонки", "Космический стрелок"
    ) + (21..50).map { "Мини-игра $it" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 28, 24, 20)
            setBackgroundColor(Color.rgb(12, 15, 32))
        }
        val title = TextView(this).apply {
            text = "🎮 MEGA GAMES 2.0"
            textSize = 28f; setTextColor(Color.WHITE); gravity = Gravity.CENTER
        }
        root.addView(title)
        val subtitle = TextView(this).apply {
            text = "50 мини-игр • Играй и соревнуйся"
            textSize = 15f; setTextColor(Color.LTGRAY); gravity = Gravity.CENTER
            setPadding(0, 10, 0, 22)
        }
        root.addView(subtitle)
        val scroll = ScrollView(this)
        val list = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        games.forEachIndexed { index, game ->
            val b = Button(this).apply {
                text = "${index + 1}. $game"
                textSize = 16f
                setOnClickListener {
                    if (index == 0) openTicTacToe()
                    else Toast.makeText(this@MainActivity, "$game — пункт каталога. Игра будет добавлена в следующей версии.", Toast.LENGTH_LONG).show()
                }
            }
            list.addView(b)
        }
        scroll.addView(list)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(root)
    }

    private fun openTicTacToe() {
        val board = Array(9) { "" }
        var turn = "X"
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER
            setBackgroundColor(Color.rgb(12, 15, 32)); setPadding(16, 30, 16, 16)
        }
        val status = TextView(this).apply {
            text = "Ход: X"; textSize = 24f; setTextColor(Color.WHITE); gravity = Gravity.CENTER
        }
        layout.addView(status)
        val grid = GridLayout(this).apply { columnCount = 3 }
        val buttons = Array(9) { i ->
            Button(this).apply {
                textSize = 30f
                setOnClickListener {
                    if (board[i].isEmpty()) {
                        board[i] = turn; text = turn
                        val wins = arrayOf(intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
                            intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8),
                            intArrayOf(0,4,8), intArrayOf(2,4,6))
                        val winner = wins.any { board[it[0]].isNotEmpty() && board[it[0]] == board[it[1]] && board[it[1]] == board[it[2]] }
                        if (winner) status.text = "Победил $turn!"
                        else if (board.all { it.isNotEmpty() }) status.text = "Ничья!"
                        else { turn = if (turn == "X") "O" else "X"; status.text = "Ход: $turn" }
                    }
                }
            }
        }
        buttons.forEach { grid.addView(it, GridLayout.LayoutParams().apply {
            width = 0; height = 110; columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        }) }
        layout.addView(grid)
        val reset = Button(this).apply { text = "Новая игра"; setOnClickListener { openTicTacToe() } }
        layout.addView(reset)
        setContentView(layout)
    }
}
