package com.example.data.repository

import com.example.data.model.CloudProject

object CloudProjectsData {
    val projects: List<CloudProject> = listOf(
        CloudProject(
            id = "proj-1",
            title = "Deploy a Highly Available Static Website",
            difficulty = "Beginner",
            timeEstimate = "45 mins",
            objective = "Host an optimized single-page web app with global CDN caching, custom domain SSL/TLS, and S3-compatible object storage.",
            concepts = listOf("Object Storage", "CDN Edge Locations", "DNS Records", "SSL/TLS Termination", "Bucket Policies"),
            architectureSummary = "User → Anycast DNS → Global CDN (Edge Cache) → Origin Object Storage Bucket (Private with OAC).",
            requirements = listOf(
                "Create private storage bucket with disabled public access",
                "Attach Origin Access Control (OAC) policy allowing only the CDN distribution",
                "Configure index.html and 404 error routing rules",
                "Verify HTTPS enforcement and edge TTL caching"
            ),
            steps = listOf(
                "Step 1: Provision an object storage bucket and upload compiled web assets (HTML, CSS, JS).",
                "Step 2: Create a Cloud CDN distribution pointing to the storage bucket origin.",
                "Step 3: Restrict bucket policy so only the CDN can read objects.",
                "Step 4: Configure DNS CNAME alias and provision free TLS certificate.",
                "Step 5: Verify global edge cache headers (X-Cache: Hit from cloudfront)."
            ),
            verificationPoints = listOf(
                "Storage bucket has block public access turned ON",
                "HTTPS redirect status code 301 is enabled",
                "Global response latency is under 50ms across all test regions"
            )
        ),
        CloudProject(
            id = "proj-2",
            title = "Build a Production Three-Tier Architecture",
            difficulty = "Intermediate",
            timeEstimate = "90 mins",
            objective = "Architect a resilient web application decoupling web presentation, API microservices, and a replicated database across two Availability Zones.",
            concepts = listOf("VPC", "Public/Private Subnets", "Application Load Balancer", "Auto Scaling Group", "Multi-AZ Database", "NAT Gateway"),
            architectureSummary = "Users → ALB in Public Subnets → EC2/Container fleet in Private App Subnets → Multi-AZ DB in Isolated Database Subnets.",
            requirements = listOf(
                "Deploy VPC with 6 subnets across 2 Availability Zones (2 Public, 2 App Private, 2 DB Isolated)",
                "Provision Internet Gateway for public ingress and NAT Gateway for private egress",
                "Configure ALB target groups with HTTP health check path `/healthz`",
                "Deploy primary relational database with automatic synchronous standby failover"
            ),
            steps = listOf(
                "Step 1: Create VPC `10.0.0.0/16` and partition subnets across AZ-a and AZ-b.",
                "Step 2: Place NAT Gateway in Public Subnet-a and configure route tables for Private Subnets.",
                "Step 3: Launch Auto Scaling Group with minimum 2 instances in Private Subnets.",
                "Step 4: Provision Managed PostgreSQL DB in Isolated Subnets with security group accepting traffic only on port 5432 from App tier.",
                "Step 5: Execute simulated AZ outage drill and verify automatic standby promotion."
            ),
            verificationPoints = listOf(
                "Database security group accepts connections exclusively from App instances",
                "Application instances survive manual termination via Auto Scaling replacement",
                "Zero packets are routable directly to database from the public internet"
            )
        ),
        CloudProject(
            id = "proj-3",
            title = "Containerize a Full-Stack Web Application",
            difficulty = "Intermediate",
            timeEstimate = "60 mins",
            objective = "Package frontend and backend services into minimal production Docker containers using multi-stage builds and docker-compose.",
            concepts = listOf("Dockerfile", "Multi-Stage Builds", "Distroless Images", "Bridge Networking", "Volume Mounts", "Environment Variables"),
            architectureSummary = "Nginx Reverse Proxy Container → Node/Go API Container → Redis Cache Container via Docker bridge network.",
            requirements = listOf(
                "Write optimized multi-stage Dockerfile keeping final image size under 50MB",
                "Configure non-root user execution inside container for defense-in-depth",
                "Set up Docker Compose orchestrating app, database, and local volume persistence"
            ),
            steps = listOf(
                "Step 1: Create build stage compiling binaries with static CGO_ENABLED=0.",
                "Step 2: Copy compiled binary into scratch or alpine minimal runtime layer.",
                "Step 3: Define non-privileged user and group in Dockerfile.",
                "Step 4: Write docker-compose.yml defining health checks and restart policies.",
                "Step 5: Inspect image layers with `docker history` and verify zero secret leakage."
            ),
            verificationPoints = listOf(
                "Image size is reduced by >80% compared to single-stage build",
                "Container runs as UID 10001 instead of root UID 0",
                "Healthcheck command passes consistently"
            )
        ),
        CloudProject(
            id = "proj-4",
            title = "Build an Event-Driven Serverless API",
            difficulty = "Intermediate",
            timeEstimate = "60 mins",
            objective = "Create a scalable serverless backend handling HTTP requests via API Gateway, executing serverless functions, and persisting to NoSQL.",
            concepts = listOf("Serverless Functions", "API Gateway", "NoSQL", "Event Triggers", "IAM Execution Roles", "Cold Start Optimization"),
            architectureSummary = "Client → HTTP API Gateway (JWT Auth) → Serverless Function (Lambda/Cloud Function) → Managed NoSQL Table.",
            requirements = listOf(
                "Configure API Gateway routes (GET /items, POST /items)",
                "Grant least-privilege IAM execution role allowing PutItem and Query only on specific table ARN",
                "Implement structured JSON error responses with status codes 200, 400, and 500"
            ),
            steps = listOf(
                "Step 1: Create NoSQL database table with `userId` partition key.",
                "Step 2: Write serverless handler function in Node or Python.",
                "Step 3: Attach IAM role granting `dynamodb:PutItem` on the target table ARN.",
                "Step 4: Create API Gateway HTTP endpoint and map routes to the function.",
                "Step 5: Run load test simulating 500 concurrent requests; verify zero server management required."
            ),
            verificationPoints = listOf(
                "Execution role has no wildcard `*` permissions",
                "Function scales automatically from 0 to 500 concurrent executions",
                "P95 latency remains under 80ms"
            )
        ),
        CloudProject(
            id = "proj-5",
            title = "Deploy a Resilient Kubernetes Cluster",
            difficulty = "Advanced",
            timeEstimate = "90 mins",
            objective = "Deploy an enterprise microservice on Kubernetes with rolling zero-downtime updates, horizontal pod autoscaling, and ingress routing.",
            concepts = listOf("Kubernetes Pods", "Deployments", "ClusterIP Service", "Ingress Controller", "HPA", "Liveness & Readiness Probes"),
            architectureSummary = "Ingress Controller → Service (ClusterIP) → 3x Replicated Pods across worker nodes with HPA scaling up to 10 Pods.",
            requirements = listOf(
                "Write Kubernetes Deployment manifest with 3 replicas and resource requests/limits",
                "Configure HTTP liveness probe (`/healthz`) and readiness probe (`/ready`)",
                "Define HorizontalPodAutoscaler targeting 70% average CPU utilization",
                "Perform rolling update to v2 with `maxUnavailable: 0` and zero dropped requests"
            ),
            steps = listOf(
                "Step 1: Write and apply `deployment.yaml` with container spec.",
                "Step 2: Create `service.yaml` exposing internal port 80 to cluster Pods.",
                "Step 3: Configure Ingress rule routing `api.example.com` to the service.",
                "Step 4: Apply HPA rule with min 3 and max 10 replicas.",
                "Step 5: Execute `kubectl set image deployment/app app=app:v2` and observe zero-downtime rolling update."
            ),
            verificationPoints = listOf(
                "Readiness probe delays traffic until container completes boot initialization",
                "Pod CPU/Memory limits prevent noisy neighbor starvation",
                "Rolling deployment completes with 100% HTTP 200 responses during curl loop"
            )
        ),
        CloudProject(
            id = "proj-6",
            title = "Architect an Automated CI/CD GitOps Pipeline",
            difficulty = "Advanced",
            timeEstimate = "75 mins",
            objective = "Build a continuous delivery pipeline triggered by Git commits that runs linter, tests, builds containers, scans vulnerabilities, and deploys.",
            concepts = listOf("GitOps", "Automated Testing", "Static Code Analysis", "Container Vulnerability Scan (Trivy)", "Canary Deployment"),
            architectureSummary = "Git Push → CI Runner → Unit Tests & Linter → Container Build → Security Scan → Canary Release (5% traffic).",
            requirements = listOf(
                "Define automated workflow file for GitHub Actions / GitLab CI",
                "Fail build immediately if unit tests fail or high-severity CVEs are discovered",
                "Tag container images with git commit SHA (no mutable `latest` tags)",
                "Deploy artifact automatically to staging and require approval for production"
            ),
            steps = listOf(
                "Step 1: Write CI workflow configuration with parallel test jobs.",
                "Step 2: Integrate static security analysis (SAST) and container vulnerability scanning.",
                "Step 3: Build and push immutable container image tagged with `git rev-parse --short HEAD`.",
                "Step 4: Update deployment manifest repository (GitOps style).",
                "Step 5: Simulate a security vulnerability and confirm pipeline halts before production."
            ),
            verificationPoints = listOf(
                "Build stops on critical CVE detection",
                "Only immutable tags are used in production manifests",
                "Zero manual SSH commands required for deployments"
            )
        )
    )
}
