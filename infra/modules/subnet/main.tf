resource "aws_subnet" "private1" {
  vpc_id            = var.vpc_id
  cidr_block        = "10.0.1.0/24"
  availability_zone = var.az_1

  tags = {
    Name = "${var.project_name}-private-ec2-1"
  }
}

resource "aws_subnet" "private2" {
  vpc_id            = var.vpc_id
  cidr_block        = "10.0.2.0/24"
  availability_zone = var.az_2

  tags = {
    Name = "${var.project_name}-private-ec2-2"
  }
}

resource "aws_subnet" "private3" {
  vpc_id            = var.vpc_id
  cidr_block        = "10.0.3.0/24"
  availability_zone = var.az_1

  tags = {
    Name = "${var.project_name}-private-rds-1"
  }
}

resource "aws_subnet" "private4" {
  vpc_id            = var.vpc_id
  cidr_block        = "10.0.4.0/24"
  availability_zone = var.az_2

  tags = {
    Name = "${var.project_name}-private-rds-2"
  }
}

resource "aws_route_table_association" "private1" {
  subnet_id      = aws_subnet.private1.id
  route_table_id = var.route_table_id
}

resource "aws_route_table_association" "private2" {
  subnet_id      = aws_subnet.private2.id
  route_table_id = var.route_table_id
}

resource "aws_route_table_association" "private3" {
  subnet_id      = aws_subnet.private3.id
  route_table_id = var.route_table_id
}

resource "aws_route_table_association" "private4" {
  subnet_id      = aws_subnet.private4.id
  route_table_id = var.route_table_id
}
