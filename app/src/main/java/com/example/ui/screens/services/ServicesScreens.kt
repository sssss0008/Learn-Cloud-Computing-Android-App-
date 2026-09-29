package com.example.ui.screens.services

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CloudService
import com.example.data.repository.CloudServicesData
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloudServicesScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showComparisonMatrix by remember { mutableStateOf(false) }

    val categories = remember {
        listOf("All") + CloudServicesData.services.map { it.category }.distinct()
    }

    val filteredServices = CloudServicesData.services.filter { srv ->
        (selectedCategory == null || selectedCategory == "All" || srv.category == selectedCategory) &&
                (searchQuery.isBlank() || srv.name.contains(searchQuery, ignoreCase = true) ||
                        srv.description.contains(searchQuery, ignoreCase = true) ||
                        srv.providerA.contains(searchQuery, ignoreCase = true) ||
                        srv.providerB.contains(searchQuery, ignoreCase = true) ||
                        srv.providerC.contains(searchQuery, ignoreCase = true))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Cloud Service Explorer",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Architecture catalog & Multi-Cloud equivalents",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = { showComparisonMatrix = !showComparisonMatrix }) {
                Icon(
                    imageVector = if (showComparisonMatrix) Icons.Default.List else Icons.Default.TableChart,
                    contentDescription = "Toggle Matrix View",
                    tint = ElectricCyan
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search EC2, Blob, Cloud SQL, S3...") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Slate400) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CloudBlueLight,
                unfocusedBorderColor = CyberBorder
            )
        )

        // Category Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.forEach { cat ->
                FilterChip(
                    selected = (selectedCategory == null && cat == "All") || selectedCategory == cat,
                    onClick = { selectedCategory = if (cat == "All") null else cat },
                    label = { Text(cat) }
                )
            }
        }

        if (showComparisonMatrix) {
            // Multi-Cloud Comparison Table
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(14.dp).verticalScroll(rememberScrollState())) {
                    Text("MULTI-CLOUD EQUIVALENTS MATRIX", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(10.dp))

                    filteredServices.forEach { srv ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(srv.name, fontWeight = FontWeight.Bold, color = Slate100, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("AWS: ${srv.providerA}", fontSize = 11.sp, color = CloudBlueLight, fontFamily = FontFamily.Monospace)
                                Text("Azure: ${srv.providerB}", fontSize = 11.sp, color = ElectricCyan, fontFamily = FontFamily.Monospace)
                            }
                            Text("GCP: ${srv.providerC}", fontSize = 11.sp, color = CloudViolet, fontFamily = FontFamily.Monospace)
                            HorizontalDivider(modifier = Modifier.padding(top = 8.dp), color = Slate800)
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredServices) { srv ->
                    ServiceCard(service = srv)
                }
            }
        }
    }
}

@Composable
fun ServiceCard(service: CloudService) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, CyberBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(service.category.uppercase(), color = ElectricCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                Text(
                    text = "${service.providerA} • ${service.providerC}",
                    fontSize = 10.sp,
                    color = Slate400,
                    fontFamily = FontFamily.Monospace
                )
            }

            Text(
                text = service.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Slate100
            )

            Text(
                text = service.description,
                fontSize = 12.sp,
                color = Slate300,
                lineHeight = 16.sp
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("WHEN TO USE:", fontSize = 10.sp, color = Slate400, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                    Text(service.whenToUse, fontSize = 11.sp, color = Slate200)
                }
            }
        }
    }
}
