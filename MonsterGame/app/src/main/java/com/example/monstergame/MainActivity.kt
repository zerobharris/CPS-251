package com.example.monstergame

import android.bluetooth.BluetoothHealth
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.monstergame.ui.theme.MonsterGameTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.random.Random

data class BattleUiState(
    val playerHealth: Int = 100,
    val monsterHealth: Int = 100,
    val message: String = "Defeat the monster!",
    val isMonsterTurn: Boolean = false,
    val gameOver: Boolean = false
)

class BattleViewModel: ViewModel() {
    var uiState by mutableStateOf(BattleUiState())
        private set

    private var counterAttackJob: Job? = null

    fun attack() {
        if (uiState.isMonsterTurn || uiState.gameOver) return
        val damage = Random.nextInt(12, 26)

        val newMonsterHealth = (uiState.monsterHealth - damage).coerceAtLeast(0)

        uiState = uiState.copy(
            monsterHealth = newMonsterHealth,
            message = "You dealt $damage damage!"
        )

        if (newMonsterHealth == 0) {
            uiState = uiState.copy(
                message = "You defeatged the monster!",
                gameOver = true
            )
        } else {
            startCounterAttack()
        }
    }

    fun heal() {
        if (uiState.isMonsterTurn || uiState.gameOver) return
        if (uiState.playerHealth >= 100) return

        val healing = Random.nextInt(15, 26)

        val newPlayerHealth = (uiState.playerHealth + healing).coerceAtMost(100)

        uiState = uiState.copy(
            PlayerHealth = newPlayerHealth,
            message = "You restored ${newPlayerHealth - uiState.playerHealth} health"
        )
        startCounterAttack()
    }

    private fun startCounterAttack() {
        uiState = uiState.copy(
            isMonsterTurn = true,
            message = "The monster is about to attack"
        )

        counterAttackJob = viewModelScope.launch {
            delay(1000)

            if (uiState.gameOver) return@launch

            val damage = Random.nextInt(10, 21)

            val newPlayerHealth = (uiState.playerHealth - damage).coerceAtLeast(0)

            if (newPlayerHealth == 0)
                uiState = uiState.copy(
                   playerHealth = 0,
                    isMonsterTurn = false,
                    gameOver = true,
                    message = "The monster defeated you"
                )
        } else {
            uiState = uiState.copy(
                playerHealth = newPlayerHealth,
                isMonsterTurn = false,
                message = "The monster counterattacked for $damage damage!"
            )
        }
    }
}

fun restart() {
    // Cancel a pending monster attack before resetting.
    counterAttackJob?.cancel()
    counterAttackJob = null

    uiState = BattleUiState()
}
}