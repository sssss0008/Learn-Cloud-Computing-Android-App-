package com.example.ui.screens.labs

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CloudLab
import com.example.data.model.LabCategory
import com.example.data.repository.CloudLabsData
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabsHubScreen(
    completedLabIds: Set<String>,
    onOpenLab: (route: String, labId: String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<LabCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredLabs = CloudLabsData.labs.filter { lab ->
        (selectedCategory == null || lab.category == selectedCategory) &&
                (searchQuery.isBlank() || lab.title.contains(searchQuery, ignoreCase = true) || lab.skills.any { it.contains(searchQuery, ignoreCase = true) })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header
        Column {
            Text(
                text = "Interactive Cloud Laboratories",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Hands-on environments, simulators, and infrastructure consoles",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search labs, tools, commands...") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Slate400) },
            modifier = Modifier.fillMaxWidth().testTag("search_labs_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CloudBlueLight,
                unfocusedBorderColor = CyberBorder
            )
        )

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { selectedCategory = null },
                label = { Text("All Labs (${CloudLabsData.labs.size})") }
            )
            LabCategory.entries.forEach { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat },
                    label = { Text(cat.name.replace("_", " ")) }
                )
            }
        }

        // Labs List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredLabs) { lab ->
                val isCompleted = completedLabIds.contains(lab.id)
                LabCard(
                    lab = lab,
                    isCompleted = isCompleted,
                    onClick = { onOpenLab(lab.labRoute, lab.id) }
                )
            }
        }
    }
}

@Composable
fun LabCard(
    lab: CloudLab,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .border(1.dp, if (isCompleted) StatusHealthy.copy(alpha = 0.5f) else CyberBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = lab.category.name,
                        color = ElectricCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text("•", color = Slate500, fontSize = 10.sp)
                    Text(lab.difficulty, color = Slate400, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                    Text("•", color = Slate500, fontSize = 10.sp)
                    Text("${lab.durationMin}m", color = Slate400, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                }

                if (isCompleted) {
                    StatusBadge("Passed")
                }
            }

            Text(
                text = lab.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Slate100
            )

            Text(
                text = lab.description,
                fontSize = 12.sp,
                color = Slate300,
                lineHeight = 16.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    lab.skills.take(3).forEach { skill ->
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Slate800
                        ) {
                            Text(
                                text = skill,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                fontSize = 10.sp,
                                color = Slate200,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(if (isCompleted) "Replay Lab" else "Start Lab", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
