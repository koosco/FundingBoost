terraform {
  required_providers {
    tls = {
      source  = "hashicorp/tls"
      version = "4.0.5"
    }
  }
}
resource "tls_private_key" "this" {
  algorithm = "RSA"
  rsa_bits = 2048
}
resource "aws_key_pair" "public_key" {
  key_name = "public_key"
  public_key = tls_private_key.this.public_key_openssh
}

resource "local_file" "private_key" {
    content  = tls_private_key.this.private_key_pem
    filename = "private_key.pem"
}
