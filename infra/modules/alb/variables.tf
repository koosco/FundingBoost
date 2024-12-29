variable "project_name" {}

variable "security_group_ids" {}

variable "subnet_ids" {}

variable "vpc_id" {}

variable "target_instance_ids" {
  type = list(string)
}

variable "server_port" {
    default = 80
}
