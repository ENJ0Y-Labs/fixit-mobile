package com.fixit.mobile.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fixit.mobile.ui.model.Workspace
import com.fixit.mobile.ui.model.WorkspaceCatalog
import com.fixit.mobile.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceScreen(workspace: Workspace, onWorkspaceChange: (Workspace) -> Unit) {
    val sections = remember(workspace) { WorkspaceCatalog.sections(workspace) }
    var selectedTitle by remember(workspace) { mutableStateOf(sections.first().title) }
    var switcherOpen by remember { mutableStateOf(false) }
    var moreOpen by remember { mutableStateOf(false) }
    val selected = sections.firstOrNull { it.title == selectedTitle } ?: sections.first()
    val primary = sections.take(4)

    Scaffold(
        containerColor = FixItBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("FixIt", fontWeight = FontWeight.Bold)
                        Text(workspace.label.uppercase(), style = MaterialTheme.typography.labelSmall, color = FixItMuted)
                    }
                },
                actions = {
                    IconButton(onClick = { switcherOpen = true }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Switch workspace")
                    }
                    DropdownMenu(expanded = switcherOpen, onDismissRequest = { switcherOpen = false }) {
                        Workspace.entries.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item.label) },
                                onClick = { switcherOpen = false; onWorkspaceChange(item) }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FixItSurface)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = FixItSurface) {
                primary.forEachIndexed { index, section ->
                    NavigationBarItem(
                        selected = selectedTitle == section.title,
                        onClick = { selectedTitle = section.title },
                        icon = {
                            Icon(
                                when (index) {
                                    0 -> Icons.Default.Home
                                    1 -> Icons.Default.Search
                                    2 -> Icons.Default.ArrowForward
                                    else -> Icons.Default.Menu
                                },
                                contentDescription = null
                            )
                        },
                        label = { Text(section.title, maxLines = 1) }
                    )
                }
                if (sections.size > 4) {
                    NavigationBarItem(
                        selected = selectedTitle !in primary.map { it.title },
                        onClick = { moreOpen = true },
                        icon = { Icon(Icons.Default.Menu, contentDescription = null) },
                        label = { Text("More") }
                    )
                }
            }
        }
    ) { inset ->
        LazyColumn(
            Modifier.fillMaxSize().padding(inset),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Welcome to FixIt", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = FixItInk)
                    Text(selected.description, color = FixItMuted)
                }
            }
            item {
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = FixItInk)) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(selected.title, color = androidx.compose.ui.graphics.Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(selected.summary, color = androidx.compose.ui.graphics.Color.White.copy(alpha = .8f))
                    }
                }
            }
            item { Text("Explore ${selected.title.lowercase()}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }
            items(selected.items) { label ->
                Card(
                    Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = FixItSurface),
                    border = BorderStroke(1.dp, FixItBorder)
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(label, Modifier.weight(1f))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = FixItMuted)
                    }
                }
            }
            item {
                Text("Preview interface · Backend integration pending", style = MaterialTheme.typography.labelSmall, color = FixItMuted, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }

    if (moreOpen) {
        ModalBottomSheet(onDismissRequest = { moreOpen = false }, containerColor = FixItSurface) {
            Text("All ${workspace.label} sections", Modifier.padding(horizontal = 22.dp, vertical = 8.dp), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            sections.drop(4).forEach { section ->
                ListItem(
                    headlineContent = { Text(section.title) },
                    supportingContent = { Text(section.description, maxLines = 1) },
                    leadingContent = { Icon(Icons.Default.ArrowForward, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().clickable { selectedTitle = section.title; moreOpen = false },
                    colors = ListItemDefaults.colors(containerColor = FixItSurface)
                )
                HorizontalDivider(color = FixItBorder)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
