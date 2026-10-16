package com.example.contactdisplay

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This project covers concepts from Chapter 7 lessons:
 * - "Validation" - for form validation and error handling
 * - "Managing Input State" - for state management in forms
 * - "Text Fields" - for input field styling and error states
 * - "Regular Expressions" - for email, phone, and ZIP code validation
 *
 * Students should review these lessons before starting:
 * - Validation lesson for form validation patterns
 * - Managing Input State lesson for state management
 * - Text Fields lesson for input field styling
 * - Regular Expressions lesson for validation patterns
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ContactValidatorApp()
            }
        }
    }
}

@Composable
fun ContactValidatorApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ContactForm()
    }
}

@Composable
fun ContactForm() {
    // TODO: Create state variables for form fields
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    // TODO: Create validation state variables
    val isNameValid = name.isNotBlank()
    val isEmailValid = validateEmail(email)
    val isPhoneValid = validatePhone(phone)
    val isZipValid = validateZipCode(zipCode)

    // TODO: Create submitted information state variable
   var submittedInfo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface,
                // TODO: Apply a RoundedCornerShape with a reasonable dp value
                RoundedCornerShape(12.dp)
            )
            // TODO: Add horizontal and vertical padding with a reasonable dp value
            // See "Text Fields" lesson for examples of padding
            .padding(
                horizontal = 16.dp,
        vertical = 20.dp
    ),
    // TODO: Arrange items vertically with a reasonable dp spacing
    // See "Text Fields" lesson for examples of vertical arrangement
    verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // TODO: Call the NameField composable here
        NameField(
            name = name,
            isNameValid = isNameValid,
            onValueChange = {name = it}
        )

        // TODO: Call the EmailField composable here
        EmailField(
            email = email,
            isEmailValid = isEmailValid,
            onValueChange = {email = it}
        )

        // TODO: Call the PhoneField composable here
        PhoneField(
            phone = phone,
            isPhoneValid = isPhoneValid,
            onValueChange = {phone = it}
        )

        // TODO: Call the ZipCodeField composable here
        ZipCodeField(
            zipCode = zipCode,
            isZipValid = isZipValid,
            onValueChange = {zipCode = it}
        )

        // TODO: Create the Submit button
       Button(
           onClick = {
               submittedInfo =
                   "Name: $name\n" +
                           "Email: $email\n" +
                           "phone: $phone\n" +
                           "ZIP Code: $zipCode"
           },
           enabled = isNameValid &&
           isEmailValid &&
           isPhoneValid &&
           isZipValid &&
           name.isNotEmpty() &&
                   email.isNotEmpty() &&
                   phone.isNotEmpty() &&
                   zipCode.isNotEmpty(),
           modifier = Modifier.fillMaxWidth()
       ) {
           Text("Submit")
       }

        // TODO: Add display for submitted information
        if (submittedInfo.isNotEmpty()) {
            Text(
                text = "Submitted Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = submittedInfo,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            )
        }
    }
}

@Composable
fun NameField(
    name: String,
    isNameValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the name input field
    OutlinedTextField(
    value = name,
    onValueChange = onValueChange,
    label = { Text("Full Name") },
    isError = !isNameValid && name.isNotEmpty(),
    supportingText = {
        if (!isNameValid && name.isNotEmpty()) {
            Text("Please enter your name")
        }
    },
    keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
    ),
    modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    // See "Text Fields" lesson for complete examples of error state styling
}

@Composable
fun EmailField(
    email: String,
    isEmailValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the email input field
    OutlinedTextField(
    value = email,
    onValueChange = onValueChange,
    label = { Text("Email Address") },
    isError = !isEmailValid && email.isNotEmpty(),
    supportingText = {
        if (!isEmailValid && email.isNotEmpty()) {
            Text("Please enter a valid email address")
        }
    },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
   ),
    modifier = Modifier.fillMaxWidth(),
 singleLine = true
    )
}


@Composable
fun PhoneField(
    phone: String,
    isPhoneValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the phone input field
    OutlinedTextField(
        value = phone,
        onValueChange = onValueChange,
        label = { Text("Phone Number") },
        isError = !isPhoneValid && phone.isNotEmpty(),
        supportingText = {
            if (!isPhoneValid && phone.isNotEmpty()) {
                Text(
                    "Please enter a valid phone number" +
                "(xxx-xxx-xxxx or xxx/xxx/xxxx")
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun ZipCodeField(
    zipCode: String,
    isZipValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the ZIP code input field
    OutlinedTextField(
        value = zipCode,
        onValueChange = onValueChange,
        label = { Text("ZIP Code") },
        isError = !isZipValid && zipCode.isNotEmpty(),
        supportingText = {
            if (!isZipValid && zipCode.isNotEmpty()) {
                Text("Please enter a valid 5-digit ZIP code")
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

// TODO: Create validation functions using regex
fun validateEmail(email: String): Boolean {
    val pattern =
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    return email.matches(Regex(pattern))
}

fun validatePhone(phone: String): Boolean {
    val pattern = "^\\d{3}[-/]\\d{3}[-/]\\d{4}$"
    return phone.matches(Regex(pattern))
}

fun validateZipCode(zipCode: String): Boolean {
    val pattern = "^\\d{5}$"
    return zipCode.matches(Regex(pattern))
}


/**
 * Preview for Android Studio's design view.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactValidatorAppPreview() {
    ContactValidatorApp()
}