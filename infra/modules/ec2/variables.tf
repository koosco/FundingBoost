variable "project_name" {}

variable "private_subnet1_id" {}

variable "private_subnet2_id" {}

variable "security_group_id" {}

variable "public_key_name" {}

variable "az_1" {}

variable "az_2" {}

variable "instance_type" {
  default = "t2.micro"
}

variable "amazon_linux" {
  default = "ami-048c8b90bfe9b49b8"
}
