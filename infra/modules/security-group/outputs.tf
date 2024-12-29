output "security_group_id" {
  value = aws_security_group.allow_ssh.id
}

output "rds_security_group_id" {
  value = aws_security_group.rds_sg.id
}
