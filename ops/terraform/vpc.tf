module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "~> 5.0"

  name = local.name

  cidr             = "10.0.0.0/16"
  private_subnets  = ["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
  public_subnets   = ["10.0.101.0/24", "10.0.102.0/24", "10.0.103.0/24"]
  database_subnets = ["10.0.201.0/24", "10.0.202.0/24", "10.0.203.0/24"]
  azs              = ["${data.aws_region.this.name}a", "${data.aws_region.this.name}b", "${data.aws_region.this.name}c"]

  enable_nat_gateway   = true
  single_nat_gateway   = true
  enable_dns_hostnames = true

  manage_default_security_group = false
  manage_default_route_table    = false
  manage_default_network_acl    = false

  map_public_ip_on_launch = true
}
