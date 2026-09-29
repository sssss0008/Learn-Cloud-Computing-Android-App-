package com.example.ui.screens.labs

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
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
import com.example.ui.components.CodeSnippetCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DockerLabScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var isBuilt by remember { mutableStateOf(true) }
    var isRunning by remember { mutableStateOf(true) }
    var memoryUsageMb by remember { mutableIntStateOf(42) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Docker Container Lifecycle Lab", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                    Text("CONTAINER ORCHESTRATION CONTROLS", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                isBuilt = true
                                isRunning = true
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("docker run", fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                        }
                        OutlinedButton(
                            onClick = { isRunning = false },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("docker stop", fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                        }
                    }
                }
            }

            // Running Container Representation
            Card(
                colors = CardDefaults.cardColors(containerColor = if (isRunning) DarkNavyCardElevated else DarkNavyCard),
                border = BorderStroke(1.dp, if (isRunning) ElectricCyan else CyberBorder),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("CONTAINER: web-api-production", fontWeight = FontWeight.Bold, color = Slate100, fontSize = 14.sp)
                        StatusBadge(if (isRunning) "Running" else "Offline")
                    }

                    Text("Image: alpine-node:20-distroless (34.2 MB)", fontSize = 12.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                    Text("Port Mapping: 0.0.0.0:8080 -> 80/tcp", fontSize = 12.sp, color = CloudBlueLight, fontFamily = FontFamily.Monospace)
                    Text("Memory RSS: ${if (isRunning) "$memoryUsageMb MB / 256 MB Limit" else "0 MB"}", fontSize = 12.sp, color = Slate300, fontFamily = FontFamily.Monospace)

                    HorizontalDivider(color = CyberBorder)

                    Text(
                        text = if (isRunning)
                            "Container process isolated with Linux PID & NET namespaces. CPU quota enforced by cgroups v2."
                        else
                            "Container stopped. Root filesystem preserved in read-only overlay layer.",
                        fontSize = 12.sp,
                        color = Slate400
                    )
                }
            }

            CodeSnippetCard(
                title = "DOCKERFILE IN REPOSITORY",
                code = "FROM node:20-alpine AS build\nWORKDIR /app\nCOPY package*.json ./\nRUN npm ci --only=production\n\nFROM gcr.io/distroless/nodejs20-debian12\nWORKDIR /app\nCOPY --from=build /app/node_modules ./node_modules\nCOPY . .\nUSER nonroot:nonroot\nEXPOSE 8080\nCMD [\"server.js\"]"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KubernetesLabScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var replicaCount by remember { mutableIntStateOf(3) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kubernetes Cluster & Kubectl Simulator", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                    Text("CLUSTER WORKLOAD CONTROLS", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Deployment Replicas: $replicaCount Pods", fontWeight = FontWeight.Bold, color = Slate100)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            IconButton(onClick = { if (replicaCount > 1) replicaCount-- }) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = ElectricCyan)
                            }
                            IconButton(onClick = { if (replicaCount < 6) replicaCount++ }) {
                                Icon(Icons.Default.Add, contentDescription = "Increase", tint = ElectricCyan)
                            }
                        }
                    }
                }
            }

            Text("ACTIVE SCHEDULED PODS ACROSS NODES", color = Slate400, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (i in 1..replicaCount) {
                    val nodeName = if (i % 2 == 1) "worker-node-us-east-1a" else "worker-node-us-east-1b"
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkNavyCard),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, CyberBorder)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("pod/web-deploy-74b59-$i", fontWeight = FontWeight.Bold, color = Slate100, fontSize = 13.sp, fontFamily = FontFamily.Monospace)
                                Text("Node: $nodeName", fontSize = 11.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                            }
                            StatusBadge("Running")
                        }
                    }
                }
            }

            CodeSnippetCard(
                title = "KUBECTL OUTPUT: kubectl get pods -o wide",
                code = buildString {
                    append("NAME                     READY   STATUS    RESTARTS   AGE\n")
                    for (i in 1..replicaCount) {
                        append("web-deploy-74b59-$i      1/1     Running   0          5m\n")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiExplorerScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var selectedMethod by remember { mutableStateOf("GET") }
    var endpoint by remember { mutableStateOf("/api/v1/cloud-clusters") }
    var responseBody by remember {
        mutableStateOf(
            """{
  "status": "success",
  "data": {
    "cluster_id": "cls-prod-9941",
    "region": "us-east-1",
    "active_nodes": 4,
    "total_vcpus": 16,
    "healthy": true
  }
}"""
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("REST API & JSON Explorer", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
                    Text("HTTP REQUEST DISPATCHER", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("GET", "POST", "PUT", "DELETE").forEach { verb ->
                            FilterChip(
                                selected = selectedMethod == verb,
                                onClick = { selectedMethod = verb },
                                label = { Text(verb, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = endpoint,
                        onValueChange = { endpoint = it },
                        label = { Text("Request Path") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CloudBlueLight,
                            unfocusedBorderColor = CyberBorder
                        )
                    )

                    Button(
                        onClick = {
                            responseBody = if (selectedMethod == "GET") {
                                """{
  "status": "success",
  "data": {
    "cluster_id": "cls-prod-9941",
    "region": "us-east-1",
    "active_nodes": 4,
    "healthy": true
  }
}"""
                            } else {
                                """{
  "status": "created",
  "message": "Resource created successfully",
  "id": "res-${System.currentTimeMillis() % 10000}"
}"""
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CloudBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Send Request", fontWeight = FontWeight.Bold)
                    }
                }
            }

            // HTTP Response Card
            Card(
                colors = CardDefaults.cardColors(containerColor = TerminalBackground),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("HTTP/1.1 200 OK (38 ms)", color = StatusHealthy, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                        Text("Content-Type: application/json", color = Slate400, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
                    }
                    HorizontalDivider(color = Slate800)
                    Text(
                        text = responseBody,
                        color = TerminalGreen,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CicdPipelineScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var isRunning by remember { mutableStateOf(false) }
    var currentStageIndex by remember { mutableIntStateOf(5) } // all complete by default
    val stages = listOf(
        "Source (Git Pull)" to "SHA: c8f2b1a - Awiskar Acharya",
        "Lint & Syntax" to "Static analysis passed with 0 warnings",
        "Unit & Integration Tests" to "142 tests passed in 18s",
        "Container Image Build" to "Docker layer cache hit 92%",
        "Vulnerability Scan (Trivy)" to "0 Critical, 0 High vulnerabilities",
        "Canary Deployment (10%)" to "Traffic shifting verified healthy"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CI/CD Pipeline Builder", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "PIPELINE WORKFLOW EXECUTION",
                color = ElectricCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            stages.forEachIndexed { index, (name, detail) ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (index <= currentStageIndex) StatusHealthy.copy(alpha = 0.5f) else CyberBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (index <= currentStageIndex) StatusHealthy else Slate700),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                        Column {
                            Text(name, fontWeight = FontWeight.Bold, color = Slate100, fontSize = 14.sp)
                            Text(detail, fontSize = 11.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IacPlaygroundScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Infrastructure as Code (IaC) Playground", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
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
            CodeSnippetCard(
                title = "main.tf (DECLARATIVE CLOUD INFRASTRUCTURE)",
                code = """resource "cloud_vpc" "production" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true
  tags = {
    Environment = "production"
    ManagedBy   = "Terraform"
  }
}

resource "cloud_subnet" "public_a" {
  vpc_id                  = cloud_vpc.production.id
  cidr_block              = "10.0.1.0/24"
  availability_zone       = "us-east-1a"
  map_public_ip_on_launch = true
}

resource "cloud_load_balancer" "alb" {
  name               = "app-alb"
  internal           = false
  load_balancer_type = "application"
  subnets            = [cloud_subnet.public_a.id]
}"""
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("LIVE TOPOLOGY PREVIEW FROM CODE", color = ElectricCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text("Plan: 3 to add, 0 to change, 0 to destroy.", color = StatusHealthy, fontFamily = FontFamily.Monospace, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("The declarative engine parses dependencies automatically: cloud_load_balancer references cloud_subnet, which in turn references cloud_vpc.", fontSize = 12.sp, color = Slate300)
                }
            }
        }
    }
}
