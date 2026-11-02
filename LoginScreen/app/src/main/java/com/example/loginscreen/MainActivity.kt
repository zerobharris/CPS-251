package com.example.loginscreen

// Core Android imports
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat

// Compose UI imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.TextButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Navigation imports
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.nio.file.WatchEvent

/**
 * This project covers concepts from Chapter 9 lessons:
 * - "Introduction to Navigation" - for setting up basic navigation structure
 * - "Passing Arguments" - for passing user data between screens
 * - "Managing the Back Stack" - for controlling navigation flow
 * - "Navigation UI Components" - for creating user interface elements
 * - "Organizing Navigation with Multiple Files" - for project structure
 *
 * Students should review these lessons before starting:
 * - Introduction to Navigation lesson for basic navigation setup
 * - Passing Arguments lesson for data transfer between screens
 * - Managing the Back Stack lesson for navigation control
 * - Navigation UI Components lesson for UI design
 * - Organizing Navigation with Multiple Files lesson for project organization
 */

/**
 * MainActivity is the entry point of the application.
 * It sets up the basic window configuration and initializes the Compose UI with navigation.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure the window to use light status bar icons
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        // Set up the Compose UI with navigation
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // TODO: Call the main navigation app function here
                    LoginApp()
                }
            }
        }
    }
}

/**
 * Main navigation app that handles the login flow
 */
@Composable
fun LoginApp() {
    // TODO: Create a navigation controller using rememberNavController()
    val navController = rememberNavController()

    // TODO: Set up NavHost with startDestination = "login"
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

    // TODO: Add composable route for "login" screen
    composable("login") {
        LoginScreen(
            onLoginSuccess = { userName ->
                navController.navigate(
                    "Welcome/${Uri.encode(userName)}"
                )
            }
        )
    }

    // TODO: Add composable route for "welcome/{userName}" with navigation arguments
    composable(
        route = "Welcome/{userName}",
        arguments = listOf(
            navArgument("userName") {
                type = NavType.StringType
            }
        )
    ) {backStackEntry ->
        val userName =
            backStackEntry.arguments?.getString("userName") ?: ""

        WelcomeScreen(
            userName = userName,
            onViewProfile = {
                navController.navigate(
                    "profile/${Uri.encode(userName)}"
                )
            },
            onLogout = {
                navController.popBackStack(
                    "login",
                    inclusive = false
                )
            }
        )
    }

    // TODO: Add composable route for "profile/{userName}" with navigation arguments
    composable(
        route = "profile/{userName}",
        arguments = listOf(
            navArgument("userName") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val userName =
            backStackEntry.arguments?.getString("userName") ?: ""

        ProfileScreen(
            userName = userName,
            onBackToWelcome = {
                navController.popBackStack()
                }
            )
        }
    }
}

/**
 * Login screen with validation using Chapter 7 concepts
 */
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    // TODO: Create state variables for form fields (name, email, password)
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // TODO: Create state variables for validation errors (nameError, emailError, validEmailBool, passwordError)
    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var validEmailBool by remember { mutableStateOf(true) }
    var passwordError by remember { mutableStateOf(false) }

    // TODO: Create state variable for password visibility (passwordVisible)
    var passwordVisible by remember { mutableStateOf(false) }

    // TODO: Define hardcoded credentials (validEmail = "student@wccnet.edu", validPassword = "password123")
    val validEmail = "student@wccnet.edu"
    val validPassword = "password123"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: Add title text "Student Login" with headlineLarge style and bold font weight
        Text(
            text = "Student Login",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        // TODO: Create a Card with elevation for the login form
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ){
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

            // TODO: Create OutlinedTextField for name input with:
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Person"
                        )
                    },
                    isError = nameError,
                    supportingText = {
                        if (nameError) {
                            Text("Please enter your name.")
                        }
                    },
                    keyboardOptions = KeyboardOptions (
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth())
            }
        }

            // TODO: Create OutlinedTextField for email input with:
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email"
                )
            },
            isError = emailError || !validEmailBool,
            supportingText = {
                if (emailError) {
                    Text("Please enter a valid email address.")
                } else if (!validEmailBool) {
                    Text("Incorrect email. Use the WCC demo email.")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

            // TODO: Create OutlinedTextField for password input with:
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock"
                )
            },
            trailingIcon = {
                TextButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Text(
                        if (passwordVisible) "Hide" else "Show"
                    )
                }
            },
            visualTransformation =
                if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            isError = passwordError,
            supportingText = {
                if (passwordError) {
                    Text("Incorrect password.")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

            Spacer(modifier = Modifier.height(16.dp))

            // TODO: Create login Button with onClick validation logic:
            Button(
                onClick= {
                    val nameIsInvalid = name.isBlank()

                    val emailHasInvalidFormat =
                        !isValidEmail(email.trim())

                    val emailIsIncorrect =
                        email.trim() != validEmail

                    val passwordIsIncorrect =
                        password != validPassword

                    nameError = nameIsInvalid
                    emailError = emailHasInvalidFormat
                    validEmailBool = !emailIsIncorrect
                    passwordError = passwordIsIncorrect

                    if (
                        !nameIsInvalid &&
                                !emailHasInvalidFormat &&
                                !emailIsIncorrect &&
                                !passwordIsIncorrect
                    ) {
                        onLoginSuccess(name.trim())
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            // TODO: Add demo credentials hint text showing validEmail and validPassword
    Text(
        text = "Demo Email: $validEmail\nDemo Password: $validPassword",
        style = MaterialTheme.typography.bodySmall
                )
            }
}

/**
 * Welcome screen that displays after successful login
 */
@Composable
fun WelcomeScreen(
    userName: String,
    onViewProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: Add welcome message "Welcome!" with headlineLarge style and bold font weight
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Add personalized greeting "Hello, $userName!" with headlineMedium style
        Text(
            text = "Hello, $userName!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: Create "View Profile" button that calls onViewProfile
        Button(
            onClick = onViewProfile
        ) {
            Text("View Profile")
        }

        // TODO: Create "Logout" button that calls onLogout
        TextButton(
            onClick = onLogout
        ) {
            Text("Logout")
        }
    }
}

/**
 * Profile screen showing user information
 */
@Composable
fun ProfileScreen(
    userName: String,
    onBackToWelcome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO: Add header "User Profile" with headlineLarge style and bold font weight
        Text(
            text = "User Profile",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // TODO: Create a Card to display profile information
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {

        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TODO: Add ProfileRow calls for: Name, Email, Student ID, Major, Year
            ProfileRow(
                label = "Name",
                value = userName
            )

            ProfileRow(
                label = "Email",
                value = "student@wccnet.edu"
            )

            ProfileRow(
                label = "Student ID",
                value = "12345678"
            )

            ProfileRow(
                label = "Major",
                value = "Computer Science"
            )

            ProfileRow(
                label = "Year",
                value = "First Year"
            )
        }
        }

        Spacer(modifier = Modifier.weight(1f))

        // TODO: Create "Back to Welcome" button that calls onBackToWelcome
        Button(
            onClick = onBackToWelcome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Welcome")
        }
    }
}

/**
 * Helper composable for profile information rows
 */
@Composable
fun ProfileRow(label: String, value: String) {
    // TODO: Create a Row with:
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/**
 * Email validation function using regex (Chapter 7: Regular Expressions)
 */
fun isValidEmail(email: String): Boolean {
    // TODO: Implement email validation using regex pattern
    val emailPattern= "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
    // Reference: Chapter 7 - Regular Expressions lesson
    return email.matches(emailPattern.toRegex())
}

/**
 * Preview function for the login screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(onLoginSuccess = {})
    }
}

/**
 * Preview function for the welcome screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(
            userName = "John Doe",
            onViewProfile = {},
            onLogout = {}
        )
    }
}

/**
 * Preview function for the profile screen
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(
            userName = "John Doe",
            onBackToWelcome = {}
        )
    }
}
