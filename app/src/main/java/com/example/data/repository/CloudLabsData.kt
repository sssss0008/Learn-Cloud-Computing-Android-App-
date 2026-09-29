package com.example.data.repository

import com.example.data.model.*

object CloudLabsData {
    val labs: List<CloudLab> = listOf(
        CloudLab(
            id = "lab-linux-terminal",
            title = "Simulated Linux Cloud Terminal",
            category = LabCategory.LINUX,
            difficulty = "Beginner",
            durationMin = 15,
            skills = listOf("Bash", "Filesystem", "Process Management", "grep", "curl"),
            description = "Interactive CLI simulator with real commands: ls, pwd, cd, cat, curl, grep, ps, systemctl, and help.",
            labRoute = "terminal_lab"
        ),
        CloudLab(
            id = "lab-subnet-calculator",
            title = "Subnet & CIDR Visualizer",
            category = LabCategory.NETWORKING,
            difficulty = "Beginner",
            durationMin = 10,
            skills = listOf("IPv4", "CIDR", "Subnet Mask", "Host Capacity", "Broadcasting"),
            description = "Calculate network address, broadcast, netmask, and usable host count dynamically for any IP and CIDR prefix.",
            labRoute = "subnet_calculator"
        ),
        CloudLab(
            id = "lab-network-visualizer",
            title = "Network Topology Visualizer",
            category = LabCategory.NETWORKING,
            difficulty = "Intermediate",
            durationMin = 15,
            skills = listOf("Routers", "Firewalls", "Load Balancers", "Reverse Proxy"),
            description = "Inspect end-to-end packet flow from User → DNS → CDN → Firewall → Load Balancer → Web Server → Database.",
            labRoute = "network_visualizer"
        ),
        CloudLab(
            id = "lab-vm-provisioning",
            title = "Cloud Virtual Machine Provisioner",
            category = LabCategory.COMPUTE,
            difficulty = "Beginner",
            durationMin = 12,
            skills = listOf("vCPU", "RAM", "Block Disk", "OS Images", "State Machine"),
            description = "Configure machine specs and witness the animated cloud provisioning lifecycle: Initializing → Provisioning → Running.",
            labRoute = "vm_provisioning"
        ),
        CloudLab(
            id = "lab-vpc-builder",
            title = "VPC & Subnet Network Architect",
            category = LabCategory.NETWORKING,
            difficulty = "Intermediate",
            durationMin = 20,
            skills = listOf("VPC", "Public Subnets", "Private Subnets", "NAT Gateway", "Route Tables"),
            description = "Design and validate an isolated Virtual Private Cloud with subnets, Internet Gateway, and route rules.",
            labRoute = "vpc_builder"
        ),
        CloudLab(
            id = "lab-dns-visualizer",
            title = "DNS 8-Step Resolution Simulator",
            category = LabCategory.NETWORKING,
            difficulty = "Beginner",
            durationMin = 10,
            skills = listOf("DNS", "Recursive Resolver", "Root Nameserver", "TLD", "Authoritative"),
            description = "Step through the query journey from browser cache to authoritative name server with animated round-trips.",
            labRoute = "dns_visualizer"
        ),
        CloudLab(
            id = "lab-iam-policy",
            title = "IAM Policy & Least-Privilege Editor",
            category = LabCategory.SECURITY,
            difficulty = "Intermediate",
            durationMin = 15,
            skills = listOf("JSON Policies", "Allow/Deny", "Wildcard Actions", "Resource ARNs"),
            description = "Test real-time authorization queries against role policies to ensure principle of least privilege.",
            labRoute = "iam_policy"
        ),
        CloudLab(
            id = "lab-docker-container",
            title = "Docker Container Lifecycle Lab",
            category = LabCategory.CONTAINERS,
            difficulty = "Intermediate",
            durationMin = 15,
            skills = listOf("docker build", "docker run", "Port Forwarding", "Volumes", "Status"),
            description = "Simulate building images, spawning isolated containers, and monitoring port forwarding and memory.",
            labRoute = "docker_lab"
        ),
        CloudLab(
            id = "lab-kubernetes",
            title = "Kubernetes Cluster & Kubectl Simulator",
            category = LabCategory.KUBERNETES,
            difficulty = "Advanced",
            durationMin = 25,
            skills = listOf("kubectl", "Pods", "Deployments", "ReplicaSet", "ClusterIP"),
            description = "Visualize cluster control plane, schedule Pods across nodes, and execute kubectl scale / get pods.",
            labRoute = "kubernetes_lab"
        ),
        CloudLab(
            id = "lab-api-explorer",
            title = "REST API & JSON Explorer",
            category = LabCategory.DEVOPS,
            difficulty = "Beginner",
            durationMin = 12,
            skills = listOf("REST", "HTTP Verbs", "Headers", "JSON Formatting", "Status Codes"),
            description = "Execute simulated GET, POST, PUT, DELETE requests against cloud endpoints with instant structured JSON output.",
            labRoute = "api_explorer"
        ),
        CloudLab(
            id = "lab-cicd-pipeline",
            title = "CI/CD Pipeline Builder",
            category = LabCategory.DEVOPS,
            difficulty = "Intermediate",
            durationMin = 15,
            skills = listOf("CI/CD", "Lint", "Unit Tests", "Vulnerability Scan", "Canary Deploy"),
            description = "Assemble continuous delivery stages: Lint → Test → Build Image → Security Scan → Deploy → Health Check.",
            labRoute = "cicd_pipeline"
        ),
        CloudLab(
            id = "lab-iac-playground",
            title = "Infrastructure as Code (IaC) Playground",
            category = LabCategory.IAC,
            difficulty = "Intermediate",
            durationMin = 18,
            skills = listOf("Declarative HCL", "Variables", "Outputs", "Topology Preview"),
            description = "Write declarative cloud resources and see live interactive topology graphs rendered from the code in real-time.",
            labRoute = "iac_playground"
        ),
        CloudLab(
            id = "lab-monitoring-dashboard",
            title = "Real-Time Telemetry & Observability Dashboard",
            category = LabCategory.OBSERVABILITY,
            difficulty = "Intermediate",
            durationMin = 15,
            skills = listOf("Golden Signals", "CPU/RAM Gauges", "Request Rate", "Latency Percentiles", "Log Stream"),
            description = "Dynamic live monitoring gauges with real-time updating synthetic metrics and interactive log stream inspector.",
            labRoute = "monitoring_dashboard"
        ),
        CloudLab(
            id = "lab-incident-simulator",
            title = "Cloud Incident & Latency Troubleshooting",
            category = LabCategory.OBSERVABILITY,
            difficulty = "Expert Challenge",
            durationMin = 20,
            skills = listOf("Root Cause Analysis", "Log Forensics", "Network Triage", "Mitigation"),
            description = "Diagnose an active production incident: high latency and 500 errors. Inspect metrics, identify root cause, and deploy fix.",
            labRoute = "incident_simulator"
        ),
        CloudLab(
            id = "lab-cost-calculator",
            title = "Cloud Cost & FinOps Estimator",
            category = LabCategory.COST,
            difficulty = "Beginner",
            durationMin = 10,
            skills = listOf("FinOps", "vCPU Pricing", "Object Storage Classes", "Egress Bandwidth"),
            description = "Interactive sliders adjusting compute instances, database storage, and network egress to estimate monthly spend.",
            labRoute = "cost_calculator"
        ),
        CloudLab(
            id = "lab-architecture-designer",
            title = "Interactive Architecture Canvas & AI Review",
            category = LabCategory.ARCHITECTURE,
            difficulty = "Advanced",
            durationMin = 30,
            skills = listOf("System Design", "Load Balancing", "High Availability", "Security Architecture"),
            description = "Drag components (Users, CDN, ALB, Microservices, Redis, Multi-AZ DB) onto canvas and get immediate automated architectural feedback.",
            labRoute = "architecture_designer"
        )
    )
}
