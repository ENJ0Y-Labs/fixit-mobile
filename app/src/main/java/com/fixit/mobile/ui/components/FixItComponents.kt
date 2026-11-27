package com.fixit.mobile.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fixit.mobile.ui.theme.FixItBorder
import com.fixit.mobile.ui.theme.FixItDanger
import com.fixit.mobile.ui.theme.FixItDangerSoft
import com.fixit.mobile.ui.theme.FixItInfo
import com.fixit.mobile.ui.theme.FixItInfoSoft
import com.fixit.mobile.ui.theme.FixItInk
import com.fixit.mobile.ui.theme.FixItMuted
import com.fixit.mobile.ui.theme.FixItPrimary
import com.fixit.mobile.ui.theme.FixItSurface
import com.fixit.mobile.ui.theme.FixItSurfaceSoft
import com.fixit.mobile.ui.theme.FixItSuccess
import com.fixit.mobile.ui.theme.FixItSuccessSoft
import com.fixit.mobile.ui.theme.FixItWarning
import com.fixit.mobile.ui.theme.FixItWarningSoft

@Composable
fun FixItPrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, loading: Boolean = false) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = enabled && !loading,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = FixItPrimary, contentColor = Color.White)
    ) {
        if (loading) {
            CircularProgressIndicator(Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
            Spacer(Modifier.width(10.dp))
        }
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun FixItSecondaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    OutlinedButton(onClick, modifier.height(48.dp), enabled = enabled, shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, FixItBorder)) {
        Text(text, color = FixItInk, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun FixItTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, modifier = modifier.fillMaxWidth(),
        label = { Text(label) }, placeholder = placeholder?.let { { Text(it) } },
        enabled = enabled, singleLine = singleLine, isError = isError,
        supportingText = supportingText?.let { { Text(it) } },
        keyboardOptions = keyboardOptions, shape = RoundedCornerShape(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixItDropdown(label: String, options: List<String>, selected: String, onSelected: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { if (enabled) expanded = !expanded }, modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selected, onValueChange = {}, readOnly = true, enabled = enabled, label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(), shape = RoundedCornerShape(8.dp)
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.exposedDropdownSize()) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = { onSelected(option); expanded = false })
            }
        }
    }
}

@Composable
fun FixItCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = FixItSurface),
        border = BorderStroke(1.dp, FixItBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp), content = content)
    }
}

@Composable
fun FixItStatusBadge(status: String, modifier: Modifier = Modifier) {
    val normalized = status.trim().lowercase()
    val (container, content) = when {
        normalized.contains("complete") || normalized.contains("verified") || normalized == "active" -> FixItSuccessSoft to FixItSuccess
        normalized.contains("pending") || normalized.contains("awaiting") || normalized.contains("review") -> FixItWarningSoft to FixItWarning
        normalized.contains("cancel") || normalized.contains("reject") || normalized.contains("failed") -> FixItDangerSoft to FixItDanger
        normalized.contains("progress") || normalized.contains("open") -> FixItInfoSoft to FixItInfo
        else -> FixItSurfaceSoft to FixItMuted
    }
    Surface(modifier = modifier, color = container, contentColor = content, shape = RoundedCornerShape(50)) {
        Text(status, Modifier.padding(horizontal = 10.dp, vertical = 5.dp), style = androidx.compose.material3.MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun FixItLoadingState(message: String = "Loading…", modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().padding(vertical = 36.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CircularProgressIndicator(color = FixItPrimary, strokeWidth = 2.5.dp)
        Text(message, color = FixItMuted, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun FixItEmptyState(title: String, message: String, modifier: Modifier = Modifier, actionLabel: String? = null, onAction: (() -> Unit)? = null) {
    Column(modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 36.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(Icons.Outlined.Inbox, null, tint = FixItMuted, modifier = Modifier.size(36.dp))
        Text(title, style = androidx.compose.material3.MaterialTheme.typography.titleMedium, color = FixItInk, fontWeight = FontWeight.SemiBold)
        Text(message, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium, color = FixItMuted)
        if (actionLabel != null && onAction != null) FixItSecondaryButton(actionLabel, onAction)
    }
}

@Composable
fun FixItErrorState(title: String = "Something went wrong", message: String, onRetry: (() -> Unit)? = null, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(Icons.Outlined.ErrorOutline, null, tint = FixItDanger, modifier = Modifier.size(36.dp))
        Text(title, style = androidx.compose.material3.MaterialTheme.typography.titleMedium, color = FixItInk, fontWeight = FontWeight.SemiBold)
        Text(message, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium, color = FixItMuted)
        if (onRetry != null) FixItPrimaryButton("Try again", onRetry)
    }
}

@Composable
fun FixItOfflineState(message: String = "Check your internet connection and try again.", modifier: Modifier = Modifier, onRetry: (() -> Unit)? = null) {
    Column(modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(Icons.Outlined.CloudOff, null, tint = FixItMuted, modifier = Modifier.size(36.dp))
        Text("You’re offline", style = androidx.compose.material3.MaterialTheme.typography.titleMedium, color = FixItInk, fontWeight = FontWeight.SemiBold)
        Text(message, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium, color = FixItMuted)
        if (onRetry != null) FixItSecondaryButton("Retry connection", onRetry)
    }
}

@Composable
fun FixItConfirmDialog(title: String, message: String, confirmLabel: String = "Continue", dismissLabel: String = "Cancel", onConfirm: () -> Unit, onDismiss: () -> Unit, destructive: Boolean = false) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.SemiBold) },
        text = { Text(message) },
        confirmButton = { TextButton(onClick = onConfirm) { Text(confirmLabel, color = if (destructive) FixItDanger else FixItPrimary) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text(dismissLabel, color = FixItMuted) } },
        shape = RoundedCornerShape(16.dp), containerColor = FixItSurface
    )
}

data class FixItNavigationItem(val label: String, val icon: ImageVector, val contentDescription: String = label)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixItTopAppBar(title: String, subtitle: String? = null, actions: @Composable RowScope.() -> Unit = {}, navigationIcon: @Composable () -> Unit = {}) {
    TopAppBar(
        title = {
            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(title, fontWeight = FontWeight.Bold, color = FixItInk)
                if (subtitle != null) Text(subtitle, style = androidx.compose.material3.MaterialTheme.typography.labelSmall, color = FixItMuted)
            }
        },
        navigationIcon = navigationIcon, actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = FixItSurface)
    )
}

@Composable
fun FixItBottomNavigation(items: List<FixItNavigationItem>, selectedLabel: String, onSelect: (String) -> Unit, modifier: Modifier = Modifier) {
    NavigationBar(modifier = modifier, containerColor = FixItSurface) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedLabel == item.label, onClick = { onSelect(item.label) },
                icon = { Icon(item.icon, contentDescription = item.contentDescription) },
                label = { Text(item.label, maxLines = 1) }
            )
        }
    }
}

@Composable
fun FixItImagePicker(selectedUri: Uri?, onImageSelected: (Uri?) -> Unit, modifier: Modifier = Modifier, label: String = "Choose image") {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> onImageSelected(uri) }
    Column(modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (selectedUri != null) {
            AsyncImage(model = selectedUri, contentDescription = "Selected image preview", modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
        } else {
            Box(
                Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(12.dp)).background(FixItSurfaceSoft).border(1.dp, FixItBorder, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Outlined.Image, null, tint = FixItMuted, modifier = Modifier.size(28.dp))
                    Text("No image selected", color = FixItMuted, style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                }
            }
        }
        FixItSecondaryButton(label, { launcher.launch("image/*") }, Modifier.fillMaxWidth())
    }
}

@Composable
fun FixItResponsiveContent(modifier: Modifier = Modifier, maxContentWidth: Dp = 640.dp, content: @Composable () -> Unit) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val contentWidth = if (maxWidth > maxContentWidth) maxContentWidth else maxWidth
        Box(Modifier.width(contentWidth).align(Alignment.TopCenter)) { content() }
    }
}
