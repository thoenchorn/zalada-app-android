package com.thoen.zaladaandroid.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.thoen.zaladaandroid.presentation.theme.glassBorder
import com.thoen.zaladaandroid.presentation.theme.glassFill

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String = "",
    value: String = "",
    onTextChanged: (value: String) -> Unit,
    passwordVisible: Boolean = false,
    leadingIcon: ImageVector? = null,
) {
    var passwordVisibleState by rememberSaveable { mutableStateOf(passwordVisible) }

    OutlinedTextField(
        value = value,
        onValueChange = onTextChanged,
        label = { Text(text = label) },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = glassFill(),
            unfocusedContainerColor = glassFill(),
            disabledContainerColor = glassFill(),
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = glassBorder(),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        ),
        leadingIcon = leadingIcon?.let { icon ->
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        visualTransformation = if (passwordVisibleState) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {
            if (keyboardType == KeyboardType.Password) {
                IconButton(onClick = { passwordVisibleState = !passwordVisibleState }) {
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(
                            id = if (passwordVisibleState) {
                                com.thoen.zaladaandroid.R.drawable.hide
                            } else {
                                com.thoen.zaladaandroid.R.drawable.show
                            }
                        ),
                        contentDescription = if (passwordVisibleState) "Hide" else "Show",
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                            MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        modifier = modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
    )
}
