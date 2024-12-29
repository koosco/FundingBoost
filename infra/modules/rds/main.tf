resource "aws_db_instance" "this" {
  identifier = var.project_name
  engine = "mysql"
  instance_class = var.instance_class
  allocated_storage = var.allocated_storage
  username = var.username
  password = var.password
  vpc_security_group_ids = var.vpc_security_group_ids
  db_subnet_group_name = aws_db_subnet_group.this.name

  skip_final_snapshot = true
  final_snapshot_identifier = "${var.project_name}-final-snapshot"
}

resource "aws_db_subnet_group" "this" {
  name = "${var.project_name}-subnet-group"
  subnet_ids = var.subnet_ids

  tags = {
    Name = "${var.project_name}-db-subnet-group"
  }
}
