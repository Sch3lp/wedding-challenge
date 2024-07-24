data "aws_region" "this" {
}

data "aws_route53_zone" "kunlabora_ninja" {
  name = "kunlabora.ninja"
}

data "aws_acm_certificate" "kunlabora_ninja" {
  domain = "kunlabora.ninja"
}

data "aws_ami" "this" {
  owners = ["099720109477"] # Canonical

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server*"]
  }

  most_recent = true
}
