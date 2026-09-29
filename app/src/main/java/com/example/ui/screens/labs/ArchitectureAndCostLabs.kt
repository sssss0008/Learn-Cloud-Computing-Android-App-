package com.example.ui.screens.labs

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchitectureDesignerScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    val activeNodes = remember {
        mutableStateListOf(
            "Users (Edge Ingress)",
            "Cloud CDN (Edge Cache)",
            "Application Load Balancer",
            "Auto-Scaled Microservices (2 AZs)",
            "In-Memory Redis Cache",
            "Multi-AZ PostgreSQL DB"
        )
    }

    var showAiReview by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Architecture Canvas & Review", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            onCompleteLab()
                            onBack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StatusHealthy),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Complete", fontSize = 12.sp)
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "TOPOLOGY NODES ON WORKSPACE",
                color = ElectricCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            // Topology Canvas view
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    activeNodes.forEachIndexed { index, node ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkNavyCard)
                                .border(1.dp, CyberBorder, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(ElectricCyan))
                                Text(node, fontSize = 13.sp, color = Slate100, fontWeight = FontWeight.SemiBold)
                            }
                            StatusBadge("Active")
                        }
                        if (index < activeNodes.size - 1) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 24.dp)
                                    .height(10.dp)
                                    .width(2.dp)
                                    .background(ElectricCyan.copy(alpha = 0.5f))
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { showAiReview = true },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Generate AI Architecture Review", fontWeight = FontWeight.Bold)
            }

            if (showAiReview) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                    border = BorderStroke(1.dp, ElectricCyan),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(18.dp))
                            Text("AI ARCHITECTURAL EVALUATION", color = ElectricCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                        }

                        ReviewScoreRow("Scalability", "9.5 / 10", "Stateless microservices auto-scale horizontally with Redis query caching.")
                        ReviewScoreRow("High Availability", "9.0 / 10", "Redundant deployment across two Availability Zones with multi-AZ failover.")
                        ReviewScoreRow("Security", "8.5 / 10", "Private subnet isolation for database. Recommend adding WAF in front of ALB.")
                        ReviewScoreRow("Cost Efficiency", "8.0 / 10", "Right-sized caching offloads 80% of database reads.")
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewScoreRow(metric: String, score: String, feedback: String) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(metric, fontWeight = FontWeight.Bold, color = Slate100, fontSize = 13.sp)
            Text(score, color = StatusHealthy, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        }
        Text(feedback, fontSize = 12.sp, color = Slate300)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostLabScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var vmCount by remember { mutableFloatStateOf(3f) }
    var storageGb by remember { mutableFloatStateOf(100f) }
    var dbTierIndex by remember { mutableIntStateOf(1) } // Small, Medium, Large

    val vmCost = (vmCount * 14).toInt() // $14/mo per standard vCPU
    val storageCost = (storageGb * 0.023).toInt() // ~$0.023 per GB
    val dbCost = when (dbTierIndex) {
        0 -> 18
        1 -> 45
        else -> 120
    }
    val totalCost = vmCost + storageCost + dbCost

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cloud Cost & FinOps Estimator", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            onCompleteLab()
                            onBack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StatusHealthy),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Complete", fontSize = 12.sp)
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Big Cost Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, ElectricCyan)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("ESTIMATED MONTHLY CLOUD SPEND", color = Slate400, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("$$totalCost / month", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = ElectricCyan, fontFamily = FontFamily.Monospace)
                    Text("Calculated using standard on-demand cloud pricing baseline.", fontSize = 11.sp, color = Slate400)
                }
            }

            // Sliders
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Compute Instances: ${vmCount.toInt()} VMs", fontWeight = FontWeight.Bold, color = Slate100)
                            Text("$$vmCost/mo", color = CloudBlueLight, fontFamily = FontFamily.Monospace)
                        }
                        Slider(
                            value = vmCount,
                            onValueChange = { vmCount = it },
                            valueRange = 1f..10f,
                            steps = 8
                        )
                    }

                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Object Storage: ${storageGb.toInt()} GB", fontWeight = FontWeight.Bold, color = Slate100)
                            Text("$$storageCost/mo", color = CloudBlueLight, fontFamily = FontFamily.Monospace)
                        }
                        Slider(
                            value = storageGb,
                            onValueChange = { storageGb = it },
                            valueRange = 20f..1000f,
                            steps = 49
                        )
                    }

                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Managed Database Tier", fontWeight = FontWeight.Bold, color = Slate100)
                            Text("$$dbCost/mo", color = CloudBlueLight, fontFamily = FontFamily.Monospace)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("Micro ($18)", "Standard ($45)", "High-Perf ($120)").forEachIndexed { index, name ->
                                FilterChip(
                                    selected = dbTierIndex == index,
                                    onClick = { dbTierIndex = index },
                                    label = { Text(name, fontSize = 11.sp) }
                                )
                            }
                        }
                    }
                }
            }

            // FinOps Tips
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("FINOPS COST OPTIMIZATION TIPS", color = StatusWarning, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text("• Purchase 1-year Savings Plans for up to 40% discount on baseline VMs.", fontSize = 12.sp, color = Slate200)
                    Text("• Enable S3 lifecycle rules to transition objects to Glacier after 90 days.", fontSize = 12.sp, color = Slate200)
                }
            }
        }
    }
}
