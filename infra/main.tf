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
}

module "key_pair" {
  source = "./modules/key-pair"
}

module "security_group" {
  source = "./modules/security-group"
  vpc_id = module.vpc.vpc_id
}
