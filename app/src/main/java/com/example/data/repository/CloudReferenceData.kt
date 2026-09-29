package com.example.data.repository

import com.example.data.model.Achievement
import com.example.data.model.CheatSheetEntry
import com.example.data.model.GlossaryTerm
import com.example.data.model.MockExamQuestion

object CloudReferenceData {
    val glossaryTerms: List<GlossaryTerm> = listOf(
        GlossaryTerm(
            id = "g-vpc",
            term = "Virtual Private Cloud (VPC)",
            category = "Networking",
            simpleExplanation = "Your private, isolated slice of network inside a cloud provider where only your resources can talk to each other.",
            technicalExplanation = "A logically isolated Software-Defined Network (SDN) constructed on top of the provider's physical network, parameterized by an IPv4/IPv6 CIDR block.",
            example = "Creating a 10.0.0.0/16 VPC containing public web subnets and private database subnets.",
            relatedConcepts = listOf("Subnets", "Route Tables", "Internet Gateway", "NAT")
        ),
        GlossaryTerm(
            id = "g-az",
            term = "Availability Zone (AZ)",
            category = "Infrastructure",
            simpleExplanation = "One or more discrete physical data centers with redundant power, networking, and cooling in a specific geographic region.",
            technicalExplanation = "Isolated fault domains within a cloud region linked by low-latency private fiber (<1-2ms round-trip).",
            example = "Deploying two web servers in us-east-1a and us-east-1b so if one building loses power, the other stays online.",
            relatedConcepts = listOf("Region", "High Availability", "Fault Tolerance")
        ),
        GlossaryTerm(
            id = "g-iam",
            term = "Identity & Access Management (IAM)",
            category = "Security",
            simpleExplanation = "The security guard of the cloud deciding who you are (Authentication) and what you are allowed to touch (Authorization).",
            technicalExplanation = "Fine-grained policy enforcement engine evaluating JSON permissions against identities, resources, and contextual attributes.",
            example = "Assigning a role to a virtual machine so it can write logs to cloud storage without hardcoded credentials.",
            relatedConcepts = listOf("Least Privilege", "Role", "Policy", "STS Token")
        ),
        GlossaryTerm(
            id = "g-lb",
            term = "Load Balancer",
            category = "Networking",
            simpleExplanation = "A traffic cop that accepts incoming user requests and distributes them evenly across multiple healthy servers.",
            technicalExplanation = "Layer 4 (TCP/UDP) or Layer 7 (HTTP/HTTPS) reverse proxy maintaining health check status and routing requests based on algorithms (round robin, least connections).",
            example = "Distributing 10,000 requests per minute across 5 backend application servers.",
            relatedConcepts = listOf("Health Check", "Target Group", "SSL Termination")
        ),
        GlossaryTerm(
            id = "g-cdn",
            term = "Content Delivery Network (CDN)",
            category = "Networking",
            simpleExplanation = "A worldwide network of caching servers that delivers files to users from the closest geographic location.",
            technicalExplanation = "Globally distributed edge Points of Presence (PoPs) using Anycast IP routing to cache static and streaming content close to the requester.",
            example = "A user in Tokyo loads image assets from a Tokyo edge server in 5ms rather than waiting 200ms to fetch from a Virginia server.",
            relatedConcepts = listOf("Edge Cache", "DNS", "Latency")
        ),
        GlossaryTerm(
            id = "g-pod",
            term = "Kubernetes Pod",
            category = "Containers",
            simpleExplanation = "The smallest deployable unit in Kubernetes, wrapping one or more containers that share network and storage.",
            technicalExplanation = "An atomic scheduling abstraction sharing Linux cgroups, namespaces, and a single loopback network interface (localhost).",
            example = "Running an application container alongside a sidecar logging proxy container in the same Pod.",
            relatedConcepts = listOf("Kubernetes", "Container", "Deployment", "Service")
        ),
        GlossaryTerm(
            id = "g-serverless",
            term = "Serverless (FaaS)",
            category = "Compute",
            simpleExplanation = "A cloud execution model where you upload your code and the cloud provider provisions, scales, and shuts down servers automatically.",
            technicalExplanation = "Ephemeral microVM or container sandboxes spawned on-demand in response to events (HTTP request, queue message), billed per millisecond.",
            example = "A function that executes only when a customer uploads a profile photo to create a thumbnail, costing $0 when nobody uploads photos.",
            relatedConcepts = listOf("Cold Start", "Event-Driven", "API Gateway")
        ),
        GlossaryTerm(
            id = "g-iac",
            term = "Infrastructure as Code (IaC)",
            category = "DevOps",
            simpleExplanation = "Writing computer code files that describe the cloud servers, networks, and databases you want built automatically.",
            technicalExplanation = "Declarative or imperative machine-readable configuration files evaluated by an engine to provision and manage cloud resources idempotently.",
            example = "Using a Terraform .tf file to deploy 3 virtual machines, a load balancer, and a firewall in 2 minutes.",
            relatedConcepts = listOf("Terraform", "Configuration Drift", "Declarative")
        ),
        GlossaryTerm(
            id = "g-rpo-rto",
            term = "RPO & RTO",
            category = "Architecture",
            simpleExplanation = "RPO = How much data you can afford to lose (in time). RTO = How long you can afford to be down before restoring service.",
            technicalExplanation = "Recovery Point Objective: maximum acceptable age of data files that must be recovered. Recovery Time Objective: maximum acceptable duration between outage and restoration.",
            example = "An RPO of 1 hour means hourly database backups are sufficient; an RTO of 15 minutes means automated failover must be ready within 15 minutes.",
            relatedConcepts = listOf("Disaster Recovery", "Backup", "Failover")
        ),
        GlossaryTerm(
            id = "g-observability",
            term = "Observability",
            category = "Monitoring",
            simpleExplanation = "The ability to understand what is happening inside a complex system based on the external signals it emits.",
            technicalExplanation = "The synthesis of structured logs, numeric metrics, and distributed trace spans to debug unforeseen failure modes.",
            example = "Tracing a slow checkout request through the API gateway, payment microservice, and database query to identify a 3-second database lock.",
            relatedConcepts = listOf("Metrics", "Logs", "Traces", "Golden Signals")
        )
    )

    val cheatSheetEntries: List<CheatSheetEntry> = listOf(
        CheatSheetEntry(
            id = "cs-1",
            category = "Linux",
            command = "ls -lah",
            purpose = "List files with details and human sizes",
            example = "ls -lah /var/log",
            explanation = "Shows hidden files, permissions, owners, file size in KB/MB, and modification dates."
        ),
        CheatSheetEntry(
            id = "cs-2",
            category = "Linux",
            command = "grep -rn \"error\" /var/log/",
            purpose = "Search recursively for a text pattern in files",
            example = "grep -rn \"Connection refused\" ./logs",
            explanation = "Recursively searches subdirectories with line numbers and prints matching lines."
        ),
        CheatSheetEntry(
            id = "cs-3",
            category = "Linux",
            command = "systemctl status <service>",
            purpose = "Inspect running status of system service",
            example = "systemctl status nginx",
            explanation = "Checks if service is active (running), its PID, memory footprint, and recent journal log lines."
        ),
        CheatSheetEntry(
            id = "cs-4",
            category = "Docker",
            command = "docker build -t app:v1 .",
            purpose = "Build container image from Dockerfile",
            example = "docker build -t webapp:latest .",
            explanation = "Executes Dockerfile directives in current directory and tags output image as app:v1."
        ),
        CheatSheetEntry(
            id = "cs-5",
            category = "Docker",
            command = "docker run -d -p 8080:80 --name web app:v1",
            purpose = "Run container in background with port mapping",
            example = "docker run -d -p 80:80 --restart always nginx",
            explanation = "Detaches (-d), maps host port 8080 to container port 80, and names the running container."
        ),
        CheatSheetEntry(
            id = "cs-6",
            category = "Kubernetes",
            command = "kubectl get pods -A -o wide",
            purpose = "List all Pods in all namespaces with node and IP",
            example = "kubectl get pods -n production",
            explanation = "Shows Pod status (Running, CrashLoopBackOff), restart counts, assigned IP, and host worker node."
        ),
        CheatSheetEntry(
            id = "cs-7",
            category = "Kubernetes",
            command = "kubectl logs -f deployment/<name>",
            purpose = "Stream live logs from all Pods in a deployment",
            example = "kubectl logs -f deployment/api-server",
            explanation = "Follows standard out and standard error streams in real time across the deployment."
        ),
        CheatSheetEntry(
            id = "cs-8",
            category = "Git",
            command = "git checkout -b feature/auth",
            purpose = "Create and switch to new branch",
            example = "git checkout -b fix/memory-leak",
            explanation = "Branches your commit history to develop new changes in isolation."
        ),
        CheatSheetEntry(
            id = "cs-9",
            category = "Networking",
            command = "curl -Iv https://example.com",
            purpose = "Inspect HTTP headers and TLS handshake",
            example = "curl -Iv https://api.cloud.io/healthz",
            explanation = "Shows DNS resolution IP, SSL certificate details, and response headers (Server, Status, Cache)."
        ),
        CheatSheetEntry(
            id = "cs-10",
            category = "Networking",
            command = "dig +short example.com A",
            purpose = "Query DNS A record for a domain",
            example = "dig +trace google.com",
            explanation = "Directly queries nameservers and prints resolved IPv4 addresses without OS resolver cache interference."
        )
    )

    val mockExamQuestions: List<MockExamQuestion> = listOf(
        MockExamQuestion(
            id = "me-1",
            domain = "Cloud Architecture",
            question = "A company requires a highly available relational database architecture that can automatically withstand the sudden failure of an entire datacenter facility. Which architecture best satisfies this requirement?",
            options = listOf(
                "Deploy a single database instance with daily automated backups stored in object storage",
                "Deploy a primary database in one Availability Zone with a synchronous standby replica in a second Availability Zone",
                "Deploy multiple databases on the same host using Docker containers",
                "Increase the CPU and RAM of the database instance to the maximum available tier"
            ),
            correctIndex = 1,
            explanation = "Multi-AZ synchronous replication provides seamless automatic failover to the standby replica in another physical datacenter (Availability Zone) without data loss."
        ),
        MockExamQuestion(
            id = "me-2",
            domain = "Security & IAM",
            question = "An application running on an EC2 instance needs to read and write files to a private cloud storage bucket. According to security best practices, how should authentication be configured?",
            options = listOf(
                "Hardcode the AWS IAM Access Key and Secret Key into the application's source code",
                "Embed the credentials in an environment file committed to the public Git repository",
                "Attach an IAM Role with a least-privilege policy to the EC2 instance",
                "Make the storage bucket public so authentication is not needed"
            ),
            correctIndex = 2,
            explanation = "Attaching an IAM Role allows the instance metadata service to supply temporary, auto-rotating STS credentials without hardcoded secrets."
        ),
        MockExamQuestion(
            id = "me-3",
            domain = "Networking",
            question = "Instances in a private subnet need to download operating system security patches from the internet, but must not accept any incoming connections from the internet. What component should be configured?",
            options = listOf(
                "Internet Gateway in the private subnet",
                "NAT Gateway in the public subnet with route from private subnet",
                "Assign public IP addresses to all private instances",
                "Disable all security group rules"
            ),
            correctIndex = 1,
            explanation = "A NAT (Network Address Translation) Gateway placed in a public subnet allows outbound internet access for private instances while dropping all unsolicited inbound connections."
        ),
        MockExamQuestion(
            id = "me-4",
            domain = "DevOps & Containers",
            question = "Which container technology mechanism is primarily responsible for metering and limiting CPU and memory resources allocated to a container?",
            options = listOf(
                "Linux Namespaces",
                "Control Groups (cgroups)",
                "OverlayFS",
                "SELinux"
            ),
            correctIndex = 1,
            explanation = "Control Groups (cgroups) organize processes into hierarchical groups to enforce resource quotas on CPU, memory, network, and disk I/O."
        ),
        MockExamQuestion(
            id = "me-5",
            domain = "Observability & SRE",
            question = "During a sudden traffic surge, an API's error rate spikes and response times exceed 5 seconds. Which telemetry component will best show exactly which microservice or database query is causing the bottleneck?",
            options = listOf(
                "Ping responses",
                "Distributed Tracing (e.g. OpenTelemetry)",
                "System uptime logs",
                "DNS TTL records"
            ),
            correctIndex = 1,
            explanation = "Distributed tracing tracks a request's end-to-end journey across microservices with detailed duration spans, pinpointing the specific slow function or database query."
        )
    )

    val achievements: List<Achievement> = listOf(
        Achievement(
            id = "ach-first-lesson",
            title = "First Cloud Lesson",
            description = "Completed your first cloud engineering lesson.",
            icon = "School",
            requiredType = "LESSONS",
            requiredCount = 1
        ),
        Achievement(
            id = "ach-linux-explorer",
            title = "Linux Explorer",
            description = "Successfully navigated commands in the Linux Cloud Terminal.",
            icon = "Terminal",
            requiredType = "LABS",
            requiredCount = 1
        ),
        Achievement(
            id = "ach-network-builder",
            title = "Network Builder",
            description = "Built or inspected a cloud VPC and subnet architecture.",
            icon = "Hub",
            requiredType = "LABS",
            requiredCount = 2
        ),
        Achievement(
            id = "ach-cloud-fundamentals",
            title = "Cloud Fundamentals Complete",
            description = "Mastered the core foundations of cloud compute, storage, and networking.",
            icon = "CloudDone",
            requiredType = "LESSONS",
            requiredCount = 4
        ),
        Achievement(
            id = "ach-container-builder",
            title = "Container Builder",
            description = "Provisioned Docker containers and explored image isolation.",
            icon = "Inventory2",
            requiredType = "LABS",
            requiredCount = 4
        ),
        Achievement(
            id = "ach-kubernetes-explorer",
            title = "Kubernetes Explorer",
            description = "Mastered Pods, control plane components, and kubectl orchestration.",
            icon = "Apps",
            requiredType = "LABS",
            requiredCount = 6
        ),
        Achievement(
            id = "ach-security-learner",
            title = "Security Architect",
            description = "Configured IAM policies enforcing the principle of least privilege.",
            icon = "Security",
            requiredType = "LABS",
            requiredCount = 8
        ),
        Achievement(
            id = "ach-devops-builder",
            title = "DevOps Master",
            description = "Built continuous integration and infrastructure as code templates.",
            icon = "LinearScale",
            requiredType = "LABS",
            requiredCount = 10
        ),
        Achievement(
            id = "ach-architecture-designer",
            title = "Architecture Designer",
            description = "Designed a complete distributed system and passed automated review.",
            icon = "AccountTree",
            requiredType = "PROJECTS",
            requiredCount = 1
        ),
        Achievement(
            id = "ach-cloud-complete",
            title = "Cloud Engineer Complete",
            description = "Earned the official Course Completion Certificate by Awiskar Acharya.",
            icon = "EmojiEvents",
            requiredType = "CERTIFICATE",
            requiredCount = 1
        )
    )
}
