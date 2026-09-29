package com.example.data.repository

import com.example.data.model.*

object CloudCoursesData {
    val courses: List<Course> = listOf(
        Course(
            id = "course-1",
            number = 1,
            title = "Computer Fundamentals",
            description = "Master how computer hardware, OS processes, memory, and storage interact to execute software.",
            level = "Level 1",
            iconName = "Computer",
            lessons = listOf(
                Lesson(
                    id = "c1-l1",
                    courseId = "course-1",
                    title = "How Computers Work: CPU, RAM & Storage",
                    durationMinutes = 8,
                    summary = "Understand the fundamental trio of compute: execution (CPU), volatile state (RAM), and persistent disk storage.",
                    sections = listOf(
                        LessonSection(
                            title = "The Von Neumann Architecture",
                            content = "Every computer and cloud server relies on three essential hardware elements:\n\n1. Central Processing Unit (CPU): The arithmetic and logic engine executing instructions fetched from memory.\n2. Random Access Memory (RAM): Ultra-fast volatile memory directly addressable by the CPU to store running applications.\n3. Secondary Storage (SSD/NVMe): Non-volatile block storage where files, binaries, and databases reside when powered down.\n\nIn cloud computing, these physical resources are partitioned into virtual slices (vCPUs and virtual memory) allocated to your virtual machines.",
                            codeSnippet = "# In Linux, view hardware specs:\nlscpu\nfree -h\nlsblk"
                        ),
                        LessonSection(
                            title = "Processes, Threads & Execution Contexts",
                            content = "An Operating System (OS) organizes programs into processes. Each process owns an isolated virtual memory space, file handles, and one or more threads of execution.\n\nThe OS scheduler rapidly cycles CPU cores among hundreds of threads, creating the illusion of parallel execution."
                        )
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "q1-1",
                            question = "Why is RAM considered volatile memory in cloud servers?",
                            options = listOf(
                                "It requires continuous power; data disappears when the system reboots",
                                "It is slower than hard disk drives",
                                "It cannot be partitioned into virtual machines",
                                "It is only accessible over the public internet"
                            ),
                            correctIndex = 0,
                            explanation = "RAM stores data electrically in dynamic capacitors, so all contents vanish upon power cycling, requiring persistent disk storage."
                        )
                    )
                ),
                Lesson(
                    id = "c1-l2",
                    courseId = "course-1",
                    title = "Operating Systems, Clients & Servers",
                    durationMinutes = 10,
                    summary = "Explore how client machines request services over network protocols from headless server operating systems.",
                    sections = listOf(
                        LessonSection(
                            title = "The Client-Server Model",
                            content = "A server is simply a computer running an OS (predominantly Linux in the cloud) optimized for background daemons, high network throughput, and uptime without a graphical desktop (GUI).\n\nClients (browsers, mobile apps, or other servers) initiate requests across standard protocols like HTTP, TCP, or gRPC."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-2",
            number = 2,
            title = "Networking Fundamentals",
            description = "Explore IP addressing, DNS resolution, TCP/UDP sockets, subnets, CIDR notation, and network routing.",
            level = "Level 1",
            iconName = "Router",
            lessons = listOf(
                Lesson(
                    id = "c2-l1",
                    courseId = "course-2",
                    title = "IP Addressing, IPv4 vs IPv6 & Subnets",
                    durationMinutes = 12,
                    summary = "Learn how internet protocol addresses route packets across worldwide networks and private subnets.",
                    interactiveLabType = "SUBNET_CALC",
                    sections = listOf(
                        LessonSection(
                            title = "IPv4 Addresses and CIDR Notation",
                            content = "An IPv4 address consists of 32 bits arranged in 4 octets (e.g. 192.168.1.1). CIDR (Classless Inter-Domain Routing) uses a slash suffix to indicate network prefix length:\n\n• /24 = 256 addresses (254 usable)\n• /16 = 65,536 addresses (65,534 usable)\n• /28 = 16 addresses (e.g. 11 usable in AWS VPC because 5 addresses are reserved for network, router, DNS, future use, and broadcast)."
                        )
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "q2-1",
                            question = "In standard IPv4 CIDR notation, how many total addresses are in a /24 subnet?",
                            options = listOf("128", "256", "512", "1024"),
                            correctIndex = 1,
                            explanation = "32 total bits - 24 prefix bits = 8 host bits. 2^8 = 256 addresses."
                        )
                    )
                ),
                Lesson(
                    id = "c2-l2",
                    courseId = "course-2",
                    title = "TCP, UDP, Ports & Firewalls",
                    durationMinutes = 11,
                    summary = "Understand reliable connection-oriented TCP vs lightweight connectionless UDP.",
                    sections = listOf(
                        LessonSection(
                            title = "Ports and Layer 4 Transport",
                            content = "Ports distinguish different services on the same IP. Standard ports include:\n• 80: HTTP\n• 443: HTTPS\n• 22: SSH\n• 53: DNS\n• 3306: MySQL\n• 5432: PostgreSQL\n• 6379: Redis"
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-3",
            number = 3,
            title = "Linux for Cloud",
            description = "Master the backbone of cloud infrastructure: command line, file systems, permissions, SSH, and systemd services.",
            level = "Level 2",
            iconName = "Terminal",
            lessons = listOf(
                Lesson(
                    id = "c3-l1",
                    courseId = "course-3",
                    title = "The Linux Filesystem & Essential Navigation",
                    durationMinutes = 15,
                    summary = "Navigate root `/`, `/etc` configs, `/var/log` telemetry, and manage files with bash commands.",
                    interactiveLabType = "LINUX_TERMINAL",
                    sections = listOf(
                        LessonSection(
                            title = "Linux Directory Hierarchy",
                            content = "Linux treats everything as a file stream. Key directories:\n• `/etc`: System configuration files (nginx.conf, systemd)\n• `/var/log`: System and application logs\n• `/home`: User workspaces\n• `/bin` & `/usr/bin`: Executable program binaries\n• `/dev`: Device representations",
                            codeSnippet = "$ pwd\n$ ls -la\n$ mkdir -p app/configs\n$ touch app/configs/server.env\n$ cat /etc/os-release"
                        )
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "q3-1",
                            question = "Where are system-wide configuration files stored in standard Linux?",
                            options = listOf("/etc", "/var", "/bin", "/tmp"),
                            correctIndex = 0,
                            explanation = "/etc contains host-specific system configurations like networking, daemon configs, and cron jobs."
                        )
                    )
                ),
                Lesson(
                    id = "c3-l2",
                    courseId = "course-3",
                    title = "Permissions, Ownership & Systemd Daemons",
                    durationMinutes = 14,
                    summary = "Control access with chmod/chown and manage persistent background services with systemctl.",
                    interactiveLabType = "LINUX_TERMINAL",
                    sections = listOf(
                        LessonSection(
                            title = "Managing Daemons with Systemd",
                            content = "Modern cloud instances run systemd to supervise web servers, databases, and background tasks.",
                            codeSnippet = "# Check status, start, and enable on boot:\nsudo systemctl status nginx\nsudo systemctl restart app.service\nsudo journalctl -u app.service -f"
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-4",
            number = 4,
            title = "Cloud Computing Fundamentals",
            description = "Understand IaaS, PaaS, SaaS, elasticity, high availability, regions, availability zones, and shared responsibility.",
            level = "Level 2",
            iconName = "Cloud",
            lessons = listOf(
                Lesson(
                    id = "c4-l1",
                    courseId = "course-4",
                    title = "Why Cloud Exists: Elasticity vs Traditional Datacenters",
                    durationMinutes = 10,
                    summary = "Differentiate capital expenditure (CapEx) hardware purchasing from operational expenditure (OpEx) on-demand elasticity.",
                    sections = listOf(
                        LessonSection(
                            title = "The 5 Essential Characteristics of Cloud",
                            content = "Defined by NIST:\n1. On-Demand Self-Service: Provision compute in seconds via API.\n2. Broad Network Access: Standard internet access protocols.\n3. Resource Pooling: Multi-tenant underlying physical hardware.\n4. Rapid Elasticity: Scale resources dynamically with load spikes.\n5. Measured Service: Transparent pay-as-you-go billing."
                        )
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            id = "q4-1",
                            question = "What is the primary operational advantage of 'Rapid Elasticity'?",
                            options = listOf(
                                "Eliminating the need to write code",
                                "Automatically expanding and shrinking compute based on live workload",
                                "Guaranteeing zero latency worldwide",
                                "Removing the need for database backups"
                            ),
                            correctIndex = 1,
                            explanation = "Elasticity lets you automatically scale out under high demand and scale down when traffic subsides to optimize costs."
                        )
                    )
                ),
                Lesson(
                    id = "c4-l2",
                    courseId = "course-4",
                    title = "Cloud Service Models: IaaS, PaaS, SaaS & FaaS",
                    durationMinutes = 12,
                    summary = "Analyze the breakdown of management responsibilities across Infrastructure, Platform, Software, and Functions.",
                    sections = listOf(
                        LessonSection(
                            title = "Shared Responsibility Model",
                            content = "• IaaS (e.g. EC2, Compute Engine): Provider manages physical servers & hypervisor; you manage OS, patching, runtimes, and apps.\n• PaaS (e.g. App Engine, Heroku): Provider manages OS and runtimes; you manage code and configs.\n• SaaS: Provider manages everything; you consume the application."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-5",
            number = 5,
            title = "Virtualization & Hypervisors",
            description = "Explore Type 1 vs Type 2 hypervisors, vCPU allocation, memory ballooning, snapshots, and virtual networking.",
            level = "Level 3",
            iconName = "Layers",
            lessons = listOf(
                Lesson(
                    id = "c5-l1",
                    courseId = "course-5",
                    title = "Hypervisors: Bare Metal vs Hosted",
                    durationMinutes = 12,
                    summary = "Examine how hardware virtualization isolates multiple operating systems on a single physical host.",
                    interactiveLabType = "VM_PROVISIONING",
                    sections = listOf(
                        LessonSection(
                            title = "Type 1 (Bare-Metal) vs Type 2 Hypervisors",
                            content = "• Type 1 Hypervisor (KVM, Xen, ESXi): Runs directly on physical bare-metal hardware. Powers modern cloud providers for maximum I/O performance.\n• Type 2 Hypervisor (VirtualBox, VMware Workstation): Runs on top of a host OS."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-6",
            number = 6,
            title = "Cloud Compute",
            description = "Deploy auto-scaling groups, configure load balancers, define health checks, and build stateless architectures.",
            level = "Level 3",
            iconName = "Memory",
            lessons = listOf(
                Lesson(
                    id = "c6-l1",
                    courseId = "course-6",
                    title = "Virtual Machines, Instance Types & Auto Scaling",
                    durationMinutes = 14,
                    summary = "Select optimal instance families (Compute, Memory, Storage optimized) and configure dynamic horizontal scaling.",
                    interactiveLabType = "VM_PROVISIONING",
                    sections = listOf(
                        LessonSection(
                            title = "Horizontal vs Vertical Scaling",
                            content = "• Vertical Scaling (Scale Up): Adding bigger CPU/RAM to a single VM. Has strict limits and downtime.\n• Horizontal Scaling (Scale Out): Launching identical stateless instances behind a Load Balancer to handle unlimited incoming traffic."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-7",
            number = 7,
            title = "Cloud Storage",
            description = "Deep dive into Object storage (S3), Block volumes (EBS), File systems (EFS/NFS), storage tiers, and lifecycles.",
            level = "Level 3",
            iconName = "Storage",
            lessons = listOf(
                Lesson(
                    id = "c7-l1",
                    courseId = "course-7",
                    title = "Object Storage vs Block Storage vs File Storage",
                    durationMinutes = 14,
                    summary = "Compare flat REST-addressable bucket objects against mountable block devices and network file systems.",
                    sections = listOf(
                        LessonSection(
                            title = "Storage Architectures Compared",
                            content = "1. Object Storage (AWS S3, GCS, Azure Blob): Infinitely scalable key-value storage. Unstructured files (images, backups, videos) accessed via HTTP GET/PUT.\n2. Block Storage (EBS, Persistent Disk): Raw virtual hard drives formatted with ext4/XFS and mounted to a single VM.\n3. File Storage (EFS, Azure Files): Managed shared NFS/SMB filesystems mounted concurrently across hundreds of VMs."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-8",
            number = 8,
            title = "Cloud Databases",
            description = "Relational SQL vs NoSQL document/key-value, managed DB clusters, read replicas, multi-AZ failover, and caching.",
            level = "Level 3",
            iconName = "Database",
            lessons = listOf(
                Lesson(
                    id = "c8-l1",
                    courseId = "course-8",
                    title = "SQL vs NoSQL in Distributed Environments",
                    durationMinutes = 15,
                    summary = "Understand relational ACID transactions vs horizontally scalable NoSQL eventual consistency.",
                    sections = listOf(
                        LessonSection(
                            title = "Read Replicas and Multi-AZ Clustering",
                            content = "In high-traffic architectures:\n• Primary Database: Accepts all write transactions and replicates synchronously to a standby in a second Availability Zone.\n• Read Replicas: Asynchronously replicate from the primary to offload heavy read queries from web users."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-9",
            number = 9,
            title = "Cloud Networking & VPC",
            description = "Architect isolated Virtual Private Clouds, private/public subnets, route tables, Internet Gateways, and NAT.",
            level = "Level 4",
            iconName = "Hub",
            lessons = listOf(
                Lesson(
                    id = "c9-l1",
                    courseId = "course-9",
                    title = "VPC Architecture: Public & Private Subnets",
                    durationMinutes = 16,
                    summary = "Design secure multi-tier networking where databases remain private and web tiers connect via Internet Gateways.",
                    interactiveLabType = "VPC_BUILDER",
                    sections = listOf(
                        LessonSection(
                            title = "Public Subnet vs Private Subnet",
                            content = "• Public Subnet: Its route table points `0.0.0.0/0` directly to an Internet Gateway (IGW). Instances receive public IP addresses.\n• Private Subnet: Has no direct internet route. Outbound requests (e.g. security patches) pass through a NAT Gateway located in the public subnet."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-10",
            number = 10,
            title = "Domain Name System (DNS)",
            description = "Trace DNS queries through Recursive Resolvers, Root servers, TLD servers, and Authoritative nameservers.",
            level = "Level 4",
            iconName = "Dns",
            lessons = listOf(
                Lesson(
                    id = "c10-l1",
                    courseId = "course-10",
                    title = "The 8-Step DNS Resolution Process",
                    durationMinutes = 12,
                    summary = "Understand how human domain names translate into machine IP addresses with TTL caching.",
                    interactiveLabType = "DNS_VISUALIZER",
                    sections = listOf(
                        LessonSection(
                            title = "Common DNS Record Types",
                            content = "• A Record: Maps domain to IPv4 address\n• AAAA Record: Maps domain to IPv6 address\n• CNAME: Canonical name alias pointing to another domain name\n• MX: Mail exchange servers\n• TXT: Verification and security (SPF, DKIM)"
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-11",
            number = 11,
            title = "Security & IAM",
            description = "Implement Identity and Access Management, least privilege policies, roles, STS tokens, encryption, and firewalls.",
            level = "Level 4",
            iconName = "Security",
            lessons = listOf(
                Lesson(
                    id = "c11-l1",
                    courseId = "course-11",
                    title = "Principles of Least Privilege & IAM Policies",
                    durationMinutes = 15,
                    summary = "Enforce precise JSON IAM policies granting only strictly necessary actions, resources, and conditions.",
                    interactiveLabType = "IAM_POLICY",
                    sections = listOf(
                        LessonSection(
                            title = "Users, Groups, Roles & Policies",
                            content = "• Users: Permanent human identities\n• Roles: Temporary assume-role credentials for compute resources (e.g. giving an EC2 instance permission to read an S3 bucket without embedding API keys in code)\n• Policies: JSON documents declaring Effect (Allow/Deny), Action, Resource, and Condition"
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-12",
            number = 12,
            title = "Containers & Docker",
            description = "Understand Linux cgroups, namespaces, UnionFS layer caching, container lifecycles, and multi-stage Dockerfiles.",
            level = "Level 5",
            iconName = "Inventory2",
            lessons = listOf(
                Lesson(
                    id = "c12-l1",
                    courseId = "course-12",
                    title = "Containers vs Virtual Machines",
                    durationMinutes = 14,
                    summary = "Discover why containers share the host Linux kernel while maintaining isolated process and network namespaces.",
                    interactiveLabType = "DOCKER_CONTAINER",
                    sections = listOf(
                        LessonSection(
                            title = "How Containers Work Internally",
                            content = "Unlike a VM which boots an entire guest operating system kernel, containers are simply isolated Linux processes using:\n1. Namespaces: Isolates process IDs (pid), network interfaces (net), mount points (mnt), and user accounts (user).\n2. Control Groups (cgroups): Meters and restricts CPU, memory, and I/O consumption per container.\n3. OverlayFS: Stackable read-only filesystem layers with a thin writable container layer on top."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-13",
            number = 13,
            title = "Docker in Production",
            description = "Build minimal distroless images, configure volume mounts, bridge networks, and multi-container Docker Compose.",
            level = "Level 5",
            iconName = "Widgets",
            lessons = listOf(
                Lesson(
                    id = "c13-l1",
                    courseId = "course-13",
                    title = "Multi-Stage Dockerfiles & Layer Optimization",
                    durationMinutes = 15,
                    summary = "Compile binaries in build stages and ship minimal runtime artifacts for security and fast deployment.",
                    sections = listOf(
                        LessonSection(
                            title = "Writing a Clean Multi-Stage Dockerfile",
                            content = "Separate build dependencies from production execution:",
                            codeSnippet = "FROM golang:1.22-alpine AS builder\nWORKDIR /app\nCOPY . .\nRUN go build -o server .\n\nFROM alpine:3.19\nWORKDIR /app\nCOPY --from=builder /app/server .\nEXPOSE 8080\nCMD [\"./server\"]"
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-14",
            number = 14,
            title = "Kubernetes & Container Orchestration",
            description = "Master Pods, ReplicaSets, Deployments, Services (ClusterIP/NodePort/LoadBalancer), Ingress, and ConfigMaps.",
            level = "Level 6",
            iconName = "Apps",
            lessons = listOf(
                Lesson(
                    id = "c14-l1",
                    courseId = "course-14",
                    title = "Kubernetes Architecture: Control Plane & Nodes",
                    durationMinutes = 18,
                    summary = "Explore how API Server, etcd, Scheduler, and Controller Manager supervise Kubelet agent nodes.",
                    interactiveLabType = "KUBERNETES",
                    sections = listOf(
                        LessonSection(
                            title = "Control Plane and Worker Node Components",
                            content = "• kube-apiserver: The JSON/REST gateway evaluating and storing cluster state.\n• etcd: Highly-consistent distributed key-value store holding cluster configuration.\n• kube-scheduler: Assigns unscheduled Pods to suitable worker nodes.\n• kubelet: Agent running on each node executing container specs via CRI."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-15",
            number = 15,
            title = "Serverless Architectures",
            description = "Event-driven functions (FaaS), trigger sources, cold start mitigation, stateless execution, and API Gateway integration.",
            level = "Level 6",
            iconName = "ElectricBolt",
            lessons = listOf(
                Lesson(
                    id = "c15-l1",
                    courseId = "course-15",
                    title = "Function as a Service & Event-Driven Patterns",
                    durationMinutes = 12,
                    summary = "Execute code without managing instances, paying purely for compute duration down to milliseconds.",
                    sections = listOf(
                        LessonSection(
                            title = "Understanding Cold Starts",
                            content = "When an event arrives and no microVM is warm, the provider must allocate a runtime sandbox, download code, initialize dependencies, and invoke the handler. Keep initializations lean outside the handler function."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-16",
            number = 16,
            title = "Modern APIs & Microservices",
            description = "Design RESTful and gRPC endpoints, handle status codes, configure rate limiting, authentication, and CORS.",
            level = "Level 6",
            iconName = "Api",
            lessons = listOf(
                Lesson(
                    id = "c16-l1",
                    courseId = "course-16",
                    title = "HTTP Methods, Status Codes & REST Conventions",
                    durationMinutes = 14,
                    summary = "Implement idempotent GET/PUT/DELETE vs state-mutating POST requests with semantic response codes.",
                    interactiveLabType = "API_EXPLORER",
                    sections = listOf(
                        LessonSection(
                            title = "Standard Status Code Groups",
                            content = "• 2xx Success: 200 OK, 201 Created, 204 No Content\n• 3xx Redirection: 301 Moved Permanently, 304 Not Modified\n• 4xx Client Error: 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 429 Too Many Requests\n• 5xx Server Error: 500 Internal Error, 502 Bad Gateway, 503 Service Unavailable, 504 Gateway Timeout"
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-17",
            number = 17,
            title = "DevOps Culture & Practices",
            description = "Eliminate silos, automate feedback loops, embrace infrastructure as code, and shift security left.",
            level = "Level 7",
            iconName = "Sync",
            lessons = listOf(
                Lesson(
                    id = "c17-l1",
                    courseId = "course-17",
                    title = "The DevOps Lifecycle & Continuous Improvement",
                    durationMinutes = 12,
                    summary = "Plan → Code → Build → Test → Release → Deploy → Operate → Monitor → Feedback.",
                    sections = listOf(
                        LessonSection(
                            title = "DevOps Principles",
                            content = "DevOps is not a job title but a set of practices combining software development and IT operations to shorten system development lifecycles while delivering features, fixes, and updates frequently in close alignment with business objectives."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-18",
            number = 18,
            title = "Git & Version Control Workflows",
            description = "Master branches, interactive rebase, pull requests, semantic versioning, and trunk-based deployment.",
            level = "Level 7",
            iconName = "CallSplit",
            lessons = listOf(
                Lesson(
                    id = "c18-l1",
                    courseId = "course-18",
                    title = "Branching Strategies: GitFlow vs Trunk-Based",
                    durationMinutes = 14,
                    summary = "Compare long-lived feature branches with rapid continuous integration on main branches.",
                    interactiveLabType = "LINUX_TERMINAL",
                    sections = listOf(
                        LessonSection(
                            title = "Trunk-Based Development in Cloud Teams",
                            content = "Modern high-performing teams merge small, frequent commits into the main trunk multiple times a day behind feature flags, triggering automated CI test suites."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-19",
            number = 19,
            title = "CI/CD Pipelines",
            description = "Automate continuous integration testing, security scanning, container builds, and canary/blue-green deployments.",
            level = "Level 7",
            iconName = "LinearScale",
            lessons = listOf(
                Lesson(
                    id = "c19-l1",
                    courseId = "course-19",
                    title = "Building a Zero-Downtime Deployment Pipeline",
                    durationMinutes = 16,
                    summary = "Construct multi-stage pipelines validating linting, unit tests, vulnerability scanning, and gradual traffic shifting.",
                    interactiveLabType = "CICD_PIPELINE",
                    sections = listOf(
                        LessonSection(
                            title = "Blue/Green vs Canary Deployments",
                            content = "• Blue/Green: Deploy new version (Green) alongside live version (Blue). Once tested, switch router/load balancer instantly. Quick rollback if errors occur.\n• Canary: Route 5% of user traffic to new version; monitor metrics for 15 minutes; then ramp up to 25%, 50%, and 100%."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-20",
            number = 20,
            title = "Infrastructure as Code (IaC)",
            description = "Define cloud architectures declaratively using Terraform-style code, manage state files, and prevent configuration drift.",
            level = "Level 8",
            iconName = "Code",
            lessons = listOf(
                Lesson(
                    id = "c20-l1",
                    courseId = "course-20",
                    title = "Declarative Infrastructure & State Management",
                    durationMinutes = 16,
                    summary = "Express desired cloud topology in code rather than clicking through provider web consoles.",
                    interactiveLabType = "IAC_PLAYGROUND",
                    sections = listOf(
                        LessonSection(
                            title = "Why Declarative IaC Matters",
                            content = "With declarative code, you describe the end state (e.g. 'I want 3 VMs and 1 Load Balancer'). The IaC engine compares current state against desired state, computes a diff execution plan, and creates or updates resources idempotently.",
                            codeSnippet = "resource \"cloud_vpc\" \"main\" {\n  cidr_block = \"10.0.0.0/16\"\n  name       = \"prod-vpc\"\n}\n\nresource \"cloud_subnet\" \"public\" {\n  vpc_id     = cloud_vpc.main.id\n  cidr_block = \"10.0.1.0/24\"\n}"
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-21",
            number = 21,
            title = "Monitoring & Observability",
            description = "Implement the three pillars: Metrics, Logs, and Traces. Configure Prometheus, Grafana dashboards, and alert thresholds.",
            level = "Level 8",
            iconName = "Analytics",
            lessons = listOf(
                Lesson(
                    id = "c21-l1",
                    courseId = "course-21",
                    title = "The Golden Signals of Distributed Systems",
                    durationMinutes = 15,
                    summary = "Monitor Latency, Traffic, Errors, and Saturation to detect incidents before customers report outages.",
                    interactiveLabType = "MONITORING_DASHBOARD",
                    sections = listOf(
                        LessonSection(
                            title = "The 4 Golden Signals",
                            content = "1. Latency: Time taken to service a request (measured in milliseconds across 50th, 95th, and 99th percentiles).\n2. Traffic: Demand on your system (HTTP requests per second or concurrent queries).\n3. Errors: Rate of requests that fail (e.g. HTTP 500s or database timeouts).\n4. Saturation: How full your service is (CPU, memory, disk I/O, or connection pool limits)."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-22",
            number = 22,
            title = "Cloud Architecture & High Availability",
            description = "Design multi-tier fault-tolerant systems, caching layers (CDN, Redis), message queues, and disaster recovery.",
            level = "Level 9",
            iconName = "AccountTree",
            lessons = listOf(
                Lesson(
                    id = "c22-l1",
                    courseId = "course-22",
                    title = "Designing Resilient Three-Tier Systems",
                    durationMinutes = 18,
                    summary = "Separate Presentation, Application Logic, and Data Tiers across multiple availability zones.",
                    interactiveLabType = "ARCHITECTURE_DESIGNER",
                    sections = listOf(
                        LessonSection(
                            title = "The Three-Tier Architecture",
                            content = "1. Web / Edge Tier: Global CDN and Application Load Balancer routing incoming traffic.\n2. Application / Compute Tier: Stateless auto-scaled containers or microVMs in private subnets.\n3. Data Tier: Multi-AZ relational database with read replicas and in-memory Redis caching."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-23",
            number = 23,
            title = "Cloud Cost Management & FinOps",
            description = "Optimize compute reservations, spot instances, egress network costs, storage lifecycles, and rightsizing.",
            level = "Level 9",
            iconName = "AttachMoney",
            lessons = listOf(
                Lesson(
                    id = "c23-l1",
                    courseId = "course-23",
                    title = "Understanding Cloud Cost Drivers",
                    durationMinutes = 14,
                    summary = "Analyze why inter-region data transfer, idle resources, and over-provisioned disks balloon cloud bills.",
                    interactiveLabType = "COST_CALCULATOR",
                    sections = listOf(
                        LessonSection(
                            title = "FinOps Best Practices",
                            content = "• Reserved Instances / Savings Plans: Commit to 1-3 year baseline usage for up to 72% discounts.\n• Spot / Preemptible VMs: Utilize spare cloud capacity with up to 90% savings for fault-tolerant batch workloads.\n• Lifecycle Rules: Automatically transition cold object storage to archive tiers after 90 days."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-24",
            number = 24,
            title = "Disaster Recovery & Business Continuity",
            description = "Define Recovery Point Objective (RPO) and Recovery Time Objective (RTO), backup strategies, and multi-region failovers.",
            level = "Level 9",
            iconName = "Autorenew",
            lessons = listOf(
                Lesson(
                    id = "c24-l1",
                    courseId = "course-24",
                    title = "RPO vs RTO and Disaster Recovery Strategies",
                    durationMinutes = 15,
                    summary = "Compare Backup & Restore, Pilot Light, Warm Standby, and Multi-Site Active/Active strategies.",
                    sections = listOf(
                        LessonSection(
                            title = "Disaster Recovery Spectrum",
                            content = "• Backup & Restore (Hours/Days RTO, High RPO): Lowest cost.\n• Pilot Light (Minutes RTO): Core data replicated continuously; compute scaled up upon disaster.\n• Warm Standby (Minutes RTO): Scaled-down production replica running 24/7.\n• Multi-Site Active/Active (Near-Zero RTO/RPO): Traffic distributed live across independent regions."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course-25",
            number = 25,
            title = "Cloud Security Architecture & Troubleshooting",
            description = "Investigate production incidents: investigate broken systems, DNS misconfigurations, security breaches, and latency spikes.",
            level = "Level 10",
            iconName = "BugReport",
            lessons = listOf(
                Lesson(
                    id = "c25-l1",
                    courseId = "course-25",
                    title = "Systematic Root Cause Troubleshooting",
                    durationMinutes = 20,
                    summary = "Follow the systematic OSI layer-by-layer troubleshooting methodology from DNS down to database deadlocks.",
                    interactiveLabType = "INCIDENT_SIMULATOR",
                    sections = listOf(
                        LessonSection(
                            title = "Incident Response Playbook",
                            content = "When an alert fires:\n1. Triage & Mitigate: Stop user-facing impact (failover to secondary region or roll back recent commit).\n2. Gather Telemetry: Correlate error rates, latency spikes, and server logs.\n3. Identify Root Cause: Isolate code bug, database connection saturation, or upstream DNS outage.\n4. Blameless Post-Mortem: Document what happened, why, and preventive action items."
                        )
                    )
                )
            )
        )
    )
}
