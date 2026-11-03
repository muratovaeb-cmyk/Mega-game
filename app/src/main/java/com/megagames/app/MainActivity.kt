
package com.megagames.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    private val games = listOf(
        "Крестики-нолики",
        "Угадай число",
        "Камень, ножницы, бумага",
        "Математика",
        "Память",
        "Реакция",
        "Змейка",
        "2048",
        "Пятнашки",
        "Викторина"
    ) + (11..50).map { "Мини-игра $it" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showMenu()
    }

    private fun showMenu() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 30, 20, 20)
            setBackgroundColor(Color.rgb(12, 15, 32))
        }

        val title = TextView(this).apply {
            text = "🎮 MEGA GAMES 2.0"
            textSize = 28f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "50 мини-игр • Играй и побеждай!"
            textSize = 16f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 15, 0, 20)
        }
        root.addView(subtitle)

        val scroll = ScrollView(this)
        val list = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        games.forEachIndexed { index, game ->
            val button = Button(this).apply {
                text = "${index + 1}. $game"
                textSize = 16f
                setOnClickListener {
                    if (index == 0) openGame()
                    else Toast.makeText(
                        this@MainActivity,
                        "Скоро появится: $game",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            list.addView(button)
        }

        scroll.addView(list)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(root)
    }

    private fun openGame() {
        val board = Array(9) { "" }
        var turn = "X"
        var finished = false

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(16, 30, 16, 16)
            setBackgroundColor(Color.rgb(12, 15, 32))
        }

        val status = TextView(this).apply {
            text = "Ход: X"
            textSize = 26f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        layout.addView(status)

        val grid = GridLayout(this).apply {
            columnCount = 3
        }

        val buttons = Array(9) { i ->
            Button(this).apply {
                textSize = 30f
                setOnClickListener {
                    if (board[i].isEmpty() && !finished) {
                        board[i] = turn
                        text = turn

                        val wins = arrayOf(
                            intArrayOf(0,1,2),
                            intArrayOf(3,4,5),
                            intArrayOf(6,7,8),
                            intArrayOf(0,3,6),
                            intArrayOf(1,4,7),
                            intArrayOf(2,5,8),
                            intArrayOf(0,4,8),
                            intArrayOf(2,4,6)
                        )

                        if (wins.any {
                            board[it[0]].isNotEmpty() &&
                            board[it[0]] == board[it[1]] &&
                            board[it[1]] == board[it[2]]
                        }) {
                            status.text = "Победил $turn! 🏆"
                            finished = true
                        } else if (board.all { it.isNotEmpty() }) {
                            status.text = "Ничья!"
                            finished = true
                        } else {
                            turn = if (turn == "X") "O" else "X"
                            status.text = "Ход: $turn"
                        }
                    }
                }
            }
        }

        buttons.forEach {
            grid.addView(it, GridLayout.LayoutParams().apply {
                width = 0
                height = 110
                columnSpec = GridLayout.spec(
                    GridLayout.UNDEFINED, 1f
                )
            })
        }

        layout.addView(grid)

        val reset = Button(this).apply {
            text = "🔄 Новая игра"
            setOnClickListener { openGame() }
        }
        layout.addView(reset)

        val back = Button(this).apply {
            text = "⬅ Назад в меню"
            setOnClickListener { showMenu() }
        }
        layout.addView(back)

        setContentView(layout)
    }
}
