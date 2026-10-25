package com.example.contactlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.tooling.preview.Preview

/**
 * This project covers concepts from Chapter 8 lessons:
 * - "Lazy Column" - for creating scrollable contact lists
 * - "Handling Clicks and Selection" - for interactive contact selection
 * - "Combining LazyColumn and LazyRow" - for understanding list composition
 *
 * Students should review these lessons before starting:
 * - LazyColumn lesson for list implementation
 * - Clicks and Selection lesson for interactive behavior
 * - Combined lesson for understanding how lists work together
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // TODO: Call the main composable function
                    ContactListApp()
                }
            }
        }
    }
}




@Composable
fun ContactListApp() {
    // TODO: Create sample contact data
    // Create a list of 25 contacts with names, emails, and phone numbers
    // Use the Contact data class provided below
    // See "Lazy Column" lesson for examples of data structures and LazyColumn usage
    val contacts = listOf(
        // TODO: Add 25Contact objects here
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

    // TODO: Call the ContactList composable
    ContactList(contacts)
}

@Composable
fun ContactList(contacts: List<Contact>) {
    // TODO: Create state variable for selected contact
    var selectedContact by remember {
        mutableStateOf<Contact?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp)
    ) {
        // TODO: Add header text
        // Create a Text composable with:
       Text(
        text = "Contact List",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(bottom = 16.dp))

        // TODO: Add selection status text
        // Create a Text composable that shows:
        Text(
        text = if (selectedContact != null) {
            "Selected: ${selectedContact!!.name}"
        } else {
            "No contact selected"
        },
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 24.dp))

        // TODO: Create LazyColumn for the contact list
        LazyColumn(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
        items(contacts) { contact ->
           ContactItem(
               contact = contact,
               isSelected = selectedContact == contact,
               onClick = {
                   selectedContact = contact
               }
           )
        }
    }
        // - Call ContactItem for each contact with proper parameters
        // See "Lazy Column" lesson for complete LazyColumn implementation examples

        // TODO: Add clear selection button
        if (selectedContact != null) {
            Button(
                onClick = {
                    selectedContact = null
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Clear Selection")
            }
        }
        // See "Handling Clicks and Selection" lesson for examples of conditional UI and button interactions
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
                    if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: Create contact avatar (colored circle with initials)
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
                val initials = contact.name.split(" ")
                    .mapNotNull { it.firstOrNull()?.toString() }
                    .joinToString("")

                Text(
                    text = initials,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // TODO: Create contact information column
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
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

            // TODO: Add selection indicator
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                    // See "Handling Clicks and Selection" lesson for examples of conditional UI elements and visual feedback
                )
            }
        }
    }
}

// Data class for contact information
data class Contact(
    val name: String,
    val email: String,
    val phone: String
)

/**
 * Preview for Android Studio's design view.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactListAppPreview() {
    MaterialTheme {
        ContactListApp()
    }
}
