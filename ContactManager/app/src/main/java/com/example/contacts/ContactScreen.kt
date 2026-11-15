package com.example.contacts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactScreen() {
    val context = LocalContext.current

    val contactViewModel: ContactViewModel = viewModel(
        factory = ContactViewModel.provideFactory(
            context.applicationContext as android.app.Application
        )
    )

    val contacts by contactViewModel.allContacts.collectAsState(
        initial = emptyList()
    )

    val searchQuery by contactViewModel.searchQuery.collectAsState()

    var contactToDelete by remember {
        mutableStateOf<Contact?>(null)
    }

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = false
            },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = nameError
        )

        if (nameError) {
            Text(
                text = "Please enter a name",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                phoneError = false
            },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = phoneError
        )
        if (phoneError) {
            Text(
                text = "Enter a valid 10-digit phone number",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Button(
                onClick = {
                    nameError = name.isBlank()
                    phoneError = phone.filter { it.isDigit() }.length != 10

                    if (!nameError && !phoneError) {
                        contactViewModel.insert(
                            Contact(
                                name = name.trim(),
                                phone = phone.trim()
                            )
                        )

                        name = ""
                        phone = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Add")
            }

            Button(
                onClick = {
                    contactViewModel.onSortOrderChange(SortOrder.DESC)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sort Desc")
            }

            Button(
                onClick = {
                    contactViewModel.onSortOrderChange(SortOrder.ASC)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sort Asc")
            }
        }

    Spacer(modifier = Modifier.height(16.dp))

    HorizontalDivider()

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Contacts:",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
        modifier = Modifier.weight(1f)
    ) {
        items(
            contacts,
            key = {contact -> contact.id}
        ) { contact ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = contact.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = contact.phone,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                IconButton(
                    onClick = {
                        contactToDelete = contact
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Contact"
                    )
                }
            }

            HorizontalDivider()
        }
    }
}

if (contactToDelete != null) {
    AlertDialog(
        onDismissRequest = {
            contactToDelete = null
        },
        title = {
            Text("Delete Contact?")
        },
        text = {
            Text("Are you sure you want to delete this contact?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    contactToDelete?.let {
                        contactViewModel.delete(it)
                    }
                    contactToDelete = null
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    contactToDelete = null
                }
            ) {
                Text("Cancel")
                }
            }
        )
    }
}