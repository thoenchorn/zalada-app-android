package com.thoen.zaladaandroid.presentation.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.presentation.authentication.component.AuthScaffold
import com.thoen.zaladaandroid.presentation.authentication.component.FieldLabel
import com.thoen.zaladaandroid.presentation.component.CardSocialMedia
import com.thoen.zaladaandroid.presentation.component.CustomTextField
import com.thoen.zaladaandroid.presentation.component.FilledButtonCustom
import com.thoen.zaladaandroid.presentation.navigation.ForgetPassword
import com.thoen.zaladaandroid.presentation.navigation.Main
import com.thoen.zaladaandroid.presentation.navigation.Signup

@Composable
fun LoginScreen(navController: NavController) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    AuthScaffold(
        title = "Welcome back",
        subtitle = "Login to your account and continue shopping.",
    ) {
        FieldLabel("Email Address")
        CustomTextField(
            keyboardType = KeyboardType.Email,
            label = "Email",
            value = username,
            onTextChanged = { username = it },
            passwordVisible = true
        )
        FieldLabel("Password")
        CustomTextField(
            keyboardType = KeyboardType.Password,
            label = "Password",
            value = password,
            onTextChanged = { password = it },
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { navController.navigate(ForgetPassword.route) },
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        FilledButtonCustom(
            onClick = { navController.navigate(Main.route) },
            text = "Login",
        )
        Spacer(modifier = Modifier.height(20.dp))
        OrDivider()
        Spacer(modifier = Modifier.height(8.dp))
        CardSocialMedia(
            text = "Continue with Google",
            onClick = {},
            icon = R.drawable.google
        )
        Spacer(modifier = Modifier.height(8.dp))
        CardSocialMedia(
            text = "Continue with Facebook",
            onClick = {},
            icon = R.drawable.facebook
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = { navController.navigate(Signup.route) }) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )
        Text(
            text = "OR",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )
    }
}
