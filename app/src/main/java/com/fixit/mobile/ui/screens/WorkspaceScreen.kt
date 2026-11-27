package com.fixit.mobile.ui.screens

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
import com.fixit.mobile.ui.components.FixItBottomNavigation
import com.fixit.mobile.ui.components.FixItCard
import com.fixit.mobile.ui.components.FixItNavigationItem
import com.fixit.mobile.ui.components.FixItResponsiveContent
import com.fixit.mobile.ui.components.FixItTopAppBar
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
    val navigationItems = primary.mapIndexed { index, section ->
        FixItNavigationItem(section.title, when (index) {
            0 -> Icons.Default.Home
            1 -> Icons.Default.Search
            2 -> Icons.Default.ArrowForward
            else -> Icons.Default.Menu
        })
    } + if (sections.size > 4) listOf(FixItNavigationItem("More", Icons.Default.Menu)) else emptyList()

    Scaffold(
        containerColor = FixItBackground,
        topBar = {
            FixItTopAppBar(
                title = "FixIt",
                subtitle = workspace.label.uppercase(),
                actions = {
                    IconButton(onClick = { switcherOpen = true }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Switch workspace")
                    }
                    DropdownMenu(expanded = switcherOpen, onDismissRequest = { switcherOpen = false }) {
                        Workspace.entries.forEach { item ->
                            DropdownMenuItem(text = { Text(item.label) }, onClick = { switcherOpen = false; onWorkspaceChange(item) })
                        }
                    }
                }
            )
        },
        bottomBar = {
            FixItBottomNavigation(
                items = navigationItems,
                selectedLabel = if (selectedTitle !in primary.map { it.title }) "More" else selectedTitle,
                onSelect = { label -> if (label == "More") moreOpen = true else selectedTitle = label }
            )
        }
    ) { inset ->
        FixItResponsiveContent(modifier = Modifier.fillMaxSize().padding(inset)) {
            LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
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
                    FixItCard {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
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
