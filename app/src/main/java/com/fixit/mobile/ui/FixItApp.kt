package com.fixit.mobile.ui

import androidx.compose.runtime.*
import com.fixit.mobile.ui.model.Workspace
import com.fixit.mobile.ui.screens.WorkspaceScreen

@Composable fun FixItApp() {
    var workspace by remember { mutableStateOf(Workspace.CUSTOMER) }
    WorkspaceScreen(workspace = workspace, onWorkspaceChange = { workspace = it })
}
