resource "aws_instance" "server1" {
  ami               = "ami-048c8b90bfe9b49b8"
  instance_type     = "t2.micro"
  subnet_id         = var.private_subnet1_id
  vpc_security_group_ids = [var.security_group_id]
  availability_zone = "ap-northeast-2a"
  key_name          = var.public_key_name

  tags = {
    Name = "server1"
  }
}

resource "aws_instance" "server2" {
  ami               = "ami-048c8b90bfe9b49b8"
  instance_type     = "t2.micro"
  subnet_id         = var.private_subnet1_id
  vpc_security_group_ids = [var.security_group_id]
  availability_zone = "ap-northeast-2a"
  key_name          = var.public_key_name

  tags = {
    Name = "server2"
  }
}

resource "aws_instance" "server3" {
  ami               = "ami-048c8b90bfe9b49b8"
  instance_type     = "t2.micro"
  subnet_id         = var.private_subnet2_id
  vpc_security_group_ids = [var.security_group_id]
  availability_zone = "ap-northeast-2c"
  key_name          = var.public_key_name

  tags = {
    Name = "server3"
  }
}

resource "aws_instance" "server4" {
  ami               = "ami-048c8b90bfe9b49b8"
  instance_type     = "t2.micro"
  subnet_id         = var.private_subnet2_id
  vpc_security_group_ids = [var.security_group_id]
  availability_zone = "ap-northeast-2c"
  key_name          = var.public_key_name

  tags = {
    Name = "server4"
  }
}
