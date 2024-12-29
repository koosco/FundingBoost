variable "project_name" {}

variable "username" {
  default = "fundingboost"
}

variable "password" {
  default   = "fundingboost123"
  sensitive = true
}

variable "instance_class" {
  default = "db.t3.micro"
}

variable "allocated_storage" {
  default = 20
}

variable "vpc_security_group_ids" {}

variable "subnet_ids" {}
