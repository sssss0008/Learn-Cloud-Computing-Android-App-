package com.example.ui.screens.aitutor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.ui.theme.*

data class ChatMessage(val isUser: Boolean, val text: String, val technicalNotes: String? = null)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiTutorScreen(
    onBack: () -> Unit
) {
    var input by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                isUser = false,
                text = "Hello! I am your AI Cloud Tutor. Ask me any question about cloud infrastructure, Kubernetes, networking, IAM policies, or system architecture.",
                technicalNotes = "Try tapping one of the quick suggestions below or type your own question."
            )
        )
    }

    val suggestions = listOf(
        "What is a VPC?",
        "Explain Kubernetes simply",
        "Why use an Application Load Balancer?",
        "Explain CAP Theorem in cloud databases"
    )

    fun handleSend(query: String) {
        val q = query.trim()
        if (q.isEmpty()) return

        messages.add(ChatMessage(isUser = true, text = q))
        input = ""

        val response = when {
            q.contains("VPC", ignoreCase = true) -> ChatMessage(
                isUser = false,
                text = "A Virtual Private Cloud (VPC) is your private, isolated slice of network inside the public cloud. Think of it like your company's private office building within a massive commercial city.",
                technicalNotes = "• Technical Layer: Software-Defined Network (SDN) overlay using VXLAN/Geneve encapsulation.\n• Core Components: CIDR Block, Public/Private Subnets, Route Tables, Internet Gateway (IGW), and NAT Gateways."
            )
            q.contains("Kubernetes", ignoreCase = true) -> ChatMessage(
                isUser = false,
                text = "Kubernetes is an automated container orchestra director. Instead of manually logging into servers to restart crashed apps or spin up new instances, Kubernetes monitors desired state and balances containers across nodes automatically.",
                technicalNotes = "• Control Plane: API Server, etcd (Raft state store), Scheduler, Controller Manager.\n• Worker Node: Kubelet agent, CRI container runtime, and kube-proxy networking."
            )
            q.contains("Load Balancer", ignoreCase = true) -> ChatMessage(
                isUser = false,
                text = "An Application Load Balancer distributes user HTTP/HTTPS requests across multiple instances so no single server gets overwhelmed, providing high availability and seamless zero-downtime upgrades.",
                technicalNotes = "• Layer 7 OSI: Inspects HTTP headers, path-based routing (e.g. /api to backend, /static to S3), and terminates TLS SSL certificates."
            )
            q.contains("CAP", ignoreCase = true) -> ChatMessage(
                isUser = false,
                text = "The CAP theorem states that a distributed data system can only guarantee two out of three: Consistency (every read gets latest write), Availability (every request receives a response), and Partition Tolerance (system continues functioning despite network packet loss).",
                technicalNotes = "• In cloud networks, Network Partitions (P) are inevitable.\n• Therefore, distributed databases must choose between CP (e.g., MongoDB, Spanner) or AP (e.g., DynamoDB, Cassandra with eventual consistency)."
            )
            else -> ChatMessage(
                isUser = false,
                text = "In cloud computing, that concept centers on elasticity, decoupling, and high availability across multiple availability zones.",
                technicalNotes = "Tip: Review Course 4 (Cloud Fundamentals) or Course 22 (Cloud Architecture) for complete diagrams and production labs."
            )
        }

        messages.add(response)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(20.dp))
                        Text("AI Cloud Engineering Tutor", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                },
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
        ) {
            // Suggestion chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                suggestions.forEach { sug ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { handleSend(sug) }
                            .border(1.dp, CyberBorder, RoundedCornerShape(8.dp)),
                        color = DarkNavyCardElevated
                    ) {
                        Text(
                            text = sug,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            fontSize = 11.sp,
                            color = CloudBlueLight
                        )
                    }
                }
            }

            // Message list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) { msg ->
                    if (msg.isUser) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = CloudBlue,
                                modifier = Modifier.widthIn(max = 280.dp)
                            ) {
                                Text(
                                    text = msg.text,
                                    modifier = Modifier.padding(12.dp),
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            }
                        }
                    } else {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated),
                                border = BorderStroke(1.dp, CyberBorder),
                                modifier = Modifier.widthIn(max = 320.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(
                                        text = msg.text,
                                        fontSize = 13.sp,
                                        color = Slate100,
                                        lineHeight = 18.sp
                                    )
                                    if (msg.technicalNotes != null) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = TerminalBackground,
                                            border = BorderStroke(1.dp, Slate800)
                                        ) {
                                            Text(
                                                text = msg.technicalNotes,
                                                modifier = Modifier.padding(10.dp),
                                                fontSize = 11.sp,
                                                color = TerminalGreen,
                                                fontFamily = FontFamily.Monospace,
                                                lineHeight = 16.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Input Row
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
                    placeholder = { Text("Ask cloud tutor...", fontSize = 13.sp) },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricCyan,
                        unfocusedBorderColor = CyberBorder
                    )
                )

                IconButton(
                    onClick = { handleSend(input) },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CloudBlue)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
                }
            }
        }
    }
}
