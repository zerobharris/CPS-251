package com.example.timer

import android.content.pm.LauncherApps
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.nio.file.WatchEvent

/**
 * This project covers concepts from Chapter 6 lessons:
 * - "Understanding State in Compose" - for state management and updates
 * - "Stateless and Stateful Composables" - for component design patterns
 * - "Launched Effect" - for side effects and timers
 *
 * Students should review these lessons before starting:
 * - Understanding State in Compose lesson for state management
 * - Stateless and Stateful Composables lesson for component patterns
 * - Launched Effect lesson for side effects and timers
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StudyTimerApp()
            }
        }
    }
}

@Composable
fun StudyTimerApp() {
    // TODO: Create state variables for the timer
 var isRunning by remember { mutableStateOf(false) }
    var sessionLength by remember { mutableStateOf(25) }
    var timeRemaining by remember { mutableStateOf(25 * 60) }
    var completedSessions by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       Text(
         text = "Study Timer",
           fontSize = 28.sp,
           fontWeight = FontWeight.Bold
       )

        Spacer(modifier = Modifier.height(24.dp))

        TimerDisplay(
            timeRemaining = timeRemaining,
            sessionLength = sessionLength
        )

        Spacer(modifier = Modifier.height(32.dp))

        TimerControls(
            isRunning = isRunning,
            onToggleTimer = {
                if (isRunning) {
                    isRunning = false
                    timeRemaining = sessionLength * 60
                } else {
                    if (timeRemaining == 0) {
                        timeRemaining = sessionLength * 60
                    }
                    isRunning = true
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

       SessionSettings(
           sessionLength = sessionLength,
        onSessionLengthChange = { newLength ->
            sessionLength = newLength
            timeRemaining = newLength * 60
            isRunning = false
        }
       )

        Spacer(modifier = Modifier.height(16.dp))

       Text(
           text = "Completed Sessions: $completedSessions",
           fontSize = 18.sp,
           fontWeight = FontWeight.Medium
       )
    }

    LaunchedEffect(isRunning) {
        while (isRunning && timeRemaining > 0) {
            delay(1000)
            timeRemaining --
            if (timeRemaining == 0) {
                isRunning = false
                completedSessions++
            }
        }
    }
}

@Composable
fun TimerDisplay(
    timeRemaining: Int,
    sessionLength: Int
) {
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val progress = 1f - (timeRemaining.toFloat() / (sessionLength * 60))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Session Length: $sessionLength minutes",
            fontSize = 16.sp
        )
    }
}

@Composable
fun TimerControls(
    isRunning: Boolean,
    onToggleTimer: () -> Unit
) {
    Button(
        onClick = onToggleTimer,
        modifier = Modifier.width(160.dp)
    ) {
        Text(
            text = if (isRunning) "Reset" else "Play",
            fontSize = 18.sp
        )
    }
}

@Composable
fun SessionSettings(
    sessionLength: Int,
    onSessionLengthChange: (Int) -> Unit
) {
     val sessionOptions = listOf(5, 15, 25, 45)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose Session Length",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            sessionOptions.forEach { length ->
                Button(
                    onClick = {
                        onSessionLengthChange(length)
                    },
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .width(70.dp),
                    contentPadding = PaddingValues(
                        horizontal = 2.dp,
                        vertical = 8.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (sessionLength == length) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                ) {
                    Text(
                        text = "${length}m",
                        fontSize = 14.sp,
                        fontWeight = if (sessionLength == length) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun StudyTimerPreview() {
    MaterialTheme {
        StudyTimerApp()
    }
}