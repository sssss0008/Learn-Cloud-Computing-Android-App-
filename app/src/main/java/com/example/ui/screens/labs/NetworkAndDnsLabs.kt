package com.example.ui.screens.labs

import androidx.compose.animation.*
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
import com.example.ui.components.CloudPulsingNode
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubnetCalculatorScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var ipInput by remember { mutableStateOf("192.168.1.0") }
    var cidrPrefix by remember { mutableFloatStateOf(24f) }

    val prefix = cidrPrefix.toInt()
    val totalHosts = (2.0.pow((32 - prefix).toDouble())).toLong()
    val usableHosts = if (totalHosts > 2) totalHosts - 2 else 0

    // Compute subnet mask string from prefix
    val mask = run {
        var bits = 0L
        for (i in 0 until prefix) {
            bits = bits or (1L shl (31 - i))
        }
        val octet1 = (bits shr 24) and 0xFF
        val octet2 = (bits shr 16) and 0xFF
        val octet3 = (bits shr 8) and 0xFF
        val octet4 = bits and 0xFF
        "$octet1.$octet2.$octet3.$octet4"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subnet & CIDR Visualizer", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
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
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "NETWORK CONFIGURATION",
                        color = ElectricCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    OutlinedTextField(
                        value = ipInput,
                        onValueChange = { ipInput = it },
                        label = { Text("Base IPv4 Address") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CloudBlueLight,
                            unfocusedBorderColor = CyberBorder
                        )
                    )

                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("CIDR Prefix: /$prefix", fontWeight = FontWeight.Bold, color = Slate100, fontFamily = FontFamily.Monospace)
                            Text("Subnet Mask: $mask", color = CloudBlueLight, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                        }
                        Slider(
                            value = cidrPrefix,
                            onValueChange = { cidrPrefix = it },
                            valueRange = 8f..30f,
                            steps = 21,
                            colors = SliderDefaults.colors(thumbColor = ElectricCyan, activeTrackColor = CloudBlue)
                        )
                    }
                }
            }

            // Calculation Output Cards
            Text(
                text = "CALCULATED METRICS",
                color = Slate400,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard("TOTAL ADDRESSES", "$totalHosts", Modifier.weight(1f), ElectricCyan)
                MetricCard("USABLE HOSTS", "$usableHosts", Modifier.weight(1f), StatusHealthy)
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailRow("Network Address", "$ipInput")
                    DetailRow("Broadcast Address", "${ipInput.substringBeforeLast(".")}.${totalHosts - 1}")
                    DetailRow("Usable Range", "${ipInput.substringBeforeLast(".")}.1 - ${ipInput.substringBeforeLast(".")}.${totalHosts - 2}")
                    DetailRow("Subnet Mask", mask)
                    DetailRow("AWS/Cloud Reserved", "5 IPs (Network, VPC Router, DNS, Future, Broadcast)")
                }
            }

            // Bitmask visual representation
            Card(
                colors = CardDefaults.cardColors(containerColor = TerminalBackground),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "32-BIT BINARY MASK REPRESENTATION",
                        color = Slate400,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        for (i in 1..32) {
                            val isNetworkBit = i <= prefix
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(18.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(if (isNetworkBit) CloudBlue else Slate800),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isNetworkBit) "1" else "0",
                                    fontSize = 8.sp,
                                    color = if (isNetworkBit) Color.White else Slate500,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("$prefix Network Bits", color = CloudBlueLight, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        Text("${32 - prefix} Host Bits", color = Slate400, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String, modifier: Modifier = Modifier, color: Color) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontSize = 10.sp, color = Slate400, fontFamily = FontFamily.Monospace)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Slate400, fontSize = 12.sp)
        Text(value, color = Slate100, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.Monospace)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkVisualizerScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    val steps = listOf(
        "User" to "Client browser or mobile app initiating HTTPS requests.",
        "Internet" to "Public internet transit through Tier 1 fiber optic backbones.",
        "Edge CDN" to "CloudFront/Cloudflare edge point of presence caching static assets.",
        "WAF / Firewall" to "Web Application Firewall inspecting packets and blocking SQLi/XSS.",
        "Load Balancer" to "Application Load Balancer distributing HTTP traffic across instances.",
        "Web / App Server" to "Stateless Docker containers or virtual machines running business logic.",
        "Managed Database" to "Multi-AZ PostgreSQL/MySQL cluster storing persistent ACID transactions."
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Network Topology Visualizer", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "INTERACTIVE TOPOLOGY CHAIN (TAP TO INSPECT)",
                fontSize = 11.sp,
                color = ElectricCyan,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            steps.forEachIndexed { index, (name, _) ->
                val isSelected = selectedIndex == index
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { selectedIndex = index }
                        .border(
                            1.5.dp,
                            if (isSelected) ElectricCyan else CyberBorder,
                            RoundedCornerShape(10.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) DarkNavyCardElevated else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) ElectricCyan else Slate800),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "${index + 1}",
                                color = if (isSelected) DeepNavy else Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                        Column {
                            Text(name, fontWeight = FontWeight.Bold, color = if (isSelected) ElectricCyan else Slate100)
                            Text("Layer ${if (index < 2) "1-3" else if (index < 5) "4-7" else "7 App"}", fontSize = 10.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
                if (index < steps.size - 1) {
                    Box(
                        modifier = Modifier
                            .padding(start = 28.dp)
                            .height(16.dp)
                            .width(2.dp)
                            .background(ElectricCyan.copy(alpha = 0.5f))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Inspection Panel
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "COMPONENT BREAKDOWN: ${steps[selectedIndex].first.uppercase()}",
                        color = ElectricCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = steps[selectedIndex].second,
                        color = Slate200,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DnsVisualizerScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }
    val dnsSteps = listOf(
        "1. Browser Cache Check" to "Browser checks its local cache (chrome://net-internals/#dns). If absent, calls OS resolver.",
        "2. Recursive Resolver" to "OS contacts ISP or public resolver (e.g. 8.8.8.8, 1.1.1.1) to resolve the hostname.",
        "3. Root Nameserver (.)" to "Resolver queries one of the 13 worldwide root servers to locate the Top-Level Domain (TLD) server.",
        "4. TLD Nameserver (.com)" to "Root server returns TLD nameserver IP addresses managing the .com registry.",
        "5. Authoritative Nameserver" to "Resolver queries the domain's authoritative nameserver (e.g. ns-1.awiskarcloud.net).",
        "6. A Record Returned" to "Authoritative server returns IP address `104.21.50.88` with Time-To-Live (TTL) of 300 seconds.",
        "7. Resolver Caches Answer" to "Recursive resolver saves the record for 300s to serve subsequent user requests instantly.",
        "8. HTTPS Connection Initiated" to "Browser receives IPv4 address, opens TCP socket on port 443, and executes TLS handshake."
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DNS 8-Step Resolution Simulator", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Resolving domain: https://learncloud.awiskar.dev",
                    color = ElectricCyan,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )

                // Current step display
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "STEP ${currentStep + 1} OF 8",
                            color = Slate400,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = dnsSteps[currentStep].first,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElectricCyan
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = dnsSteps[currentStep].second,
                            fontSize = 14.sp,
                            color = Slate200,
                            lineHeight = 20.sp
                        )
                    }
                }

                // Step progress indicators
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (i in 0 until 8) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (i <= currentStep) ElectricCyan else Slate800)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { if (currentStep > 0) currentStep-- },
                    enabled = currentStep > 0,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Previous")
                }
                Button(
                    onClick = { if (currentStep < 7) currentStep++ },
                    enabled = currentStep < 7,
                    modifier = Modifier.weight(1f).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(if (currentStep < 7) "Next Step" else "Resolved!")
                }
            }
        }
    }
}
