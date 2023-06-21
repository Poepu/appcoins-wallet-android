package com.appcoins.wallet.ui.widgets.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.appcoins.wallet.ui.common.theme.WalletColors
import com.appcoins.wallet.ui.widgets.R

@Composable
fun WalletTextField(value: String, onValueChange: (String) -> Unit) {
  TextField(
    value = value,
    onValueChange = onValueChange,
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 24.dp),
    singleLine = true,
    shape = RoundedCornerShape(8.dp),
    colors = TextFieldDefaults.colors(
      focusedContainerColor = WalletColors.styleguide_blue,
      unfocusedContainerColor = WalletColors.styleguide_blue,
      focusedIndicatorColor = WalletColors.styleguide_blue,
      unfocusedIndicatorColor = WalletColors.styleguide_blue,
      focusedTextColor = WalletColors.styleguide_light_grey,
      unfocusedTextColor = WalletColors.styleguide_light_grey,
    ),
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
    placeholder = {
      Text(
        text = stringResource(R.string.mywallet_choose_name_field),
        color = WalletColors.styleguide_dark_grey
      )
    }
  )
}