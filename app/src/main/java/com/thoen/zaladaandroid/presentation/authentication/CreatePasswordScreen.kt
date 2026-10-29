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
import com.thoen.zaladaandroid.presentation.navigation.Login

@Composable
fun CreatePasswordScreen(navController: NavController) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    AuthScaffold(
        title = "Create password",
        subtitle = "Set a new password for your account.",
        onBack = { navController.popBackStack() },
    ) {
        FieldLabel("New Password")
        CustomTextField(
            keyboardType = KeyboardType.Password,
            label = "New Password",
            value = password,
            onTextChanged = { password = it },
        )
        FieldLabel("Confirm Password")
        CustomTextField(
            keyboardType = KeyboardType.Password,
            label = "Confirm Password",
            value = confirmPassword,
            onTextChanged = { confirmPassword = it },
        )
        Spacer(modifier = Modifier.height(20.dp))
        FilledButtonCustom(
            onClick = { navController.navigate(Login.route) },
            text = "Create Password",
        )
    }
}
