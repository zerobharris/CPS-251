package com.example.testcontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactListApp()
                }
            }
        }
    }
}

@Composable
fun ContactListApp() {

    val contacts = listOf(
        Contact("John Doe", "john@example.com", "555-0101"),
        Contact("Jane Smith", "jane@example.com", "555-0102"),
        Contact("Michael Johnson", "michael@example.com", "555-0103"),
        Contact("Emily Davis", "emily@example.com", "555-0104"),
        Contact("Chris Brown", "chris@example.com", "555-0105"),
        Contact("Sarah Wilson", "sarah@example.com", "555-0106"),
        Contact("David Miller", "david@example.com", "555-0107"),
        Contact("Jessica Garcia", "jessica@example.com", "555-0108"),
        Contact("Daniel Martinez", "daniel@example.com", "555-0109"),
        Contact("Ashley Anderson", "ashley@example.com", "555-0110"),
        Contact("Matthew Taylor", "matthew@example.com", "555-0111"),
        Contact("Amanda Thomas", "amanda@example.com", "555-0112"),
        Contact("Joshua Jackson", "joshua@example.com", "555-0113"),
        Contact("Jennifer White", "jennifer@example.com", "555-0114"),
        Contact("Andrew Harris", "andrew@example.com", "555-0115"),
        Contact("Nicole Martin", "nicole@example.com", "555-0116"),
        Contact("Ryan Thompson", "ryan@example.com", "555-0117"),
        Contact("Lauren Robinson", "lauren@example.com", "555-0118"),
        Contact("Brandon Clark", "brandon@example.com", "555-0119"),
        Contact("Megan Lewis", "megan@example.com", "555-0120"),
        Contact("Kevin Lee", "kevin@example.com", "555-0121"),
        Contact("Rachel Walker", "rachel@example.com", "555-0122"),
        Contact("Justin Hall", "justin@example.com", "555-0123"),
        Contact("Stephanie Allen", "stephanie@example.com", "555-0124"),
        Contact("Eric Young", "eric@example.com", "555-0125")
    )

    ContactList(contacts)
}

@Composable
fun ContactList(contacts: List<Contact>) {

    var selectedContact by remember {
        mutableStateOf<Contact?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 24.dp)
    ) {

        Text(
            text = "Contact List",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = selectedContact?.let {
                "Selected: ${it.name}"
            } ?: "No contact selected",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = contacts,
                key = { contact -> contact.phone }
            ) { contact ->

                ContactItem(
                    contact = contact,
                    isSelected = selectedContact == contact,
                    onClick = {
                        selectedContact = contact
                    }
                )
            }
        }

        if (selectedContact != null) {
            Button(
                onClick = {
                    selectedContact = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Clear Selection")
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Contact avatar with initials
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                val initials = contact.name
                    .trim()
                    .split(Regex("\\s+"))
                    .mapNotNull { it.firstOrNull()?.toString() }
                    .take(2)
                    .joinToString("")
                    .uppercase()

                Text(
                    text = initials,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            // Contact information
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = contact.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Display a checkmark when selected
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// Contact information
data class Contact(
    val name: String,
    val email: String,
    val phone: String
)

// Preview for Android Studio
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ContactListAppPreview() {
    MaterialTheme {
        ContactListApp()
    }
}