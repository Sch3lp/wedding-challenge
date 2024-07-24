resource "aws_instance" "this" {
  ami       = data.aws_ami.this.id
  subnet_id = module.vpc.private_subnets[0]

  vpc_security_group_ids = [aws_security_group.this.id]

  iam_instance_profile = aws_iam_instance_profile.this.name

  root_block_device {
    volume_size           = "8"
    volume_type           = "standard"
    encrypted             = true
    delete_on_termination = false
  }

  instance_type = "t3a.medium"

  lifecycle {
    ignore_changes = [ami]
  }
}

#####
# IAM
#####
resource "aws_iam_instance_profile" "this" {
  name = local.name
  role = aws_iam_role.this.name
}

resource "aws_iam_role" "this" {
  name               = local.name
  description        = "TF: Role for jumphost to work with AWS SSM"
  assume_role_policy = data.aws_iam_policy_document.assume.json
}

data "aws_iam_policy_document" "assume" {
  statement {
    effect  = "Allow"
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["ec2.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy_attachment" "ssm" {
  role       = aws_iam_role.this.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_role_policy_attachment" "ecr" {
  role       = aws_iam_role.this.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
}

#####
# Security group
#####
resource "aws_security_group" "this" {
  name   = local.name
  vpc_id = module.vpc.vpc_id

  ingress {
    description = "TF: Allow SSH"
    protocol    = "tcp"
    from_port   = 22
    to_port     = 22
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description     = "TF: Allow HTTP from the NLB"
    protocol        = "tcp"
    from_port       = 80
    to_port         = 80
    security_groups = [module.nlb.security_group_id]
  }

  egress {
    description = "TF: Allow all outbound traffic"
    protocol    = "all"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
}
