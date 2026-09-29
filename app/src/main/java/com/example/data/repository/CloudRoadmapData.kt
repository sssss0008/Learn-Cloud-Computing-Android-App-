package com.example.data.repository

import com.example.data.model.*

object CloudRoadmapData {
    val roadmapSteps = listOf(
        "Computer Fundamentals" to "CPUs, memory, disk I/O, processes, and OS abstractions",
        "Networking Basics" to "OSI model, TCP/IP, sockets, ports, and packet routing",
        "Linux Fundamentals" to "Filesystems, permissions, shell scripting, and system services",
        "Cloud Computing Basics" to "IaaS, PaaS, SaaS, shared responsibility, and hyper-scale economics",
        "Virtualization" to "Hypervisors, vCPU scheduling, overlays, and virtual disk images",
        "Cloud Infrastructure" to "Regions, availability zones, edge points of presence, and backbone links",
        "Compute" to "Elastic virtual instances, bare metal, auto-scaling groups, and health checks",
        "Storage" to "S3-compatible object stores, block volumes (EBS), and NFS network shares",
        "Databases" to "ACID relational clusters, NoSQL key-value, document stores, and read replicas",
        "Networking & VPC" to "Software Defined Networks (SDN), subnets, route tables, and NAT gateways",
        "Security & IAM" to "Least privilege, role assumptions, STS tokens, encryption in transit/rest",
        "Containers" to "cgroups, namespaces, image layers, UnionFS, and container runtimes",
        "Kubernetes" to "Control plane, etcd, kubelet, Pods, Deployments, Services, and Ingress",
        "Serverless" to "MicroVM execution (Firecracker), event dispatchers, and state decoupling",
        "DevOps Principles" to "Continuous feedback loops, immutable infrastructure, and shift-left security",
        "CI/CD Pipelines" to "Automated artifact builds, integration testing, and canary deployments",
        "Infrastructure as Code" to "Declarative state machines, drift detection, and modular templates",
        "Monitoring & Observability" to "Three pillars: metrics, structured logs, and distributed trace spans",
        "Cloud Architecture" to "Well-Architected Framework: Reliability, security, cost, and latency",
        "Real Projects" to "Production multi-tier deployments, disaster failover drills, and zero-downtime releases",
        "Certification Preparation" to "Scenario-based architectural exam questions and knowledge drills",
        "CLOUD ENGINEER" to "Industry-ready professional designing resilient distributed cloud platforms"
    )
}
