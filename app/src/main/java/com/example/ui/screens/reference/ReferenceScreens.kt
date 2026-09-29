package com.example.ui.screens.reference

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CheatSheetEntry
import com.example.data.model.GlossaryTerm
import com.example.data.repository.CloudCoursesData
import com.example.data.repository.CloudLabsData
import com.example.data.repository.CloudReferenceData
import com.example.ui.components.CodeSnippetCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlossaryScreen(
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filtered = CloudReferenceData.glossaryTerms.filter {
        searchQuery.isBlank() || it.term.contains(searchQuery, ignoreCase = true) || it.simpleExplanation.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cloud Architecture Glossary", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search term (e.g. VPC, IAM, Pod, CDN)") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Slate400) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CloudBlueLight,
                    unfocusedBorderColor = CyberBorder
                )
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered) { term ->
                    GlossaryCard(term = term)
                }
            }
        }
    }
}

@Composable
fun GlossaryCard(term: GlossaryTerm) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, CyberBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(term.term, fontWeight = FontWeight.Bold, color = Slate100, fontSize = 15.sp)
                Text(term.category, color = ElectricCyan, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            }

            Text("Simple Explanation:", color = Slate400, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            Text(term.simpleExplanation, fontSize = 13.sp, color = Slate200, lineHeight = 18.sp)

            Text("Technical Engineering Detail:", color = Slate400, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
            Text(term.technicalExplanation, fontSize = 12.sp, color = Slate300, lineHeight = 16.sp)

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = DarkNavyCard
            ) {
                Text(
                    text = "Example: ${term.example}",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 11.sp,
                    color = CloudBlueLight
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheatSheetScreen(
    onBack: () -> Unit
) {
    var selectedCat by remember { mutableStateOf<String?>(null) }
    val categories = remember { listOf("All") + CloudReferenceData.cheatSheetEntries.map { it.category }.distinct() }

    val filtered = CloudReferenceData.cheatSheetEntries.filter {
        selectedCat == null || selectedCat == "All" || it.category == selectedCat
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cloud & Linux Cheat Sheet", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = (selectedCat == null && cat == "All") || selectedCat == cat,
                        onClick = { selectedCat = if (cat == "All") null else cat },
                        label = { Text(cat) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered) { entry ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, CyberBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(entry.purpose, fontWeight = FontWeight.Bold, color = Slate100, fontSize = 13.sp)
                                Text(entry.category, color = ElectricCyan, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = TerminalBackground,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "$ ${entry.command}",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    color = TerminalGreen,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Text(entry.explanation, fontSize = 11.sp, color = Slate300)
                        }
                    }
                }
            }
        }
    }
}
