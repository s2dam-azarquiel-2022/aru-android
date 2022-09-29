package net.azarquiel.blackjack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var deck: Array<Card>
    private var nextCard: Int = 0
    private lateinit var players: MutableList<Player>
    private var playing: Int = -1
    private lateinit var cardsView: LinearLayout

    private fun genDeck(): Array<Card> {
        return Array(40)
            { rank -> Card(rank % 10 + 1, Suit.values()[rank / 10]) }
            .also { it.shuffle(Random(System.currentTimeMillis())) }
    }

    private fun Card.addToView() {
        cardsView.addView(ImageView(this@MainActivity).also {
            it.setImageResource(resources.getIdentifier(
                "${this.suit}${this.rank}",
                "drawable",
                packageName
            ))
        })
    }

    private fun addNextCardToView() {
        deck[nextCard].let { card ->
            card.addToView()
            players[playing].points += card.rank
        }
        nextCard++
    }

    private fun calcWinners(): MutableList<Player>? {
        var winners: MutableList<Player>? = null
        for (player in players) {
            Log.d("aru", player.toString())
            if (player.points <= 21) {
                if (winners == null) {
                    winners = mutableListOf(player)
                } else if ((winners.size == 0) or (winners[0].points == player.points)) {
                    winners.add(player)
                } else if (player.points > winners[0].points) {
                    winners = mutableListOf(player)
                }
            }
        }
        return winners
    }

    private fun endGame() {
        val winners: List<Player>? = calcWinners()

        AlertDialog.Builder(this)
            .setTitle("Completed!")
            .setMessage("${winners.toString()} won!")
            .setPositiveButton("New game") { _, _ -> setupNewGame() }
            .setNegativeButton("End game") { _, _ -> finish() }
            .show()
    }

    private fun calcPoints() {
        if (playing == players.size - 1) { endGame() }
        else { setupNextPlayer() }
    }

    private fun setupNextPlayer() {
        cardsView.removeAllViews()
        deck = genDeck()
        playing++
        for (i in 1..2) { addNextCardToView() }
    }

    private fun setupNewGame() {
        playing = -1
        players = MutableList(2) { i -> Player(name = "Player ${i+1}")}
        setupNextPlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardsView = findViewById(R.id.cardsView)

        setupNewGame()
        findViewById<ImageView>(R.id.deck).setOnClickListener { addNextCardToView() }
        findViewById<Button>(R.id.stopBtn).setOnClickListener { calcPoints() }
    }
}