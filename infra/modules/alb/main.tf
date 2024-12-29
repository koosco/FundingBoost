resource "aws_lb" "main" {
  name                       = "funding-boost-alb"
  internal                   = false
  load_balancer_type         = "application"
  security_groups            = var.security_group_ids
  subnets                    = var.subnet_ids
  enable_deletion_protection = false

  tags = {
    Name = "${var.project_name}-alb"
  }
}

resource "aws_lb_target_group" "main" {
  name     = "funding-boost-target-group"
  port     = var.server_port
  protocol = "HTTP"
  vpc_id   = var.vpc_id

  health_check {
    path                = "/api"
    protocol            = "HTTP"
    interval            = 30
    timeout             = 30
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }
}

resource "aws_lb_listener" "main" {
  load_balancer_arn = aws_lb.main.arn
  port              = var.server_port
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.main.arn
  }
}

resource "aws_lb_target_group_attachment" "server1" {
  target_group_arn = aws_lb_target_group.main.arn
  target_id        = var.target_instance_ids[0]
  port             = var.server_port
}

resource "aws_lb_target_group_attachment" "server2" {
  target_group_arn = aws_lb_target_group.main.arn
  target_id        = var.target_instance_ids[1]
  port             = var.server_port
}

resource "aws_lb_target_group_attachment" "server3" {
  target_group_arn = aws_lb_target_group.main.arn
  target_id        = var.target_instance_ids[2]
  port             = var.server_port
}

resource "aws_lb_target_group_attachment" "server4" {
  target_group_arn = aws_lb_target_group.main.arn
  target_id        = var.target_instance_ids[3]
  port             = var.server_port
}

