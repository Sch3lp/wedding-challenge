#####
# EIP
#####
resource "aws_eip" "this" {
  count = 3
}

#####
# NLB
#####
module "nlb" {
  source  = "terraform-aws-modules/alb/aws"
  version = "~> 9.0"

  name = local.name

  load_balancer_type = "network"

  vpc_id = module.vpc.vpc_id

  subnet_mapping = [for i, eip in aws_eip.this :
    {
      allocation_id = eip.id
      subnet_id     = module.vpc.public_subnets[i]
    }
  ]

  # Security Group
  security_group_ingress_rules = {
    http = {
      from_port   = 80
      to_port     = 80
      ip_protocol = "tcp"
      description = "TF: Allow HTTP traffic"
      cidr_ipv4   = "0.0.0.0/0"
    }
    https = {
      from_port   = 443
      to_port     = 443
      ip_protocol = "tcp"
      description = "TF: Allow HTTPS traffic"
      cidr_ipv4   = "0.0.0.0/0"
    }
  }
  security_group_egress_rules = {
    all = {
      ip_protocol = "-1"
      cidr_ipv4   = module.vpc.vpc_cidr_block
    }
  }

  listeners = {
    http = {
      port     = 80
      protocol = "TCP"
      forward = {
        target_group_key = "${local.name}"
      }
      tags = {
        Name = "${local.name}/http-https-redirect"
      }
    }
    https = {
      port            = 443
      protocol        = "TLS"
      certificate_arn = data.aws_acm_certificate.kunlabora_ninja.arn
      forward = {
        target_group_key = "${local.name}"
      }
      tags = {
        Name = "${local.name}/https-traffic"
      }
    }
  }

  target_groups = {
    "${local.name}" = {
      protocol             = "TCP"
      port                 = 80
      target_type          = "instance"
      target_id            = aws_instance.this.id
      deregistration_delay = 10
      health_check = {
        enabled             = true
        interval            = 30
        path                = "/actuator/health"
        port                = "traffic-port"
        healthy_threshold   = 3
        unhealthy_threshold = 3
        timeout             = 6
      }
    }
  }
}

resource "aws_route53_record" "a_wedding_challenge_kunlabora_ninja" {
  name    = local.name
  type    = "A"
  zone_id = data.aws_route53_zone.kunlabora_ninja.zone_id

  alias {
    name                   = module.nlb.dns_name
    zone_id                = module.nlb.zone_id
    evaluate_target_health = false
  }
}
