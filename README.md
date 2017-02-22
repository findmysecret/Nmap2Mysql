# Nmap2Mysql
Resolve the XML format of the NMAP scan results to the MYSQL database.

1.需准备mysql连接器驱动，版本无特定要求。（mysql-connector-java-5.1.39-bin.jar）
2.指定当前数据库用户，编辑JDBC.java中username、userpass
3.指定nmap xml路径，编辑Nmap2Mysql.java中path
