@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.whatsapp.feature.authentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.whatsapp.R
import com.example.whatsapp.app.MainActivity
import com.example.whatsapp.core.data.model.Country
import com.example.whatsapp.core.presentation.designsystem.components.WhatsAppDialog
import com.example.whatsapp.core.presentation.designsystem.components.WhatsAppHorizontalDivider
import com.example.whatsapp.core.presentation.designsystem.components.WhatsAppTextField
import com.example.whatsapp.core.presentation.designsystem.components.maxCodeWidth
import com.example.whatsapp.core.presentation.designsystem.theme.blueMagenta
import com.example.whatsapp.core.presentation.designsystem.theme.gray3
import com.example.whatsapp.core.presentation.ui.ObserveAsEvents
import com.example.whatsapp.core.presentation.ui.UiText

@Composable
fun AuthenticationRoot(
    modifier: Modifier = Modifier,
    viewModel: AuthenticationViewModel = hiltViewModel<AuthenticationViewModel>(),
    navigateToChat: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { events ->
        when (events) {
            AuthenticationEvents.NavigateToChats -> navigateToChat()
            is AuthenticationEvents.ShowToast -> Toast.makeText(
                context, events.message, Toast.LENGTH_LONG
            ).show()
        }
    }

    AuthenticationScreen(
        modifier = modifier,
        state = state,
        onActions = viewModel::onActions
    )

}


@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    state: AuthenticationUiState,
    onActions: (AuthenticationActions) -> Unit,
) {
    val context = LocalContext.current
    val activity = context as MainActivity
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AuthenticationTopBar(
                title = stringResource(R.string.phone_number),
                enabled = state.isDoneEnabled,
                action = { onActions(AuthenticationActions.OnDone(activity = activity)) }
            )
        },
    ) { innerPadding ->
        AuthenticationBody(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            selectedCountry = state.selectedCountry,
            isExpanded = state.isExpanded,
            countryList = state.countryList,
            onSelect = {
                onActions(AuthenticationActions.UpdateCountry(country = it))
            },
            updateIsExpanded = {
                onActions(AuthenticationActions.UpdateIsExpanded(isExpanded = it))
            },
            phoneNumber = state.phoneNumber,
            onPhoneChange = {
                onActions(AuthenticationActions.UpdatePhoneNumber(phoneNumber = it))
            },
            phoneNumberEnabled = !state.isLoading,
            isLoading = state.isLoading
        )
        if (state.showVerification) {
            VerifyCodeDialog(
                isConfirmEnabled = state.isVerifiedEnabled,
                confirmButton = {
                    onActions(AuthenticationActions.VerifyOtp)
                },
                dismissButton = {
                    onActions(AuthenticationActions.DismissOtpVerification)
                },
                isLoading = state.isVerifying,
                verificationCode = state.verificationCode,
                onVerificationChange = {
                    onActions(AuthenticationActions.UpdateVerificationCode(verificationCode = it))
                },
                description = state.description
            )
        }
    }
}

@Composable
fun VerifyCodeDialog(
    modifier: Modifier = Modifier,
    isConfirmEnabled: Boolean,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
    verificationCode: String,
    isLoading: Boolean,
    onVerificationChange: (String) -> Unit,
    description: UiText,
) {
    WhatsAppDialog(
        modifier = modifier.fillMaxWidth(),
        title = stringResource(R.string.enter_verification_code),
        description = description.asString(),
        confirmButton = confirmButton,
        isConfirmEnabled = isConfirmEnabled,
        dismissButton = dismissButton,
        isLoading = isLoading
    ) {
        WhatsAppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = verificationCode,
            onValueChange = {
                onVerificationChange(it)
            },
            placeHolderText = stringResource(R.string.enter_verification_code),
            showIndicator = true
        )
    }
}

@Composable
fun AuthenticationTopBar(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean,
    action: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 12.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .clickable(enabled = enabled, onClick = action),
            text = stringResource(R.string.done),
            style = MaterialTheme.typography.titleMedium.copy(
                color = if (enabled) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.blueMagenta,
            ),
        )

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun AuthenticationBody(
    modifier: Modifier = Modifier,
    selectedCountry: Country,
    isExpanded: Boolean,
    updateIsExpanded: (Boolean) -> Unit,
    countryList: List<Country>,
    onSelect: (Country) -> Unit,
    phoneNumber: String,
    onPhoneChange: (String) -> Unit,
    phoneNumberEnabled: Boolean,
    isLoading: Boolean
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp, vertical = 18.dp),
                text = stringResource(R.string.confirm_description),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                ),
            )

            WhatsAppHorizontalDivider(modifier = Modifier.fillMaxWidth())

            CountryDropDownMenu(
                modifier = Modifier.fillMaxWidth(),
                selectedCountry = selectedCountry,
                isExpanded = isExpanded,
                countryList = countryList,
                onSelect = {
                    onSelect(it)
                },
                updateIsExpanded = {
                    updateIsExpanded(it)
                }
            )

            WhatsAppHorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )

            CodeWithPhoneNumber(
                code = selectedCountry.code,
                phoneNumber = phoneNumber,
                enabled = phoneNumberEnabled,
                onPhoneChange = {
                    onPhoneChange(it)
                }
            )

            WhatsAppHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.gray3
            )
        }
    }
}

@Composable
fun CodeWithPhoneNumber(
    modifier: Modifier = Modifier,
    code: String,
    phoneNumber: String,
    onPhoneChange: (String) -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = modifier
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier,
            text = code,
            style = MaterialTheme.typography.titleMedium.copy()
        )
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 4.dp),
            color = MaterialTheme.colorScheme.gray3
        )
        WhatsAppTextField(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            value = phoneNumber,
            onValueChange = { newPhoneNumber ->
                onPhoneChange(newPhoneNumber)
            },
            enabled = enabled,
            placeHolderText = stringResource(R.string.phone_number)
        )
    }
}


@Composable
fun CountryDropDownMenu(
    modifier: Modifier = Modifier,
    selectedCountry: Country,
    isExpanded: Boolean,
    countryList: List<Country>,
    onSelect: (Country) -> Unit,
    updateIsExpanded: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded ->
            updateIsExpanded(isExpanded)
        },
    ) {
        SelectedCountry(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                ),
            country = selectedCountry,
            expanded = isExpanded
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(shape = MaterialTheme.shapes.medium),
            expanded = isExpanded,
            onDismissRequest = {
                updateIsExpanded(false)
            },
            scrollState = scrollState,
            matchTextFieldWidth = true,
            shape = MaterialTheme.shapes.medium,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            CountryList(
                countryList = countryList,
                selectedCountry = selectedCountry,
                onSelect = {
                    onSelect(it)
                    updateIsExpanded(false)
                }
            )
        }
    }
}

@Composable
fun SelectedCountry(
    modifier: Modifier = Modifier,
    country: Country,
    expanded: Boolean,
) {
    DropdownMenuItem(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = {
            Text(
                modifier = Modifier.offset(x = (-16).dp),
                text = country.name,
                style = MaterialTheme.typography.labelMedium
            )
        },
        onClick = {},
        leadingIcon = null,
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
        },
        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
    )
}

@Composable
fun CountryList(
    modifier: Modifier = Modifier,
    countryList: List<Country>,
    selectedCountry: Country,
    onSelect: (Country) -> Unit,
) {
    val maxCodeWidth = maxCodeWidth(countryList = countryList)
    countryList.forEach { country ->
        CountryItem(
            modifier = modifier.fillMaxWidth(),
            country = country,
            selectedCountry = selectedCountry,
            codeWidth = maxCodeWidth,
            onSelect = {
                onSelect(it)
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        WhatsAppHorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun CountryItem(
    modifier: Modifier = Modifier,
    country: Country,
    codeWidth: Dp,
    selectedCountry: Country,
    onSelect: (Country) -> Unit
) {
    val isSelected = selectedCountry == country
    Row(
        modifier = modifier
            .height(30.dp)
            .clickable {
                onSelect(country)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.width(codeWidth),
            text = country.code,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            text = country.name,
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        if (isSelected) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
