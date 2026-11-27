package com.fixit.mobile.ui

import androidx.compose.runtime.*
import com.fixit.mobile.ui.admin.AdminApp
import com.fixit.mobile.ui.customer.CustomerApp
import com.fixit.mobile.ui.model.Workspace
import com.fixit.mobile.ui.provider.ProviderApp

@Composable
fun FixItApp() {
    var workspace by remember { mutableStateOf(Workspace.CUSTOMER) }
    when (workspace) {
        Workspace.CUSTOMER -> CustomerApp(onWorkspaceChange = { workspace = it })
        Workspace.PROVIDER -> ProviderApp(onWorkspaceChange = { workspace = it })
        Workspace.ADMIN -> AdminApp(onWorkspaceChange = { workspace = it })
    }
}
