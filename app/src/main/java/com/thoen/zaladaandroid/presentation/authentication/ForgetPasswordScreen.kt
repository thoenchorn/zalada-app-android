package com.thoen.zaladaandroid.presentation.authentication

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thoen.zaladaandroid.presentation.authentication.component.AuthScaffold
import com.thoen.zaladaandroid.presentation.authentication.component.FieldLabel
import com.thoen.zaladaandroid.presentation.component.CustomTextField
import com.thoen.zaladaandroid.presentation.component.FilledButtonCustom
import com.thoen.zaladaandroid.presentation.navigation.OTP

@Composable
fun ForgetPasswordScreen(navController: NavController) {
    var email by rememberSaveable { mutableStateOf("") }

    AuthScaffold(
        title = "Forgot password?",
        subtitle = "Enter your email address. You will receive a link to create a new password.",
        onBack = { navController.popBackStack() },
    ) {
        FieldLabel("Email Address")
        CustomTextField(
            keyboardType = KeyboardType.Email,
            label = "Email",
            value = email,
            onTextChanged = { email = it },
        )
        Spacer(modifier = Modifier.height(20.dp))
        FilledButtonCustom(
            onClick = { navController.navigate(OTP.route) },
            text = "Continue",
        )
    }
}
