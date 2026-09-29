package com.example.data.repository

import com.example.data.model.CloudService

object CloudServicesData {
    val services: List<CloudService> = listOf(
        CloudService(
            id = "srv-compute-vm",
            name = "Virtual Machine Instances",
            category = "Compute",
            description = "Resizable virtual compute capacity in the cloud with custom vCPU and RAM allocations.",
            whenToUse = "Legacy applications, full OS root control, bespoke kernel modules, or long-running monolithic services.",
            architectureExample = "Web tier instances behind a layer 7 load balancer with auto-scaling triggers based on CPU utilization.",
            relatedServices = listOf("Auto Scaling", "Load Balancer", "Block Storage"),
            providerA = "Amazon EC2",
            providerB = "Azure Virtual Machines",
            providerC = "Google Compute Engine"
        ),
        CloudService(
            id = "srv-compute-k8s",
            name = "Managed Kubernetes",
            category = "Compute",
            description = "Fully managed Kubernetes control plane automating deployment, scaling, and management of containerized apps.",
            whenToUse = "Complex microservice architectures, polyglot backends, high portability across clouds, and fine-grained Pod autoscaling.",
            architectureExample = "Ingress controller routing traffic to frontend and backend service Pods distributed across multiple worker nodes.",
            relatedServices = listOf("Container Registry", "VPC", "Managed Prometheus"),
            providerA = "Amazon EKS",
            providerB = "Azure Kubernetes Service (AKS)",
            providerC = "Google Kubernetes Engine (GKE)"
        ),
        CloudService(
            id = "srv-compute-serverless",
            name = "Serverless Functions (FaaS)",
            category = "Compute",
            description = "Run code on demand without provisioning or managing servers, billed strictly per millisecond of compute time.",
            whenToUse = "Event handlers, asynchronous file processing (thumbnails), webhook consumers, and lightweight REST APIs.",
            architectureExample = "S3 file upload trigger invokes function which resizes images and writes metadata to a NoSQL database.",
            relatedServices = listOf("API Gateway", "Message Queue", "Object Storage"),
            providerA = "AWS Lambda",
            providerB = "Azure Functions",
            providerC = "Google Cloud Functions"
        ),
        CloudService(
            id = "srv-storage-object",
            name = "Object Storage",
            category = "Storage",
            description = "Highly durable, flat-namespace, key-value storage for unstructured data accessible via HTTP REST APIs.",
            whenToUse = "Static website hosting, user file uploads, backups, machine learning datasets, and media archives.",
            architectureExample = "Direct browser upload with pre-signed URLs, served through a global Content Delivery Network (CDN).",
            relatedServices = listOf("CDN", "Storage Lifecycles", "Serverless Functions"),
            providerA = "Amazon S3",
            providerB = "Azure Blob Storage",
            providerC = "Google Cloud Storage"
        ),
        CloudService(
            id = "srv-storage-block",
            name = "Block Storage Volumes",
            category = "Storage",
            description = "High-performance network-attached block devices mounted to virtual machine instances as raw disk partitions.",
            whenToUse = "Database transaction logs, boot volumes, and filesystem workloads requiring sub-millisecond IOPS.",
            architectureExample = "Mounted as `/var/lib/mysql` to an EC2 instance with scheduled point-in-time snapshots.",
            relatedServices = listOf("Virtual Machine Instances", "Snapshot Backup"),
            providerA = "Amazon EBS",
            providerB = "Azure Managed Disks",
            providerC = "Google Persistent Disk"
        ),
        CloudService(
            id = "srv-db-relational",
            name = "Managed Relational Database",
            category = "Database",
            description = "Automated provisioning, backup, patching, and multi-AZ replication for PostgreSQL, MySQL, and MariaDB.",
            whenToUse = "Structured data requiring ACID transactional integrity, complex joins, and relational integrity constraints.",
            architectureExample = "Primary DB in Private Subnet A with synchronous standby in Private Subnet B and asynchronous read replicas.",
            relatedServices = listOf("VPC Private Subnet", "In-Memory Cache", "Database Proxy"),
            providerA = "Amazon RDS / Aurora",
            providerB = "Azure Database for PostgreSQL",
            providerC = "Cloud SQL / Spanner"
        ),
        CloudService(
            id = "srv-db-nosql",
            name = "Distributed NoSQL Database",
            category = "Database",
            description = "Low-latency document or key-value store offering single-digit millisecond response times at massive scale.",
            whenToUse = "User session states, shopping carts, gaming leaderboards, real-time IoT event ingestion, and dynamic product catalogs.",
            architectureExample = "Partition key based lookups serving millions of concurrent requests without locking bottlenecks.",
            relatedServices = listOf("Serverless Functions", "API Gateway"),
            providerA = "Amazon DynamoDB",
            providerB = "Azure Cosmos DB",
            providerC = "Google Firestore / Bigtable"
        ),
        CloudService(
            id = "srv-db-cache",
            name = "In-Memory Cache",
            category = "Database",
            description = "Ultra-fast Redis or Memcached clusters storing ephemeral keys in RAM for microsecond data retrieval.",
            whenToUse = "Offloading heavy read queries from relational databases, rate-limiting counters, and real-time pub/sub messaging.",
            architectureExample = "Application checks Redis for cached query response; falls back to relational DB on cache miss (Cache-Aside).",
            relatedServices = listOf("Managed Relational Database", "Virtual Machines"),
            providerA = "Amazon ElastiCache",
            providerB = "Azure Cache for Redis",
            providerC = "Google Cloud Memorystore"
        ),
        CloudService(
            id = "srv-net-loadbalancer",
            name = "Application Load Balancer",
            category = "Networking",
            description = "Layer 7 traffic distributor routing HTTP/HTTPS requests across healthy backend target groups based on paths/hosts.",
            whenToUse = "High availability, SSL/TLS termination, canary traffic routing, and health checking across fleets of instances.",
            architectureExample = "Distributes incoming traffic across 4 web instances across 2 Availability Zones; terminates TLS certificate.",
            relatedServices = listOf("Virtual Machines", "Auto Scaling", "SSL Certificate Manager"),
            providerA = "AWS ALB",
            providerB = "Azure Application Gateway",
            providerC = "Google Cloud Load Balancing"
        ),
        CloudService(
            id = "srv-net-cdn",
            name = "Content Delivery Network (CDN)",
            category = "Networking",
            description = "Globally distributed network of edge cache locations caching static and streaming content close to end users.",
            whenToUse = "Minimizing latency for global users, reducing origin server load, and shielding against DDoS attacks.",
            architectureExample = "Caches CSS, JS, and image assets at 300+ global edge locations; routes API requests to origin with HTTP keep-alive.",
            relatedServices = listOf("Object Storage", "DNS", "Web Application Firewall"),
            providerA = "Amazon CloudFront",
            providerB = "Azure CDN",
            providerC = "Google Cloud CDN"
        ),
        CloudService(
            id = "srv-sec-iam",
            name = "Identity & Access Management (IAM)",
            category = "Security",
            description = "Centralized identity governance controlling authentication and granular authorization across all cloud resources.",
            whenToUse = "Managing engineer credentials, federated SSO, and provisioning temporary execution roles for compute resources.",
            architectureExample = "EC2 instance assigned an IAM Role allowing read-only access to S3 without baking secrets into the codebase.",
            relatedServices = listOf("Key Management Service", "Secrets Manager"),
            providerA = "AWS IAM",
            providerB = "Microsoft Entra ID (Azure AD)",
            providerC = "Google Cloud IAM"
        ),
        CloudService(
            id = "srv-devops-cicd",
            name = "Managed CI/CD Pipeline",
            category = "DevOps",
            description = "Automated continuous integration and delivery service compiling code, running tests, and executing canary deployments.",
            whenToUse = "Eliminating manual SSH deployments, enforcing automated testing gates, and achieving zero-downtime releases.",
            architectureExample = "Git push triggers container image build, vulnerability scans, integration tests, and blue/green production deploy.",
            relatedServices = listOf("Container Registry", "Kubernetes", "Secrets Manager"),
            providerA = "AWS CodePipeline",
            providerB = "Azure DevOps / Pipelines",
            providerC = "Google Cloud Build"
        )
    )
}
