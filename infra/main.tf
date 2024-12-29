provider "aws" {
  region = "ap-northeast-2"
}

module "vpc" {
  source = "./modules/vpc"
}

module "subnet" {
  source         = "./modules/subnet"
  vpc_id         = module.vpc.vpc_id
  route_table_id = module.vpc.route_table_id
  project_name   = var.project_name
  az_1           = var.az_1
  az_2           = var.az_2
}

module "key_pair" {
  source = "./modules/key-pair"
}

module "security_group" {
  source       = "./modules/security-group"
  vpc_id       = module.vpc.vpc_id
  project_name = var.project_name
}

module "ec2" {
  source             = "./modules/ec2"
  private_subnet1_id = module.subnet.private_subnet1_id
  private_subnet2_id = module.subnet.private_subnet2_id
  security_group_id  = module.security_group.security_group_id
  public_key_name    = module.key_pair.public_key_name
  project_name       = var.project_name
  az_1               = var.az_1
  az_2               = var.az_2
}

module "alb" {
  source = "./modules/alb"
  vpc_id = module.vpc.vpc_id
  subnet_ids = [
    module.subnet.private_subnet1_id,
    module.subnet.private_subnet2_id
  ]
  target_instance_ids = [
    module.ec2.server1_id,
    module.ec2.server2_id,
    module.ec2.server3_id,
    module.ec2.server4_id
  ]
  security_group_ids = [
    module.security_group.security_group_id
  ]
  project_name = var.project_name
}
