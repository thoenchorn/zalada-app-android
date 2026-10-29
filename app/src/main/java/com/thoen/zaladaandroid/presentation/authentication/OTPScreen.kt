package com.thoen.zaladaandroid.presentation.authentication

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidDevOs.verifyswift.base.SwiftAuth
import com.thoen.zaladaandroid.presentation.authentication.component.AuthScaffold
import com.thoen.zaladaandroid.presentation.component.FilledButtonCustom
import com.thoen.zaladaandroid.presentation.navigation.CreatePassword

@Composable
fun OTPScreen(navController: NavController) {
    var otpValue by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    AuthScaffold(
        title = "OTP Verification",
        subtitle = "Enter the 6 digit code sent to your email address.",
        onBack = { navController.popBackStack() },
    ) {
        SwiftAuth(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            otpValue = otpValue,
            onOtpTextChange = { otpValue = it },
            onCompletedNumbers = {
                keyboardController?.hide()
            },
        )
        Spacer(modifier = Modifier.height(24.dp))
        FilledButtonCustom(
            onClick = { navController.navigate(CreatePassword.route) },
            text = "Verify",
        )
    }
}
