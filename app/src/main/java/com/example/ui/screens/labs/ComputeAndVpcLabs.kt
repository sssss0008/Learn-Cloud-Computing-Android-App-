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
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VmProvisioningLabScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var vCpu by remember { mutableStateOf("2 vCPU") }
    var ram by remember { mutableStateOf("4 GB") }
    var storage by remember { mutableStateOf("40 GB SSD") }
    var osImage by remember { mutableStateOf("Ubuntu 22.04 LTS") }
    var networkType by remember { mutableStateOf("Private Subnet") }

    var provisioningState by remember { mutableStateOf("NOT_STARTED") } // NOT_STARTED, INITIALIZING, PROVISIONING, CONFIGURING_NET, HEALTH_CHECK, RUNNING
    val coroutineScope = rememberCoroutineScope()

    fun startProvisioning() {
        coroutineScope.launch {
            provisioningState = "INITIALIZING"
            delay(1000)
            provisioningState = "PROVISIONING"
            delay(1200)
            provisioningState = "CONFIGURING_NET"
            delay(1000)
            provisioningState = "HEALTH_CHECK"
            delay(1000)
            provisioningState = "RUNNING"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cloud Virtual Machine Provisioner", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (provisioningState == "RUNNING") {
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
            // Configuration Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "VIRTUAL HARDWARE SPECIFICATION",
                        color = ElectricCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    SpecSelector("Compute Cores", listOf("1 vCPU", "2 vCPU", "4 vCPU", "8 vCPU"), vCpu) { vCpu = it }
                    SpecSelector("System Memory", listOf("2 GB", "4 GB", "8 GB", "16 GB"), ram) { ram = it }
                    SpecSelector("Block Storage", listOf("20 GB", "40 GB SSD", "100 GB SSD"), storage) { storage = it }
                    SpecSelector("Operating System", listOf("Ubuntu 22.04 LTS", "Debian 12", "Alpine Linux"), osImage) { osImage = it }
                    SpecSelector("Network VPC Tier", listOf("Public Subnet", "Private Subnet"), networkType) { networkType = it }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = { startProvisioning() },
                        enabled = provisioningState == "NOT_STARTED" || provisioningState == "RUNNING",
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(if (provisioningState == "RUNNING") "Re-Provision Instance" else "Create VM Instance")
                    }
                }
            }

            // Provisioning state machine timeline
            if (provisioningState != "NOT_STARTED") {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("PROVISIONING STATE", color = Slate400, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            StatusBadge(if (provisioningState == "RUNNING") "Running" else "Provisioning")
                        }

                        TimelineStep("Initializing hypervisor slice", provisioningState != "NOT_STARTED")
                        TimelineStep("Allocating $vCpu & $ram from pool", provisioningState in listOf("PROVISIONING", "CONFIGURING_NET", "HEALTH_CHECK", "RUNNING"))
                        TimelineStep("Attaching $storage volume & $osImage", provisioningState in listOf("CONFIGURING_NET", "HEALTH_CHECK", "RUNNING"))
                        TimelineStep("Attaching virtual NIC to $networkType", provisioningState in listOf("HEALTH_CHECK", "RUNNING"))
                        TimelineStep("Health checks: 2/2 passed. Instance ACTIVE.", provisioningState == "RUNNING")

                        if (provisioningState == "RUNNING") {
                            HorizontalDivider(color = CyberBorder)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Private IP: 10.0.2.45", color = CloudBlueLight, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                                Text("Uptime: 0m 12s", color = StatusHealthy, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecSelector(title: String, options: List<String>, selected: String, onSelect: (String) -> Unit) {
    Column {
        Text(title, fontSize = 12.sp, color = Slate300, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            options.forEach { opt ->
                val isSelected = opt == selected
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onSelect(opt) }
                        .border(1.dp, if (isSelected) ElectricCyan else CyberBorder, RoundedCornerShape(8.dp)),
                    color = if (isSelected) CloudBlue.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                ) {
                    Text(
                        text = opt,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        fontSize = 11.sp,
                        color = if (isSelected) ElectricCyan else Slate200,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

@Composable
private fun TimelineStep(text: String, isDone: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(if (isDone) StatusHealthy else Slate700),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
            }
        }
        Text(
            text = text,
            fontSize = 12.sp,
            color = if (isDone) Slate100 else Slate500,
            fontFamily = FontFamily.Monospace
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VpcBuilderScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var hasPublicSubnet by remember { mutableStateOf(true) }
    var hasPrivateSubnet by remember { mutableStateOf(true) }
    var hasInternetGateway by remember { mutableStateOf(true) }
    var hasNatGateway by remember { mutableStateOf(true) }
    var hasLoadBalancer by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VPC & Subnet Network Architect", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                text = "VPC TOPOLOGY CANVAS: 10.0.0.0/16",
                color = ElectricCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            // VPC Container Boundary
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, CloudBlueLight, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("VPC: Production-VPC", fontWeight = FontWeight.Bold, color = Slate100)
                        Text("10.0.0.0/16", color = ElectricCyan, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                    }

                    if (hasInternetGateway) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF0F2B48),
                            border = BorderStroke(1.dp, CloudBlueLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Language, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Internet Gateway (IGW) - Direct WAN Route", fontSize = 12.sp, color = Slate100)
                            }
                        }
                    }

                    // Public Subnet Box
                    if (hasPublicSubnet) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, StatusHealthy.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("PUBLIC SUBNET (10.0.1.0/24)", fontSize = 11.sp, color = StatusHealthy, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                Text("Route: 0.0.0.0/0 -> Internet Gateway", fontSize = 11.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    if (hasLoadBalancer) ComponentChip(Icons.Default.AltRoute, "ALB")
                                    if (hasNatGateway) ComponentChip(Icons.Default.Security, "NAT GW")
                                }
                            }
                        }
                    }

                    // Private Subnet Box
                    if (hasPrivateSubnet) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, StatusWarning.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("PRIVATE SUBNET (10.0.2.0/24)", fontSize = 11.sp, color = StatusWarning, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                Text("Route: 0.0.0.0/0 -> NAT Gateway (Outbound Only)", fontSize = 11.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    ComponentChip(Icons.Default.Computer, "App Server")
                                    ComponentChip(Icons.Default.Storage, "PostgreSQL DB")
                                }
                            }
                        }
                    }
                }
            }

            // Controls
            Text(
                text = "TOGGLE ARCHITECTURE COMPONENTS",
                color = Slate400,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ToggleRow("Public Subnet (DMZ)", hasPublicSubnet) { hasPublicSubnet = it }
                    ToggleRow("Private Subnet (App & DB)", hasPrivateSubnet) { hasPrivateSubnet = it }
                    ToggleRow("Internet Gateway", hasInternetGateway) { hasInternetGateway = it }
                    ToggleRow("NAT Gateway (Private Egress)", hasNatGateway) { hasNatGateway = it }
                    ToggleRow("Application Load Balancer", hasLoadBalancer) { hasLoadBalancer = it }
                }
            }
        }
    }
}

@Composable
private fun ComponentChip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = Slate800
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(12.dp))
            Text(label, fontSize = 11.sp, color = Slate200, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 13.sp, color = Slate200)
        Switch(
            checked = checked,
            onCheckedChange = onChecked,
            colors = SwitchDefaults.colors(checkedThumbColor = ElectricCyan, checkedTrackColor = CloudBlue)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IamPolicyLabScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var selectedRole by remember { mutableStateOf("Developer") }
    var selectedAction by remember { mutableStateOf("s3:GetObject") }
    var selectedResource by remember { mutableStateOf("arn:aws:s3:::app-storage/*") }

    val isAllowed = when (selectedRole) {
        "Developer" -> selectedAction in listOf("s3:GetObject", "s3:PutObject", "logs:GetLogEvents")
        "Read-Only Auditor" -> selectedAction in listOf("s3:GetObject", "logs:GetLogEvents")
        "Admin" -> true
        else -> false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("IAM Policy & Least-Privilege Editor", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("IAM IDENTITY & ACTION EVALUATOR", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

                    Text("Selected Role / Principal", fontSize = 12.sp, color = Slate400)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Developer", "Read-Only Auditor", "Admin").forEach { role ->
                            FilterChip(
                                selected = selectedRole == role,
                                onClick = { selectedRole = role },
                                label = { Text(role) }
                            )
                        }
                    }

                    Text("Target Action Requested", fontSize = 12.sp, color = Slate400)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("s3:GetObject", "s3:PutObject", "s3:DeleteBucket", "rds:DeleteDBCluster", "logs:GetLogEvents").forEach { action ->
                            FilterChip(
                                selected = selectedAction == action,
                                onClick = { selectedAction = action },
                                label = { Text(action, fontFamily = FontFamily.Monospace, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            }

            // Real-time policy evaluation card
            Card(
                colors = CardDefaults.cardColors(containerColor = if (isAllowed) DarkNavyCardElevated else Color(0xFF2D1515)),
                border = BorderStroke(1.dp, if (isAllowed) StatusHealthy else StatusError),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("AUTHORIZATION DECISION", color = Slate400, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        StatusBadge(if (isAllowed) "Healthy" else "Error")
                    }

                    Text(
                        text = if (isAllowed) "ACTION ALLOWED" else "EXPLICIT / DEFAULT DENY",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isAllowed) StatusHealthy else StatusError,
                        fontFamily = FontFamily.Monospace
                    )

                    Text(
                        text = if (isAllowed)
                            "Principal '$selectedRole' has permission statement granting '$selectedAction' on '$selectedResource'."
                        else
                            "Principal '$selectedRole' lacks permission '$selectedAction'. In accordance with least privilege, requests are denied by default unless explicitly granted.",
                        fontSize = 13.sp,
                        color = Slate200,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
