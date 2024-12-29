resource "aws_instance" "server1" {
  ami               = var.amazon_linux
  instance_type     = var.instance_type
  subnet_id         = var.private_subnet1_id
  vpc_security_group_ids = [var.security_group_id]
  availability_zone = var.az_1
  key_name          = var.public_key_name

  tags = {
    Name = "${var.project_name}-server1"
  }
}

resource "aws_instance" "server2" {
  ami               = var.amazon_linux
  instance_type     = var.instance_type
  subnet_id         = var.private_subnet1_id
  vpc_security_group_ids = [var.security_group_id]
  availability_zone = var.az_1
  key_name          = var.public_key_name

  tags = {
    Name = "${var.project_name}-server2"
  }
}

resource "aws_instance" "server3" {
  ami               = var.amazon_linux
  instance_type     = var.instance_type
  subnet_id         = var.private_subnet2_id
  vpc_security_group_ids = [var.security_group_id]
  availability_zone = var.az_2
  key_name          = var.public_key_name

  tags = {
    Name = "${var.project_name}-server3"
  }
}

resource "aws_instance" "server4" {
  ami               = var.amazon_linux
  instance_type     = var.instance_type
  subnet_id         = var.private_subnet2_id
  vpc_security_group_ids = [var.security_group_id]
  availability_zone = var.az_2
  key_name          = var.public_key_name

  tags = {
    Name = "${var.project_name}-server4"
  }
}
