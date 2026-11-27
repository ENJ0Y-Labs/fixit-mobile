package com.fixit.mobile.ui

import androidx.compose.runtime.*
import com.fixit.mobile.ui.customer.CustomerApp
import com.fixit.mobile.ui.model.Workspace
import com.fixit.mobile.ui.screens.WorkspaceScreen

@Composable
fun FixItApp() {
    var workspace by remember { mutableStateOf(Workspace.CUSTOMER) }

    if (workspace == Workspace.CUSTOMER) {
        CustomerApp(onWorkspaceChange = { workspace = it })
    } else {
        WorkspaceScreen(
            workspace = workspace,
            onWorkspaceChange = { workspace = it }
        )
    }
}
