output "ecs_cluster_name" {
  value = aws_ecs_cluster.kunlaquota-cluster.name
}

output "ecs_service_name" {
  value = aws_ecs_service.kunlaquota-service.name
}

output "alb_dns_name" {
  value = aws_lb.app_lb.dns_name
  description = "The DNS name of the load balancer"
}