resource "aws_ecs_cluster" "kunlaquota-cluster" {
  name = "KunlaQuotaCluster"

  tags = {
    Owner: "TimS"
    Name: "tims-kunlaquota-ecs-cluster"
  }
}

