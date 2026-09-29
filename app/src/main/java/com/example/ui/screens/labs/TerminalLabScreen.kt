package com.example.ui.screens.labs

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlinx.coroutines.launch

data class TerminalLine(val text: String, val isCommand: Boolean = false, val isError: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalLabScreen(
    onBack: () -> Unit,
    onCompleteLab: () -> Unit
) {
    var input by remember { mutableStateOf("") }
    var currentDir by remember { mutableStateOf("/home/cloud-engineer") }
    val outputHistory = remember {
        mutableStateListOf(
            TerminalLine("Linux 6.8.0-cloud-amd64 #1 SMP PREEMPT x86_64"),
            TerminalLine("Welcome to Awiskar Acharya Cloud Engineering Terminal"),
            TerminalLine("Type 'help' for available commands or try 'ls', 'pwd', 'cat app.env', 'curl'"),
            TerminalLine("----------------------------------------------------------------------")
        )
    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val quickCommands = listOf("pwd", "ls -lah", "cat app.env", "curl localhost:8080/healthz", "ps aux", "systemctl status nginx", "uname -a", "clear")

    fun executeCommand(rawCmd: String) {
        val cmd = rawCmd.trim()
        if (cmd.isEmpty()) return

        outputHistory.add(TerminalLine("$currentDir $ $cmd", isCommand = true))

        val parts = cmd.split(" ").filter { it.isNotBlank() }
        val root = parts.firstOrNull()?.lowercase() ?: ""
        val args = parts.drop(1)

        when (root) {
            "clear" -> {
                outputHistory.clear()
            }
            "pwd" -> {
                outputHistory.add(TerminalLine(currentDir))
            }
            "ls" -> {
                if (currentDir.endsWith("projects")) {
                    outputHistory.add(TerminalLine("drwxr-xr-x 2 root root 4096 Sep 28 09:30 website"))
                    outputHistory.add(TerminalLine("drwxr-xr-x 2 root root 4096 Sep 28 09:30 api-service"))
                    outputHistory.add(TerminalLine("-rw-r--r-- 1 root root  820 Sep 28 09:30 docker-compose.yml"))
                } else {
                    outputHistory.add(TerminalLine("drwxr-xr-x 3 cloud cloud 4096 Sep 28 09:30 projects/"))
                    outputHistory.add(TerminalLine("drwxr-xr-x 2 cloud cloud 4096 Sep 28 09:30 configs/"))
                    outputHistory.add(TerminalLine("-rw-r--r-- 1 cloud cloud  245 Sep 28 09:30 app.env"))
                    outputHistory.add(TerminalLine("-rwxr-xr-x 1 cloud cloud  512 Sep 28 09:30 deploy.sh"))
                }
            }
            "cd" -> {
                val target = args.firstOrNull()
                when (target) {
                    null, "~" -> currentDir = "/home/cloud-engineer"
                    ".." -> currentDir = if (currentDir.contains("/projects")) "/home/cloud-engineer" else "/"
                    "projects" -> currentDir = "/home/cloud-engineer/projects"
                    "configs" -> currentDir = "/home/cloud-engineer/configs"
                    "/" -> currentDir = "/"
                    else -> outputHistory.add(TerminalLine("bash: cd: $target: No such file or directory", isError = true))
                }
            }
            "cat" -> {
                val file = args.firstOrNull()
                when (file) {
                    "app.env" -> {
                        outputHistory.add(TerminalLine("PORT=8080\nENVIRONMENT=production\nDATABASE_URL=postgres://db.internal:5432/app\nCACHE_ENABLED=true\nLOG_LEVEL=info"))
                    }
                    "deploy.sh" -> {
                        outputHistory.add(TerminalLine("#!/bin/bash\necho 'Deploying container to cloud...'\ndocker build -t app:v1 .\ndocker push registry.cloud.io/app:v1\necho 'Deployment complete.'"))
                    }
                    "docker-compose.yml" -> {
                        outputHistory.add(TerminalLine("version: '3.8'\nservices:\n  web:\n    image: nginx:alpine\n    ports:\n      - '80:80'\n  api:\n    image: node:20-alpine\n    ports:\n      - '8080:8080'"))
                    }
                    null -> outputHistory.add(TerminalLine("cat: missing file argument", isError = true))
                    else -> outputHistory.add(TerminalLine("cat: $file: No such file or directory", isError = true))
                }
            }
            "curl" -> {
                outputHistory.add(TerminalLine("HTTP/1.1 200 OK\nContent-Type: application/json\nX-Cloud-Region: us-east-1a\n\n{\"status\":\"healthy\",\"uptime_seconds\":86400,\"active_workers\":4}"))
            }
            "ps" -> {
                outputHistory.add(TerminalLine("PID   USER     TIME  COMMAND\n  1   root     0:02  /sbin/init\n 42   systemd  0:00  /lib/systemd/systemd-journald\n180   cloud    0:15  node /app/server.js\n240   root     0:01  nginx: master process /usr/sbin/nginx"))
            }
            "systemctl" -> {
                outputHistory.add(TerminalLine("● nginx.service - A high performance web server\n     Loaded: loaded (/lib/systemd/systemd/nginx.service; enabled)\n     Active: active (running) since Sun 2026-09-28 09:00:00 UTC\n    Process: 240 ExecStart=/usr/sbin/nginx (code=exited, status=0/SUCCESS)"))
            }
            "uname" -> {
                outputHistory.add(TerminalLine("Linux awiskar-cloud-node-01 6.8.0-cloud-amd64 x86_64 GNU/Linux"))
            }
            "whoami" -> {
                outputHistory.add(TerminalLine("cloud-engineer"))
            }
            "help" -> {
                outputHistory.add(TerminalLine("Available cloud commands:\n• pwd, ls, cd [dir], cat [file]\n• curl [url], ps aux, systemctl status [svc]\n• whoami, uname -a, clear"))
            }
            else -> {
                outputHistory.add(TerminalLine("bash: $root: command not found. Type 'help' for command list.", isError = true))
            }
        }

        input = ""
        coroutineScope.launch {
            if (outputHistory.isNotEmpty()) {
                listState.animateScrollToItem(outputHistory.size - 1)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Linux Cloud Terminal Lab", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Simulated Cloud Shell Environment", fontSize = 11.sp, color = ElectricCyan, fontFamily = FontFamily.Monospace)
                    }
                },
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
                        Text("Complete Lab", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
        ) {
            // Quick command suggestion chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                quickCommands.forEach { cmd ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { executeCommand(cmd) }
                            .border(1.dp, CyberBorder, RoundedCornerShape(6.dp)),
                        color = DarkNavyCardElevated
                    ) {
                        Text(
                            text = cmd,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 11.sp,
                            color = CloudBlueLight,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            // Terminal Screen
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Slate800, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = TerminalBackground)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Terminal title bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0F172A))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(StatusError))
                            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(StatusWarning))
                            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(StatusHealthy))
                        }
                        Text(
                            text = "bash - 80x24 (cloud-vm-01)",
                            color = Slate400,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        IconButton(
                            onClick = { outputHistory.clear() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.DeleteOutline, contentDescription = "Clear", tint = Slate400, modifier = Modifier.size(16.dp))
                        }
                    }

                    // Terminal Output Console
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(outputHistory) { line ->
                            Text(
                                text = line.text,
                                color = when {
                                    line.isError -> TerminalRed
                                    line.isCommand -> TerminalGreen
                                    else -> TerminalText
                                },
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Command input row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    placeholder = { Text("Type command (e.g. ls, cat app.env)", fontFamily = FontFamily.Monospace, fontSize = 12.sp) },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("terminal_input"),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricCyan,
                        unfocusedBorderColor = CyberBorder
                    ),
                    leadingIcon = {
                        Text("$ ", color = TerminalGreen, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                    }
                )

                IconButton(
                    onClick = { executeCommand(input) },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(CloudBlue)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
                }
            }
        }
    }
}
