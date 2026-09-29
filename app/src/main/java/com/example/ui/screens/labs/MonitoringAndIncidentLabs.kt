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
fun MonitoringLabScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var severityFilter by remember { mutableStateOf("ALL") }

    val logs = listOf(
        Triple("09:31:02", "INFO", "alb-ingress: HTTP 200 GET /api/v1/items (42ms) client=198.51.100.22"),
        Triple("09:31:04", "INFO", "web-pod-4: autoscale trigger evaluated; 4 pods healthy"),
        Triple("09:31:07", "WARN", "cache-redis: memory utilization reached 78% of maxmemory"),
        Triple("09:31:10", "INFO", "db-primary: vacuum analyze completed on table 'transactions'"),
        Triple("09:31:14", "ERROR", "payment-service: upstream timeout contacting payment gateway (status=504)")
    )

    val filteredLogs = if (severityFilter == "ALL") logs else logs.filter { it.second == severityFilter }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Real-Time Telemetry & Observability", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                text = "SYSTEM TELEMETRY - 4 GOLDEN SIGNALS",
                color = ElectricCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                GaugeCard("CPU LOAD", "42%", StatusHealthy, Modifier.weight(1f))
                GaugeCard("MEMORY", "67%", StatusHealthy, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                GaugeCard("LATENCY P95", "142 ms", CloudBlueLight, Modifier.weight(1f))
                GaugeCard("ERROR RATE", "0.7%", StatusWarning, Modifier.weight(1f))
            }

            // Log stream inspector
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("STRUCTURED LOG STREAM", color = Slate400, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("ALL", "INFO", "WARN", "ERROR").forEach { sev ->
                                FilterChip(
                                    selected = severityFilter == sev,
                                    onClick = { severityFilter = sev },
                                    label = { Text(sev, fontSize = 10.sp, fontFamily = FontFamily.Monospace) }
                                )
                            }
                        }
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = TerminalBackground),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            filteredLogs.forEach { (time, sev, msg) ->
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(time, color = Slate500, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                                    Text(
                                        sev,
                                        color = when (sev) {
                                            "ERROR" -> StatusError
                                            "WARN" -> StatusWarning
                                            else -> StatusHealthy
                                        },
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Text(msg, color = Slate200, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GaugeCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, fontSize = 10.sp, color = Slate400, fontFamily = FontFamily.Monospace)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = color, fontFamily = FontFamily.Monospace)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentSimulatorScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var resolved by remember { mutableStateOf(false) }
    var selectedCause by remember { mutableStateOf<String?>(null) }

    val options = listOf(
        "DNS misconfiguration causing lookup failures",
        "Database connection pool exhaustion due to missing index",
        "DDoS attack overwhelming public load balancer",
        "Storage bucket permissions revoked"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cloud Incident & Latency Troubleshooting", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (resolved) {
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
            // Alert Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2E1212)),
                border = BorderStroke(1.dp, StatusError),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = StatusError)
                    Column {
                        Text("SEVERITY 1: API LATENCY SURGE TO 4,800ms", fontWeight = FontWeight.Bold, color = StatusError, fontSize = 13.sp)
                        Text("HTTP 504 Gateway Timeouts reported on `/api/v1/orders`", color = Slate300, fontSize = 12.sp)
                    }
                }
            }

            // Evidence Panel
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("INCIDENT TELEMETRY EVIDENCE", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text("• Web servers CPU: 18% (Low)\n• Load Balancer active connections: 2,400 (Normal)\n• Database CPU: 99% (Saturated)\n• Database active connections: 100/100 (Max limit reached)\n• Slow query log: SELECT * FROM orders WHERE customer_id = ? taking 3.8s", fontSize = 12.sp, color = Slate200, fontFamily = FontFamily.Monospace, lineHeight = 18.sp)
                }
            }

            // Investigation options
            Text("ROOT CAUSE ANALYSIS (SELECT THE CAUSE):", color = Slate400, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                options.forEachIndexed { index, option ->
                    val isSelected = selectedCause == option
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCause = option
                                if (index == 1) resolved = true
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) (if (index == 1) DarkNavyCardElevated else Color(0xFF2A1515)) else DarkNavyCard
                        ),
                        border = BorderStroke(1.dp, if (isSelected) (if (index == 1) StatusHealthy else StatusError) else CyberBorder)
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.padding(14.dp),
                            fontSize = 13.sp,
                            color = Slate100
                        )
                    }
                }
            }

            if (resolved) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                    border = BorderStroke(1.dp, StatusHealthy),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("CORRECT! REMEDIATION DEPLOYED", color = StatusHealthy, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Adding an index on `orders(customer_id)` reduced query scan time from 3.8s down to 2ms, clearing the connection pool backlog immediately.", fontSize = 12.sp, color = Slate200)
                    }
                }
            }
        }
    }
}
