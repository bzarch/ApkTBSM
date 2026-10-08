package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.screens.*
import com.example.ui.theme.GraphiteBackground
import com.example.ui.theme.GraphiteSurface
import com.example.ui.theme.MaroonLight
import com.example.ui.theme.MaroonPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContainer(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContainer(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    // Handle back button for sub-screens
    BackHandler(enabled = uiState.currentSubScreen != null) {
        viewModel.navigateBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = GraphiteBackground,
        bottomBar = {
            if (uiState.currentSubScreen == null) {
                TbsmBottomNavigationBar(
                    currentTab = uiState.currentTab,
                    onTabSelected = { viewModel.selectTab(it) }
                )
            }
        },
        floatingActionButton = {
            if (uiState.currentSubScreen == null) {
                FloatingActionButton(
                    onClick = { viewModel.navigateToSubScreen("AI_CHAT") },
                    containerColor = MaroonPrimary,
                    contentColor = TextWhite,
                    modifier = Modifier.testTag("fab_quick_ai")
                ) {
                    Icon(imageVector = Icons.Default.SmartToy, contentDescription = "AI Assistant")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                // Secondary / Sub-Screens
                uiState.currentSubScreen == "SETTINGS" -> {
                    SettingsScreen(viewModel = viewModel, onBack = { viewModel.navigateBack() })
                }
                uiState.currentSubScreen == "AI_CHAT" -> {
                    AiChatScreen(viewModel = viewModel, onBack = { viewModel.navigateBack() })
                }
                uiState.currentSubScreen == "COMPONENT_DETAIL" -> {
                    ComponentDetailScreen(
                        componentId = uiState.selectedItemId ?: "piston",
                        onBack = { viewModel.navigateBack() }
                    )
                }
                uiState.currentSubScreen == "MATERIAL_DETAIL" -> {
                    MaterialDetailScreen(
                        materialId = uiState.selectedItemId ?: "siklus_mesin_4_tak",
                        onBack = { viewModel.navigateBack() }
                    )
                }
                uiState.currentSubScreen == "PRACTICUM_DETAIL" -> {
                    PracticumDetailScreen(
                        practicumId = uiState.selectedItemId ?: "prak_busi",
                        onBack = { viewModel.navigateBack() }
                    )
                }
                // Primary Tabs
                uiState.currentTab == "HOME" -> {
                    HomeScreen(
                        viewModel = viewModel,
                        onNavigate = { screen, id, cat ->
                            if (screen in listOf("HOME", "TOOLS", "DIAGNOSIS", "BELAJAR", "GARAGE")) {
                                viewModel.selectTab(screen)
                            } else {
                                viewModel.navigateToSubScreen(screen, id, cat)
                            }
                        }
                    )
                }
                uiState.currentTab == "TOOLS" -> {
                    ToolsScreen(
                        viewModel = viewModel,
                        onNavigate = { screen, id, cat ->
                            viewModel.navigateToSubScreen(screen, id, cat)
                        }
                    )
                }
                uiState.currentTab == "DIAGNOSIS" -> {
                    DiagnosisScreen(
                        viewModel = viewModel,
                        onNavigate = { screen, id, cat ->
                            viewModel.navigateToSubScreen(screen, id, cat)
                        }
                    )
                }
                uiState.currentTab == "BELAJAR" -> {
                    BelajarScreen(
                        viewModel = viewModel,
                        onNavigate = { screen, id, cat ->
                            viewModel.navigateToSubScreen(screen, id, cat)
                        }
                    )
                }
                uiState.currentTab == "GARAGE" -> {
                    GarageScreen(
                        viewModel = viewModel,
                        onNavigate = { screen, id, cat ->
                            viewModel.navigateToSubScreen(screen, id, cat)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TbsmBottomNavigationBar(
    currentTab: String,
    onTabSelected: (String) -> Unit
) {
    val items = listOf(
        NavigationItem("HOME", "Home", Icons.Default.Home),
        NavigationItem("TOOLS", "Tools", Icons.Default.Build),
        NavigationItem("DIAGNOSIS", "Diagnosis", Icons.Default.Warning),
        NavigationItem("BELAJAR", "Belajar", Icons.AutoMirrored.Filled.MenuBook),
        NavigationItem("GARAGE", "Garage", Icons.Default.TwoWheeler)
    )

    NavigationBar(
        containerColor = GraphiteSurface,
        tonalElevation = 8.dp,
        modifier = Modifier.testTag("bottom_nav_bar")
    ) {
        items.forEach { item ->
            val isSelected = currentTab == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) MaroonLight else TextMuted
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (isSelected) TextWhite else TextMuted
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaroonPrimary.copy(alpha = 0.3f)
                ),
                modifier = Modifier.testTag("nav_item_${item.route.lowercase()}")
            )
        }
    }
}

data class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
